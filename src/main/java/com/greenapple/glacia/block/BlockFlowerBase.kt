package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.BlockItemBase
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.potion.Effect
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.state.properties.SlabType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry

class BlockFlowerBase(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, effect: Effect, effectDuration: Int, initializer: (Properties.()->Unit)?=null) : FlowerBlock(effect, effectDuration, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.PLANT).apply {initializer?.invoke(this)}), IBlockBase {

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let {item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)

    override fun isValidGround(state: BlockState, worldIn: IBlockReader, pos: BlockPos) = state.block === Glacia.Blocks.GLACIAL_DIRT
    override fun isValidPosition(state: BlockState, worldIn: IWorldReader, pos: BlockPos) = super.isValidPosition(state, worldIn, pos) && pos.down().let {position -> isValidGround(worldIn.getBlockState(position), worldIn, position)}
}