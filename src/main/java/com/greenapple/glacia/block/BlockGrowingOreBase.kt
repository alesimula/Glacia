package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.storage.loot.*

/**
 * Don't forget to add the block to the 'fences' tag group
 */
class BlockGrowingOreBase(registryName: String, unlocalizedName: String, itemGroup: ItemGroup?, private val droppedItem: Item, private val dropWithSilkTouch: Boolean=true, width: Double=16.0, height: Double=16.0, initializer: (Properties.()->Unit)?=null) : BlockGrowingBase(registryName, unlocalizedName, itemGroup, Material.ROCK, width, height, initializer) {

    constructor(registryName: String, unlocalizedName: String, droppedItem: Item,  dropWithSilkTouch: Boolean=true, width: Double=16.0, height: Double=16.0, initializer: (Properties.()->Unit)?=null) : this(registryName, unlocalizedName, Glacia.ItemGroup.DECORATIONS, droppedItem, dropWithSilkTouch, width, height, initializer)

    init {
        validGroundBlocks {material === Material.ROCK}
    }

    override fun getExpDrop(state: BlockState, reader: net.minecraft.world.IWorldReader, pos: BlockPos, fortune: Int, silktouch: Int): Int {
        return if (silktouch == 0) MathHelper.nextInt(RANDOM, 3, 7); else 0
    }

    override fun getDrops(state: BlockState, builder: LootContext.Builder) = generateOreDrops(state, builder, droppedItem, dropWithSilkTouch)
}