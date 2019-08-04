package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraftforge.common.extensions.IForgeBlock

interface IBlockBase : IForgeBlock {
    val unlocalizedName: String
    var blockItem: BlockItemBase?

    fun blockItemVariant(name: String) = blockItem?.getVariant(name)

    override fun onPlantGrow(state: BlockState?, world: IWorld?, pos: BlockPos?, source: BlockPos?) {
        if (state != Glacia.Blocks.GLACIAL_DIRT.defaultState) pos?.apply {world?.setBlockState(this, Glacia.Blocks.GLACIAL_DIRT.defaultState, 2)}
    }
}