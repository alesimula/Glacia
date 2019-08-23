package com.greenapple.glacia.block

import com.greenapple.glacia.item.BlockItemBase
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.fluid.FlowingFluid
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
class BlockFluidBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, material: Material, fluid: FlowingFluid, initializer: (Properties.()->Unit)?=null) : FlowingFluidBlock(fluid, Properties.create(material).doesNotBlockMovement().tickRandomly().hardnessAndResistance(100.0F).noDrops().apply {initializer?.invoke(this)}), IBlockBase {

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let {item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)
}