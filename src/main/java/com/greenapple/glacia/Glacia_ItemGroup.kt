package com.greenapple.glacia

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class Glacia_ItemGroup private constructor(name: String, private val iconItemProvider: ()-> Item?) : net.minecraft.item.ItemGroup("${Glacia.MODID}.$name") {
    override fun createIcon(): ItemStack = ItemStack(iconItemProvider)
    companion object {
        @JvmStatic val BLOCKS = Glacia_ItemGroup("blocks") {Glacia.Blocks.GLACIAL_DIRT.blockItemVatiant("snowy")}
        @JvmStatic val DECORATIONS = Glacia_ItemGroup("decorations") {Glacia.Blocks.GLACIAL_BERRY.blockItem} //TODO sapling icon
        @JvmStatic val BLUESTONE = Glacia_ItemGroup("bluestone") {Glacia.Blocks.GLACIAL_STONE.blockItem}
        @JvmStatic val TRANSPORTING = Glacia_ItemGroup("transporting") {Glacia.Blocks.GLACIAL_STONE.blockItem}
        @JvmStatic val MISC = Glacia_ItemGroup("misc") {Glacia.Blocks.GLACIAL_STONE.blockItem}
        @JvmStatic val FOOD = Glacia_ItemGroup("food") {Glacia.Blocks.GLACIAL_STONE.blockItem}
        @JvmStatic val TOOLS = Glacia_ItemGroup("tools") {Glacia.Blocks.GLACIAL_STONE.blockItem}
        @JvmStatic val COMBAT = Glacia_ItemGroup("combat") {Glacia.Blocks.GLACIAL_STONE.blockItem}
        @JvmStatic val BREWING = Glacia_ItemGroup("brewing") {Glacia.Blocks.GLACIAL_STONE.blockItem} //TODO potion morph snowman icon
        @JvmStatic val MATERIALS = Glacia_ItemGroup("materials") {Glacia.Blocks.GLACIAL_STONE.blockItem}
    }
}