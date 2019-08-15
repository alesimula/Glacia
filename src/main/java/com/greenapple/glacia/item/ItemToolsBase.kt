package com.greenapple.glacia.item

import com.greenapple.glacia.Glacia
import net.minecraft.item.*
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils
import net.minecraft.util.text.TranslationTextComponent

class ItemSwordBase(registryName: String, unlocalizedName: String, tier: IItemTier, attackDamage: Int, attackSpeed: Float) : SwordItem(tier, attackDamage, attackSpeed, Properties().group(Glacia.ItemGroup.COMBAT)) {
    init {setRegistryName(registryName)}
    private val unlocalizedNameText : ITextComponent by lazy { TextComponentUtils.toTextComponent {unlocalizedName}}
    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let {name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}

class ItemPickaxeBase(registryName: String, unlocalizedName: String, tier: IItemTier, attackDamage: Int, attackSpeed: Float) : PickaxeItem(tier, attackDamage, attackSpeed, Properties().group(Glacia.ItemGroup.TOOLS)) {
    init {setRegistryName(registryName)}
    private val unlocalizedNameText : ITextComponent by lazy { TextComponentUtils.toTextComponent {unlocalizedName}}
    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let {name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}

class ItemAxeBase(registryName: String, unlocalizedName: String, tier: IItemTier, attackDamage: Float, attackSpeed: Float) : AxeItem(tier, attackDamage, attackSpeed, Properties().group(Glacia.ItemGroup.TOOLS)) {
    init {setRegistryName(registryName)}
    private val unlocalizedNameText : ITextComponent by lazy { TextComponentUtils.toTextComponent {unlocalizedName}}
    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let {name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}

class ItemShovelBase(registryName: String, unlocalizedName: String, tier: IItemTier, attackDamage: Float, attackSpeed: Float) : ShovelItem(tier, attackDamage, attackSpeed, Properties().group(Glacia.ItemGroup.TOOLS)) {
    init {setRegistryName(registryName)}
    private val unlocalizedNameText : ITextComponent by lazy { TextComponentUtils.toTextComponent {unlocalizedName}}
    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let {name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}

class ItemHoeBase(registryName: String, unlocalizedName: String, tier: IItemTier, attackSpeed: Float) : HoeItem(tier, attackSpeed, Properties().group(Glacia.ItemGroup.TOOLS)) {
    init {setRegistryName(registryName)}
    private val unlocalizedNameText : ITextComponent by lazy { TextComponentUtils.toTextComponent {unlocalizedName}}
    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let {name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}