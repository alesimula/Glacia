package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.ItemBase
import com.greenapple.glacia.item.ItemFoodBase
import net.minecraft.item.Item

object Glacia_Items : IForgeRegistryCollection<Item> {
    val THING = ItemBase("thing", "I am stuff", Glacia.ItemGroup.FOOD)
    //Food
    val SABER_TOOTHED_CAT_MEAT = ItemFoodBase("saber_toothed_cat_meat", "Saber-toothed Cat Meat", 3, 0.3F) {meat()}
    val SABER_TOOTHED_CAT_MEAT_COOKED = ItemFoodBase("saber_toothed_cat_meat_cooked", "Seared Saber-toothed Cat Meat", 9, 0.9F) {meat()}
    val GLACIAL_BERRY = ItemFoodBase("glacial_berry2", "Glacial berry", 1, 0.1F)

    //var ItemGlacialDoor: Item? = null
    //var ItemGlacialCrystal: Item? = null
    //var CrystalHelmet: Item? = null
    //var CrystalBody: Item? = null
    //var CrystalLegs: Item? = null
    //var CrystalBoots: Item? = null
    //var CrystalSword: Item? = null
    //var CrystalPickaxe: Item? = null
    //var CrystalAxe: Item? = null
    //var CrystalShovel: Item? = null
    //var CrystalHoe: Item? = null
    //var ItemGlacialIce: Item? = null
    //var IceHelmet: Item? = null
    //var IceBody: Item? = null
    //var IceLegs: Item? = null
    //var IceBoots: Item? = null
    //var IceSword: Item? = null
    //var CrystalRod: Item? = null
    //var IceBastardSword: Item? = null
    //var IceDagger: Item? = null
    //var IceWarHammer: Item? = null
    //var IceRod: Item? = null
    //var IcePickaxe: Item? = null
    //var IceAxe: Item? = null
    //var IceShovel: Item? = null
    //var IceHoe: Item? = null
    //var PortalSwitcher: Item? = null
    //var MagicStonePiece: Item? = null
    //var GlacialWoodRod: Item? = null
    //var GlacialWoodSword: Item? = null
    //var GlacialWoodPickaxe: Item? = null
    //var GlacialWoodAxe: Item? = null
    //var GlacialWoodShovel: Item? = null
    //var GlacialWoodHoe: Item? = null
    //var MisteriousBody: Item? = null
    //var MisteriousArmorPiece: Item? = null
    //var BrokenQuadiumBulb: Item? = null
    //var QuadiumBulb: Item? = null
    //var PenguinFeathers: Item? = null
    //var ItemGlaciaBlueStone: Item? = null
    //var QuadiumPearl: Item? = null
    //var GlacialTurtleHead: Item? = null
    //var GlacialTurtleShell: Item? = null
    //var GlacialTurtleLimbs: Item? = null
    //var GlacialTurtlePaws: Item? = null
    //var GlacialReturnRune: Item? = null
    //var GlaciaPotionMorphSnowman: Item? = null
}