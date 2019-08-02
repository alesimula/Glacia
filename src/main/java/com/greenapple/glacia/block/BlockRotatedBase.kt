package com.greenapple.glacia.block

import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor

class BlockRotatedBase private constructor(registryName: String, override val unlocalizedName: String, properties: Properties,  initializer: (Properties.()->Unit)?=null) : RotatedPillarBlock(properties.apply {initializer?.invoke(this)}), IBlockBase {

    constructor(registryName: String, name: String, material: Material, materialColor: MaterialColor =material.color, initializer: (Properties.()->Unit)?=null) : this(registryName, name, Properties.create(material, materialColor), initializer)
    constructor(registryName: String, name: String, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : this(registryName, name, Properties.create(material, dyeColor), initializer)
    constructor(registryName: String, name: String, material: Material, initializer: (Properties.()->Unit)?=null) : this(registryName, name, material, material.color, initializer)

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
}