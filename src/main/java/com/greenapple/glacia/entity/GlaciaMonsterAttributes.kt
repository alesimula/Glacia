package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.entity.ai.attributes.Attribute
import net.minecraft.entity.ai.attributes.IAttribute
import net.minecraft.entity.ai.attributes.IAttributeInstance
import java.security.InvalidParameterException
import kotlin.math.round
import kotlin.reflect.KClass

object GlaciaMonsterAttributes {
    inline fun <reified E: Enum<E>> EnumAttribute(name: String, defaultValue: Enum<E>?=null) = enumValues<E>().let {values -> object : Attribute(null as IAttribute?, name, defaultValue?.ordinal?.toDouble()?:0.0) {
        override fun clampValue(value: Double) = round(value.coerceIn(0.0, (values.size-1).toDouble()))
        override infix fun equals(other: Any?) = other is E || super.equals(other)
    }}

    enum class Gender {MALE, FEMALE}

    val GENDER = EnumAttribute("${Glacia.MODID}.gender", Gender.MALE)
}

infix fun IAttributeInstance?.isEnum(enum: Enum<*>) = this?.run {attribute == enum && baseValue == enum.ordinal.toDouble()} ?: false
infix fun IAttributeInstance.setEnum(enum: Enum<*>) = if (attribute == enum) baseValue = enum.ordinal.toDouble() else throw InvalidParameterException()