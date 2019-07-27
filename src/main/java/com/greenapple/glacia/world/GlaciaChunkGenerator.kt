package com.greenapple.glacia.world

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityClassification
import net.minecraft.util.SharedSeedRandom
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.Biomes
import net.minecraft.world.biome.provider.BiomeProvider
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.GenerationStage
import net.minecraft.world.gen.Heightmap
import net.minecraft.world.gen.WorldGenRegion
import net.minecraft.world.gen.feature.IFeatureConfig
import net.minecraft.world.gen.feature.structure.Structure
import net.minecraft.world.gen.feature.template.TemplateManager

import javax.annotation.Nullable
import java.util.logging.Level.CONFIG


class GlaciaChunkGenerator(world: IWorld, biomeProvider: BiomeProvider, settings: GenerationSettings) : ChunkGenerator<GenerationSettings>(world, biomeProvider, settings) {

    override fun decorate(region: WorldGenRegion) {
        val i = region.mainChunkX
        val j = region.mainChunkZ
        val k = i * 16
        val l = j * 16
        val blockpos = BlockPos(k, 0, l)
        val biome = Biomes.MOUNTAINS
        val sharedseedrandom = SharedSeedRandom()
        val i1 = sharedseedrandom.setDecorationSeed(region.seed, k, l)

        biome.decorate(GenerationStage.Decoration.VEGETAL_DECORATION, this, region, i1, sharedseedrandom, blockpos)
        biome.decorate(GenerationStage.Decoration.UNDERGROUND_ORES, this, region, i1, sharedseedrandom, blockpos)
    }

    override fun generateSurface(chunk: IChunk) {
        val bedrock = Glacia.Blocks.GLACIAL_BEDROCK.defaultState
        val stone = Glacia.Blocks.GLACIAL_STONE.defaultState
        val dirt = Glacia.Blocks.GLACIAL_DIRT.defaultState
        val snow = Glacia.Blocks.GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SNOWY, true)
        var x1: Int
        var y1: Int
        var z1: Int
        val worldHeight = 64

        val pos = BlockPos.MutableBlockPos()

        x1 = 0
        while (x1 < 16) {
            z1 = 0
            while (z1 < 16) {
                chunk.setBlockState(pos.setPos(x1, 0, z1), bedrock, false)
                z1++
            }
            x1++
        }

        //if (GRASS_ENABLED) {
            x1 = 0
            while (x1 < 16) {
                y1 = 1
                while (y1 < worldHeight - 3) {
                    z1 = 0
                    while (z1 < 16) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), stone, false)
                        z1++
                    }
                    y1++
                }
                x1++
            }
            x1 = 0
            while (x1 < 16) {
                y1 = worldHeight - 3
                while (y1 < worldHeight - 1) {
                    z1 = 0
                    while (z1 < 16) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), dirt, false)
                        z1++
                    }
                    y1++
                }
                x1++
            }
            x1 = 0
            while (x1 < 16) {
                y1 = worldHeight - 1
                while (y1 < worldHeight) {
                    z1 = 0
                    while (z1 < 16) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), snow, false)
                        z1++
                    }
                    y1++
                }
                x1++
            }
        /*} else {
            x1 = 0
            while (x1 < 16) {
                y1 = 1
                while (y1 < worldHeight) {
                    z1 = 0
                    while (z1 < 16) {
                        chunk.setBlockState(pos.setPos(x1, y1, z1), stone, false)
                        z1++
                    }
                    y1++
                }
                x1++
            }
        }*/
    }

    override fun getGroundHeight(): Int {
        return this.world.seaLevel + 1
    }

    override fun func_222529_a(p_222529_1_: Int, p_222529_2_: Int, p_222529_3_: Heightmap.Type): Int {
        return 0
    }

    override fun carve(p_222538_1_: IChunk, p_222538_2_: GenerationStage.Carving) {}

    override fun getPossibleCreatures(creatureType: EntityClassification, pos: BlockPos): List<Biome.SpawnListEntry> {
        return Biomes.MOUNTAINS.getSpawns(creatureType)
    }

    override fun findNearestStructure(worldIn: World, name: String, pos: BlockPos, radius: Int, p_211403_5_: Boolean): BlockPos? {
        return null
    }

    override fun hasStructure(biomeIn: Biome, structureIn: Structure<out IFeatureConfig>): Boolean {
        return true
    }

    override fun initStructureStarts(p_222533_1_: IChunk, p_222533_2_: ChunkGenerator<*>, p_222533_3_: TemplateManager) {

    }

    override fun makeBase(iWorld: IWorld, iChunk: IChunk) {

    }


    override fun <C : IFeatureConfig> getStructureConfig(biomeIn: Biome, structureIn: Structure<C>): C? {
        return null
    }
}