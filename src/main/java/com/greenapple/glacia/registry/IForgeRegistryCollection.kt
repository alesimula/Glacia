package com.greenapple.glacia.registry

import com.greenapple.glacia.block.IBlockBase
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

interface IForgeRegistryCollection <E: IForgeRegistryEntry<E>>

/*private inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistryCollection<E>.toRegistryEntryArray() =
        this::class.declaredMemberProperties.filter {property -> property.visibility == KVisibility.PUBLIC && property.parameters.size==1 && property.returnType.isSubtypeOf(E::class.starProjectedType)}
                .mapNotNull {property -> runCatching {property.call(this) as? E}.getOrElse {property.javaField?.apply {if (!isAccessible) isAccessible=true}?.get(this) as? E}}.toTypedArray()*/

private inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistryCollection<E>.toRegistryEntryArray() =
        this::class.java.declaredFields.filter {property -> E::class.java.isAssignableFrom(property.type)}
                .mapNotNull {property -> kotlin.runCatching {property.apply {isAccessible=true}.get(this) as? E}.getOrNull()}.toTypedArray()

@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistry<E>.register(registryCollection: IForgeRegistryCollection<E>) = this.registerAll(*registryCollection.toRegistryEntryArray())

private fun IForgeRegistry<Item>.getBlockItems(registryCollection: IForgeRegistryCollection<Block>, defaultGroup: ItemGroup?=null)
        = registryCollection.toRegistryEntryArray().mapNotNull {block ->  (block as? IBlockBase)?.let {return@mapNotNull block.getBlockItemForRegistry(this)}
        ?: defaultGroup?.let {group -> BlockItem(block, Item.Properties().apply {group(group)}).apply {registryName = block.registryName}} }.toTypedArray()

fun IForgeRegistry<Item>.registerBlockItems(registryCollection: IForgeRegistryCollection<Block>, defaultGroup: ItemGroup?=null)
        = this.registerAll(*this.getBlockItems(registryCollection, defaultGroup))