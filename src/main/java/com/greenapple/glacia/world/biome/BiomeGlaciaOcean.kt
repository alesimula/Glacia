package com.greenapple.glacia.world.biome

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.SeaGrassConfig
import net.minecraft.world.gen.feature.structure.*
import net.minecraft.world.gen.placement.IPlacementConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig

class BiomeGlaciaOcean : Biome {
    constructor(registryName : String) : super(Biome.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilderConfig(Glacia.Blocks.GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SNOWY, true), Glacia.Blocks.GLACIAL_DIRT.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState))
            .precipitation(RainType.SNOW).category(Category.OCEAN).depth(-1f).scale(0.1f).temperature(0F).downfall(0.5f).waterColor(4159204).waterFogColor(329011).parent(null as String?)) {
        setRegistryName(registryName)

        this.addStructure(Feature.MINESHAFT, MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL))
        this.addStructure(Feature.SHIPWRECK, ShipwreckConfig(false))
        this.addStructure(Feature.OCEAN_RUIN, OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3f, 0.9f))
        DefaultBiomeFeatures.addOceanCarvers(this)
        DefaultBiomeFeatures.addStructures(this)
        DefaultBiomeFeatures.addLakes(this)
        DefaultBiomeFeatures.addMonsterRooms(this)
        DefaultBiomeFeatures.addStoneVariants(this)
        DefaultBiomeFeatures.addOres(this)
        DefaultBiomeFeatures.addSedimentDisks(this)
        DefaultBiomeFeatures.func_222296_u(this)
        DefaultBiomeFeatures.addDefaultFlowers(this)
        DefaultBiomeFeatures.func_222348_W(this)
        DefaultBiomeFeatures.addMushrooms(this)
        DefaultBiomeFeatures.addReedsAndPumpkins(this)
        DefaultBiomeFeatures.addSprings(this)
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SEAGRASS, SeaGrassConfig(48, 0.3), Placement.TOP_SOLID_HEIGHTMAP, IPlacementConfig.NO_PLACEMENT_CONFIG))
        DefaultBiomeFeatures.func_222320_ai(this)
        DefaultBiomeFeatures.func_222287_ah(this)
        DefaultBiomeFeatures.addFreezeTopLayer(this)
        this.addSpawn(EntityClassification.WATER_CREATURE, Biome.SpawnListEntry(EntityType.SQUID, 1, 1, 4))
        this.addSpawn(EntityClassification.WATER_CREATURE, Biome.SpawnListEntry(EntityType.COD, 10, 3, 6))
        this.addSpawn(EntityClassification.WATER_CREATURE, Biome.SpawnListEntry(EntityType.DOLPHIN, 1, 1, 2))
        this.addSpawn(EntityClassification.AMBIENT, Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 1))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4))
        this.addSpawn(EntityClassification.MONSTER, Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1))
    }
}