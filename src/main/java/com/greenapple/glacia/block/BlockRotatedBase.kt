package com.greenapple.glacia.block

import com.greenapple.glacia.item.BlockItemBase
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry

class BlockRotatedBase private constructor(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, properties: Properties, initializer: (Properties.()->Unit)?=null) : RotatedPillarBlock(properties.apply {initializer?.invoke(this)}), IBlockBase {

    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, materialColor: MaterialColor =material.color, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, Properties.create(material, materialColor), initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, Properties.create(material, dyeColor), initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, material, material.color, initializer)

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let { item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)
}