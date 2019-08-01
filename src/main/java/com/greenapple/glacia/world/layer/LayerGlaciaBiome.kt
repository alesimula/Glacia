package com.greenapple.glacia.world.layer

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.id
import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.OverworldGenSettings
import net.minecraft.world.gen.layer.traits.IC0Transformer
import net.minecraftforge.common.BiomeManager

class LayerGlaciaBiome(private val settings: OverworldGenSettings?) : IC0Transformer {

    companion object {
        val BIOMES = listOf(
                BiomeManager.BiomeEntry(Glacia.Biomes.PLAINS, 3),
                BiomeManager.BiomeEntry(Glacia.Biomes.OCEAN, 2)
        )
    }

    override fun apply(context: INoiseRandom, value: Int): Int {
        return if (this.settings != null && this.settings.biomeId >= 0) this.settings.biomeId
        else getWeightedBiomeEntry(context).biome.id
    }

    private fun getWeightedBiomeEntry(context: INoiseRandom): BiomeManager.BiomeEntry {
        val totalWeight = net.minecraft.util.WeightedRandom.getTotalWeight(BIOMES)
        val weight = context.random(totalWeight)
        return net.minecraft.util.WeightedRandom.getRandomItem(BIOMES, weight) as BiomeManager.BiomeEntry
    }
}