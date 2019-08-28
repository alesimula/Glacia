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
import java.util.*
import kotlin.random.Random
import kotlin.reflect.KProperty

// <editor-fold defaultstate="collapsed" desc="Delegation methods">
private val dataManagerCache : Cache<UUID, EntityDataManager> = CacheBuilder.newBuilder().weakKeys().build()
private class EntityDataDelegate<This: Entity, Return> (serializer: IDataSerializer<Return>, private val defaultProvider: This.()->Return) {
    val Class<This>.dataParameter : DataParameter<Return> by LazyWithReceiver(false) {EntityDataManager.createKey(this, serializer)}
    val This.cachedDataManager; get() = dataManagerCache.getIfPresent(uniqueID)
    val This.dataParameter; get() = this.javaClass.dataParameter
    fun This.registerData(value: Return) : Return? {
        return synchronized(uniqueID) {
            cachedDataManager ?: dataManager.apply {dataManagerCache.put(uniqueID, this)}
        }.runCatching {dataManager.register(dataParameter, value); value}.getOrNull()
    }

    operator fun getValue(thisRef:This, property: KProperty<*>) = defaultProvider(thisRef).let {default -> thisRef.registerData(default); runCatching{thisRef.cachedDataManager?.get(thisRef.dataParameter) ?: default}.getOrElse {default}}
    operator fun setValue(thisRef:This, property: KProperty<*>, value: Return) {thisRef.registerData(value) ?: runCatching {thisRef.cachedDataManager?.set(thisRef.dataParameter, value)}}
}
private inline fun <reified This: Entity, Return> entityData(serializer: IDataSerializer<Return>, noinline defaultProvider: This.()->Return) = EntityDataDelegate(serializer, defaultProvider)
// </editor-fold>

var LivingEntity.isMale by entityData(DataSerializers.BOOLEAN) {Random.nextBoolean()}