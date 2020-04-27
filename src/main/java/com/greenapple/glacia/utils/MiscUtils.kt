package com.greenapple.glacia.utils

import com.greenapple.glacia.delegate.reflectField
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.potion.EffectInstance

fun Block.overrideLightValue(value: Int? = null) = stateContainer.validStates.forEach {it.lightLevelKt = value ?: getLightValue(it)}
var BlockState.lightLevelKt : Int by reflectField("field_215708_d", true)

//Effect
var EffectInstance.durationKt : Int by reflectField("field_76460_b")