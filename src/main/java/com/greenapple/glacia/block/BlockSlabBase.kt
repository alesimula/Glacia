package com.greenapple.glacia.block

import com.greenapple.glacia.item.BlockItemBase
import net.minecraft.block.*
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.SlabType
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry

class BlockSlabBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, block: Block) : SlabBlock(Properties.from(block)), IBlockBase {

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    private var customRenderLayer : BlockRenderLayer = super.getRenderLayer()
    var isTranslucent = false
    var seeThroughGroup = false

    fun setRenderLayer(renderLayer: BlockRenderLayer) {customRenderLayer = renderLayer}
    override fun getRenderLayer(): BlockRenderLayer = customRenderLayer

    /**
     * First function in AbstractGlassBlock
     */
    override fun func_220080_a(state: BlockState, world: IBlockReader, pos: BlockPos): Float = if (isTranslucent) 1.0f else super.func_220080_a(state, world, pos)
    override fun propagatesSkylightDown(state: BlockState, reader: IBlockReader, pos: BlockPos) = isTranslucent
    override fun isNormalCube(state: BlockState, world: IBlockReader, pos: BlockPos) = if (isTranslucent) false else super.isNormalCube(state, world, pos)
    override fun isSideInvisible(state: BlockState, adjacentBlockState: BlockState, side: Direction)
            = if (renderLayer !== BlockRenderLayer.SOLID && seeThroughGroup && adjacentBlockState === state) true else super.isSideInvisible(state, adjacentBlockState, side)

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let { item-> arrayListOf(ItemStack(item, if (state.get(TYPE)===SlabType.DOUBLE) 2 else 1))}
            ?: super.getDrops(state, builder)
}