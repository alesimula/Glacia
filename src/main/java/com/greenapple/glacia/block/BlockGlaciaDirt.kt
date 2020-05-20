package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.registry.renderType
import com.greenapple.glacia.utils.RenderTypeBase
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.SnowBlock
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraft.item.ItemGroup
import net.minecraft.state.BooleanProperty
import net.minecraft.state.IntegerProperty
import net.minecraft.state.StateContainer
import net.minecraft.tags.FluidTags
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.IWorldReader
import net.minecraft.world.lighting.LightEngine
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.IPlantable
import java.util.*

open class BlockGlaciaDirt : BlockBase {

    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, materialColor: MaterialColor=material.color, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, materialColor, initializer.init)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, dyeColor, initializer.init)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, initializer.init)

    companion object {
        val SNOWY = BooleanProperty.create("snowy")
        val SPORIC = BooleanProperty.create("sporic")
        val LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15)
        val (Properties.()->Unit)?.init : Properties.() -> Unit; get() = {
            tickRandomly()
            this@init?.invoke(this)
        }
    }

    override fun isEmissiveRendering(state: BlockState) = state.lightValue > 0

    //override fun getLightValue(state: BlockState, world: IBlockReader?, pos: BlockPos?) = if (state.has(LIGHT_LEVEL)) state.get(LIGHT_LEVEL) else super.getLightValue(state, world, pos)

    init {
        defaultState = stateContainer.baseState.with(SNOWY, false).with(SPORIC, false)
    }
    val stateSnowy = defaultState.with(SNOWY, true)

    override fun canSustainPlant(state: BlockState, world: IBlockReader, pos: BlockPos, facing: Direction, plantable: IPlantable): Boolean {
        val plant: BlockState = plantable.getPlant(world, pos.offset(facing))
        val canSustainVanillaPlant = super.canSustainPlant(state, world, pos, facing, plantable)
        val canSustainGlaciaPlant = plant.block === Glacia.Blocks.GLACIAL_SAPLING
        return canSustainVanillaPlant || canSustainGlaciaPlant
    }

    override fun canBeReplacedByLogs(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = true

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(SNOWY).add(SPORIC).add(LIGHT_LEVEL)
    }

    private fun isSurfaceExposed(state: BlockState, world: IWorldReader, pos: BlockPos): Boolean {
        val upperPos = pos.up()
        val upperState = world.getBlockState(upperPos)
        return if (upperState.block === Blocks.SNOW && upperState.get(SnowBlock.LAYERS) == 1) true
        else LightEngine.func_215613_a(world, state, pos, upperState, upperPos, Direction.UP, upperState.getOpacity(world, upperPos)) < world.maxLightLevel
    }

    private fun isSurfaceGrowable(state: BlockState, world: IWorldReader, pos: BlockPos) = isSurfaceExposed(state, world, pos) && !world.getFluidState(pos.up()).isTagged(FluidTags.WATER)

    /*override fun onNeighborChange(state: BlockState, world: IWorldReader, pos: BlockPos, neighbor: BlockPos) {
        val upperPos = pos.up()
        if (world.getBlockState(upperPos).block === Blocks.SNOW) {
            world.setBlockState(pos, state.with(SNOWY, true))
            world.setBlockState(upperPos, Blocks.AIR.defaultState)
        }
    }*/

    override fun updatePostPlacement(state: BlockState, facing: Direction, facingState: BlockState, world: IWorld, pos: BlockPos, facingPos: BlockPos): BlockState {
        val upperPos = pos.up()
        return if (world.getBlockState(upperPos).block === Blocks.SNOW) {
            world.setBlockState(upperPos, Blocks.AIR.defaultState, 16)
            state.with(SNOWY, true)
        }
        else state
    }

    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        //TODO isRemote is bugged, must find a way to workaround
        //if (!world.isRemote) {
        if (state.get(SNOWY)) {
            if (world.getBlockState(pos.up()).block !== Blocks.AIR && !isSurfaceExposed(state, world, pos)) world.setBlockState(pos, state.with(SNOWY, false))
            else for (i in 0..3) {
                val closeBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1)
                val closeBlockState = world.getBlockState(closeBlockPos)
                if (closeBlockState.block === Glacia.Blocks.GLACIAL_DIRT && !closeBlockState.get(SNOWY) && world.getLight(closeBlockPos.up()) >= 9 && isSurfaceGrowable(closeBlockState, world, closeBlockPos)) {
                    world.setBlockState(closeBlockPos, closeBlockState.with(SNOWY, true))
                }
            }
        }
        //}
    }
}