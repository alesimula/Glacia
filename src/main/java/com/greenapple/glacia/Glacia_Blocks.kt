package com.greenapple.glacia

import com.greenapple.glacia.block.*
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.util.BlockRenderLayer

object Glacia_Blocks {
    val GLACIAL_DIRT = BlockGlaciaDirt("glacial_dirt", "Glacial dirt", Material.EARTH) {hardnessAndResistance(0.6F).sound(SoundType.GROUND)}
    val GLACIAL_BEDROCK = BlockBase("glacial_bedrock", "Glacial bedrock", Material.ROCK) {hardnessAndResistance(-1.0F, 3600000.0F).noDrops()}
    val GLACIAL_STONE = BlockBase("glacial_stone", "Glacial stone", Material.ROCK) {hardnessAndResistance(2F, 10F)}
    val GLACIAL_COBBLESTONE = BlockBase("glacial_cobblestone", "Glacial cobblestone", Material.ROCK) {hardnessAndResistance(1.8F, 8F)}
    val GLACIAL_MAGIC_STONE = BlockBase("glacial_magic_stone", "Glacial magic stone", Material.ROCK) {hardnessAndResistance(2.3F, 12F)}
    val GLACIA_PORTAL = BlockGlaciaPortal("glacia_portal", "Glacia portal", Material.PORTAL) {hardnessAndResistance(2.3F, 12F)}
    //TODO GLACIAL_FIRE
    val GLACIAL_TREE_LOG = BlockRotatedBase("glacial_tree_log", "Glacial tree log", Material.WOOD) {hardnessAndResistance(2F).sound(SoundType.WOOD)}
    val GLACIAL_TREE_LEAVES = BlockBase("glacial_tree_leaves", "Glacial tree leaves", Material.LEAVES) {hardnessAndResistance(0.2F).sound(SoundType.PLANT)}
            .apply {renderLayer = BlockRenderLayer.CUTOUT}
    val GLACIAL_PLANKS = BlockBase("glacial_planks", "Glacial wood planks", Material.WOOD) {hardnessAndResistance(2F, 3F).sound(SoundType.WOOD)}
    //TODO SLAB
    val GLACIAL_STAIRS = BlockStairsBase("glacial_stairs", "Glacial wood stairs", GLACIAL_PLANKS)
    //TODO DOOR
    //TODO FENCE
    //TODO GATE
    val GLACIAL_CRYSTAL_ORE = BlockBase("glacial_crystal_ore", "Glacial crystal ore", Material.ROCK) {hardnessAndResistance(3.5F, 7F)}
    val GLACIAL_ICE_ORE = BlockBase("glacial_ice_ore", "Glacial ice ore", Material.ROCK) {hardnessAndResistance(4F, 8F)}
    //TODO GLACIAL SAPLING
    //TODO ICE FLOWER
    /*  TODO sand block properties  */
    val SNOWY_SAND = BlockGlaciaDirt("snowy_sand", "Snowy sand", Material.SAND) {hardnessAndResistance(0.5F).sound(SoundType.SAND)}
    val GLACIAL_BERRY = BlockPlantFacingBase("glacial_berry", "Glacial berry", Material.ORGANIC, 8.0, 14.0) {doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.PLANT)}
    //TODO CRYSTAL OF POWER (custom model)
    val MAGIC_ICE = BlockGlaciaDirt("magic_ice", "Magic ice", Material.ICE) {hardnessAndResistance(0.8F).slipperiness(0.98F).variableOpacity().sound(SoundType.GLASS)}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; seeThroughGroup = true; isTranslucent = true}
    val ICE_COLUMN = BlockColumnBase("ice_column", "Ice column", Material.ICE) {hardnessAndResistance(1.2F, 3F).slipperiness(0.98F).sound(SoundType.GLASS).variableOpacity()}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; seeThroughGroup = true}
    val GRANITE_COLUMN = BlockColumnBase("granite_column", "Granite column", Material.ROCK) {hardnessAndResistance(1.8F, 8F)}
            .apply {renderLayer = BlockRenderLayer.CUTOUT}
    val COMPACTED_ICE = BlockGlaciaDirt("compacted_ice", "Compacted ice", Material.ICE) {hardnessAndResistance(1F).slipperiness(0.98F).variableOpacity().sound(SoundType.GLASS)}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; seeThroughGroup = true; isTranslucent = true}
}