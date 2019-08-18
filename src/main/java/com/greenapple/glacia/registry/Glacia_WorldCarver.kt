package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.world.feature.FeatureGlaciaFlora
import com.greenapple.glacia.world.feature.FeatureGlacialTree
import com.mojang.datafixers.Dynamic
import net.minecraft.world.gen.carver.CaveWorldCarver
import net.minecraft.world.gen.carver.ICarverConfig
import net.minecraft.world.gen.carver.WorldCarver
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraft.world.gen.feature.ProbabilityConfig
import java.util.function.Function

object Glacia_WorldCarver : IForgeRegistryCollection<WorldCarver<*>> {
    class WorldCarverCaveBase(registryName: String, height: Int) : CaveWorldCarver(Function {ProbabilityConfig.deserialize(it)}, height) {
        init {
            setRegistryName(registryName)
            carvableBlocks = setOf(*carvableBlocks.toTypedArray(), Glacia.Blocks.GLACIAL_STONE, Glacia.Blocks.GLACIAL_DIRT, Glacia.Blocks.SNOWY_SAND)
        }
    }
    val CAVE = WorldCarverCaveBase("cave", 256)
}