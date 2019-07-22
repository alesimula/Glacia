package com.greenapple.glacia.block

import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor

open class BlockBase private constructor (override val unlocalizedName: String, properties: Properties, initializer: (Properties.()->Unit)?=null) : Block(properties.apply {initializer?.invoke(this)}), IBlockNamed {

    constructor(name: String, material: Material, materialColor: MaterialColor=material.color, initializer: (Properties.()->Unit)?=null) : this(name, Properties.create(material, materialColor), initializer)
    constructor(name: String, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : this(name, Properties.create(material, dyeColor), initializer)
    constructor(name: String, material: Material, initializer: (Properties.()->Unit)?=null) : this(name, material, material.color, initializer)

    override var blockItem: BlockItemBase?=null
}