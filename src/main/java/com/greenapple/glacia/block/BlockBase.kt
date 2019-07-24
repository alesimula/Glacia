package com.greenapple.glacia.block

import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.storage.loot.LootContext

open class BlockBase private constructor (registryName: String, override val unlocalizedName: String, properties: Properties, initializer: (Properties.()->Unit)?=null) : Block(properties.apply {initializer?.invoke(this)}), IBlockNamed {

    constructor(registryName: String, name: String, material: Material, materialColor: MaterialColor=material.color, initializer: (Properties.()->Unit)?=null) : this(registryName, name, Properties.create(material, materialColor), initializer)
    constructor(registryName: String, name: String, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : this(registryName, name, Properties.create(material, dyeColor), initializer)
    constructor(registryName: String, name: String, material: Material, initializer: (Properties.()->Unit)?=null) : this(registryName, name, material, material.color, initializer)

    init {
        setRegistryName(registryName)
    }

    private var customRenderLayer : BlockRenderLayer = super.getRenderLayer()
    var isTranslucent = false
    var seeThroughGroup = false

    fun setRenderLayer(renderLayer: BlockRenderLayer) {customRenderLayer = renderLayer}
    override fun getRenderLayer(): BlockRenderLayer = customRenderLayer

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = blockItem?.let {item-> arrayListOf(ItemStack(item))} ?: super.getDrops(state, builder)

    /**
     * First function in AbstractGlassBlock
     */
    override fun func_220080_a(state: BlockState, world: IBlockReader, pos: BlockPos): Float = if (isTranslucent) 1.0f else super.func_220080_a(state, world, pos)
    override fun propagatesSkylightDown(state: BlockState, reader: IBlockReader, pos: BlockPos) = isTranslucent
    override fun isNormalCube(state: BlockState, worldIn: IBlockReader, pos: BlockPos) = !isTranslucent

    override fun isSideInvisible(state: BlockState, adjacentBlockState: BlockState, side: Direction)
            = if (renderLayer !== BlockRenderLayer.SOLID && seeThroughGroup && adjacentBlockState.block === this) true else super.isSideInvisible(state, adjacentBlockState, side)

    override var blockItem: BlockItemBase?=null
}