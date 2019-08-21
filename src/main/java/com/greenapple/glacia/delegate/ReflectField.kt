package com.greenapple.glacia.delegate

import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

var Field.modifiersKt : Int by ReflectField("modifiers")

open class ReflectField<This: Any,Return> (thisRef: KClass<This>, name: String, isFinal: Boolean=false) {
    private val field by lazy {ObfuscationReflectionHelper.findField(thisRef.java, name).apply {if (isFinal) modifiersKt = modifiers and Modifier.FINAL.inv()}}

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef:This?,property:KProperty<*>) = field.get(thisRef) as Return
    operator fun setValue(thisRef:This?,property: KProperty<*>, value: Return) = field.set(thisRef, value)
}

inline fun <reified This: Any, Return>ReflectField(name: String, isFinal: Boolean=false) = ReflectField<This, Return>(This::class, name, isFinal)