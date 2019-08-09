package com.greenapple.glacia.block

import net.minecraft.block.*
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.SlabType
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry

class BlockSlabBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, block: Block) : SlabBlock(Properties.from(block)), IBlockBase {

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let { item-> arrayListOf(ItemStack(item, if (state.get(TYPE)===SlabType.DOUBLE) 2 else 1))}
            ?: super.getDrops(state, builder)
}