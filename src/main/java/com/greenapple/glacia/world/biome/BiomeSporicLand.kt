package com.greenapple.glacia.world.biome

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.entity.EntityClassification
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.placement.IPlacementConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig

class BiomeSporicLand(registryName : String) : Biome(Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilderConfig(Glacia.Blocks.GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SPORIC, true), Glacia.Blocks.GLACIAL_DIRT.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState)).precipitation(RainType.NONE).category(Category.THEEND).depth(0.1f).scale(0.2f).temperature(0.5f).downfall(0.5f).waterColor(4159204).waterFogColor(329011).parent(null as String?)) {
    init {
        setRegistryName(registryName)
        addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.CHORUS_PLANT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHORUS_PLANT.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)))
        this.addSpawn(EntityClassification.CREATURE, SpawnListEntry(Glacia.Entity.GLACIAL_SEEKER, 10, 5, 8))
        this.addSpawn(EntityClassification.MONSTER, SpawnListEntry(Glacia.Entity.GLACIAL_SEEKER, 100, 5, 8))
    }

    override fun doesSnowGenerate(world: IWorldReader, pos: BlockPos) = false
}