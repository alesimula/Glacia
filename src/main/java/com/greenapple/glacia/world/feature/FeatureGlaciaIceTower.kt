package com.greenapple.glacia.world.feature

import com.greenapple.glacia.Glacia
import com.mojang.datafixers.Dynamic
import java.util.Random
import java.util.function.Function
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.feature.BirchTreeFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import kotlin.math.pow


/**
 * @see BirchTreeFeature
 */
class FeatureGlaciaIceTower(registryName: String, configIn: Function<Dynamic<*>, out NoFeatureConfig>, doBlockNotifyIn: Boolean) : Feature<NoFeatureConfig>(configIn, doBlockNotifyIn) {

    init {
        setRegistryName(registryName)
    }

    companion object {
        private val LOG = Glacia.Blocks.GLACIAL_TREE_LOG.defaultState
        private val LEAF = Glacia.Blocks.GLACIAL_TREE_LEAVES.defaultState
        private const val CHANCE = 0.5
        private val CHANGE_INV = (CHANCE.pow(-1))
        
        private val BLOCK_GLACIAL_DIRT = Glacia.Blocks.GLACIAL_DIRT
    }
    private fun IWorld.getBlock(pos: BlockPos) = this.getBlockState(pos).block
    private fun IWorld.getBlock(x: Int, y: Int, z: Int) = this.getBlock(BlockPos(x, y, z))

    fun IWorld.setMagicIceAt(x: Int, y: Int, z: Int) {
        setBlockState(BlockPos(x,y,z),Glacia.Blocks.MAGIC_ICE.defaultState, 19)
    }

