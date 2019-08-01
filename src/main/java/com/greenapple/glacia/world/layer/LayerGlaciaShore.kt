package com.greenapple.glacia.world.layer

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.id
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.INoiseRandom
import net.minecraft.world.gen.layer.traits.ICastleTransformer

object LayerGlaciaShore : ICastleTransformer {

    override fun apply(context: INoiseRandom, north: Int, west: Int, south: Int, east: Int, center: Int): Int {
        val biome = Registry.BIOME.getByValue(center)
        if (biome != null) {
            if (!GlaciaLayerUtils.isOcean(center) && (GlaciaLayerUtils.isOcean(north) || GlaciaLayerUtils.isOcean(west) || GlaciaLayerUtils.isOcean(south) || GlaciaLayerUtils.isOcean(east))) {
                return Glacia.Biomes.BEACH.id
            }
        }

        return center
    }
}