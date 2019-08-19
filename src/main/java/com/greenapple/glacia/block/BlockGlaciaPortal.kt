package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.delegate.LazyWithReceiver
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.NetherPortalBlock
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.entity.Entity
import net.minecraft.item.DyeColor
import net.minecraft.util.math.BlockPos
import net.minecraft.world.dimension.DimensionType
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.world.gen.Heightmap
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.Hand
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemGroup
import net.minecraft.network.play.server.*
import net.minecraft.state.EnumProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction
import net.minecraft.util.Rotation
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.Teleporter
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fml.hooks.BasicEventHooks


open class BlockGlaciaPortal : BlockBase {

    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, materialColor: MaterialColor =material.color, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, materialColor, initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, dyeColor, initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, initializer)

    init {
        this.defaultState = stateContainer.baseState.with(AXIS, Direction.Axis.X)
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
        return if (!flag && facingState.block !== this && !NetherPortalBlock.Size(worldIn, currentPos, axis2).func_208508_f()) Blocks.AIR.defaultState else super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos)
    }

    companion object {
        val AXIS: EnumProperty<Direction.Axis> = BlockStateProperties.HORIZONTAL_AXIS
        private val X_AABB : VoxelShape = makeCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0)
        private val Z_AABB : VoxelShape = makeCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0)

        fun changeDim(player: ServerPlayerEntity, pos: BlockPos, destination: DimensionType) {
            if (!ForgeHooks.onTravelToDimension(player, destination)) return
            val origin = player.dimension

            val serverWorld = player.server.getWorld(origin)
            player.dimension = destination
            val serverWorld1 = player.server.getWorld(destination)
            val worldInfo = player.world.worldInfo
            player.connection.sendPacket(SRespawnPacket(destination, worldInfo.generator, player.interactionManager.gameType))
            player.connection.sendPacket(SServerDifficultyPacket(worldInfo.difficulty, worldInfo.isDifficultyLocked))
            val playerList = player.server.playerList
            playerList.updatePermissionLevel(player)
            serverWorld.removeEntity(player, true)
            player.revive()

            val pitch = player.rotationPitch
            val yaw = player.rotationYaw

            player.setLocationAndAngles(pos.x + .5, pos.y + .5, pos.z + .5, yaw, pitch)
            serverWorld.profiler.endSection()
            serverWorld.profiler.startSection("placing")
            player.setLocationAndAngles(pos.x + .5, pos.y + .5, pos.z + .5, yaw, pitch)

            serverWorld.profiler.endSection()
            player.setWorld(serverWorld1)
            serverWorld1.func_217447_b(player)
            player.connection.setPlayerLocation(pos.x + .5, pos.y + .5, pos.z + .5, yaw, pitch)
            player.interactionManager.setWorld(serverWorld1);
            player.connection.sendPacket(SPlayerAbilitiesPacket(player.abilities));
            playerList.sendWorldInfo(player, serverWorld1)
            playerList.sendInventory(player)

            for(potionEffect in player.activePotionEffects)
                player.connection.sendPacket(SPlayEntityEffectPacket(player.entityId, potionEffect))

            player.connection.sendPacket(SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false))
            /* TODO access transformer
            player.lastExperience = -1
            player.lastHealth = -1F
            player.lastFoodLevel = -1*/
            BasicEventHooks.firePlayerChangedDimensionEvent(player, origin, destination)
        }
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, playerIn: PlayerEntity, hand: Hand, rts: BlockRayTraceResult): Boolean {
        if (!worldIn.isRemote) worldIn.server?.let {server ->
            val glacia = Glacia.DIMENSION.dimensionType

            //FROM OVERWORLD TO GLACIA DIM
            if (worldIn.dimension.type.id == DimensionType.OVERWORLD.id) {
                val otherWorld = server.getWorld(glacia)
                otherWorld.getBlockState(pos)
                var otherWorldPos = otherWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos)
                var foundBlock = false
                val mutableBlockPos = BlockPos.MutableBlockPos(0, 0, 0)

                for (y in 0..255) {
                    for (x in pos.x - 6 until pos.x + 6) {
                        for (z in pos.z - 6 until pos.z + 6) {
                            mutableBlockPos.setPos(x, y, z)
                            if (otherWorld.getBlockState(mutableBlockPos).block === this) {
                                otherWorldPos = BlockPos(x, y + 1, z)
                                foundBlock = true
                                break
                            }
                        }
                    }
                }
                if (foundBlock) {
                    changeDim(playerIn as ServerPlayerEntity, otherWorldPos, glacia)
                }
                if (!foundBlock) {
                    if (!otherWorld.glaciaTeleporter.func_222268_a(playerIn, playerIn.rotationYaw)) {
                        otherWorld.glaciaTeleporter.makePortal(playerIn)
                        otherWorld.glaciaTeleporter.func_222268_a(playerIn, playerIn.rotationYaw)
                    }
                    changeDim(playerIn as ServerPlayerEntity, otherWorldPos, glacia)
                }
            }

            //FROM GLACIA DIM TO OVERWORLD
            if (worldIn.getDimension().type === glacia) {
                val overWorld = server.getWorld(DimensionType.OVERWORLD)
                overWorld.getBlockState(pos)
                var overWorldPos = overWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos)
                var foundBlock = false
                val mutableBlockPos = BlockPos.MutableBlockPos(0, 0, 0)

                for (y in 0..255) {
                    for (x in pos.x - 6 until pos.x + 6) {
                        for (z in pos.z - 6 until pos.z + 6) {
                            mutableBlockPos.setPos(x, y, z)
                            if (overWorld.getBlockState(mutableBlockPos).block === this) {
                                overWorldPos = BlockPos(x, y + 1, z)
                                foundBlock = true
                                break
                            }
                        }
                    }
                }
                if (foundBlock) {
                    changeDim(playerIn as ServerPlayerEntity, overWorldPos, DimensionType.OVERWORLD)
                }
                if (!foundBlock) {
                    overWorld.setBlockState(overWorldPos.down(), this.defaultState)
                    changeDim(playerIn as ServerPlayerEntity, overWorldPos, DimensionType.OVERWORLD)
                }
            }
            return true
        }
        return false
    }

    override fun tick(state: BlockState, world: World, pos: BlockPos, random: java.util.Random) {
        super.tick(state, world, pos, random)
        (world as? ServerWorld)?.glaciaTeleporter?.tick(world.gameTime)
    }

    private val ServerWorld.glaciaTeleporter : Teleporter by LazyWithReceiver(false) {object : Teleporter(this) {
        override fun makePortal(entityIn: Entity): Boolean {
            var d0 = -1.0
            val x = MathHelper.floor(entityIn.posX)
            val y = MathHelper.floor(entityIn.posY)
            val z = MathHelper.floor(entityIn.posZ)
            var x1 = x
            var y1 = y
            var z1 = z
            var l1 = 0
            val i2 = random.nextInt(4)
            val mutableBlockPos = BlockPos.MutableBlockPos()

            for (j2 in x - 16..x + 16) {
                val d1 = j2.toDouble() + 0.5 - entityIn.posX

                for (l2 in z - 16..z + 16) {
                    val d2 = l2.toDouble() + 0.5 - entityIn.posZ

                    var j3 = this.world.getActualHeight() - 1
                    label276@ while (j3 >= 0) {
                        if (this.world.isAirBlock(mutableBlockPos.setPos(j2, j3, l2))) {
                            while (j3 > 0 && this.world.isAirBlock(mutableBlockPos.setPos(j2, j3 - 1, l2))) {
                                --j3
                            }

                            for (k3 in i2 until i2 + 4) {
                                var l3 = k3 % 2
                                var i4 = 1 - l3
                                if (k3 % 4 >= 2) {
                                    l3 = -l3
                                    i4 = -i4
                                }

                                for (j4 in 0..2) {
                                    for (k4 in 0..3) {
                                        for (l4 in -1..3) {
                                            val i5 = j2 + (k4 - 1) * l3 + j4 * i4
                                            val j5 = j3 + l4
                                            val k5 = l2 + (k4 - 1) * i4 - j4 * l3
                                            mutableBlockPos.setPos(i5, j5, k5)
                                            if (l4 < 0 && !this.world.getBlockState(mutableBlockPos).getMaterial().isSolid() || l4 >= 0 && !this.world.isAirBlock(mutableBlockPos)) {
                                                --j3
                                                continue@label276
                                            }
                                        }
                                    }
                                }

                                val d5 = j3.toDouble() + 0.5 - entityIn.posY
                                val d7 = d1 * d1 + d5 * d5 + d2 * d2
                                if (d0 < 0.0 || d7 < d0) {
                                    d0 = d7
                                    x1 = j2
                                    y1 = j3
                                    z1 = l2
                                    l1 = k3 % 4
                                }
                            }
                        }
                        --j3
                    }
                }
            }

            if (d0 < 0.0) {
                for (l5 in x - 16..x + 16) {
                    val d3 = l5.toDouble() + 0.5 - entityIn.posX

                    for (j6 in z - 16..z + 16) {
                        val d4 = j6.toDouble() + 0.5 - entityIn.posZ

                        var i7 = this.world.getActualHeight() - 1
                        label214@ while (i7 >= 0) {
                            if (this.world.isAirBlock(mutableBlockPos.setPos(l5, i7, j6))) {
                                while (i7 > 0 && this.world.isAirBlock(mutableBlockPos.setPos(l5, i7 - 1, j6))) {
                                    --i7
                                }

                                for (l7 in i2 until i2 + 2) {
                                    val l8 = l7 % 2
                                    val k9 = 1 - l8

                                    for (i10 in 0..3) {
                                        for (k10 in -1..3) {
                                            val i11 = l5 + (i10 - 1) * l8
                                            val j11 = i7 + k10
                                            val k11 = j6 + (i10 - 1) * k9
                                            mutableBlockPos.setPos(i11, j11, k11)
                                            if (k10 < 0 && !this.world.getBlockState(mutableBlockPos).getMaterial().isSolid() || k10 >= 0 && !this.world.isAirBlock(mutableBlockPos)) {
                                                --i7
                                                continue@label214
                                            }
                                        }
                                    }

                                    val d6 = i7.toDouble() + 0.5 - entityIn.posY
                                    val d8 = d3 * d3 + d6 * d6 + d4 * d4
                                    if (d0 < 0.0 || d8 < d0) {
                                        d0 = d8
                                        x1 = l5
                                        y1 = i7
                                        z1 = j6
                                        l1 = l7 % 2
                                    }
                                }
                            }
                            --i7
                        }
                    }
                }
            }

            val i6 = x1
            var k2 = y1
            val k6 = z1
            var l6 = l1 % 2
            var i3 = 1 - l6
            if (l1 % 4 >= 2) {
                l6 = -l6
                i3 = -i3
            }

            if (d0 < 0.0) {
                y1 = MathHelper.clamp(y1, 70, this.world.getActualHeight() - 10)
                k2 = y1

                for (j7 in -1..1) {
                    for (i8 in 1..2) {
                        for (i9 in -1..2) {
                            val l9 = i6 + (i8 - 1) * l6 + j7 * i3
                            val j10 = k2 + i9
                            val l10 = k6 + (i8 - 1) * i3 - j7 * l6
                            val flag = i9 < 0
                            mutableBlockPos.setPos(l9, j10, l10)
                            this.world.setBlockState(mutableBlockPos, if (flag) Glacia.Blocks.GLACIAL_MAGIC_STONE.defaultState else Blocks.AIR.defaultState)
                        }
                    }
                }
            }

            for (k7 in -1..2) {
                for (j8 in -1..3) {
                    if (k7 == -1 || k7 == 2 || j8 == -1 || j8 == 3) {
                        mutableBlockPos.setPos(i6 + k7 * l6, k2 + j8, k6 + k7 * i3)
                        this.world.setBlockState(mutableBlockPos, Glacia.Blocks.GLACIAL_MAGIC_STONE.defaultState, 3)
                    }
                }
            }

            val blockstate = this@BlockGlaciaPortal.defaultState.with(AXIS, if (l6 == 0) Direction.Axis.Z else Direction.Axis.X)

            for (k8 in 0..1) {
                for (j9 in 0..2) {
                    mutableBlockPos.setPos(i6 + k8 * l6, k2 + j9, k6 + k8 * i3)
                    this.world.setBlockState(mutableBlockPos, blockstate, 18)
                }
            }

            return true
        }
    }}
}