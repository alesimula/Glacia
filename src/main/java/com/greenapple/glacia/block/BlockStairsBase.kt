package com.greenapple.glacia.block

import net.minecraft.block.*
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.registries.IForgeRegistry

class BlockStairsBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, block: Block) : StairsBlock(block.defaultState, Properties.from(block)), IBlockBase {

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null
}