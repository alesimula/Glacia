package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

class Glacia_ItemGroup private constructor(name: String, private val iconItemProvider: ()-> Item?) : net.minecraft.item.ItemGroup("${Glacia.MODID}.$name") {
    override fun createIcon(): ItemStack = ItemStack(iconItemProvider)
    companion object {
        @JvmStatic val BLOCKS : ItemGroup = Glacia_ItemGroup("block") { Glacia_Blocks.GLACIAL_DIRT.blockItemVariant("snowy") }
        @JvmStatic val DECORATIONS : ItemGroup = Glacia_ItemGroup("decorations") { Glacia_Blocks.GLACIAL_BERRY.blockItem } //TODO sapling icon
        @JvmStatic val BLUESTONE : ItemGroup = Glacia_ItemGroup("bluestone") { Glacia_Blocks.GLACIAL_STONE.blockItem }
        @JvmStatic val TRANSPORTING : ItemGroup = Glacia_ItemGroup("transporting") { Glacia_Blocks.GLACIAL_STONE.blockItem }
        @JvmStatic val MISC : ItemGroup = Glacia_ItemGroup("misc") { Glacia_Blocks.GLACIAL_STONE.blockItem }
        @JvmStatic val FOOD : ItemGroup = Glacia_ItemGroup("food") { Glacia_Blocks.GLACIAL_STONE.blockItem }
        @JvmStatic val TOOLS : ItemGroup = Glacia_ItemGroup("tools") { Glacia_Blocks.GLACIAL_STONE.blockItem }
        @JvmStatic val COMBAT : ItemGroup = Glacia_ItemGroup("combat") { Glacia_Blocks.GLACIAL_STONE.blockItem }
        @JvmStatic val BREWING : ItemGroup = Glacia_ItemGroup("brewing") { Glacia_Blocks.GLACIAL_STONE.blockItem } //TODO potion morph snowman icon
        @JvmStatic val MATERIALS : ItemGroup = Glacia_ItemGroup("materials") { Glacia_Blocks.GLACIAL_STONE.blockItem }
    }
}