package com.greenapple.glacia.registry

import com.greenapple.glacia.world.feature.FeatureGlaciaBerries
import com.greenapple.glacia.world.feature.FeatureGlaciaFlora
import com.greenapple.glacia.world.feature.FeatureGlacialTree
import com.mojang.datafixers.Dynamic
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig

object Glacia_Feature : IForgeRegistryCollection<Feature<*>> {
    val GLACIAL_TREE = FeatureGlacialTree("glacial_tree", java.util.function.Function<Dynamic<*>, NoFeatureConfig> {NoFeatureConfig.deserialize(it)}, false, false)
    val BERRIES = FeatureGlaciaBerries("berries", java.util.function.Function<Dynamic<*>, NoFeatureConfig> {NoFeatureConfig.deserialize(it)})
    val FLORA = FeatureGlaciaFlora("flora", java.util.function.Function<Dynamic<*>, NoFeatureConfig> {NoFeatureConfig.deserialize(it)})
}