package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.world.feature.*
import com.mojang.datafixers.Dynamic
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig

object Glacia_Feature : IForgeRegistryCollection<Feature<*>> {
    //val GLACIAL_TREE = FeatureGlacialTree("glacial_tree", java.util.function.Function<Dynamic<*>, NoFeatureConfig> {NoFeatureConfig.deserialize(it)}, false, false)
    //val GLACIAL_TREE = FeatureTreeBase("glacial_tree", Glacia.Blocks.GLACIAL_TREE_LOG, Glacia.Blocks.GLACIAL_TREE_LEAVES, 2, 2)
    val BERRIES = FeatureGlaciaBerries("berries", java.util.function.Function<Dynamic<*>, NoFeatureConfig> {NoFeatureConfig.deserialize(it)})
    val FLORA = FeatureGlaciaFlora("flora", java.util.function.Function<Dynamic<*>, NoFeatureConfig> {NoFeatureConfig.deserialize(it)})
    val ICE_TOWER = FeatureGlaciaIceTower("ice_tower", java.util.function.Function<Dynamic<*>, NoFeatureConfig> {NoFeatureConfig.deserialize(it)})
}