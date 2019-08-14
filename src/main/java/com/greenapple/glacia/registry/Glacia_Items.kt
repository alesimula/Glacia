package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.ItemBase
import com.greenapple.glacia.item.ItemFoodBase
import net.minecraft.item.Item

object Glacia_Items : IForgeRegistryCollection<Item> {
    //Food
    val SABER_TOOTHED_CAT_MEAT = ItemFoodBase("saber_toothed_cat_meat", "Saber-toothed Cat Meat", 3, 0.3F) {meat()}
    val SABER_TOOTHED_CAT_MEAT_COOKED = ItemFoodBase("saber_toothed_cat_meat_cooked", "Seared Saber-toothed Cat Meat", 9, 0.9F) {meat()}
    val GLACIAL_BERRY = ItemFoodBase("glacial_berry", "Glacial berry", 1, 0.1F)
    //Materials
    val GLACIAL_CRYSTAL = ItemBase("glacial_crystal", "Glacial Crystal", Glacia.ItemGroup.MATERIALS)
    val GLACIAL_ICE = ItemBase("glacial_ice", "Glacial Ice", Glacia.ItemGroup.MATERIALS)
    val CRYSTAL_ROD = ItemBase("crystal_rod", "Crystal Rod", Glacia.ItemGroup.MATERIALS)
    val ICE_ROD = ItemBase("ice_rod", "Ice Rod", Glacia.ItemGroup.MATERIALS)
    val MAGIC_STONE_SHARD = ItemBase("magic_stone_shard", "Magic Stone Shard", Glacia.ItemGroup.MATERIALS)
    val GLACIAL_WOOD_ROD = ItemBase("glacial_wood_rod", "Glacial Wood Rod", Glacia.ItemGroup.MATERIALS)
    //Misc
    val MYSTERIUS_ARMOR_SHARD = ItemBase("mysterious_armor_shard", "Mysterious Armor Shard", Glacia.ItemGroup.MISC)
    val PENGUIN_FEATHERS = ItemBase("penguin_feathers", "Penguin Feathers", Glacia.ItemGroup.MISC)

    //CrystalHelmet.setCreativeTab(GlaciaCreativeTabCombat);
    //CrystalBody.setCreativeTab(GlaciaCreativeTabCombat);
    //CrystalLegs.setCreativeTab(GlaciaCreativeTabCombat);
    //CrystalBoots.setCreativeTab(GlaciaCreativeTabCombat);
    //CrystalSword.setCreativeTab(GlaciaCreativeTabCombat);
    //IceHelmet.setCreativeTab(GlaciaCreativeTabCombat);
    //var GlacialTurtleHead: Item? = null
    //var GlacialTurtleShell: Item? = null
    //var GlacialTurtleLimbs: Item? = null
    //var GlacialTurtlePaws: Item? = null
    //var GlacialReturnRune: Item? = null
    //IceBody.setCreativeTab(GlaciaCreativeTabCombat);
    //IceLegs.setCreativeTab(GlaciaCreativeTabCombat);
    //IceBoots.setCreativeTab(GlaciaCreativeTabCombat);
    //IceSword.setCreativeTab(GlaciaCreativeTabCombat);
    //IceBastardSword.setCreativeTab(GlaciaCreativeTabCombat);
    //IceDagger.setCreativeTab(GlaciaCreativeTabCombat);
    //IceWarHammer.setCreativeTab(GlaciaCreativeTabCombat);
    //GlacialWoodSword.setCreativeTab(GlaciaCreativeTabCombat);
    //MisteriousBody.setCreativeTab(GlaciaCreativeTabCombat);

    //CrystalPickaxe.setCreativeTab(GlaciaCreativeTabTools);
    //CrystalAxe.setCreativeTab(GlaciaCreativeTabTools);
    //CrystalShovel.setCreativeTab(GlaciaCreativeTabTools);
    //CrystalHoe.setCreativeTab(GlaciaCreativeTabTools);
    //IcePickaxe.setCreativeTab(GlaciaCreativeTabTools);
    //IceAxe.setCreativeTab(GlaciaCreativeTabTools);
    //IceShovel.setCreativeTab(GlaciaCreativeTabTools);
    //IceHoe.setCreativeTab(GlaciaCreativeTabTools);
    //GlacialWoodPickaxe.setCreativeTab(GlaciaCreativeTabTools);
    //GlacialWoodAxe.setCreativeTab(GlaciaCreativeTabTools);
    //GlacialWoodShovel.setCreativeTab(GlaciaCreativeTabTools);
    //GlacialWoodHoe.setCreativeTab(GlaciaCreativeTabTools);

    //PortalSwitcher.setCreativeTab(GlaciaCreativeTabBrewing);
    //BrokenQuadiumBulb.setCreativeTab(GlaciaCreativeTabBrewing);
    //QuadiumBulb.setCreativeTab(GlaciaCreativeTabBrewing);
    //QuadiumPearl.setCreativeTab(GlaciaCreativeTabBrewing);
    //GlacialReturnRune.setCreativeTab(GlaciaCreativeTabBrewing);
    //GlaciaPotionMorphSnowman.setCreativeTab(GlaciaCreativeTabBrewing);

    //ItemGlaciaBlueStone.setCreativeTab(GlaciaCreativeTabBluestone);
}