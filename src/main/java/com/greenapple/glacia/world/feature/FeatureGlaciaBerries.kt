package com.greenapple.glacia.world.feature

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockPlantFacingBase
import com.mojang.datafixers.Dynamic
import java.util.Random
import java.util.function.Function
import net.minecraft.block.BlockState
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.IWorldWriter
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.GenerationSettings
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FlowersFeature
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraft.world.gen.feature.PlainsFlowersFeature

/**
 * @see PlainsFlowersFeature
 */
class FeatureGlaciaBerries(registryName : String, function: Function<Dynamic<*>, out NoFeatureConfig>) : Feature<NoFeatureConfig>(function, false) {

    init {
        setRegistryName(registryName)
    }

    override fun place(worldIn: IWorld, generator: ChunkGenerator<out GenerationSettings>, rand: Random, pos: BlockPos, config: NoFeatureConfig): Boolean {
        val stateProvider = this.getRandomFloraProvider(rand, pos)
        var i = 0

        for (j in 0..63) {
            val blockstate = stateProvider.invoke()
            val blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8))
            if (worldIn.isAirBlock(blockpos) && blockpos.y < 255 && blockstate.isValidPosition(worldIn, blockpos)) {
                worldIn.setBlockState(blockpos, blockstate, 2)
                ++i
            }
        }

        return i > 0
    }

    fun getRandomFloraProvider(random: Random, pos: BlockPos): ()->BlockState {
        return {Glacia.Blocks.GLACIAL_BERRY.defaultState.with(BlockPlantFacingBase.FACING, Direction.Plane.HORIZONTAL.random(random))}
    }
}