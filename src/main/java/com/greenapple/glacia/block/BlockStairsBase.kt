package com.greenapple.glacia.block

import net.minecraft.block.*

class BlockStairsBase(registryName: String, override val unlocalizedName: String, block: Block) : StairsBlock(block.defaultState, Properties.from(block)), IBlockBase {

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
}