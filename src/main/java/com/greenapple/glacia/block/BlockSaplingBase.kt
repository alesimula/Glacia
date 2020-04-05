package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.BlockItemBase
import com.greenapple.glacia.registry.renderType
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.SaplingBlock
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.trees.Tree
import net.minecraft.client.renderer.RenderType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.biome.DefaultBiomeFeatures
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry
import java.util.*

class BlockSaplingBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, treeConfig: ()->TreeFeatureConfig) : SaplingBlock(
        object : Tree() {
            val treeConfig by lazy {treeConfig()}
            override fun getTreeFeature(random: Random, p1: Boolean) : ConfiguredFeature<TreeFeatureConfig, *> = Feature.NORMAL_TREE.withConfiguration(this.treeConfig)
        },
        Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.PLANT)
), IBlockBase {
    companion object {
        val EMPTY_FEATURE = TreeFeatureConfig.Builder(SimpleBlockStateProvider(Blocks.AIR.defaultState), SimpleBlockStateProvider(Blocks.AIR.defaultState), BlobFoliagePlacer(0,0)).build()
    }
    init {
        setRegistryName(registryName)
        renderType = RenderType.getCutout()
    }

    override fun isValidGround(state: BlockState, worldIn: IBlockReader, pos: BlockPos) = state.block === Glacia.Blocks.GLACIAL_DIRT

    override fun canGrow(p_176473_1_: IBlockReader, p_176473_2_: BlockPos, p_176473_3_: BlockState, p_176473_4_: Boolean): Boolean {
        return super.canGrow(p_176473_1_, p_176473_2_, p_176473_3_, p_176473_4_)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let {item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)
}