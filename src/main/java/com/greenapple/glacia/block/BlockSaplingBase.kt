package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.BlockItemBase
import com.mojang.datafixers.Dynamic
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.trees.Tree
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.gen.feature.AbstractTreeFeature
import net.minecraft.world.gen.feature.NoFeatureConfig
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry
import java.util.*

class BlockSaplingBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, treeProvider: java.util.function.Function<Dynamic<*>, NoFeatureConfig>.(Random)->AbstractTreeFeature<NoFeatureConfig>) : SaplingBlock(
        object : Tree() {
            override fun getTreeFeature(random: Random) = treeProvider(java.util.function.Function {NoFeatureConfig.deserialize(it)}, random)
        },
        Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT)
), IBlockBase {
    init {
        setRegistryName(registryName)
    }

    override fun isValidGround(state: BlockState, worldIn: IBlockReader, pos: BlockPos) = state.block === Glacia.Blocks.GLACIAL_DIRT

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let {item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)
}