package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.*
import net.minecraft.item.*

object Glacia_Items : IForgeRegistryCollection<Item> {
    // Food
    val SABER_TOOTHED_CAT_MEAT = ItemFoodBase("saber_toothed_cat_meat", "Saber-toothed Cat Meat", 3, 0.3F) {meat()}
    val SABER_TOOTHED_CAT_MEAT_COOKED = ItemFoodBase("saber_toothed_cat_meat_cooked", "Seared Saber-toothed Cat Meat", 9, 0.9F) {meat()}
    val GLACIAL_BERRY = ItemFoodBase("glacial_berry", "Glacial berry", 1, 0.1F)
    // Materials
    val GLACIAL_CRYSTAL = ItemBase("glacial_crystal", "Glacial Crystal", Glacia.ItemGroup.MATERIALS)
    val GLACIAL_ICE = ItemBase("glacial_ice", "Glacial Ice", Glacia.ItemGroup.MATERIALS)
    val CRYSTAL_ROD = ItemBase("crystal_rod", "Crystal Rod", Glacia.ItemGroup.MATERIALS)
    val ICE_ROD = ItemBase("ice_rod", "Ice Rod", Glacia.ItemGroup.MATERIALS)
    val MAGIC_STONE_SHARD = ItemBase("magic_stone_shard", "Magic Stone Shard", Glacia.ItemGroup.MATERIALS)
    val GLACIAL_WOOD_ROD = ItemBase("glacial_wood_rod", "Glacial Wood Rod", Glacia.ItemGroup.MATERIALS)
    // Misc
    val MYSTERIUS_ARMOR_SHARD = ItemBase("mysterious_armor_shard", "Mysterious Armor Shard", Glacia.ItemGroup.MISC)
    val PENGUIN_FEATHERS = ItemBase("penguin_feathers", "Penguin Feathers", Glacia.ItemGroup.MISC)
    // Tools
    val GLACIAL_WOOD_PICKAXE = ItemPickaxeBase("glacial_wood_pickaxe", "Glacial Wood Pickaxe", ItemTier.WOOD, 1, -2.7F)
    val GLACIAL_WOOD_AXE = ItemAxeBase("glacial_wood_axe", "Glacial Wood Axe", ItemTier.WOOD, 6.8F, -3F)
    val GLACIAL_WOOD_SHOVEL = ItemShovelBase("glacial_wood_shovel", "Glacial Wood Shovel", ItemTier.WOOD, 2F, -3F)
    val GLACIAL_WOOD_HOE = ItemHoeBase("glacial_wood_hoe", "Glacial Wood Hoe", ItemTier.WOOD, -2.8F)
    val CRYSTAl_PICKAXE = ItemPickaxeBase("crystal_pickaxe", "Crystal Pickaxe", ItemTier.IRON, 1, -2.7F)
    val CRYSTAl_AXE = ItemAxeBase("crystal_axe", "Crystal Axe", ItemTier.IRON, 6.4F, -3F)
    val CRYSTAl_SHOVEL = ItemShovelBase("crystal_shovel", "Crystal Shovel", ItemTier.IRON, 2F, -3F)
    val CRYSTAl_HOE = ItemHoeBase("crystal_hoe", "Crystal Hoe", ItemTier.IRON, -0.9F)
    val ICE_PICKAXE = ItemPickaxeBase("ice_pickaxe", "Ice Pickaxe", ItemTier.DIAMOND, 2, -2.2F)
    val ICE_AXE = ItemAxeBase("ice_axe", "Ice Axe", ItemTier.DIAMOND, 5.5F, -3F)
    val ICE_SHOVEL = ItemShovelBase("ice_shovel", "Ice Shovel", ItemTier.DIAMOND, 2F, -3F)
    val ICE_HOE = ItemHoeBase("ice_hoe", "Ice Hoe", ItemTier.DIAMOND, 0.3F)
    // Combat
    val GLACIAL_WOOD_SWORD = ItemSwordBase("glacial_wood_sword", "Glacial Wood Sword", ItemTier.WOOD, 3, -2.3F)
    val CRYSTAl_SWORD = ItemSwordBase("crystal_sword", "Crystal Sword", ItemTier.IRON, 3, -2.3F)
    val ICE_SWORD = ItemSwordBase("ice_sword", "Ice Sword", ItemTier.DIAMOND, 4, -2.3F)
    val ICE_DAGGER = ItemSwordBase("ice_dagger", "Ice Dagger", ItemTier.DIAMOND, 3, -1.3F)
    val ICE_BASTARD_SWORD = ItemSwordBase("ice_bastard_sword", "Ice Bastard Sword", ItemTier.DIAMOND, 5, -2.7F)
    val ICE_WARHAMMER = ItemSwordBase("ice_warhammer", "Ice Warhammer", ItemTier.DIAMOND, 8, -3.8F)

    //IcePickaxe.setCreativeTab(GlaciaCreativeTabTools);
    //IceAxe.setCreativeTab(GlaciaCreativeTabTools);
    //IceShovel.setCreativeTab(GlaciaCreativeTabTools);
    //IceHoe.setCreativeTab(GlaciaCreativeTabTools);

    //CrystalHelmet.setCreativeTab(GlaciaCreativeTabCombat);
    //CrystalBody.setCreativeTab(GlaciaCreativeTabCombat);
    //CrystalLegs.setCreativeTab(GlaciaCreativeTabCombat);
    //CrystalBoots.setCreativeTab(GlaciaCreativeTabCombat);
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
    //MisteriousBody.setCreativeTab(GlaciaCreativeTabCombat);

    //PortalSwitcher.setCreativeTab(GlaciaCreativeTabBrewing);
    //BrokenQuadiumBulb.setCreativeTab(GlaciaCreativeTabBrewing);
    //QuadiumBulb.setCreativeTab(GlaciaCreativeTabBrewing);
    //QuadiumPearl.setCreativeTab(GlaciaCreativeTabBrewing);
    //GlacialReturnRune.setCreativeTab(GlaciaCreativeTabBrewing);
    //GlaciaPotionMorphSnowman.setCreativeTab(GlaciaCreativeTabBrewing);

    //ItemGlaciaBlueStone.setCreativeTab(GlaciaCreativeTabBluestone);
}