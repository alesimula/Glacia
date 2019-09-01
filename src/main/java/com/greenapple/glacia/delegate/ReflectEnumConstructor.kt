package com.greenapple.glacia.delegate

import sun.reflect.ConstructorAccessor
import java.lang.reflect.Constructor
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

private val Constructor<*>.constructorAccessorKt : ConstructorAccessor? by ReflectField("constructorAccessor")
private val constructorAccessorRetriever by lazy {Constructor::class.java.getDeclaredMethod("acquireConstructorAccessor").apply {isAccessible = true}}

open class ReflectEnumConstructor<This: Enum<*>> (thisRef: KClass<This>, vararg args: KClass<*>) {
    private val constructorAccessor by lazy { thisRef.java.run {
        (if (args.isEmpty()) runCatching {getDeclaredConstructor(String::class.java, Int::class.java)}.getOrElse {declaredConstructors[0]} else getDeclaredConstructor(String::class.java, Int::class.java, *args.map {it.java}.toTypedArray())).run {
            isAccessible = true
            (constructorAccessorKt ?: constructorAccessorRetriever(this)) as ConstructorAccessor
        }
    }}
    @Suppress("VAL_REASSIGNMENT_VIA_BACKING_FIELD_ERROR")
    private val nextId = 0; get() = field++
    inner class EnumConstructor : (Array<out Any?>) -> This {
        @Suppress("UNCHECKED_CAST")
        override fun invoke(vararg args: Any?): This = constructorAccessor.newInstance(arrayOf("${this::class.qualifiedName}_$nextId", -1, *args)) as This
    }
    private val constructor = EnumConstructor()

    operator fun getValue(thisRef:Any?,property:KProperty<*>) = constructor
}

inline fun <reified This: Enum<*>>ReflectEnumConstructor(vararg args: KClass<*>) = ReflectEnumConstructor(This::class, *args)