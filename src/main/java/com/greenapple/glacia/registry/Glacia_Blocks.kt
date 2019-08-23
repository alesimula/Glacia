package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.*
import net.minecraft.block.Block
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.fluid.Fluids
import net.minecraft.potion.Effects
import net.minecraft.util.BlockRenderLayer

@Suppress("UNUSED")
object Glacia_Blocks : IForgeRegistryCollection<Block> {
    val GLACIAL_DIRT = BlockGlaciaDirt("glacial_dirt", "Glacial dirt", Glacia_ItemGroup.BLOCKS, Material.EARTH) {hardnessAndResistance(0.6F).sound(SoundType.GROUND)}
            .addItemVariant("snowy", "Glacial snowy dirt") {with(BlockGlaciaDirt.SNOWY, true)}
    val PERMAFROST = BlockBase("permafrost", "Permafrost", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(-1.0F, 3600000.0F).noDrops()}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; seeThroughGroup = true}
    val GLACIAL_STONE = BlockBase("glacial_stone", "Glacial stone", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(2F, 10F)}
    val GLACIAL_COBBLESTONE = BlockBase("glacial_cobblestone", "Glacial cobblestone", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(1.8F, 8F)}
    val GLACIAL_MAGIC_STONE = BlockBase("glacial_magic_stone", "Glacial magic stone", Glacia_ItemGroup.BREWING, Material.ROCK) {hardnessAndResistance(2.3F, 12F)}
    val GLACIA_PORTAL = BlockGlaciaPortal("glacia_portal", "Glacia portal", Glacia_ItemGroup.BREWING) {hardnessAndResistance(2.3F, 12F)}
    val GLACIAL_FIRE = BlockFireBase("glacial_fire", "Glacial fire")
    val GLACIAL_TREE_LOG = BlockRotatedBase("glacial_tree_log", "Glacial tree log", Glacia_ItemGroup.BLOCKS, Material.WOOD) {hardnessAndResistance(2F).sound(SoundType.WOOD)}
    val GLACIAL_TREE_LEAVES = BlockBase("glacial_tree_leaves", "Glacial tree leaves", Glacia_ItemGroup.DECORATIONS, Material.LEAVES) {hardnessAndResistance(0.2F).sound(SoundType.PLANT)}
            .apply {renderLayer = BlockRenderLayer.CUTOUT}
    val GLACIAL_PLANKS = BlockBase("glacial_planks", "Glacial wood planks", Glacia_ItemGroup.BLOCKS, Material.WOOD) {hardnessAndResistance(2F, 3F).sound(SoundType.WOOD)}
    val GLACIAL_SLAB = BlockSlabBase("glacial_slab", "Glacial wood slab", Glacia_ItemGroup.BLOCKS, GLACIAL_PLANKS)
    val GLACIAL_STAIRS = BlockStairsBase("glacial_stairs", "Glacial wood stairs", Glacia_ItemGroup.BLOCKS, GLACIAL_PLANKS)
    val GLACIAL_DOOR = BlockDoorBase("glacial_door", "Glacial door", Glacia_ItemGroup.DECORATIONS, GLACIAL_PLANKS)
    val GLACIAL_FENCE = BlockFenceBase("glacial_fence", "Glacial fence", Glacia_ItemGroup.DECORATIONS, GLACIAL_PLANKS)
    val GLACIAL_FENCE_GATE = BlockFenceGateBase("glacial_fence_gate", "Glacial fence gate", Glacia_ItemGroup.DECORATIONS, GLACIAL_PLANKS)
    val GLACIAL_CRYSTAL_ORE = BlockBase("glacial_crystal_ore", "Glacial crystal ore", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(3.5F, 7F)}
    val GLACIAL_ICE_ORE = BlockBase("glacial_ice_ore", "Glacial ice ore", Glacia_ItemGroup.BLOCKS, Material.ROCK) {hardnessAndResistance(4F, 8F)}
    //TODO GLACIAL SAPLING
    val ICE_FLOWER = BlockFlowerBase("ice_flower", "Ice flower", Glacia_ItemGroup.DECORATIONS, Effects.SLOWNESS, 4)
    /*  TODO sand block properties  */
    val SNOWY_SAND = BlockFallingBase("snowy_sand", "Snowy sand", Glacia_ItemGroup.BLOCKS, Material.SAND) {hardnessAndResistance(0.5F).sound(SoundType.SAND)}
    val GLACIAL_BERRY = BlockPlantFacingBase("glacial_berry", "Glacial berry", Glacia_ItemGroup.DECORATIONS, Material.ORGANIC, 8.0, 14.0) {doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.PLANT)}
    val CATALYST_CRYSTAL = BlockGrowingBase("catalyst_crystal", "Catalyst crystal", Glacia_ItemGroup.DECORATIONS, Material.ROCK, 13.0, 9.6) {doesNotBlockMovement().hardnessAndResistance(3.0F).lightValue(14).variableOpacity().sound(SoundType.GLASS)}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; isTranslucent = true}
            .validGroundBlocks {material === Material.ROCK}
    val MAGIC_ICE = BlockBase("magic_ice", "Magic ice", Glacia_ItemGroup.BLOCKS, Material.ICE) {hardnessAndResistance(0.8F).slipperiness(0.98F).variableOpacity().sound(SoundType.GLASS)}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; seeThroughGroup = true; isTranslucent = true}
    val ICE_COLUMN = BlockColumnBase("ice_column", "Ice column", Glacia_ItemGroup.DECORATIONS, Material.ICE) {hardnessAndResistance(1.2F, 3F).slipperiness(0.98F).sound(SoundType.GLASS).variableOpacity()}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; seeThroughGroup = true}
    val GRANITE_COLUMN = BlockColumnBase("granite_column", "Granite column", Glacia_ItemGroup.DECORATIONS, Material.ROCK) {hardnessAndResistance(1.8F, 8F)}
            .apply {renderLayer = BlockRenderLayer.CUTOUT}
    val COMPACTED_ICE = BlockBase("compacted_ice", "Compacted ice", Glacia_ItemGroup.BLOCKS, Material.ICE) {hardnessAndResistance(1F).slipperiness(0.98F).variableOpacity().sound(SoundType.GLASS)}
            .apply {renderLayer = BlockRenderLayer.TRANSLUCENT; seeThroughGroup = true; isTranslucent = true}
    //TODO add plasma once the fluid system has been properly implemented
    //val PLASMA = BlockFluidBase("plasma", "plasma", null, Material.LAVA, Glacia.Fluids.PLASMA) {lightValue(15)}
}