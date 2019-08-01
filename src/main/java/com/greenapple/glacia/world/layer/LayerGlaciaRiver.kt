package com.greenapple.glacia.world.layer

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.id
import net.minecraft.world.biome.Biomes
import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.area.IArea
import net.minecraft.world.gen.layer.traits.IAreaTransformer2
import net.minecraft.world.gen.layer.traits.IC0Transformer
import net.minecraft.world.gen.layer.traits.ICastleTransformer
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer

object LayerGlaciaRiver : ICastleTransformer {

    val VANILLA_RIVER by lazy {Biomes.RIVER.id}
    val RIVER by lazy {Glacia.Biomes.RIVER.id}

    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int): Int {
        val i = riverFilter(center)
        return if (i == riverFilter(east) && i == riverFilter(north) && i == riverFilter(west) && i == riverFilter(south)) -1 else RIVER
    }

    private fun riverFilter(biomeId: Int): Int {
        return if (biomeId >= 2) 2 + (biomeId and 1) else biomeId
    }


    val START = IC0Transformer {context, value ->
        if (GlaciaLayerUtils.isShallowOcean(value)) value else context.random(299999) + 2
    }

    val MIX : IAreaTransformer2 = object : IAreaTransformer2, IDimOffset0Transformer {
        override fun apply(context: INoiseRandom, area1: IArea, area2: IArea, value1: Int, value2: Int): Int {
            val i = area1.getValue(this.func_215721_a(value1), this.func_215722_b(value2))
            val j = area2.getValue(this.func_215721_a(value1), this.func_215722_b(value2))
            return when {
                GlaciaLayerUtils.isOcean(i) -> i
                j == VANILLA_RIVER || j == RIVER -> RIVER
                else -> i
            }
        }
    }
}