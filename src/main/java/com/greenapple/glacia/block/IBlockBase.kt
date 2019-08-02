package com.greenapple.glacia.block

import net.minecraftforge.common.extensions.IForgeBlock

interface IBlockBase : IForgeBlock {
    val unlocalizedName: String
    var blockItem: BlockItemBase?

    fun blockItemVatiant(name: String) = blockItem?.getVariant(name)
}