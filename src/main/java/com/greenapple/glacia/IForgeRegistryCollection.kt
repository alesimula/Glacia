package com.greenapple.glacia

import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.javaField

interface IForgeRegistryCollection <E: IForgeRegistryEntry<E>>

private inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistryCollection<E>.toRegistryEntryArray() =
        this::class.declaredMemberProperties.filter {property -> property.visibility == KVisibility.PUBLIC && property.parameters.size==1 && property.returnType.isSubtypeOf(E::class.starProjectedType)}
                .mapNotNull {property -> runCatching {property.call(this) as? E}.getOrElse {property.javaField?.apply {if (!isAccessible) isAccessible=true}?.get(this) as? E}}.toTypedArray()

/*private inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistryCollection<E>.toRegistryEntryArrayJava() =
        this::class.java.declaredFields.filter {property -> property.isAccessible && E::class.java.isAssignableFrom(property.type)}
                .mapNotNull {property -> kotlin.runCatching {property.get(this) as? E}.getOrNull()}.toTypedArray()*/

@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistry<E>.register(registryCollection: IForgeRegistryCollection<E>) = this.registerAll(*registryCollection.toRegistryEntryArray())