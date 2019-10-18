package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.delegate.LazyWithReceiver
import com.greenapple.glacia.utils.changeDim
import com.greenapple.glacia.world.GlaciaTeleporter
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.pattern.BlockPattern
import net.minecraft.world.dimension.DimensionType
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.world.gen.Heightmap
import net.minecraft.util.Hand
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemGroup
import net.minecraft.network.play.server.*
import net.minecraft.state.EnumProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.Direction
import net.minecraft.util.Rotation
import net.minecraft.util.math.*
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.Teleporter
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fml.hooks.BasicEventHooks

open class BlockGlaciaPortal(registryName: String, name: String) : BlockBase(registryName, name, null, Material.PORTAL, initializer) {

    companion object {
        val AXIS: EnumProperty<Direction.Axis> = BlockStateProperties.HORIZONTAL_AXIS
        private val X_AABB : VoxelShape = makeCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0)
        private val Z_AABB : VoxelShape = makeCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0)

        val initializer : Properties.() -> Unit = {
            hardnessAndResistance(-1.0F)
            sound(SoundType.GLASS)
            lightValue(11)
            noDrops()
            doesNotBlockMovement()
        }
    }

    init {
        this.defaultState = stateContainer.baseState.with(AXIS, Direction.Axis.X)
        renderLayer = BlockRenderLayer.TRANSLUCENT
        seeThroughGroup = true
        isTranslucent = true
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(AXIS)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState = defaultState.with(AXIS, context.placementHorizontalFacing.rotateY().axis)

    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape {
        return when (state.get<Direction.Axis>(AXIS) as Direction.Axis) {
            Direction.Axis.Z -> Z_AABB
            Direction.Axis.X -> X_AABB
            else -> X_AABB
        }
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return when (rot) {
            Rotation.COUNTERCLOCKWISE_90, Rotation.CLOCKWISE_90 -> when (state.get(AXIS)) {
                Direction.Axis.Z -> state.with(AXIS, Direction.Axis.X)
                Direction.Axis.X -> state.with(AXIS, Direction.Axis.Z)
                else -> state
            }
            else -> state
        }
    }

    override fun updatePostPlacement(stateIn: BlockState, facing: Direction, facingState: BlockState, worldIn: IWorld, currentPos: BlockPos, facingPos: BlockPos): BlockState {
        val axis1 = facing.axis
        val axis2 = stateIn.get(AXIS)
        val flag = axis2 !== axis1 && axis1.isHorizontal
        return if (!flag && facingState.block !== this && !Size(worldIn, currentPos, axis2).isValidPortalPlacement()) Blocks.AIR.defaultState else super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos)
    }

    fun isPortal(world: IWorld, pos: BlockPos): Size? = Size(world, pos, Direction.Axis.X).takeIf {it.isValid && it.portalBlockCount == 0}
            ?: Size(world, pos, Direction.Axis.Z).takeIf {it.isValid && it.portalBlockCount == 0}

    fun trySpawnPortal(worldIn: IWorld, pos: BlockPos): Boolean {
        isPortal(worldIn, pos)?.takeIf {!net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(worldIn, pos, it.netherPortalSize)}?.run {
            placePortalBlocks()
            true
        }
        return false
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, playerIn: PlayerEntity, hand: Hand, rts: BlockRayTraceResult): Boolean {
        if (!worldIn.isRemote) worldIn.server?.let {server ->
            val dimension =  if (worldIn.dimension.type.id == DimensionType.OVERWORLD.id) Glacia.DIMENSION.dimensionType else DimensionType.OVERWORLD
            val newWorld = server.getWorld(dimension)
            val newPos = newWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos)
            (playerIn as ServerPlayerEntity).changeDim(newPos, dimension) {glaciaTeleporter}
            return true
        }
        return false
    }

    override fun tick(state: BlockState, world: World, pos: BlockPos, random: java.util.Random) {
        super.tick(state, world, pos, random)
        (world as? ServerWorld)?.glaciaTeleporter?.tick(world.gameTime)
    }

    inner class Size(private val world: IWorld, private val pos: BlockPos, private val axis: Direction.Axis) {
        val rightDir: Direction
        val leftDir: Direction
        var portalBlockCount = 0; private set
        var bottomLeft: BlockPos? = null; private set
        var height = 0; private set
        var width = 0; private set

        val isValid; get() = this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21

        init {
            var newPos = pos
            if (axis === Direction.Axis.X) {
                this.leftDir = Direction.EAST
                this.rightDir = Direction.WEST
            } else {
                this.leftDir = Direction.NORTH
                this.rightDir = Direction.SOUTH
            }

            while (newPos.y > pos.y - 21 && newPos.y > 0 && this.isValidBlockType(world.getBlockState(newPos.down()))) {
                newPos = newPos.down()
            }

            val i = this.getDistanceUntilEdge(newPos, this.leftDir) - 1
            if (i >= 0) {
                this.bottomLeft = newPos.offset(this.leftDir, i)
                this.width = this.getDistanceUntilEdge(this.bottomLeft!!, this.rightDir)
                if (this.width < 2 || this.width > 21) {
                    this.bottomLeft = null
                    this.width = 0
                }
            }

            if (this.bottomLeft != null) {
                this.height = this.calculatePortalHeight()
            }

        }

        private fun getDistanceUntilEdge(pos: BlockPos, direction: Direction): Int {
            var i = 0
            while (i < 22) {
                val offset = pos.offset(direction, i)
                if (!this.isValidBlockType(this.world.getBlockState(offset)) || this.world.getBlockState(offset.down()).block !== Glacia.Blocks.GLACIAL_MAGIC_STONE) {
                    break
                }
                ++i
            }

            val block = this.world.getBlockState(pos.offset(direction, i)).block
            return if (block === Glacia.Blocks.GLACIAL_MAGIC_STONE) i else 0
        }

        private fun calculatePortalHeight(): Int {
            this.height = 0
            rootLoop@ while (this.height < 21) {
                for (i in 0 until this.width) {
                    val pos = this.bottomLeft!!.offset(this.rightDir, i).up(this.height)
                    val state = this.world.getBlockState(pos)
                    if (!this.isValidBlockType(state)) {
                        break@rootLoop
                    }

                    var block = state.block
                    if (block === this@BlockGlaciaPortal) {
                        ++this.portalBlockCount
                    }

                    if (i == 0) {
                        block = this.world.getBlockState(pos.offset(this.leftDir)).block
                        if (block !== Glacia.Blocks.GLACIAL_MAGIC_STONE) {
                            break@rootLoop
                        }
                    } else if (i == this.width - 1) {
                        block = this.world.getBlockState(pos.offset(this.rightDir)).block
                        if (block !== Glacia.Blocks.GLACIAL_MAGIC_STONE) {
                            break@rootLoop
                        }
                    }
                }
                ++this.height
            }

            for (j in 0 until this.width) {
                if (this.world.getBlockState(this.bottomLeft!!.offset(this.rightDir, j).up(this.height)).block !== Glacia.Blocks.GLACIAL_MAGIC_STONE) {
                    this.height = 0
                    break
                }
            }

            return if (this.height in 3..21) {
                this.height
            } else {
                this.bottomLeft = null
                this.width = 0
                this.height = 0
                0
            }
        }

        private fun isValidBlockType(state: BlockState): Boolean {
            val block = state.block
            return state.isAir || block === Glacia.Blocks.GLACIAL_FIRE || block === this@BlockGlaciaPortal
        }

        fun placePortalBlocks() {
            for (i in 0 until this.width) {
                val blockpos = this.bottomLeft!!.offset(this.rightDir, i)

                for (j in 0 until this.height) {
                    this.world.setBlockState(blockpos.up(j), this@BlockGlaciaPortal.defaultState.with(AXIS, this.axis), 18)
                }
            }
        }

        private fun isValidBlockCount(): Boolean {
            return this.portalBlockCount >= this.width * this.height
        }

        val netherPortalSize; get() = NetherPortalBlock.Size(world, pos, axis)

        fun isValidPortalPlacement(): Boolean {
            return this.isValid && this.isValidBlockCount()
        }
    }

    fun createPatternHelper(worldIn: IWorld, p_181089_2_: BlockPos): BlockPattern.PatternHelper {
        var axis: Direction.Axis = Direction.Axis.Z
        var size = Size(worldIn, p_181089_2_, Direction.Axis.X)
        val loadingCache = BlockPattern.createLoadingCache(worldIn, true)
        if (!size.isValid) {
            axis = Direction.Axis.X
            size = Size(worldIn, p_181089_2_, Direction.Axis.Z)
        }

        if (!size.isValid) {
            return BlockPattern.PatternHelper(p_181089_2_, Direction.NORTH, Direction.UP, loadingCache, 1, 1, 1)
        } else {
            val intArray = IntArray(Direction.AxisDirection.values().size)
            val direction = size.rightDir.rotateYCCW()
            val pos = size.bottomLeft!!.up(size.height - 1)

            for (axisDirection in Direction.AxisDirection.values()) {
                val patternHelper = BlockPattern.PatternHelper(if (direction.axisDirection == axisDirection) pos else pos.offset(size.rightDir, size.width - 1), Direction.getFacingFromAxis(axisDirection, axis), Direction.UP, loadingCache, size.width, size.height, 1)

                for (i in 0 until size.width) {
                    for (j in 0 until size.height) {
                        val cachedBlockInfo = patternHelper.translateOffset(i, j, 1)
                        if (!cachedBlockInfo.blockState.isAir) {
                            ++intArray[axisDirection.ordinal]
                        }
                    }
                }
            }

            var axisDirection1: Direction.AxisDirection = Direction.AxisDirection.POSITIVE

            for (axisDirection2 in Direction.AxisDirection.values()) {
                if (intArray[axisDirection2.ordinal] < intArray[axisDirection1.ordinal]) {
                    axisDirection1 = axisDirection2
                }
            }

            return BlockPattern.PatternHelper(if (direction.axisDirection == axisDirection1) pos else pos.offset(size.rightDir, size.width - 1), Direction.getFacingFromAxis(axisDirection1, axis), Direction.UP, loadingCache, size.width, size.height, 1)
        }
    }

    private val ServerWorld.glaciaTeleporter : Teleporter by LazyWithReceiver(false) {GlaciaTeleporter(this, this@BlockGlaciaPortal)}
}