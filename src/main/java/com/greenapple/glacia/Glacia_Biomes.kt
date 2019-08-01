package com.greenapple.glacia

import com.greenapple.glacia.world.biome.BiomeGlaciaBeach
import com.greenapple.glacia.world.biome.BiomeGlaciaOcean
import com.greenapple.glacia.world.biome.BiomeGlaciaPlains
import com.greenapple.glacia.world.biome.BiomeGlaciaRiver

object Glacia_Biomes {
    val PLAINS = BiomeGlaciaPlains("plains")
    val OCEAN = BiomeGlaciaOcean("ocean")
    val BEACH = BiomeGlaciaBeach("beach")
    val RIVER = BiomeGlaciaRiver("river")
}