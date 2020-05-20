package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.*
import com.greenapple.glacia.utils.PropertyPair
import com.greenapple.glacia.utils.RenderTypeBase
import com.greenapple.glacia.utils.deepClone
import com.greenapple.glacia.utils.overrideLightValue
import com.greenapple.glacia.world.biome.GlaciaBiomeFeatures
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.item.ItemTier
import net.minecraft.potion.Effects

@Suppress("UNUSED")
object Glacia_Blocks : IForgeRegistryCollection<Block> {
    val PLASMA_TORCH = BlockTorchBase("plasma_torch") {Glacia.Items.PLASMA_TORCH}
    val PLASMA_WALL_TORCH = BlockWallTorchBase("plasma_wall_torch") {Glacia.Items.PLASMA_TORCH}
    val GLACIAL_DIRT = BlockGlaciaDirt("glacial_dirt", "Glacial dirt", Glacia_ItemGroup.BLOCKS, Material.EARTH) {hardnessAndResistance(0.6F).sound(SoundType.GROUND)}
            .addItemVariant("snowy", "Glacial snowy dirt") {with(BlockGlaciaDirt.SNOWY, true)}
            .addItemVariant("sporic", "Glacial sporic dirt") {with(BlockGlaciaDirt.SPORIC, true)}.also {it.overrideLightValue(15, PropertyPair(BlockGlaciaDirt.SPORIC, true))}
    val PERMAFROST = BlockBase("permafrost", "Permafrost", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(-1.0F, 3600000.0F).noDrops().notSolid()}
            .apply {seeThroughGroup = true; renderType = RenderTypeBase.TRANSLUCENT}
    val GLACIAL_STONE = BlockBase("glacial_stone", "Glacial stone", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(2F, 10F)}
    val GLACIAL_COBBLESTONE = BlockBase("glacial_cobblestone", "Glacial cobblestone", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(1.8F, 8F)}
    val GLACIAL_MAGIC_STONE = BlockBase("glacial_magic_stone", "Glacial magic stone", Glacia_ItemGroup.BREWING, Material.ROCK) {hardnessAndResistance(2.3F, 12F)}
    val GLACIA_PORTAL = BlockGlaciaPortal("glacia_portal", "Glacia portal")
    val GLACIAL_FIRE = BlockFireBase("glacial_fire", "Glacial fire")
    val GLACIAL_TREE_LOG = BlockRotatedBase("glacial_tree_log", "Glacial tree log", Glacia_ItemGroup.BLOCKS, Material.WOOD) {hardnessAndResistance(2F).sound(SoundType.WOOD)}
    val GLACIAL_TREE_LEAVES = BlockBase("glacial_tree_leaves", "Glacial tree leaves", Glacia_ItemGroup.DECORATIONS, Material.LEAVES) {hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()}.apply {
        renderType = RenderTypeBase.CUTOUT
    }
    val GLACIAL_PLANKS = BlockBase("glacial_planks", "Glacial wood planks", Glacia_ItemGroup.BLOCKS, Material.WOOD) {hardnessAndResistance(2F, 3F).sound(SoundType.WOOD)}
    val GLACIAL_SLAB = BlockSlabBase("glacial_slab", "Glacial wood slab", Glacia_ItemGroup.BLOCKS, GLACIAL_PLANKS)
    val GLACIAL_STAIRS = BlockStairsBase("glacial_stairs", "Glacial wood stairs", Glacia_ItemGroup.BLOCKS, GLACIAL_PLANKS)
    val GLACIAL_DOOR = BlockDoorBase("glacial_door", "Glacial door", Glacia_ItemGroup.DECORATIONS, GLACIAL_PLANKS)
    val GLACIAL_FENCE = BlockFenceBase("glacial_fence", "Glacial fence", Glacia_ItemGroup.DECORATIONS, GLACIAL_PLANKS)
    val GLACIAL_FENCE_GATE = BlockFenceGateBase("glacial_fence_gate", "Glacial fence gate", Glacia_ItemGroup.DECORATIONS, GLACIAL_PLANKS)
    val GLACIAL_CRYSTAL_ORE = BlockOreBase("glacial_crystal_ore", "Glacial crystal ore", Glacia.Items.GLACIAL_CRYSTAL) {hardnessAndResistance(3.5F, 7F)}
    val GLACIAL_ICE_ORE = BlockOreBase("glacial_ice_ore", "Glacial ice ore", Glacia.Items.GLACIAL_ICE, ItemTier.IRON) {hardnessAndResistance(4F, 8F)}
    val GLACIAL_SAPLING = BlockSaplingBase("glacial_sapling", "Glacial sapling", Glacia_ItemGroup.DECORATIONS) {GlaciaBiomeFeatures.GLACIA_TREE}
    val ICE_FLOWER = BlockFlowerBase("ice_flower", "Ice flower", Glacia_ItemGroup.DECORATIONS, Effects.SLOWNESS, 4)
    val SNOWY_SAND = BlockFallingBase("snowy_sand", "Snowy sand", Glacia_ItemGroup.BLOCKS, Material.SAND) {hardnessAndResistance(0.5F).sound(SoundType.SAND)}
    val GLACIAL_BERRY = BlockPlantFacingBase("glacial_berry", "Glacial berry", Glacia_ItemGroup.DECORATIONS, Material.ORGANIC, 8.0, 14.0) {doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.PLANT)}
    val CATALYST_CRYSTAL = BlockGrowingOreBase("catalyst_crystal", "Catalyst crystal", Glacia.Items.CATALYST_CRYSTAL, ItemTier.IRON, true, 13.0, 9.6) {doesNotBlockMovement().hardnessAndResistance(3.0F).lightValue(14).variableOpacity().sound(SoundType.GLASS).notSolid()}
            .apply {isTranslucent = true; renderType = RenderTypeBase.TRANSLUCENT}
    val MAGIC_ICE = BlockBase("magic_ice", "Magic ice", Glacia_ItemGroup.BLOCKS, Material.ICE) {hardnessAndResistance(0.8F).slipperiness(0.98F).variableOpacity().sound(SoundType.GLASS).notSolid()}
            .apply {seeThroughGroup = true; isTranslucent = true; renderType = RenderTypeBase.TRANSLUCENT}
    val ICE_COLUMN = BlockColumnBase("ice_column", "Ice column", Glacia_ItemGroup.DECORATIONS, Material.ROCK) {hardnessAndResistance(1.2F, 3F).slipperiness(0.98F).sound(SoundType.GLASS).variableOpacity().notSolid()}
            .apply {seeThroughGroup = true; renderType = RenderTypeBase.TRANSLUCENT}
    //TODO solid or not solid?
    val GRANITE_COLUMN = BlockColumnBase("granite_column", "Granite column", Glacia_ItemGroup.DECORATIONS, Material.ROCK) {hardnessAndResistance(1.8F, 8F).notSolid()}
    val COMPACTED_ICE = BlockBase("compacted_ice", "Compacted ice", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(1F).slipperiness(0.98F).variableOpacity().sound(SoundType.GLASS).notSolid()}
            .apply {seeThroughGroup = true; isTranslucent = true; renderType = RenderTypeBase.TRANSLUCENT}
    val ICE_BRICKS = BlockBase("ice_bricks", "Ice Bricks", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(1F).slipperiness(0.98F).variableOpacity().sound(SoundType.GLASS).notSolid()}
            .apply {seeThroughGroup = true; isTranslucent = true; renderType = RenderTypeBase.TRANSLUCENT}
    val QUADIUM_SALT = BlockExplosiveBase("quadium_salt", "Quadium Salt Block", Glacia.Items.QUADIUM_SALT, ItemTier.DIAMOND) {hardnessAndResistance(1.8F, 9F).variableOpacity().sound(SoundType.GLASS).notSolid()}
            .apply {seeThroughGroup = true; isTranslucent = true; renderType = RenderTypeBase.TRANSLUCENT}
    val ICE_BRICKS_SLAB = BlockSlabBase("ice_bricks_slab", "Ice Bricks Slab", Glacia_ItemGroup.BLOCKS, ICE_BRICKS)
            .apply {seeThroughGroup = true; isTranslucent = true; renderType = RenderTypeBase.TRANSLUCENT}
    val PLASMA = BlockFluidBase("plasma", "Plasma", null, Material.LAVA.deepClone(), Glacia.Fluids.PLASMA) {lightValue(15).noDrops()}
}