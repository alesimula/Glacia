package com.greenapple.glacia.world

import com.google.common.collect.Sets
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biomes
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.biome.provider.SingleBiomeProviderSettings
import net.minecraft.world.gen.feature.structure.Structure
import java.util.Collections
import java.util.Random

class GlaciaBiomeProvider(settings: SingleBiomeProviderSettings) : BiomeProvider() {
    private val biome: Biome

    init {
        this.biome = settings.biome
    }

    companion object {
        private val SPAWN = listOf(Biomes.PLAINS)
    }

    override fun getBiomesToSpawnIn(): List<Biome> {
        return SPAWN
    }

    override fun getBiome(x: Int, y: Int): Biome {
        return this.biome
    }

    override fun getBiomes(x: Int, z: Int, width: Int, length: Int, cacheFlag: Boolean): Array<Biome> {
        val abiome = arrayOfNulls<Biome>(width * length)
        abiome.fill(this.biome, 0,width * length)
        return abiome as Array<Biome>
    }

    override fun findBiomePosition(x: Int, z: Int, range: Int, biomes: List<Biome>, random: Random): BlockPos? {
        return if (biomes.contains(this.biome)) BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1)) else null
    }

    override fun hasStructure(structureIn: Structure<*>): Boolean {
        return false
    }

    override fun getSurfaceBlocks(): Set<BlockState> {
        if (this.topBlocksCache.isEmpty()) {
            this.topBlocksCache.add(this.biome.surfaceBuilderConfig.top)
        }

        return this.topBlocksCache
    }

    override fun getBiomesInSquare(centerX: Int, centerZ: Int, sideLength: Int): Set<Biome> {
        return Sets.newHashSet(this.biome)
    }
}