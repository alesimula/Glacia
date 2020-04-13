package com.greenapple.glacia.world.biome

import com.greenapple.glacia.Glacia
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorldReader
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig

//TODO sand, sand and gravel
class BiomeGlaciaBeach(registryName : String) : Biome(Biome.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilderConfig(Glacia.Blocks.SNOWY_SAND.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState, Glacia.Blocks.SNOWY_SAND.defaultState))
        .precipitation(RainType.SNOW).category(Category.BEACH).depth(0.0f).scale(0.025f).temperature(0.1f).downfall(0.4f).waterColor(4159204).waterFogColor(329011).parent(null as String?)) {
    init {
        setRegistryName(registryName)

        //this.addStructure(Feature.MINESHAFT, MineshaftConfig(0.004, MineshaftStructure.Type.NORMAL))
        this.addStructure(Feature.BURIED_TREASURE.withConfiguration(BuriedTreasureConfig(0.01f)))
        //this.addStructure(Feature.SHIPWRECK, ShipwreckConfig(true))
        GlaciaBiomeFeatures.addCarvers(this)
        //DefaultBiomeFeatures.addStructures(this)
        GlaciaBiomeFeatures.addLakes(this)
        //DefaultBiomeFeatures.addMonsterRooms(this)
        GlaciaBiomeFeatures.addStoneVariants(this)
        GlaciaBiomeFeatures.addOres(this)
        //DefaultBiomeFeatures.addSedimentDisks(this)
        GlaciaBiomeFeatures.addSprings(this)
        DefaultBiomeFeatures.addFreezeTopLayer(this)
    }

    override fun doesSnowGenerate(world: IWorldReader, pos: BlockPos) = false
}