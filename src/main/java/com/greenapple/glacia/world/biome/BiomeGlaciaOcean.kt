package com.greenapple.glacia.world.biome

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
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

        this.addStructure(Feature.MINESHAFT.withConfiguration(MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL)))
        this.addStructure(Feature.SHIPWRECK.withConfiguration(ShipwreckConfig(false)))
        this.addStructure(Feature.OCEAN_RUIN.withConfiguration(OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3f, 0.9f)))
        //TODO ocean carvers
        GlaciaBiomeFeatures.addCarvers(this)
        DefaultBiomeFeatures.addStructures(this)
        GlaciaBiomeFeatures.addLakes(this)
        DefaultBiomeFeatures.addMonsterRooms(this)
        GlaciaBiomeFeatures.addStoneVariants(this)
        GlaciaBiomeFeatures.addOres(this)
        GlaciaBiomeFeatures.addSedimentDisks(this)
        DefaultBiomeFeatures.addScatteredOakTrees(this)
        DefaultBiomeFeatures.addDefaultFlowers(this)
        DefaultBiomeFeatures.addSparseGrass(this)
        DefaultBiomeFeatures.addMushrooms(this)
        DefaultBiomeFeatures.addReedsAndPumpkins(this)
        GlaciaBiomeFeatures.addSprings(this)
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEAGRASS.withConfiguration(SeaGrassConfig(48, 0.3)).withPlacement(Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)))
        DefaultBiomeFeatures.func_222320_ai(this)
        DefaultBiomeFeatures.addExtraKelp(this)
        DefaultBiomeFeatures.addFreezeTopLayer(this)

        this.addSpawn(EntityClassification.CREATURE, SpawnListEntry(EntityType.POLAR_BEAR, 15, 1, 2))
    }

    override fun doesSnowGenerate(world: IWorldReader, pos: BlockPos) = false
}