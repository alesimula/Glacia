package com.greenapple.glacia.item

import com.greenapple.glacia.Glacia
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.*
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils
import net.minecraft.util.text.TranslationTextComponent

class ItemArmorBase(registryName: String, unlocalizedName: String, material: IArmorMaterial, slot: EquipmentSlotType) : ArmorItem(material, slot, Properties().group(Glacia.ItemGroup.COMBAT)) {
    init {setRegistryName(registryName)}
    private val unlocalizedNameText : ITextComponent by lazy { TextComponentUtils.toTextComponent {unlocalizedName}}
    override fun getDisplayName(stack: ItemStack): ITextComponent = TranslationTextComponent(translationKey).let { name ->
        if (name.unformattedComponentText == translationKey) unlocalizedNameText
        else name
    }
}