package com.greenapple.glacia.item

import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils
import net.minecraft.util.text.TranslationTextComponent

open class ItemBase(registryName: String, private val unlocalizedName: String, group: ItemGroup, initializer: (Properties.()->Unit)?=null) : Item(Properties().group(group).apply {initializer?.invoke(this)}) {

    init {
        setRegistryName(registryName)
    }

    private val unlocalizedNameText : ITextComponent by lazy {TextComponentUtils.toTextComponent {unlocalizedName}}

    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let {name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}