package com.greenapple.glacia.block

import com.greenapple.glacia.item.BlockItemBase
import net.minecraft.block.*
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry

class BlockStairsBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, block: Block) : StairsBlock(block.defaultState, Properties.from(block)), IBlockBase {

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let { item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)
}