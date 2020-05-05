package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.BlockItemBase
import com.greenapple.glacia.item.toBlockItem
import net.minecraft.block.BlockState
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraftforge.common.extensions.IForgeBlock
import net.minecraftforge.registries.IForgeRegistry

interface IBlockBase : IForgeBlock {
    val unlocalizedName: String
    var blockItem: BlockItemBase?
    val itemGroup: ItemGroup?

    var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>)->BlockItemBase)?

    fun blockItemVariant(name: String) = blockItem?.getVariant(name)
    fun getBlockItemForRegistry(registry: IForgeRegistry<Item>) = itemGroup?.let {this.toBlockItem(itemGroup).let {blockItem -> (itemVariantProvider?:{this}).invoke(blockItem, registry)}}

    override fun onPlantGrow(state: BlockState?, world: IWorld?, pos: BlockPos?, source: BlockPos?) {
        if (state != Glacia.Blocks.GLACIAL_DIRT.defaultState) pos?.apply {world?.setBlockState(this, Glacia.Blocks.GLACIAL_DIRT.defaultState, 2)}
    }
}

fun <B: IBlockBase> B.addItemVariant(registryNameSuffix: String, name: String?=null, stateInit: BlockState.()->BlockState) : B {
    val oldProvider = itemVariantProvider
    itemVariantProvider = {registry -> oldProvider?.invoke(this, registry); name?.let {addVariant(registry, registryNameSuffix, name, stateInit)} ?: addVariant(registry, registryNameSuffix) {stateInit(this)}}
    return this
}