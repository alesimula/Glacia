package com.greenapple.glacia.entity

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.greenapple.glacia.delegate.LazyWithReceiver
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.network.datasync.DataParameter
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.network.datasync.IDataSerializer
import java.lang.ref.WeakReference
import java.util.*
import kotlin.random.Random
import kotlin.reflect.KProperty

// <editor-fold defaultstate="collapsed" desc="Delegation methods">
//val dataManagerCache : Cache<UUID, EntityDataManager> = CacheBuilder.newBuilder().weakKeys().weakValues().build()
val dataManagerCache : MutableMap<UUID, WeakReference<EntityDataManager>> = Collections.synchronizedMap(WeakHashMap<UUID, WeakReference<EntityDataManager>>())
private class EntityDataDelegate<This: Entity, Return> (serializer: IDataSerializer<Return>, private val defaultProvider: This.()->Return) {
    val Class<This>.dataParameter : DataParameter<Return> by LazyWithReceiver(false) {EntityDataManager.createKey(this, serializer)}
    val This.cachedDataManager : EntityDataManager; get() = dataManagerCache.putIfAbsent(uniqueID, WeakReference(dataManager))?.get() ?: dataManager
    val This.dataParameter; get() = this.javaClass.dataParameter
    fun This.registerData(value: Return) = cachedDataManager.runCatching {dataManager.register(dataParameter, value); value}.getOrNull()

    operator fun getValue(thisRef:This, property: KProperty<*>) = thisRef.run {defaultProvider(this).let {default -> registerData(default) ?: runCatching{cachedDataManager.get(dataParameter) ?: default}.getOrElse {default}}}
    operator fun setValue(thisRef:This, property: KProperty<*>, value: Return) = thisRef.apply {registerData(value) ?: runCatching {cachedDataManager.set(dataParameter, value)}}
}
private inline fun <reified This: Entity, Return> entityData(serializer: IDataSerializer<Return>, noinline defaultProvider: This.()->Return) = EntityDataDelegate(serializer, defaultProvider)
// </editor-fold>

var LivingEntity.isMale by entityData(DataSerializers.BOOLEAN) {Random.nextBoolean()}