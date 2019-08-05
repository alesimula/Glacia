package com.greenapple.glacia.registry

import com.greenapple.glacia.world.biome.*
import net.minecraft.world.biome.Biome

object Glacia_Biomes : IForgeRegistryCollection<Biome> {
    val PLAINS = BiomeGlaciaPlains("plains")
    val FOREST = BiomeGlaciaForest("forest")
    val OCEAN = BiomeGlaciaOcean("ocean")
    val BEACH = BiomeGlaciaBeach("beach")
    val RIVER = BiomeGlaciaRiver("river")
}