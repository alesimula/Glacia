package com.greenapple.glacia.delegate

import sun.reflect.ConstructorAccessor
import java.lang.reflect.Constructor
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

private val constructorAccessorField by lazy {Constructor::class.java.getDeclaredField("constructorAccessor").apply {isAccessible = true}}
private val constructorAccessorRetriever by lazy {Constructor::class.java.getDeclaredMethod("acquireConstructorAccessor").apply {isAccessible = true}}

open class ReflectEnumConstructor<This: Any> (thisRef: KClass<This>) {
    private val oreFeatureConfigConstructor by lazy {thisRef.java.declaredConstructors[0].run {
        isAccessible = true
        (constructorAccessorField.get(this) ?: constructorAccessorRetriever.invoke(this)) as ConstructorAccessor
    }}
    inner class EnumConstructor : (String, Array<out Any?>) -> This {
        @Suppress("UNCHECKED_CAST")
        override fun invoke(enumName: String, vararg args: Any?): This = oreFeatureConfigConstructor.newInstance(arrayOf(enumName, -1, *args)) as This
    }

    operator fun getValue(thisRef:This?,property:KProperty<*>) = EnumConstructor()
}

inline fun <reified This: Any>ReflectEnumConstructor() = ReflectEnumConstructor(This::class)