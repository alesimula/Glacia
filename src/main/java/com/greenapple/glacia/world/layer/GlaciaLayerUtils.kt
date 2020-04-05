package com.greenapple.glacia.world.layer

import com.google.common.collect.ImmutableList
import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.id
import net.minecraft.world.WorldType
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.IExtendedNoiseRandom
import net.minecraft.world.gen.LazyAreaLayerContext
import net.minecraft.world.gen.OverworldGenSettings
import net.minecraft.world.gen.area.IArea
import net.minecraft.world.gen.area.IAreaFactory
import net.minecraft.world.gen.layer.*
import net.minecraftforge.common.extensions.IForgeWorldType
import java.util.function.LongFunction

object GlaciaLayerUtils {

    private val SHALLOW_OCEANS = arrayOf(
            Glacia.Biomes.OCEAN
    )
    private val OCEANS = arrayOf(
            *SHALLOW_OCEANS
    )
    private val SHALLOW_OCEANS_ID by lazy {OCEANS_ID.copyOf(SHALLOW_OCEANS.size)}
    private val OCEANS_ID by lazy {IntArray(OCEANS.size) {index -> OCEANS[index].id}}


    @JvmStatic fun isShallowOcean(biome: Biome) = biome in SHALLOW_OCEANS
    @JvmStatic fun isShallowOcean(biome: Int) = biome in SHALLOW_OCEANS_ID
    @JvmStatic fun isOcean(biome: Biome) = biome in OCEANS
    @JvmStatic fun isOcean(biome: Int) = biome in OCEANS_ID


    /*val BIOME_TYPE_GLACIA by lazy {BiomeManager.BiomeType.create("glacia").apply {
        BiomeManager.addBiome(this, BiomeManager.BiomeEntry(Glacia.Biomes.PLAINS, 3))
    }}*/

    fun <T : IArea, C : IExtendedNoiseRandom<T>> IForgeWorldType.getBiomeLayerGlacia(parentLayer: IAreaFactory<T>, chunkSettings: OverworldGenSettings, contextFactory: LongFunction<C>): IAreaFactory<T> {
        var layer = parentLayer
        layer = LayerGlaciaBiome(chunkSettings).apply(contextFactory.apply(200L), layer)
        //parentLayer = AddBambooForestLayer.INSTANCE.apply(contextFactory.apply(1001L), parentLayer)
        layer = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, layer, 2, contextFactory)
        layer = EdgeBiomeLayer.INSTANCE.apply(contextFactory.apply(1000L), layer)
        return layer
    }

    private fun <T : IArea, C : IExtendedNoiseRandom<T>> buildOverworldProcedure(worldTypeIn: WorldType, settings: OverworldGenSettings, contextFactory: LongFunction<C>): IAreaFactory<T> {
        var iareafactory = IslandLayer.INSTANCE.apply(contextFactory.apply(1L))
        iareafactory = ZoomLayer.FUZZY.apply(contextFactory.apply(2000L), iareafactory)
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(1L), iareafactory)
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2001L), iareafactory)
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory)
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(50L), iareafactory)
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(70L), iareafactory)
        iareafactory = RemoveTooMuchOceanLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory)
        var iareafactory1 = OceanLayer.INSTANCE.apply(contextFactory.apply(2L))
        iareafactory1 = LayerUtil.repeat(2001L, ZoomLayer.NORMAL, iareafactory1, 6, contextFactory)
        iareafactory = AddSnowLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory)
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), iareafactory)
        iareafactory = EdgeLayer.CoolWarm.INSTANCE.apply(contextFactory.apply(2L), iareafactory)
        iareafactory = EdgeLayer.HeatIce.INSTANCE.apply(contextFactory.apply(2L), iareafactory)
        iareafactory = EdgeLayer.Special.INSTANCE.apply(contextFactory.apply(3L), iareafactory)
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2002L), iareafactory)
        iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2003L), iareafactory)
        iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(4L), iareafactory)
        iareafactory = AddMushroomIslandLayer.INSTANCE.apply(contextFactory.apply(5L), iareafactory)
        iareafactory = DeepOceanLayer.INSTANCE.apply(contextFactory.apply(4L), iareafactory)
        iareafactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory)
        var i = 4
        var j = i
        if (settings != null) {
            i = settings.biomeSize
            j = settings.riverSize
        }

        if (worldTypeIn === WorldType.LARGE_BIOMES) {
            i = 6
        }

        i = LayerUtil.getModdedBiomeSize(worldTypeIn, i)

        var lvt_7_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory)
        lvt_7_1_ = LayerGlaciaRiver.START.apply(contextFactory.apply(100L), lvt_7_1_)
        var lvt_8_1_ = worldTypeIn.getBiomeLayerGlacia(iareafactory, settings, contextFactory)
        val lvt_9_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, 2, contextFactory)
        lvt_8_1_ = HillsLayer.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_, lvt_9_1_)
        lvt_7_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, 2, contextFactory)
        lvt_7_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, j, contextFactory)
        lvt_7_1_ = LayerGlaciaRiver.apply(contextFactory.apply(1L), lvt_7_1_)
        lvt_7_1_ = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), lvt_7_1_)
        lvt_8_1_ = RareBiomeLayer.INSTANCE.apply(contextFactory.apply(1001L), lvt_8_1_)

        for (k in 0 until i) {
            lvt_8_1_ = ZoomLayer.NORMAL.apply(contextFactory.apply((1000 + k).toLong()), lvt_8_1_)
            if (k == 0) {
                lvt_8_1_ = AddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), lvt_8_1_)
            }

            if (k == 1 || i == 1) {
                lvt_8_1_ = LayerGlaciaShore.apply(contextFactory.apply(1000L), lvt_8_1_)
            }
        }

        lvt_8_1_ = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_)
        lvt_8_1_ = LayerGlaciaRiver.MIX.apply(contextFactory.apply(100L), lvt_8_1_, lvt_7_1_)
        lvt_8_1_ = MixOceansLayer.INSTANCE.apply(contextFactory.apply(100L), lvt_8_1_, iareafactory1)
        //val iareafactory5 = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(10L), lvt_8_1_)
        //return ImmutableList.of(lvt_8_1_, iareafactory5, lvt_8_1_)
        return lvt_8_1_
    }

    fun buildOverworldProcedure(p_227474_0_: Long, p_227474_2_: WorldType, p_227474_3_: OverworldGenSettings): Layer {
        val iareafactory = buildOverworldProcedure(p_227474_2_, p_227474_3_, LongFunction { p_227473_2_: Long -> LazyAreaLayerContext(25, p_227474_0_, p_227473_2_) })
        return Layer(iareafactory)
    }

    /*fun buildOverworldProcedure(seed: Long, typeIn: WorldType, settings: OverworldGenSettings): Array<Layer> {
        val immutablelist = buildOverworldProcedure(typeIn, settings, LongFunction{ p_215737_2_ -> LazyAreaLayerContext(25, seed, p_215737_2_) })
        val layer = Layer(immutablelist[0])
        val layer1 = Layer(immutablelist[1])
        val layer2 = Layer(immutablelist[2])
        return arrayOf(layer, layer1, layer2)
    }*/
}