package com.greenapple.glacia.block

import com.greenapple.glacia.RegistryEvents
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.*
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentUtils
import org.apache.logging.log4j.LogManager
/*
open class BlockItemBase(val blockState: BlockState, val unlocalizedName: String, variant: String, builder: Item.Properties) : BlockItem(blockState.block, builder) {

    init {
        if (blockState === block.defaultState) registryName = block.registryName
        else {
            LogManager.getLogger().info(block.registryName?.path+"."+variant)
            setRegistryName(block.registryName?.path+"."+variant)
        }
    }

    constructor(block: Block, name: String, builder: Item.Properties) : this(block.defaultState, name,"", builder)
    constructor(blockNamed: IBlockNamed, builder: Item.Properties) : this(blockNamed.block.defaultState, blockNamed.unlocalizedName,"", builder)

    private val unlocalizedNameText : ITextComponent by lazy {TextComponentUtils.toTextComponent {unlocalizedName}}

    override fun getDisplayName(stack: ItemStack): ITextComponent = super.getDisplayName(stack).let {name ->
        if (name.unformattedComponentText == block.translationKey) unlocalizedNameText
        else name
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        return blockState
    }
}

fun BlockState.toBlockItem(name: String, variant: String, group: ItemGroup?=null) = BlockItemBase(this, name, variant, Item.Properties().apply {group?.let {group(it)}})
fun Block.toBlockItem(name: String, group: ItemGroup?=null) = defaultState.toBlockItem(name, "", group)
fun IBlockNamed.toBlockItem(group: ItemGroup?=null) = block.toBlockItem(unlocalizedName, group)*/