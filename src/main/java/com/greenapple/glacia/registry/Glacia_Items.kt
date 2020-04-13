package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.*
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.*
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents
import net.minecraftforge.fml.ModLoadingContext

@Suppress("UNUSED")
object Glacia_Items : IForgeRegistryCollection<Item> {

    enum class ArmorMaterial(private val maxDamageFactor: Int, private val damageReductionArray : IntArray, private val enchantability: Int, private val soundEvent: SoundEvent, private val toughness: Float, private val repairMatrialItem: Item) : IArmorMaterial {
        CRYSTAL(24, intArrayOf(2, 6, 7, 2), 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0f, GLACIAL_CRYSTAL),
        ICE(33, intArrayOf(3, 7, 9, 3), 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2f, GLACIAL_ICE),
        TURTLE(13, intArrayOf(4, 7, 10, 3), 10, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 2f, Items.AIR),
        MYSTERIOUS(33, intArrayOf(3, 6, 8, 3), 10, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, 2f, MYSTERIUS_ARMOR_SHARD);
        // <editor-fold defaultstate="collapsed" desc="Interface implementation">
        companion object {
            private val MAX_DAMAGE_ARRAY = intArrayOf(13, 15, 16, 11)
        }
        private val _name = "${ModLoadingContext.get().activeNamespace}:${name.toLowerCase()}"
        private val _repairMaterial : Ingredient by lazy {Ingredient.fromItems(repairMatrialItem)}
        override fun getName() = _name
        override fun getDurability(slot: EquipmentSlotType) = MAX_DAMAGE_ARRAY[slot.index] * maxDamageFactor
        override fun getDamageReductionAmount(p_200902_1_: EquipmentSlotType) = damageReductionArray[p_200902_1_.index]
        override fun getEnchantability() = enchantability
        override fun getSoundEvent() = soundEvent
        override fun getToughness() = toughness
        override fun getRepairMaterial() = _repairMaterial
        // </editor-fold>
    }

