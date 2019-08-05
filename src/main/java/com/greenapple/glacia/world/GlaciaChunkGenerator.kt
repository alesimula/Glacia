package com.greenapple.glacia.world

import com.greenapple.glacia.Glacia
import net.minecraft.entity.EntityClassification
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.IWorld
import net.minecraft.world.WorldType
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biomes
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.*
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.structure.Structure
import java.util.*


class GlaciaChunkGenerator(world: IWorld, biomeProvider: BiomeProvider, settings: OverworldGenSettings) : NoiseChunkGenerator<OverworldGenSettings>(world, biomeProvider, 4, 8, 256, settings, true) {

    companion object {
        @JvmStatic private val field_222576_h = Util.make(FloatArray(25), { p_222575_0_ ->
            for (i in -2..2) {
                for (j in -2..2) {
                    val f = 10.0f / MathHelper.sqrt((i * i + j * j).toFloat() + 0.2f)
                    p_222575_0_[i + 2 + (j + 2) * 5] = f
                }
            }
        })
    }

    private val depthNoise: OctavesNoiseGenerator = OctavesNoiseGenerator(this.randomSeed.apply {skip(2620)}, 16)
    private val isAmplified = world.worldInfo.generator === WorldType.AMPLIFIED

    override fun func_222549_a(p_222549_1_: Int, p_222549_2_: Int): DoubleArray {
        val adouble = DoubleArray(2)
        var f = 0.0f
        var f1 = 0.0f
        var f2 = 0.0f
        val f3 = this.biomeProvider.func_222366_b(p_222549_1_, p_222549_2_).depth

        for (j in -2..2) {
            for (k in -2..2) {
                val biome = this.biomeProvider.func_222366_b(p_222549_1_ + j, p_222549_2_ + k)
                var f4 = biome.depth
                var f5 = biome.scale
                if (this.isAmplified && f4 > 0.0f) {
                    f4 = 1.0f + f4 * 2.0f
                    f5 = 1.0f + f5 * 4.0f
                }

                var f6 = field_222576_h[j + 2 + (k + 2) * 5] / (f4 + 2.0f)
                if (biome.depth > f3) {
                    f6 /= 2.0f
                }

                f += f5 * f6
                f1 += f4 * f6
                f2 += f6
            }
        }

        f /= f2
        f1 /= f2
        f = f * 0.9f + 0.1f
        f1 = (f1 * 4.0f - 1.0f) / 8.0f
        adouble[0] = f1.toDouble() + this.func_222574_c(p_222549_1_, p_222549_2_)
        adouble[1] = f.toDouble()
        return adouble
    }

    override fun func_222545_a(p_222545_1_: Double, p_222545_3_: Double, p_222545_5_: Int): Double {
        var d1 = (p_222545_5_.toDouble() - (8.5 + p_222545_1_ * 8.5 / 8.0 * 4.0)) * 12.0 * 128.0 / 256.0 / p_222545_3_
        if (d1 < 0.0) {
            d1 *= 4.0
        }

        return d1
    }

    override fun func_222548_a(p_222548_1_: DoubleArray, p_222548_2_: Int, p_222548_3_: Int) {
        this.func_222546_a(p_222548_1_, p_222548_2_, p_222548_3_, 684.412f.toDouble(), 684.412f.toDouble(), 8.555149841308594, 4.277574920654297, 3, -10)
    }

    private fun func_222574_c(p_222574_1_: Int, p_222574_2_: Int): Double {
        var d0 = this.depthNoise.func_215462_a((p_222574_1_ * 200).toDouble(), 10.0, (p_222574_2_ * 200).toDouble(), 1.0, 0.0, true) / 8000.0
        if (d0 < 0.0) {
            d0 = -d0 * 0.3
        }

        d0 = d0 * 3.0 - 2.0
        if (d0 < 0.0) {
            d0 /= 28.0
        } else {
            if (d0 > 1.0) {
                d0 = 1.0
            }

            d0 /= 40.0
        }

        return d0
    }

    override fun makeBedrock(chunkIn: IChunk, rand: Random) {
        val blockPos_mutablePos = BlockPos.MutableBlockPos()
        val i = chunkIn.pos.xStart
        val j = chunkIn.pos.zStart
        val t = this.getSettings()
        val k = t.bedrockFloorHeight
        val l = t.bedrockRoofHeight

        for (blockpos in BlockPos.getAllInBoxMutable(i, 0, j, i + 15, 0, j + 15)) {
            if (l > 0) {
                for (i1 in l downTo l - 4) {
                    if (i1 >= l - rand.nextInt(5)) {
                        chunkIn.setBlockState(blockPos_mutablePos.setPos(blockpos.x, i1, blockpos.z), Glacia.Blocks.GLACIAL_BEDROCK.defaultState, false)
                    }
                }
            }

            if (k < 256) {
                for (j1 in k + 4 downTo k) {
                    if (j1 <= k + rand.nextInt(5)) {
                        chunkIn.setBlockState(blockPos_mutablePos.setPos(blockpos.x, j1, blockpos.z), Glacia.Blocks.GLACIAL_BEDROCK.defaultState, false)
                    }
                }
            }
        }
    }

    /*verride fun decorate(region: WorldGenRegion) {
        /*val i = region.mainChunkX
        val j = region.mainChunkZ
        val k = i * 16
        val l = j * 16
        val blockpos = BlockPos(k, 0, l)
        val biome = Biomes.MOUNTAINS
        val sharedseedrandom = SharedSeedRandom()
        val i1 = sharedseedrandom.setDecorationSeed(region.seed, k, l)

        biome.decorate(GenerationStage.Decoration.VEGETAL_DECORATION, this, region, i1, sharedseedrandom, blockpos)
        biome.decorate(GenerationStage.Decoration.UNDERGROUND_ORES, this, region, i1, sharedseedrandom, blockpos)*/
    }

    fun generateTerrain(chunkIn: IChunk) {
        this.getBiomeProvider().biomesToSpawnIn
    }*/

    override fun getGroundHeight() = this.world.seaLevel + 1

    /*override fun func_222529_a(p_222529_1_: Int, p_222529_2_: Int, p_222529_3_: Heightmap.Type): Int {
        return 0
    }

    override fun carve(p_222538_1_: IChunk, p_222538_2_: GenerationStage.Carving) {}*/

    /*override fun getPossibleCreatures(creatureType: EntityClassification, pos: BlockPos): List<Biome.SpawnListEntry> {
        return Biomes.MOUNTAINS.getSpawns(creatureType)
    }*/

    override fun hasStructure(biomeIn: Biome, structureIn: Structure<out IFeatureConfig>): Boolean {
        return true
    }

    override fun getSeaLevel(): Int {
        return 63
    }

    /*override fun initStructureStarts(p_222533_1_: IChunk, p_222533_2_: ChunkGenerator<*>, p_222533_3_: TemplateManager) {

    }

    override fun makeBase(iWorld: IWorld, iChunk: IChunk) {

    }


    override fun <C : IFeatureConfig> getStructureConfig(biomeIn: Biome, structureIn: Structure<C>): C? {
        return null
    }*/
}