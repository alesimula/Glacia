package com.greenapple.glacia.world.biome

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.SeaGrassConfig
import net.minecraft.world.gen.placement.IPlacementConfig
import net.minecraft.world.gen.placement.Placement
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig

//TODO sand, sand and gravel
class BiomeGlaciaRiver(registryName : String) : Biome(Biome.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilderConfig(Glacia.Blocks.GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SNOWY, true), Glacia.Blocks.GLACIAL_DIRT.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState))
        .precipitation(RainType.SNOW).category(Category.RIVER).depth(-0.5f).scale(0.0f).temperature(0.1f).downfall(0.5f).waterColor(4159204).waterFogColor(329011).parent(null as String?)) {
    init {
        setRegistryName(registryName)

        //this.addStructure(Feature.MINESHAFT, MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL))
        //DefaultBiomeFeatures.addCarvers(this)
        //DefaultBiomeFeatures.addStructures(this)
        DefaultBiomeFeatures.addLakes(this)
        //DefaultBiomeFeatures.addMonsterRooms(this)
        //DefaultBiomeFeatures.addStoneVariants(this)
        //DefaultBiomeFeatures.addOres(this)
        //DefaultBiomeFeatures.addSedimentDisks(this)
        DefaultBiomeFeatures.addSprings(this)
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SEAGRASS, SeaGrassConfig(48, 0.4), Placement.TOP_SOLID_HEIGHTMAP, IPlacementConfig.NO_PLACEMENT_CONFIG))
        DefaultBiomeFeatures.addFreezeTopLayer(this)
    }
}