package com.greenapple.glacia.world.feature

import com.greenapple.glacia.Glacia
import com.mojang.datafixers.Dynamic
import net.minecraft.block.Block
import java.util.Random
import java.util.function.Function
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MutableBoundingBox
import net.minecraft.world.IWorld
import net.minecraft.world.gen.IWorldGenerationBaseReader
import net.minecraft.world.gen.IWorldGenerationReader
import net.minecraft.world.gen.feature.AbstractTreeFeature
import net.minecraft.world.gen.feature.BirchTreeFeature
import net.minecraft.world.gen.feature.NoFeatureConfig
import kotlin.math.abs

/**
 * @see BirchTreeFeature
 */
class FeatureGlacialTree(registryName: String, configIn: Function<Dynamic<*>, out NoFeatureConfig>, doBlockNotifyIn: Boolean, private val useExtraRandomHeight: Boolean) : AbstractTreeFeature<NoFeatureConfig>(configIn, doBlockNotifyIn) {

    init {
        setRegistryName(registryName)
        this.setSapling(Blocks.BIRCH_SAPLING as net.minecraftforge.common.IPlantable)
    }

    companion object {
        private val LOG = Glacia.Blocks.GLACIAL_TREE_LOG.defaultState
        private val LEAF = Glacia.Blocks.GLACIAL_TREE_LEAVES.defaultState
    }

    val Block.isGlacialSoil; get() = this === Glacia.Blocks.GLACIAL_DIRT

    protected fun IWorldGenerationBaseReader.getBlock(pos: BlockPos): Block {
        var block: Block = Blocks.AIR
        this.hasBlockState(pos) {blockState ->
            block = blockState.block
            true
        }
        return block
    }

    override fun setDirtAt(reader: IWorldGenerationReader, pos: BlockPos, origin: BlockPos) {
        if (reader !is IWorld) {
            this.setBlockState(reader, pos, Glacia.Blocks.GLACIAL_DIRT.defaultState)
            return
        }
        reader.getBlockState(pos).onPlantGrow(reader, pos, origin)
    }

    public override fun place(changedBlocks: Set<BlockPos>, worldIn: IWorldGenerationReader, rand: Random, position: BlockPos, p_208519_5_: MutableBoundingBox): Boolean {
        var i = rand.nextInt(3) + 5
        if (this.useExtraRandomHeight) {
            i += rand.nextInt(7)
        }

        var flag = true
        if (position.y >= 1 && position.y + i + 1 <= worldIn.maxHeight) {
            for (j in position.y..position.y + 1 + i) {
                var k = 1
                if (j == position.y) {
                    k = 0
                }

                if (j >= position.y + 1 + i - 2) {
                    k = 2
                }

                val mutableBlockPos = BlockPos.MutableBlockPos()

                var l = position.x - k
                while (l <= position.x + k && flag) {
                    var i1 = position.z - k
                    while (i1 <= position.z + k && flag) {
                        if (j >= 0 && j < worldIn.maxHeight) {
                            if (!AbstractTreeFeature.func_214587_a(worldIn, mutableBlockPos.setPos(l, j, i1))) {
                                flag = false
                            }
                        } else {
                            flag = false
                        }
                        ++i1
                    }
                    ++l
                }
            }

            if (!flag) {
                return false
            } else if (worldIn.getBlock(position.down()).isGlacialSoil && position.y < worldIn.maxHeight - i - 1) {
                this.setDirtAt(worldIn, position.down(), position)

                for (l1 in position.y - 3 + i..position.y + i) {
                    val j2 = l1 - (position.y + i)
                    val k2 = 1 - j2 / 2

                    for (l2 in position.x - k2..position.x + k2) {
                        val i3 = l2 - position.x

                        for (j1 in position.z - k2..position.z + k2) {
                            val k1 = j1 - position.z
                            if (abs(i3) != k2 || abs(k1) != k2 || rand.nextInt(2) != 0 && j2 != 0) {
                                val blockpos = BlockPos(l2, l1, j1)
                                if (AbstractTreeFeature.isAirOrLeaves(worldIn, blockpos)) {
                                    this.setLogState(changedBlocks, worldIn, blockpos, LEAF, p_208519_5_)
                                }
                            }
                        }
                    }
                }

                for (i2 in 0 until i) {
                    if (AbstractTreeFeature.isAirOrLeaves(worldIn, position.up(i2))) {
                        this.setLogState(changedBlocks, worldIn, position.up(i2), LOG, p_208519_5_)
                    }
                }

                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }
}