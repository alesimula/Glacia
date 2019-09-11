package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import net.minecraft.block.Blocks
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.gen.carver.CaveWorldCarver
import net.minecraft.world.gen.carver.WorldCarver
import net.minecraft.world.gen.feature.ProbabilityConfig
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Function

object Glacia_WorldCarver : IForgeRegistryCollection<WorldCarver<*>> {
    class WorldCarverCaveBase(registryName: String, height: Int) : CaveWorldCarver(Function {ProbabilityConfig.deserialize(it)}, height) {
        init {
            setRegistryName(registryName)
            carvableBlocks = setOf(*carvableBlocks.toTypedArray(), Glacia.Blocks.GLACIAL_STONE, Glacia.Blocks.GLACIAL_DIRT, Glacia.Blocks.SNOWY_SAND)
        }

        override fun carveBlock(chunkIn: IChunk, carvingMask: BitSet, rand: Random, mutablePos1: BlockPos.MutableBlockPos, mutablePos2: BlockPos.MutableBlockPos, mutablePos3: BlockPos.MutableBlockPos, p_222703_7_: Int, p_222703_8_: Int, p_222703_9_: Int, p_222703_10_: Int, p_222703_11_: Int, p_222703_12_: Int, p_222703_13_: Int, p_222703_14_: Int, atomicBool: AtomicBoolean): Boolean {
            val i = p_222703_12_ or (p_222703_14_ shl 4) or (p_222703_13_ shl 8)
            if (carvingMask.get(i)) {
                return false
            } else {
                carvingMask.set(i)
                mutablePos1.setPos(p_222703_10_, p_222703_13_, p_222703_11_)
                val state = chunkIn.getBlockState(mutablePos1)
                val state2 = chunkIn.getBlockState(mutablePos2.setPos(mutablePos1).move(Direction.UP))
                if (state === Glacia.Blocks.GLACIAL_DIRT.stateSnowy || state.block === Blocks.MYCELIUM) {
                    atomicBool.set(true)
                }

                if (!this.canCarveBlock(state, state2)) {
                    return false
                } else {
                    if (p_222703_13_ < 11) {
                        chunkIn.setBlockState(mutablePos1, Glacia.Blocks.PLASMA.defaultState, false)
                    } else {
                        chunkIn.setBlockState(mutablePos1, CAVE_AIR, false)
                        if (atomicBool.get()) {
                            mutablePos3.setPos(mutablePos1).move(Direction.DOWN)
                            if (chunkIn.getBlockState(mutablePos3) === Glacia.Blocks.GLACIAL_DIRT.defaultState) {
                                chunkIn.setBlockState(mutablePos3, chunkIn.getBiome(mutablePos1).surfaceBuilderConfig.top, false)
                            }
                        }
                    }

                    return true
                }
            }
        }
    }
    val CAVE = WorldCarverCaveBase("cave", 256)
}