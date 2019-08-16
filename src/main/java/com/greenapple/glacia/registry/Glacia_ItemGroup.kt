package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.modKey
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fml.ModLoadingContext

class Glacia_ItemGroup private constructor(private val name: String, private val unlocalizedName: String, private val iconItemProvider: ()-> Item?) : net.minecraft.item.ItemGroup("${ModLoadingContext.get().activeNamespace}.$name") {
    companion object {
        @JvmStatic val BLOCKS : ItemGroup = Glacia_ItemGroup("blocks", "Blocks") {Glacia.Blocks.GLACIAL_DIRT.blockItemVariant("snowy")}
        @JvmStatic val DECORATIONS : ItemGroup = Glacia_ItemGroup("decorations", "Decorations") {Glacia.Blocks.GLACIAL_BERRY.blockItem}
        @JvmStatic val BLUESTONE : ItemGroup = Glacia_ItemGroup("bluestone", "Bluestone") {Glacia.Items.BLUESTONE}
        @JvmStatic val TRANSPORTING : ItemGroup = Glacia_ItemGroup("transporting", "Transporting") {Glacia.Blocks.GLACIAL_STONE.blockItem}
        @JvmStatic val MISC : ItemGroup = Glacia_ItemGroup("misc", "Miscellaneous") {Glacia.Items.PENGUIN_FEATHERS}
        @JvmStatic val FOOD : ItemGroup = Glacia_ItemGroup("food", "Food") {Glacia.Items.SABER_TOOTHED_CAT_MEAT}
        @JvmStatic val TOOLS : ItemGroup = Glacia_ItemGroup("tools", "Tools") {Glacia.Items.CRYSTAl_AXE}
        @JvmStatic val COMBAT : ItemGroup = Glacia_ItemGroup("combat", "Combat") {Glacia.Items.CRYSTAl_SWORD}
        @JvmStatic val BREWING : ItemGroup = Glacia_ItemGroup("brewing", "Brewing") {Glacia.Items.POTION_MORPH_SNOWMAN}
        @JvmStatic val MATERIALS : ItemGroup = Glacia_ItemGroup("materials", "Materials") {Glacia.Items.GLACIAL_ICE}
    }

    private var translatedText: TranslationTextComponent? = null

    override fun createIcon(): ItemStack = ItemStack(iconItemProvider)
    override fun getTranslationKey(): String = super.getTranslationKey().let {prevTranslationKey -> (translatedText ?: TranslationTextComponent(prevTranslationKey)).run {
        translatedText = this
        if (unformattedComponentText == prevTranslationKey) "$modKey$unlocalizedName"
        else prevTranslationKey
    }}
}