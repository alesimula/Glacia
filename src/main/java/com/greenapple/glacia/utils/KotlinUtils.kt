package com.greenapple.glacia.utils

import com.google.gson.Gson
import kotlin.reflect.KClass

interface WrappedClass<O> {val value: O}
private inline class WrappedClassImpl<O>(private val wrapped: Any?): WrappedClass<O> {override val value: O get() = wrapped as O}
fun <O: Any?> wrappedClass(value: O): WrappedClass<O> = WrappedClassImpl(value)

infix fun KClass<*>.extends(klass: KClass<*>) = klass.java.isAssignableFrom(java)

inline fun <reified O: Any> O.deepClone(): O = Gson().let { gson-> gson.fromJson<O>(gson.toJson(this, O::class.java), O::class.java)}
inline fun <reified O: Any, reified R: O> O.deepCloneTo(klass: KClass<R>): R = Gson().let { gson-> gson.fromJson<R>(gson.toJson(this, O::class.java), R::class.java)}