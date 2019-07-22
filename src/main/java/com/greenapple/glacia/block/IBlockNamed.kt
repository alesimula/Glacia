package com.greenapple.glacia.block

import net.minecraftforge.common.extensions.IForgeBlock

interface IBlockNamed : IForgeBlock {
    val unlocalizedName: String
    var blockItem: BlockItemBase?

    fun getVariant(name: String) = blockItem?.getVariant(name)
}