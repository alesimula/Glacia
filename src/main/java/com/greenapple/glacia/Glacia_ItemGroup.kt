package com.greenapple.glacia

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class Glacia_ItemGroup private constructor(name: String, private val iconItemProvider: ()-> Item?) : net.minecraft.item.ItemGroup("${Glacia.MODID}.$name") {
    override fun createIcon(): ItemStack = ItemStack(iconItemProvider)
    companion object {
        val BLOCKS = Glacia_ItemGroup("blocks") {Glacia_Blocks.GLACIAL_DIRT.blockItem?.getVariant("snowy")}
        val DECORATIONS = Glacia_ItemGroup("decorations") {Glacia_Blocks.GLACIAL_STONE.blockItem} //TODO sapling icon
        val BLUESTONE = Glacia_ItemGroup("bluestone") {Glacia_Blocks.GLACIAL_STONE.blockItem}
        val TRANSPORTING = Glacia_ItemGroup("transporting") {Glacia_Blocks.GLACIAL_STONE.blockItem}
        val MISC = Glacia_ItemGroup("misc") {Glacia_Blocks.GLACIAL_STONE.blockItem}
        val FOOD = Glacia_ItemGroup("food") {Glacia_Blocks.GLACIAL_STONE.blockItem}
        val TOOLS = Glacia_ItemGroup("tools") {Glacia_Blocks.GLACIAL_STONE.blockItem}
        val COMBAT = Glacia_ItemGroup("combat") {Glacia_Blocks.GLACIAL_STONE.blockItem}
        val BREWING = Glacia_ItemGroup("brewing") {Glacia_Blocks.GLACIAL_STONE.blockItem} //TODO potion morph snowman icon
        val MATERIALS = Glacia_ItemGroup("materials") {Glacia_Blocks.GLACIAL_STONE.blockItem}
    }
}