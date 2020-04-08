package com.greenapple.glacia.entity

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
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

// <editor-fold defaultstate="collapsed" desc="Delegation methods">
//val dataManagerCache : Cache<UUID, EntityDataManager> = CacheBuilder.newBuilder().weakKeys().weakValues().build()

private val dataManagerCache : MutableMap<UUID, WeakReference<EntityDataManager>> = Collections.synchronizedMap(WeakHashMap<UUID, WeakReference<EntityDataManager>>())
interface IEntityDataDelegate<This: Entity, Return> {
    operator fun getValue(thisRef:This, property: KProperty<*>) : Return
    operator fun setValue(thisRef:This, property: KProperty<*>, value: Return)
}
private class EntityDataDelegate<This: Entity, Return> (serializer: IDataSerializer<Return>, private val defaultProvider: This.()->Return, private val sideEffect: This.(Return)->Unit={}) : IEntityDataDelegate<This, Return> {
    private val KClass<out This>.dataParameter : DataParameter<Return> by LazyWithReceiver(false) {EntityDataManager.createKey(this.java, serializer)}
    private val This.cachedDataManager : EntityDataManager; get() = dataManagerCache.putIfAbsent(uniqueID, WeakReference(dataManager))?.get() ?: dataManager
    private val This.dataParameter; get() = this::class.dataParameter
    private fun This.getOrSet(value: Return) = cachedDataManager.runCatching {get(dataParameter) ?: value.also {if (it!=null) set(dataParameter, value)}}.getOrElse {cachedDataManager.set(dataParameter, value); value}
    private fun This.registerData(value: Return) = cachedDataManager.runCatching {dataManager.register(dataParameter, value); value}.getOrNull()

    override operator fun getValue(thisRef:This, property: KProperty<*>) = thisRef.run {defaultProvider(this).let {default -> registerData(default)?.apply {sideEffect(this)} ?: getOrSet(default)}}
    override operator fun setValue(thisRef:This, property: KProperty<*>, value: Return) = thisRef.run {registerData(value) ?: runCatching {cachedDataManager.set(dataParameter, value)}; sideEffect(value)}
}

/**
 * These delegates should only be called in a static/singleton context
 */
@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <reified This: Entity, Return> entityData(serializer: IDataSerializer<Return>, noinline defaultProvider: This.()->Return, noinline sideEffect: This.(Return)->Unit={}) : IEntityDataDelegate<This, Return> = EntityDataDelegate(serializer, defaultProvider, sideEffect)
inline fun <reified This: Entity, Return> entityData(serializer: IDataSerializer<Return>, noinline defaultProvider: This.()->Return) = entityData(serializer, defaultProvider) {}
// </editor-fold>

var LivingEntity.isMale by entityData(DataSerializers.BOOLEAN) {Random.nextBoolean()}