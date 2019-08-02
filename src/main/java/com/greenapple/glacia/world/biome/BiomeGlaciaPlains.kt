package com.greenapple.glacia.world.biome

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.structure.MineshaftConfig
import net.minecraft.world.gen.feature.structure.MineshaftStructure
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig
import net.minecraft.world.gen.feature.structure.VillageConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig

class BiomeGlaciaPlains : Biome {
    constructor(registryName : String) : super(Biome.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilderConfig(Glacia.Blocks.GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SNOWY, true), Glacia.Blocks.GLACIAL_DIRT.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState))
            .precipitation(RainType.SNOW).category(Category.PLAINS).depth(0.125f).scale(0.05f).temperature(-30F).downfall(0.5f).waterColor(4159204).waterFogColor(329011).parent(null as String?)) {
        setRegistryName(registryName)

        //////SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG
        /*this.addStructure(Feature.VILLAGE, VillageConfig("village/plains/town_centers", 6))
        this.addStructure(Feature.PILLAGER_OUTPOST, PillagerOutpostConfig(0.004))
        this.addStructure(Feature.MINESHAFT, MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL))
        this.addStructure(Feature.STRONGHOLD, IFeatureConfig.NO_FEATURE_CONFIG)*/
        DefaultBiomeFeatures.addCarvers(this)
        //DefaultBiomeFeatures.addStructures(this)
        //TODO make one without lava
        GlaciaBiomeFeatures.addLakes(this)
        //DefaultBiomeFeatures.addMonsterRooms(this)
        //DefaultBiomeFeatures.func_222283_Y(this)
        //DefaultBiomeFeatures.addStoneVariants(this)
        GlaciaBiomeFeatures.addSedimentDisks(this)
        //TODO custom ore generation
        DefaultBiomeFeatures.addOres(this)
        GlaciaBiomeFeatures.addSprings(this)
        DefaultBiomeFeatures.addFreezeTopLayer(this)
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4))
    }
}