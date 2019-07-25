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


class GlaciaDimension(world: World, type: DimensionType) : Dimension(world, type) {

    override fun createChunkGenerator(): ChunkGenerator<*> {
        //TODO biome provider (Thanks YAMDA)
        //return YAMDA.generatorType.create(this.world, YAMDA.biomeProviderType.create(YAMDA.biomeProviderType.createSettings().setBiome(Biomes.PLAINS)), YAMDA.generatorType.createSettings())
        val endgenerationsettings = ChunkGeneratorType.FLOATING_ISLANDS.createSettings()
        endgenerationsettings.defaultBlock = Blocks.END_STONE.defaultState
        endgenerationsettings.defaultFluid = Blocks.AIR.defaultState
        endgenerationsettings.spawnPos = this.spawnCoordinate
        return ChunkGeneratorType.FLOATING_ISLANDS.create(this.world, BiomeProviderType.THE_END.create(BiomeProviderType.THE_END.createSettings().setSeed(this.world.seed)), endgenerationsettings)
    }

    override fun findSpawn(chunkPos: ChunkPos, checkValid: Boolean): BlockPos? {
        return null
    }

    override fun findSpawn(x: Int, z: Int, checkValid: Boolean): BlockPos? {
        return null
    }

    override fun calculateCelestialAngle(worldTime: Long, partialTicks: Float): Float {
        //TODO day/night stuff (Thanks YAMDA)
        //if (YAMDAConfig.CONFIG.day.get()) {
        if (true) {
            val j = 6000
            var f1 = (j + partialTicks) / 24000.0f - 0.25f

            if (f1 < 0.0f) {
                f1 += 1.0f
            }

            if (f1 > 1.0f) {
                f1 -= 1.0f
            }

            val f2 = f1
            f1 = 1.0f - ((Math.cos(f1 * Math.PI) + 1.0) / 2.0).toFloat()
            f1 = f2 + (f1 - f2) / 3.0f
            return f1
        } else {
            val i = (worldTime % 24000L).toInt()
            var f = (i.toFloat() + partialTicks) / 24000.0f - 0.25f

            if (f < 0.0f) {
                ++f
            }

            if (f > 1.0f) {
                --f
            }

            val f1 = 1.0f - ((Math.cos(f.toDouble() * Math.PI) + 1.0) / 2.0).toFloat()
            f = f + (f1 - f) / 3.0f
            return f
        }
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