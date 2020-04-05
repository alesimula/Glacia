package com.greenapple.glacia.world

import com.greenapple.glacia.Glacia
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.biome.provider.BiomeProviderType
import net.minecraft.world.biome.provider.IBiomeProviderSettings
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings
import net.minecraft.world.dimension.Dimension
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.*
import net.minecraft.world.storage.WorldInfo
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.lang.reflect.Constructor
import java.util.function.Supplier
import kotlin.math.cos


class GlaciaDimension(world: World, type: DimensionType) : Dimension(world, type, 0.0F) {

    companion object {
        //TODO remove when BiomeProviderType constructor is changed to public
        var biomeProviderTyperConstructor: Constructor<BiomeProviderType<*, *>> = BiomeProviderType::class.java.getDeclaredConstructor(java.util.function.Function::class.java, java.util.function.Function::class.java).apply {isAccessible=true}
        fun<C: IBiomeProviderSettings,T: BiomeProvider> biomeProviderType(settingsInit: (WorldInfo)->C, biomeProviderInit: (C)->T) = biomeProviderTyperConstructor.newInstance(java.util.function.Function(biomeProviderInit), java.util.function.Function(settingsInit)) as BiomeProviderType<C,T>

        //private val SKY_RENDERER by lazy {GlaciaSkyRenderer()}
        private val generatorType = ChunkGeneratorType(IChunkGeneratorFactory {world, provider, settings -> GlaciaChunkGenerator(world, provider, settings)}, false, Supplier {OverworldGenSettings()})
        private val biomeProviderType = biomeProviderType({OverworldBiomeProviderSettings(it)}) {GlaciaBiomeProvider(it)}
    }

    private fun<C : GenerationSettings> C.glaciaSettings() = this.apply {
        defaultBlock = Glacia.Blocks.GLACIAL_STONE.defaultState
    }

    val WorldInfo.init; get() = this.apply {
        //generator = WorldType.FLAT
    }

    override fun createChunkGenerator(): ChunkGenerator<*> {
        //TODO biome provider (Thanks YAMDA)
        //func_226840_a_ -> createSettings().setWorldInfo
        return generatorType.create(this.world, biomeProviderType.create(biomeProviderType.func_226840_a_(world.worldInfo.init).setGeneratorSettings(OverworldGenSettings())), generatorType.createSettings().glaciaSettings())

        /*val endgenerationsettings = ChunkGeneratorType.FLOATING_ISLANDS.createSettings()
        endgenerationsettings.defaultBlock = Blocks.END_STONE.defaultState
        endgenerationsettings.defaultFluid = Blocks.AIR.defaultState
        endgenerationsettings.spawnPos = this.spawnCoordinate
        return ChunkGeneratorType.FLOATING_ISLANDS.create(this.world, BiomeProviderType.THE_END.create(BiomeProviderType.THE_END.createSettings().setSeed(this.world.seed)), endgenerationsettings)*/
    }

    //TODO per-biome skycolor
    /**
     * @see Biome.calculateSkyColor()
     * @see Biome.getSkyColor()
     */
    /*override fun getSkyColor(cameraPos: BlockPos?, partialTicks: Float) = super.getSkyColor(cameraPos, partialTicks).run {
        Vec3d(x*0.55, y*0.15, z*0.7)
        //return SKY_RENDERER.customSkyColor
    }*/

    override fun calcSunriseSunsetColors(celestialAngle: Float, partialTicks: Float): FloatArray? = super.calcSunriseSunsetColors(celestialAngle, partialTicks)?.apply {
        this[0] *= 0.55F
        this[1] *= 2F
        this[2] *= 0.7F
    }

    @OnlyIn(Dist.CLIENT)
    override fun getFogColor(p_76562_1_: Float, p_76562_2_: Float): Vec3d {
        var f = MathHelper.cos(p_76562_1_ * (Math.PI.toFloat() * 2f)) * 2.0f + 0.5f
        f = MathHelper.clamp(f, 0.0f, 1.0f)
        var f1 = 0.7529412f
        var f2 = 0.84705883f
        var f3 = 1.0f
        f1 *= (f * 0.94f + 0.06f)
        f2 *= (f * 0.94f + 0.06f)
        f3 *= (f * 0.91f + 0.09f)
        return Vec3d(f1.toDouble()*0.55, f2.toDouble()*0.15, f3.toDouble()*0.7)
    }

    //TODO use sky renderer
    /*override fun getSkyRenderer(): net.minecraftforge.client.IRenderHandler {
        return SKY_RENDERER
    }*/

    override fun findSpawn(chunkPos: ChunkPos, checkValid: Boolean): BlockPos? {
        return null
    }

    override fun findSpawn(x: Int, z: Int, checkValid: Boolean): BlockPos? {
        return null
    }

    /**
     * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
     */
    override fun calculateCelestialAngle(worldTime: Long, partialTicks: Float): Float {
        val d0 = MathHelper.frac(worldTime.toDouble() / 24000.0 - 0.25)
        val d1 = 0.5 - cos(d0 * Math.PI) / 2.0
        return (d0 * 2.0 + d1).toFloat() / 3.0f
    }


    override fun isSurfaceWorld(): Boolean {
        return true
    }

    override fun canRespawnHere(): Boolean {
        return false
    }

    @OnlyIn(Dist.CLIENT)
    override fun doesXZShowFog(x: Int, z: Int): Boolean {
        return false
    }

    override fun getWorldTime(): Long {
        return super.getWorldTime()
    }

    override fun hasSkyLight(): Boolean {
        return true
    }
}