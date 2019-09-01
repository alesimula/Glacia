package com.greenapple.glacia.block

import com.greenapple.glacia.item.BlockItemBase
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.storage.loot.LootContext
import net.minecraft.world.storage.loot.LootTables
import net.minecraftforge.registries.IForgeRegistry

open class BlockBase private constructor (registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, properties: Properties, initializer: (Properties.()->Unit)?=null) : Block(properties.apply {initializer?.invoke(this)}), IBlockBase {

    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, materialColor: MaterialColor=material.color, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, Properties.create(material, materialColor), initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, Properties.create(material, dyeColor), initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, material, material.color, initializer)


    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    protected val noDrops = lootTable === LootTables.EMPTY
    private var customRenderLayer : BlockRenderLayer = super.getRenderLayer()
    var isTranslucent = false
    var seeThroughGroup = false

    fun setRenderLayer(renderLayer: BlockRenderLayer) {customRenderLayer = renderLayer}
    override fun getRenderLayer(): BlockRenderLayer = customRenderLayer

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.takeIf {!noDrops}?.let {item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)

    /**
     * First function in AbstractGlassBlock
     */
    override fun func_220080_a(state: BlockState, world: IBlockReader, pos: BlockPos): Float = if (isTranslucent) 1.0f else super.func_220080_a(state, world, pos)
    override fun propagatesSkylightDown(state: BlockState, reader: IBlockReader, pos: BlockPos) = isTranslucent
    override fun isNormalCube(state: BlockState, worldIn: IBlockReader, pos: BlockPos) = !isTranslucent

    override fun isSideInvisible(state: BlockState, adjacentBlockState: BlockState, side: Direction)
            = if (renderLayer !== BlockRenderLayer.SOLID && seeThroughGroup && adjacentBlockState.block === this) true else super.isSideInvisible(state, adjacentBlockState, side)
}