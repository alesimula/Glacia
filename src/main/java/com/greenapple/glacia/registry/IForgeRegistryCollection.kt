package com.greenapple.glacia.registry

import com.greenapple.glacia.block.IBlockBase
import com.greenapple.glacia.utils.extends
import com.greenapple.glacia.utils.runClient
import net.minecraft.block.Block
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.fluid.Fluid
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.common.extensions.IForgeBlock
import net.minecraftforge.common.extensions.IForgeFluid
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

interface IAbstractRegistryCollection <E: Any>
interface IForgeRegistryCollection <E: IForgeRegistryEntry<E>> : IAbstractRegistryCollection<E>
abstract class ICustomRegistryCollection <E: Any>(private val registrationMethod: E.()->Unit) : IAbstractRegistryCollection<E> {
    companion object {
        internal inline fun <reified E : Any> ICustomRegistryCollection<E>.registerAllInternal(entries: Array<E>) = entries.forEach {this.registrationMethod(it)}
    }
}

private val renderTypeMap = mutableMapOf<IForgeRegistryEntry<*>, RenderType>()
var Block.renderType get() = renderTypeMap[this]; set(value) {if (value!=null) renderTypeMap[this] = value else renderTypeMap.remove(this)}
var Fluid.renderType get() = renderTypeMap[this]; set(value) {if (value!=null) renderTypeMap[this] = value else renderTypeMap.remove(this)}
private fun Block.registerRenderType() = renderTypeMap.remove(this)?.let {renderType-> RenderTypeLookup.setRenderLayer(this, renderType)}
private fun Fluid.registerRenderType() = renderTypeMap.remove(this)?.let {renderType-> RenderTypeLookup.setRenderLayer(this, renderType)}

/*private inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistryCollection<E>.toRegistryEntryArray() =
        this::class.declaredMemberProperties.filter {property -> property.visibility == KVisibility.PUBLIC && property.parameters.size==1 && property.returnType.isSubtypeOf(E::class.starProjectedType)}
                .mapNotNull {property -> runCatching {property.call(this) as? E}.getOrElse {property.javaField?.apply {if (!isAccessible) isAccessible=true}?.get(this) as? E}}.toTypedArray()*/

private inline fun <reified E : Any> IAbstractRegistryCollection<E>.toRegistryEntryArray() =
        this::class.java.declaredFields.filter {property -> E::class.java.isAssignableFrom(property.type)}
                .mapNotNull {property -> kotlin.runCatching {property.apply {isAccessible=true}.get(this) as? E}.getOrNull()}.toTypedArray()

@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <reified E : IForgeRegistryEntry<E>> IForgeRegistry<E>.register(registryCollection: IForgeRegistryCollection<E>) = registryCollection.toRegistryEntryArray().let {entries ->
    this.registerAll(*entries)
    runClient {when {
        E::class extends IForgeBlock::class -> for (block in entries) (block as? Block)?.registerRenderType()
        E::class extends IForgeFluid::class -> for (fluid in entries) (fluid as? Fluid)?.registerRenderType()
    }}
}
@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <reified E : Any> ICustomRegistryCollection<E>.registerAll() = ICustomRegistryCollection.apply {registerAllInternal(toRegistryEntryArray())}

private fun IForgeRegistry<Item>.getBlockItems(registryCollection: IForgeRegistryCollection<Block>, defaultGroup: ItemGroup?=null)
        = registryCollection.toRegistryEntryArray().mapNotNull {block ->  (block as? IBlockBase)?.let {return@mapNotNull block.getBlockItemForRegistry(this)}
        ?: defaultGroup?.let {group -> BlockItem(block, Item.Properties().apply {group(group)}).apply {registryName = block.registryName}} }.toTypedArray()

fun IForgeRegistry<Item>.registerBlockItems(registryCollection: IForgeRegistryCollection<Block>, defaultGroup: ItemGroup?=null)
        = this.registerAll(*this.getBlockItems(registryCollection, defaultGroup))

fun TextureStitchEvent.Pre.registerFluidTextures(registryCollection: IForgeRegistryCollection<Fluid>)
        = registryCollection.toRegistryEntryArray().forEach {it.attributes.run {overlayTexture?.run {addSprite(this)}; stillTexture?.run {addSprite(this)}; flowingTexture?.run {addSprite(this)}}}