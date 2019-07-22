package com.greenapple.glacia.block

import com.greenapple.glacia.RegistryEvents
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.DyeColor
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.nbt.INBT
import net.minecraft.nbt.IntNBT
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.util.Hand
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import org.apache.logging.log4j.LogManager
import java.util.*

open class BlockGlaciaDirt : BlockBase, IBlockNamed {

    constructor(name: String, material: Material, materialColor: MaterialColor=material.color, initializer: (Properties.()->Unit)?=null) : super(name, material, materialColor, initializer.init)
    constructor(name: String, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : super(name, material, dyeColor, initializer.init)
    constructor(name: String, material: Material, initializer: (Properties.()->Unit)?=null) : super(name, material, initializer.init)

    companion object {
        val SNOWY = BooleanProperty.create("snowy")
        val (Properties.()->Unit)?.init : Properties.() -> Unit; get() = {
            LogManager.getLogger().info("AAAAA")
            tickRandomly()
            this@init?.invoke(this)
        }
    }

    init {
        defaultState = stateContainer.baseState.with(SNOWY, false)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(SNOWY)
    }

    override fun randomTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        //TODO isRemote is bugged, must find a way to workaround
        //if (!world.isRemote) {
        if (state.get(SNOWY)) {
            if (world.getBlockState(pos.up()).block !== Blocks.AIR) world.setBlockState(pos, state.with(SNOWY, false))
            else for (i in 0..3) {
                val closeBlockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1)
                val closeBlockState = world.getBlockState(closeBlockPos)
                if (closeBlockState.block === RegistryEvents.GLACIAL_DIRT && !closeBlockState.get(SNOWY)) {
                    world.setBlockState(closeBlockPos, closeBlockState.with(SNOWY, true))
                }
            }
        }
        //}
    }
}