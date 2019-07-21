package com.greenapple.glacia.block

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils

open class BlockItemBase(val unlocalizedName: String, blockIn: Block, builder: Item.Properties) : BlockItem(blockIn, builder) {

    init {
        registryName = block.registryName
    }

    constructor(blockNamed: IBlockNamed, builder: Item.Properties) : this(blockNamed.unlocalizedName, blockNamed.block, builder)

    private val unlocalizedNameText : ITextComponent by lazy {TextComponentUtils.toTextComponent {unlocalizedName}}

    override fun getDisplayName(stack: ItemStack): ITextComponent = super.getDisplayName(stack).let {name ->
        if (name.unformattedComponentText == block.translationKey) unlocalizedNameText
        else name
    }
}

fun Block.toBlockItem(name: String, group: ItemGroup?=null) = BlockItemBase(name, this, Item.Properties().apply {group?.let {group(it)}})
fun IBlockNamed.toBlockItem(group: ItemGroup?=null) = block.toBlockItem(unlocalizedName, group)