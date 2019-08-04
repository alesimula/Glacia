package com.greenapple.glacia.world.biome

import com.google.common.collect.Lists
import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.placement.*

object GlaciaBiomeFeatures {
    fun addLakes(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Feature.LAKE, LakesConfig(Blocks.WATER.defaultState), Placement.WATER_LAKE, LakeChanceConfig(4)))
    }

    fun addSprings(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, LiquidsConfig(Fluids.WATER.defaultState), Placement.COUNT_BIASED_RANGE, CountRangeConfig(50, 8, 8, 256)))
    }

    fun addSedimentDisks(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, SphereReplaceConfig(Glacia.Blocks.SNOWY_SAND.defaultState, 7, 2, Lists.newArrayList(Glacia.Blocks.GLACIAL_DIRT.defaultState, Glacia.Blocks.GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SNOWY, true))), Placement.COUNT_TOP_SOLID, FrequencyConfig(3)))
        //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, SphereReplaceConfig(Blocks.CLAY.defaultState, 4, 1, Lists.newArrayList(Blocks.DIRT.defaultState, Blocks.CLAY.defaultState)), Placement.COUNT_TOP_SOLID, FrequencyConfig(1)))
        //biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, SphereReplaceConfig(Blocks.GRAVEL.defaultState, 6, 2, Lists.newArrayList(Blocks.DIRT.defaultState, Blocks.GRASS_BLOCK.defaultState)), Placement.COUNT_TOP_SOLID, FrequencyConfig(1)))
    }

    fun addGlacialTrees(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Glacia.Feature.GLACIAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, AtSurfaceWithExtraConfig(10, 0.1f, 1)))
    }

    //func_222299_R
    fun addPlainsVegetation(biomeIn: Biome) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, MultipleRandomFeatureConfig(arrayOf<Feature<*>>(Glacia.Feature.GLACIAL_TREE), arrayOf<IFeatureConfig>(IFeatureConfig.NO_FEATURE_CONFIG), floatArrayOf(0.33333334f), Glacia.Feature.GLACIAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, AtSurfaceWithExtraConfig(0, 0.05f, 1)))
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Glacia.Feature.FLORA, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOISE_HEIGHTMAP_32, NoiseDependant(-0.8, 15, 4)))
        //biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.GRASS, GrassFeatureConfig(Blocks.GRASS.defaultState), Placement.NOISE_HEIGHTMAP_DOUBLE, NoiseDependant(-0.8, 5, 10)))
    }
}