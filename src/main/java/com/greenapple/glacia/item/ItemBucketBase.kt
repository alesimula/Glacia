package com.greenapple.glacia.item

import com.greenapple.glacia.Glacia
import net.minecraft.fluid.Fluid
import net.minecraft.item.*
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils
import net.minecraft.util.text.TranslationTextComponent

class ItemBucketBase(registryName: String, unlocalizedName: String, fluid: Fluid) : BucketItem(fluid, Properties().group(Glacia.ItemGroup.MISC).maxStackSize(1).containerItem(Items.BUCKET)) {
    init {setRegistryName(registryName)}
    private val unlocalizedNameText : ITextComponent by lazy { TextComponentUtils.toTextComponent {unlocalizedName}}
    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let { name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}