package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraft.item.ItemGroup
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import java.util.*

open class BlockGlaciaDirt : BlockBase {

    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, materialColor: MaterialColor=material.color, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, materialColor, initializer.init)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, dyeColor, initializer.init)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, initializer.init)

    companion object {
        val SNOWY = BooleanProperty.create("snowy")
        val (Properties.()->Unit)?.init : Properties.() -> Unit; get() = {
            tickRandomly()
            this@init?.invoke(this)
        }
    }

    init {
        defaultState = stateContainer.baseState.with(SNOWY, false)
    }

    override fun canBeReplacedByLogs(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = true

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(SNOWY)
    }

    override fun randomTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        //TODO isRemote is bugged, must find a way to workaround
        //if (!world.isRemote) {
        if (state.get(SNOWY)) {
            if (world.getBlockState(pos.up()).block !== Blocks.AIR && (world.getLight(pos.up()) < 9)) world.setBlockState(pos, state.with(SNOWY, false))
            else for (i in 0..3) {
                val closeBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1)
                val closeBlockState = world.getBlockState(closeBlockPos)
                if (closeBlockState.block === Glacia.Blocks.GLACIAL_DIRT && !closeBlockState.get(SNOWY)) {
                    world.setBlockState(closeBlockPos, closeBlockState.with(SNOWY, true))
                }
            }
        }
        //}
    }
}