    override fun place(world: IWorld, generator: ChunkGenerator<out GenerationSettings>, rand: Random, pos: BlockPos, config: NoFeatureConfig): Boolean {
        val x = pos.x
        val y = pos.y
        val z = pos.z
        val randModel = rand.nextInt((3 * CHANGE_INV).toInt())

        //check that each corner is one of the valid spawn blocks
        if (world.getBlock(x, y -1, z) !== BLOCK_GLACIAL_DIRT || world.getBlock(x, y - 1, z + 1) !== BLOCK_GLACIAL_DIRT || world.getBlock(x, y - 1, z + 2) !== BLOCK_GLACIAL_DIRT || world.getBlock(x, y - 1, z + 3) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 1, y - 1, z) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 1, y - 1, z + 1) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 1, y - 1, z + 2) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 1, y - 1, z + 3) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 2, y - 1, z) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 2, y - 1, z + 1) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 2, y - 1, z + 2) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 2, y - 1, z + 3) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 3, y - 1, z) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 3, y - 1, z + 1) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 3, y - 1, z + 2) !== BLOCK_GLACIAL_DIRT || world.getBlock(x + 3, y - 1, z + 3) !== BLOCK_GLACIAL_DIRT) {
            return false
        } else if (randModel == 0) {
            world.setMagicIceAt(x, y, z)
            world.setMagicIceAt(x, y, z + 1)
            world.setMagicIceAt(x, y, z + 2)
            world.setMagicIceAt(x, y, z + 3)
            world.setMagicIceAt(x, y + 1, z + 2)
            world.setMagicIceAt(x, y + 1, z + 3)
            world.setMagicIceAt(x, y + 2, z + 2)
            world.setMagicIceAt(x + 1, y, z)
            world.setMagicIceAt(x + 1, y, z + 1)
            world.setMagicIceAt(x + 1, y, z + 2)
            world.setMagicIceAt(x + 1, y, z + 3)
            world.setMagicIceAt(x + 1, y + 1, z)
            world.setMagicIceAt(x + 1, y + 1, z + 1)
            world.setMagicIceAt(x + 1, y + 1, z + 2)
            world.setMagicIceAt(x + 1, y + 1, z + 3)
            world.setMagicIceAt(x + 1, y + 2, z + 1)
            world.setMagicIceAt(x + 1, y + 2, z + 2)
            world.setMagicIceAt(x + 1, y + 3, z + 2)
            world.setMagicIceAt(x + 2, y, z)
            world.setMagicIceAt(x + 2, y, z + 1)
            world.setMagicIceAt(x + 2, y, z + 2)
            world.setMagicIceAt(x + 2, y, z + 3)
            world.setMagicIceAt(x + 2, y + 1, z + 1)
            world.setMagicIceAt(x + 2, y + 1, z + 2)
            world.setMagicIceAt(x + 2, y + 2, z + 1)
            world.setMagicIceAt(x + 2, y + 2, z + 2)
            world.setMagicIceAt(x + 2, y + 3, z + 1)
            world.setMagicIceAt(x + 2, y + 3, z + 2)
            world.setMagicIceAt(x + 2, y + 4, z + 2)
            world.setMagicIceAt(x + 2, y + 5, z + 2)
            world.setMagicIceAt(x + 3, y, z)
            world.setMagicIceAt(x + 3, y, z + 1)
            world.setMagicIceAt(x + 3, y, z + 2)
            world.setMagicIceAt(x + 3, y, z + 3)
            world.setMagicIceAt(x + 3, y + 1, z + 2)
            return true
        } else if (randModel == 1 || !(world.getBlock(x + 4, y - 1, z) === BLOCK_GLACIAL_DIRT && world.getBlock(x + 4, y - 1, z + 1) === BLOCK_GLACIAL_DIRT && world.getBlock(x + 4, y - 1, z + 2) === BLOCK_GLACIAL_DIRT && world.getBlock(x + 4, y - 1, z + 3) === BLOCK_GLACIAL_DIRT && world.getBlock(x + 4, y - 1, z + 4) === BLOCK_GLACIAL_DIRT && world.getBlock(x + 3, y - 1, z + 4) === BLOCK_GLACIAL_DIRT && world.getBlock(x + 2, y - 1, z + 4) === BLOCK_GLACIAL_DIRT && world.getBlock(x + 1, y - 1, z + 4) === BLOCK_GLACIAL_DIRT && world.getBlock(x, y - 1, z + 4) === BLOCK_GLACIAL_DIRT)) {
            world.setMagicIceAt(x, y, z)
            world.setMagicIceAt(x, y, z + 1)
            world.setMagicIceAt(x, y, z + 2)
            world.setMagicIceAt(x, y, z + 3)
            world.setMagicIceAt(x, y + 1, z + 2)
            world.setMagicIceAt(x, y + 1, z + 3)
            world.setMagicIceAt(x, y + 2, z + 2)
            world.setMagicIceAt(x + 1, y, z)
            world.setMagicIceAt(x + 1, y, z + 1)
            world.setMagicIceAt(x + 1, y, z + 2)
            world.setMagicIceAt(x + 1, y, z + 3)
            world.setMagicIceAt(x + 1, y + 1, z)
            world.setMagicIceAt(x + 1, y + 1, z + 1)
            world.setMagicIceAt(x + 1, y + 1, z + 2)
            world.setMagicIceAt(x + 1, y + 1, z + 3)
            world.setMagicIceAt(x + 1, y + 2, z + 1)
            world.setMagicIceAt(x + 1, y + 2, z + 2)
            world.setMagicIceAt(x + 1, y + 3, z + 1)
            world.setMagicIceAt(x + 2, y, z)
            world.setMagicIceAt(x + 2, y, z + 1)
            world.setMagicIceAt(x + 2, y, z + 2)
            world.setMagicIceAt(x + 2, y, z + 3)
            world.setMagicIceAt(x + 2, y + 1, z)
            world.setMagicIceAt(x + 2, y + 1, z + 1)
            world.setMagicIceAt(x + 2, y + 1, z + 2)
            world.setMagicIceAt(x + 2, y + 2, z + 1)
            world.setMagicIceAt(x + 2, y + 2, z + 2)
            world.setMagicIceAt(x + 3, y, z)
            world.setMagicIceAt(x + 3, y, z + 1)
            world.setMagicIceAt(x + 3, y, z + 2)
            world.setMagicIceAt(x + 3, y, z + 3)
            world.setMagicIceAt(x + 3, y + 1, z + 2)
            return true
        } else if (randModel == 2) {
            world.setMagicIceAt(x, y, z + 1)
            world.setMagicIceAt(x, y, z + 2)
            world.setMagicIceAt(x + 1, y, z)
            world.setMagicIceAt(x + 1, y, z + 1)
            world.setMagicIceAt(x + 1, y, z + 2)
            world.setMagicIceAt(x + 1, y, z + 3)
            world.setMagicIceAt(x + 1, y + 1, z + 1)
            world.setMagicIceAt(x + 1, y + 1, z + 2)
            world.setMagicIceAt(x + 1, y + 1, z + 3)
            world.setMagicIceAt(x + 2, y, z)
            world.setMagicIceAt(x + 2, y, z + 1)
            world.setMagicIceAt(x + 2, y, z + 2)
            world.setMagicIceAt(x + 2, y, z + 3)
            world.setMagicIceAt(x + 2, y, z + 4)
            world.setMagicIceAt(x + 2, y + 1, z)
            world.setMagicIceAt(x + 2, y + 1, z + 1)
            world.setMagicIceAt(x + 2, y + 1, z + 2)
            world.setMagicIceAt(x + 2, y + 1, z + 3)
            world.setMagicIceAt(x + 2, y + 2, z + 1)
            world.setMagicIceAt(x + 2, y + 2, z + 2)
            world.setMagicIceAt(x + 2, y + 3, z + 1)
            world.setMagicIceAt(x + 2, y + 3, z + 2)
            world.setMagicIceAt(x + 2, y + 4, z + 1)
            world.setMagicIceAt(x + 2, y + 5, z + 1)
            world.setMagicIceAt(x + 3, y, z)
            world.setMagicIceAt(x + 3, y, z + 1)
            world.setMagicIceAt(x + 3, y, z + 2)
            world.setMagicIceAt(x + 3, y, z + 3)
            world.setMagicIceAt(x + 3, y, z + 4)
            world.setMagicIceAt(x + 3, y + 1, z)
            world.setMagicIceAt(x + 3, y + 1, z + 1)
            world.setMagicIceAt(x + 3, y + 1, z + 2)
            world.setMagicIceAt(x + 3, y + 2, z + 1)
            world.setMagicIceAt(x + 3, y + 2, z + 2)
            world.setMagicIceAt(x + 3, y + 3, z + 1)
            world.setMagicIceAt(x + 3, y + 3, z + 2)
            world.setMagicIceAt(x + 3, y + 4, z + 1)
            world.setMagicIceAt(x + 3, y + 4, z + 2)
            world.setMagicIceAt(x + 3, y + 5, z + 2)
            world.setMagicIceAt(x + 3, y + 6, z + 2)
            world.setMagicIceAt(x + 3, y + 7, z + 2)
            world.setMagicIceAt(x + 4, y, z)
            world.setMagicIceAt(x + 4, y, z + 1)
            world.setMagicIceAt(x + 4, y, z + 2)
            world.setMagicIceAt(x + 4, y, z + 3)
            world.setMagicIceAt(x + 4, y, z + 4)
            world.setMagicIceAt(x + 4, y + 1, z + 2)
            return true
        } else {
            return false
        }
    }
}