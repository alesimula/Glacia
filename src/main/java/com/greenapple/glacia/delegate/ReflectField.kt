package com.greenapple.glacia.delegate

import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

open class ReflectField<This: Any,Return> (thisRef: KClass<This>, name: String) {
    private val field by lazy {ObfuscationReflectionHelper.findField(thisRef.java, name)}

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef:This?,property:KProperty<*>) = field.get(thisRef) as Return
    operator fun setValue(thisRef:This?,property: KProperty<*>, value: Return) = field.set(thisRef, value)
}

inline fun <reified This: Any, Return>ReflectField(name: String) = ReflectField<This, Return>(This::class, name)