    // Food
    val SABER_TOOTHED_CAT_MEAT = ItemFoodBase("saber_toothed_cat_meat", "Saber-toothed Cat Meat", 3, 0.3F) {meat()}
    val SABER_TOOTHED_CAT_MEAT_COOKED = ItemFoodBase("saber_toothed_cat_meat_cooked", "Seared Saber-toothed Cat Meat", 9, 0.9F) {meat()}
    val GLACIAL_BERRY = ItemFoodBase("glacial_berry", "Glacial berry", 1, 0.1F)
    // Materials
    val GLACIAL_CRYSTAL = ItemBase("glacial_crystal", "Glacial Crystal", Glacia.ItemGroup.MATERIALS)
    val GLACIAL_ICE = ItemBase("glacial_ice", "Glacial Ice", Glacia.ItemGroup.MATERIALS)
    val CATALYST_CRYSTAL = ItemBase("catalyst_crystal", "Catalyst Crystal Shard", Glacia.ItemGroup.MATERIALS)
    val GLACIAL_ICE_POWDER = ItemBase("glacial_ice_powder", "Glacial Ice Powder", Glacia.ItemGroup.MATERIALS)
    val CRYSTAL_ROD = ItemBase("crystal_rod", "Crystal Rod", Glacia.ItemGroup.MATERIALS)
    val ICE_ROD = ItemBase("ice_rod", "Ice Rod", Glacia.ItemGroup.MATERIALS)
    val MAGIC_STONE_SHARD = ItemBase("magic_stone_shard", "Magic Stone Shard", Glacia.ItemGroup.MATERIALS)
    val GLACIAL_WOOD_ROD = ItemBase("glacial_wood_rod", "Glacial Wood Rod", Glacia.ItemGroup.MATERIALS)
    val QUADIUM_SALT = ItemBase("quadium_salt", "Quadium Salt", Glacia.ItemGroup.MATERIALS)
    // Misc
    val MYSTERIUS_ARMOR_SHARD = ItemBase("mysterious_armor_shard", "Mysterious Armor Shard", Glacia.ItemGroup.MISC)
    val PENGUIN_FEATHERS = ItemBase("penguin_feathers", "Penguin Feathers", Glacia.ItemGroup.MISC)
    val PLASMA_BUCKET = ItemBucketBase("plasma_bucket", "Plasma Bucket", Glacia.Fluids.PLASMA)
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
    val GLACIAL_LIGHTER = ItemGlacialLighter("glacial_lighter", "Glacial Lighter")
    // Combat
    val GLACIAL_WOOD_SWORD = ItemSwordBase("glacial_wood_sword", "Glacial Wood Sword", ItemTier.WOOD, 3, -2.3F)
    val CRYSTAl_SWORD = ItemSwordBase("crystal_sword", "Crystal Sword", ItemTier.IRON, 3, -2.3F)
    val ICE_SWORD = ItemSwordBase("ice_sword", "Ice Sword", ItemTier.DIAMOND, 4, -2.3F)
    val ICE_DAGGER = ItemSwordBase("ice_dagger", "Ice Dagger", ItemTier.DIAMOND, 3, -1.3F)
    val ICE_BASTARD_SWORD = ItemSwordBase("ice_bastard_sword", "Ice Bastard Sword", ItemTier.DIAMOND, 5, -2.7F)
    val ICE_WARHAMMER = ItemSwordBase("ice_warhammer", "Ice Warhammer", ItemTier.DIAMOND, 8, -3.8F)
    val CRYSTAL_HELMET = ItemArmorBase("crystal_helmet", "Crystal Helmet", ArmorMaterial.CRYSTAL, EquipmentSlotType.HEAD)
    val CRYSTAL_CHESTPLATE = ItemArmorBase("crystal_chestplate", "Crystal Chestplate", ArmorMaterial.CRYSTAL, EquipmentSlotType.CHEST)
    val CRYSTAL_LEGGINGS = ItemArmorBase("crystal_leggings", "Crystal Leggings", ArmorMaterial.CRYSTAL, EquipmentSlotType.LEGS)
    val CRYSTAL_BOOTS = ItemArmorBase("crystal_boots", "Crystal Boots", ArmorMaterial.CRYSTAL, EquipmentSlotType.FEET)
    val ICE_HELMET = ItemArmorBase("ice_helmet", "Ice Helmet", ArmorMaterial.ICE, EquipmentSlotType.HEAD)
    val ICE_CHESTPLATE = ItemArmorBase("ice_chestplate", "Ice Chestplate", ArmorMaterial.ICE, EquipmentSlotType.CHEST)
    val ICE_LEGGINGS = ItemArmorBase("ice_leggings", "Ice Leggings", ArmorMaterial.ICE, EquipmentSlotType.LEGS)
    val ICE_BOOTS = ItemArmorBase("ice_boots", "Ice Boots", ArmorMaterial.ICE, EquipmentSlotType.FEET)
    val TURTLE_HEAD = ItemArmorBase("turtle_head", "Turtle Head", ArmorMaterial.TURTLE, EquipmentSlotType.HEAD)
    val TURTLE_SHELL = ItemArmorBase("turtle_shell", "Turtle Shell", ArmorMaterial.TURTLE, EquipmentSlotType.CHEST)
    val TURTLE_LIMBS = ItemArmorBase("turtle_limbs", "Turtle Limbs", ArmorMaterial.TURTLE, EquipmentSlotType.LEGS)
    val TURTLE_PAWS = ItemArmorBase("turtle_paws", "Turtle Paws", ArmorMaterial.TURTLE, EquipmentSlotType.FEET)
    val MYSTERIOUS_CHESTPLATE = ItemArmorBase("mysterious_chestplate", "Mysterious Chestplate", ArmorMaterial.MYSTERIOUS, EquipmentSlotType.CHEST)
    // Brewing
    val QUADIUM_PEARL = ItemBase("quadium_pearl", "Quadium Pearl", Glacia.ItemGroup.BREWING)
    val QUADIUM_BULB = ItemBase("quadium_bulb", "Quadium Bulb", Glacia.ItemGroup.BREWING)
    val QUADIUM_BULB_BROKEN = ItemBase("quadium_bulb_broken", "Broken Quadium Bulb", Glacia.ItemGroup.BREWING)
    val RETURN_RUNE = ItemBase("return_rune", "Return To Overworld", Glacia.ItemGroup.BREWING)
    //*TODO val POTION_MORPH_SNOWMAN = ItemBase("potion_morph_snowman", "Morph Snowman", Glacia.ItemGroup.BREWING)
    // Bluestone
    val BLUESTONE = ItemBase("bluestone", "Bluestone", Glacia.ItemGroup.BLUESTONE)
}