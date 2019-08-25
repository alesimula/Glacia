package com.greenapple.glacia.world

import com.google.common.collect.Sets
import com.greenapple.glacia.Glacia
import com.greenapple.glacia.world.layer.GlaciaLayerUtils
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.layer.*
import java.util.Collections
import java.util.Random

/**
 * Look at OverworldBiomeProvider if any function name changes
 */
class GlaciaBiomeProvider(settings: OverworldBiomeProviderSettings) : BiomeProvider() {

    private var genBiomes : Layer
    private val biomeFactoryLayer: Layer

    private val seed = settings.worldInfo.seed.run {
        var seed = this
        seed = seed.inv() + (seed shl 21) // key = (key << 21) - key - 1;
        seed = (seed + 0x67726565) xor seed.ushr(21)
        seed += ((seed xor 0x6e617070) shl 4) + (seed shl 9) // key * 265
        seed = (seed + 0x6c657275) xor seed.ushr(16)
        seed += ((seed xor 0x6c657321) shl 1) + (seed shl 3) // key * 21
        seed = (seed + 0x68696672) xor seed.ushr(24)
        seed += ((seed xor 0x69656e64) shl 40)
        seed
    }

    init {
        val layers = GlaciaLayerUtils.buildOverworldProcedure(seed, settings.worldInfo.generator, settings.generatorSettings)
        genBiomes = layers[0]
        biomeFactoryLayer = layers[1]
    }

    companion object {
        private val BIOMES_TO_SPAWN_IN = mutableListOf(
                Glacia.Biomes.PLAINS
        )
        private val BIOMES = BIOMES_TO_SPAWN_IN.apply {/*add(
                Glacia.Biomes.WHATEVER
        )*/}.toTypedArray()
    }

    override fun getBiomesToSpawnIn(): List<Biome> {
        return BIOMES_TO_SPAWN_IN
    }

    override fun getBiomesInSquare(centerX: Int, centerZ: Int, sideLength: Int): Set<Biome> {
        val i = centerX - sideLength shr 2
        val j = centerZ - sideLength shr 2
        val k = centerX + sideLength shr 2
        val l = centerZ + sideLength shr 2
        val i1 = k - i + 1
        val j1 = l - j + 1
        val set = Sets.newHashSet<Biome>()
        Collections.addAll(set, *this.genBiomes.generateBiomes(i, j, i1, j1))
        return set
    }

    /**
     * Gets the biome from the provided coordinates
     */
    override fun getBiome(x: Int, y: Int): Biome {
        return this.biomeFactoryLayer.func_215738_a(x, y)
    }

    override fun func_222366_b(p_222366_1_: Int, p_222366_2_: Int): Biome {
        return this.genBiomes.func_215738_a(p_222366_1_, p_222366_2_)
    }

    override fun getBiomes(x: Int, z: Int, width: Int, length: Int, cacheFlag: Boolean): Array<Biome> {
        return this.biomeFactoryLayer.generateBiomes(x, z, width, length)
    }

    override fun findBiomePosition(x: Int, z: Int, range: Int, biomes: List<Biome>, random: Random): BlockPos? {
        val i = x - range shr 2
        val j = z - range shr 2
        val k = x + range shr 2
        val l = z + range shr 2
        val i1 = k - i + 1
        val j1 = l - j + 1
        val abiome = this.genBiomes.generateBiomes(i, j, i1, j1)
        var blockpos: BlockPos? = null
        var k1 = 0

        for (l1 in 0 until i1 * j1) {
            val i2 = i + l1 % i1 shl 2
            val j2 = j + l1 / i1 shl 2
            if (biomes.contains(abiome[l1])) {
                if (blockpos == null || random.nextInt(k1 + 1) == 0) {
                    blockpos = BlockPos(i2, 0, j2)
                }

                ++k1
            }
        }

        return blockpos
    }

    override fun hasStructure(structureIn: Structure<*>): Boolean {
        return (this.hasStructureCache).computeIfAbsent(structureIn) {structure ->
            for (biome in BIOMES) {
                if (biome.hasStructure(structure)) {
                    return@computeIfAbsent true
                }
            }
            false
        }
    }

    override fun getSurfaceBlocks(): Set<BlockState> {
        if (this.topBlocksCache.isEmpty()) {
            for (biome in BIOMES) {
                this.topBlocksCache.add(biome.surfaceBuilderConfig.top)
            }
        }

        return this.topBlocksCache
    }
}