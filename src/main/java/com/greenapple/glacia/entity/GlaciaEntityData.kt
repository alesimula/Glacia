package com.greenapple.glacia.entity

import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.network.datasync.IDataSerializer
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

// <editor-fold defaultstate="collapsed" desc="Delegation methods">
private class EntityDataDelegate<This: Entity, Return> (private val default: Return, private val dataProvider: This.()->DataParameter<Return>) {
    operator fun getValue(thisRef:This, property: KProperty<*>) = runCatching {thisRef.dataManager.get(dataProvider(thisRef)) ?: default}.getOrElse {default}
    operator fun setValue(thisRef:This, property: KProperty<*>, value: Return) {runCatching {thisRef.dataManager.set(dataProvider(thisRef), value)}}
}
private class EntityDataKeyDelegate<This: Entity, D> (thisRef: KClass<This>, serializer: IDataSerializer<D>) {
    val value : DataParameter<D> by lazy {EntityDataManager.createKey(thisRef.java, serializer)}
    operator fun getValue(thisRef:This, property: KProperty<*>) = value
}
private inline fun <reified This: Entity, Return> dataGetter(default: Return, noinline dataProvider: This.()->DataParameter<Return>) = EntityDataDelegate(default, dataProvider)
private inline fun <reified This: Entity, D> dataKey(serializer: IDataSerializer<D>) = EntityDataKeyDelegate(This::class, serializer)
// </editor-fold>

val LivingEntity.IS_MALE by dataKey(DataSerializers.BOOLEAN)
var LivingEntity.isMale by dataGetter (true) {IS_MALE}