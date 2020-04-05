package com.greenapple.glacia.world.feature

import com.mojang.datafixers.Dynamic
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.IWorldGenerationBaseReader
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.TreeFeature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer

//TODO foliage and trunk height, more settings
class FeatureTreeBase private constructor(registryName: String, trunk: BlockState, leaves: BlockState, foliageX: Int, foliageY: Int, config: (TreeFeatureConfig.Builder.()->Unit)={}, private val configFactory : (Dynamic<*>?)->TreeFeatureConfig = getConfigProvider(trunk, leaves, foliageX, foliageY, config)) : TreeFeature(configFactory) {
    val noConfig get() = withConfiguration(configFactory(null))
    companion object {
        private fun getConfigProvider(trunk: BlockState, leaves: BlockState, foliageX: Int, foliageY: Int, configIn: (TreeFeatureConfig.Builder.()->Unit)) : (Dynamic<*>?)->TreeFeatureConfig =
            {TreeFeatureConfig.Builder(SimpleBlockStateProvider(trunk), SimpleBlockStateProvider(leaves), BlobFoliagePlacer(foliageX, foliageY)).apply {configIn()}.build()}
    }

    constructor(registryName: String, trunk: BlockState, leaves: BlockState, foliageX: Int, foliageY: Int, config: (TreeFeatureConfig.Builder.()->Unit)={}) : this(registryName, trunk, leaves, foliageX, foliageY, config, getConfigProvider(trunk, leaves, foliageX, foliageY, config))
    constructor(registryName: String, trunk: Block, leaves: Block, foliageX: Int, foliageY: Int, config: (TreeFeatureConfig.Builder.()->Unit)={}) : this(registryName, trunk.defaultState, leaves.defaultState, foliageX, foliageY, config)
    init {
        setRegistryName(registryName)
    }
}