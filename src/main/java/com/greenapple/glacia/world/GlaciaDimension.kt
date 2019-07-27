package com.greenapple.glacia.world

import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProviderType
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.IChunkGeneratorFactory
import java.util.function.Supplier
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings



class GlaciaDimension(world: World, type: DimensionType) : Dimension(world, type) {

    companion object {
        private val SKY_RENDERER by lazy {GlaciaSkyRenderer()}
        var generatorType = ChunkGeneratorType(IChunkGeneratorFactory {world, provider, settings -> GlaciaChunkGenerator(world, provider, settings)}, false, Supplier {GenerationSettings()})
        var biomeProviderType = BiomeProviderType(java.util.function.Function<SingleBiomeProviderSettings, GlaciaBiomeProvider> {settings: SingleBiomeProviderSettings-> GlaciaBiomeProvider(settings)}, Supplier { SingleBiomeProviderSettings() })
    }

    override fun createChunkGenerator(): ChunkGenerator<*> {
        //TODO biome provider (Thanks YAMDA)
        return generatorType.create(this.world, biomeProviderType.create(biomeProviderType.createSettings().setBiome(Biomes.PLAINS)), generatorType.createSettings())

        /*val endgenerationsettings = ChunkGeneratorType.FLOATING_ISLANDS.createSettings()
        endgenerationsettings.defaultBlock = Blocks.END_STONE.defaultState
        endgenerationsettings.defaultFluid = Blocks.AIR.defaultState
        endgenerationsettings.spawnPos = this.spawnCoordinate
        return ChunkGeneratorType.FLOATING_ISLANDS.create(this.world, BiomeProviderType.THE_END.create(BiomeProviderType.THE_END.createSettings().setSeed(this.world.seed)), endgenerationsettings)*/
    }

    override fun getSkyColor(cameraPos: BlockPos?, partialTicks: Float): Vec3d {
        return SKY_RENDERER.customSkyColor
    }

    override fun getSkyRenderer(): net.minecraftforge.client.IRenderHandler {
        return SKY_RENDERER
    }

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
        val d1 = 0.5 - Math.cos(d0 * Math.PI) / 2.0
        return (d0 * 2.0 + d1).toFloat() / 3.0f
    }


    override fun isSurfaceWorld(): Boolean {
        return true
    }

    @OnlyIn(Dist.CLIENT)
    override fun getFogColor(p_76562_1_: Float, p_76562_2_: Float): Vec3d {
        var f = MathHelper.cos(p_76562_1_ * (Math.PI.toFloat() * 2f)) * 2.0f + 0.5f
        f = MathHelper.clamp(f, 0.0f, 1.0f)
        var f1 = 0.7529412f
        var f2 = 0.84705883f
        var f3 = 1.0f
        f1 = f1 * (f * 0.94f + 0.06f)
        f2 = f2 * (f * 0.94f + 0.06f)
        f3 = f3 * (f * 0.91f + 0.09f)
        return Vec3d(f1.toDouble(), f2.toDouble(), f3.toDouble())
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