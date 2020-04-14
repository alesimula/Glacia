package com.greenapple.glacia.utils

import com.google.gson.Gson
import com.greenapple.glacia.delegate.ReflectField
import net.minecraft.potion.EffectInstance
import kotlin.reflect.KClass

infix fun KClass<*>.extends(klass: KClass<*>) = klass.java.isAssignableFrom(java)

inline fun <reified O: Any> O.deepClone(): O = Gson().let {gson-> gson.fromJson<O>(gson.toJson(this, O::class.java), O::class.java)}
inline fun <reified O: Any, reified R: O> O.deepCloneTo(klass: KClass<R>): R = Gson().let {gson-> gson.fromJson<R>(gson.toJson(this, O::class.java), R::class.java)}

//Effect
var EffectInstance.durationKt : Int by ReflectField("field_76460_b")