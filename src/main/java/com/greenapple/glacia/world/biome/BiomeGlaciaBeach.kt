package com.greenapple.glacia.world.biome

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig
import net.minecraft.world.gen.feature.structure.MineshaftConfig
import net.minecraft.world.gen.feature.structure.MineshaftStructure
import net.minecraft.world.gen.feature.structure.ShipwreckConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig

//TODO sand, sand and gravel
class BiomeGlaciaBeach(registryName : String) : Biome(Biome.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilderConfig(Glacia.Blocks.SNOWY_SAND.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState))
        .precipitation(RainType.SNOW).category(Category.BEACH).depth(0.0f).scale(0.025f).temperature(0.1f).downfall(0.4f).waterColor(4159204).waterFogColor(329011).parent(null as String?)) {
    init {
        setRegistryName(registryName)

        //this.addStructure(Feature.MINESHAFT, MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL))
        this.addStructure(Feature.BURIED_TREASURE, BuriedTreasureConfig(0.01f))
        //this.addStructure(Feature.SHIPWRECK, ShipwreckConfig(true))
        //DefaultBiomeFeatures.addCarvers(this)
        //DefaultBiomeFeatures.addStructures(this)
        DefaultBiomeFeatures.addLakes(this)
        //DefaultBiomeFeatures.addMonsterRooms(this)
        //DefaultBiomeFeatures.addStoneVariants(this)
        //DefaultBiomeFeatures.addOres(this)
        //DefaultBiomeFeatures.addSedimentDisks(this)
        DefaultBiomeFeatures.addSprings(this)
        DefaultBiomeFeatures.addFreezeTopLayer(this)
    }
}