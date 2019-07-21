package com.greenapple.glacia.block

import net.minecraftforge.common.extensions.IForgeBlock

interface IBlockNamed : IForgeBlock {
    val unlocalizedName: String
}