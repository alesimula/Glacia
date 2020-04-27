package com.greenapple.glacia.block

import com.greenapple.glacia.item.BlockItemBase
import com.greenapple.glacia.registry.renderType
import net.minecraft.block.*
import net.minecraft.client.renderer.RenderType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.SlabType
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry

/**
 * Don't forget to add the block to the 'fences' tag group
 */
class BlockDoorBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, block: Block) : DoorBlock(Properties.from(block).notSolid()), IBlockBase {

    init {
        setRegistryName(registryName)
        renderType = RenderType.getCutout()
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let {item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)
}