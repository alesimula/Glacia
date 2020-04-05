package com.greenapple.glacia.world.biome

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import com.google.common.collect.Lists
import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import com.greenapple.glacia.delegate.ReflectEnumConstructor
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer
import net.minecraft.world.gen.placement.*
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator
import net.minecraft.world.gen.treedecorator.TreeDecorator
import net.minecraftforge.common.IPlantable
import java.util.function.Predicate

private val fillerBlockTypeConstructor by ReflectEnumConstructor<OreFeatureConfig.FillerBlockType>()
fun fillerBlockType(name: String, shouldReplace: BlockState.()->Boolean) = fillerBlockTypeConstructor(name, Predicate(shouldReplace))

object GlaciaBiomeFeatures {
    val FILLER_GLACIAL_STONE = fillerBlockType("glacial_stone") {block === Glacia.Blocks.GLACIAL_STONE}
    val PLASMA_SPRING_CONFIG = LiquidsConfig(Glacia.Fluids.PLASMA.defaultState, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))
    val GLACIA_TREE : TreeFeatureConfig = TreeFeatureConfig.Builder(SimpleBlockStateProvider(Glacia.Blocks.GLACIAL_TREE_LOG.defaultState), SimpleBlockStateProvider(Glacia.Blocks.GLACIAL_TREE_LEAVES.defaultState), BlobFoliagePlacer(2, 0)).baseHeight(5).heightRandA(2).foliageHeight(3).ignoreVines().setSapling(Glacia.Blocks.GLACIAL_SAPLING as IPlantable).build()


    fun addCarvers(biome: Biome) {
        biome.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(Glacia.WorldCarver.CAVE, ProbabilityConfig(0.14285715f)))
        //biome.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(WorldCarver.CANYON, ProbabilityConfig(0.02f)))
    }

    fun addLakes(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(BlockStateFeatureConfig(Blocks.WATER.defaultState)).withPlacement(Placement.WATER_LAKE.configure(ChanceConfig(4))))
        biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(BlockStateFeatureConfig(Glacia.Blocks.PLASMA.defaultState)).withPlacement(Placement.LAVA_LAKE.configure(ChanceConfig(80))))
    }

    fun addSprings(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.WATER_SPRING_CONFIG).withPlacement(Placement.COUNT_BIASED_RANGE.configure(CountRangeConfig(50, 8, 8, 256))))
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(PLASMA_SPRING_CONFIG).withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(CountRangeConfig(20, 8, 16, 256))))
    }

    fun addSedimentDisks(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(SphereReplaceConfig(Glacia.Blocks.SNOWY_SAND.defaultState, 7, 2, Lists.newArrayList(Glacia.Blocks.GLACIAL_DIRT.defaultState, Glacia.Blocks.GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SNOWY, true)))).withPlacement(Placement.COUNT_TOP_SOLID.configure(FrequencyConfig(3))))
        //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(SphereReplaceConfig(DefaultBiomeFeatures.CLAY, 4, 1, Lists.newArrayList(DefaultBiomeFeatures.DIRT, DefaultBiomeFeatures.CLAY))).withPlacement(Placement.COUNT_TOP_SOLID.configure(FrequencyConfig(1))))
        //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(SphereReplaceConfig(DefaultBiomeFeatures.GRAVEL, 6, 2, Lists.newArrayList(DefaultBiomeFeatures.DIRT, DefaultBiomeFeatures.GRASS_BLOCK))).withPlacement(Placement.COUNT_TOP_SOLID.configure(FrequencyConfig(1))))
    }

    //TODO config maybe?
    fun addGlacialTrees(biomeIn: Biome) {
        //biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Glacia.Feature.GLACIAL_TREE.noConfig.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(10, 0.1f, 1))))
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.NORMAL_TREE.withConfiguration(GLACIA_TREE).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(10, 0.1f, 1))))
    }

    /*fun addGlacialTrees(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Glacia.Feature.GLACIAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, AtSurfaceWithExtraConfig(10, 0.1f, 1)))
    }*/

    fun addBerries(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Glacia.Feature.BERRIES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOISE_HEIGHTMAP_32.configure(NoiseDependant(-0.8, 15, 4))))
    }

    fun addIceTowers(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Glacia.Feature.ICE_TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(1, 0f, 0))))
    }

    fun addPlainsVegetation(biomeIn: Biome) {
        //biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(MultipleRandomFeatureConfig(listOf(Glacia.Feature.GLACIAL_TREE.noConfig.func_227227_a_(0.33333334F)), Glacia.Feature.GLACIAL_TREE.noConfig)).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(0, 0.05f, 1))))
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(MultipleRandomFeatureConfig(listOf(Feature.FANCY_TREE.withConfiguration(GLACIA_TREE).func_227227_a_(0.1F)), Feature.NORMAL_TREE.withConfiguration(GLACIA_TREE))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(AtSurfaceWithExtraConfig(0, 0.1F, 1))));
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Glacia.Feature.FLORA.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOISE_HEIGHTMAP_32.configure(NoiseDependant(-0.8, 15, 4))))
    }

    fun addOres(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(OreFeatureConfig(FILLER_GLACIAL_STONE, Glacia.Blocks.GLACIAL_CRYSTAL_ORE.defaultState, 9)).withPlacement(Placement.COUNT_RANGE.configure(CountRangeConfig(20, 0, 0, 64))))
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(OreFeatureConfig(FILLER_GLACIAL_STONE, Glacia.Blocks.CATALYST_CRYSTAL.defaultState, 8)).withPlacement(Placement.COUNT_RANGE.configure(CountRangeConfig(8, 0, 0, 16))))
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(OreFeatureConfig(FILLER_GLACIAL_STONE, Glacia.Blocks.GLACIAL_ICE_ORE.defaultState, 8)).withPlacement(Placement.COUNT_RANGE.configure(CountRangeConfig(1, 0, 0, 16))))
    }
}