package com.greenapple.glacia

import net.minecraft.item.Item
import net.minecraft.item.ItemStack

class Glacia_ItemGroup private constructor(name: String, private val iconItemProvider: ()-> Item?) : net.minecraft.item.ItemGroup("${Glacia.MODID}.$name") {
    override fun createIcon(): ItemStack = ItemStack(iconItemProvider)
    companion object {
        val BLOCKS = Glacia_ItemGroup("blocks") {RegistryEvents.GLACIAL_DIRT.blockItem?.getVariant("snowy")}
    }
}