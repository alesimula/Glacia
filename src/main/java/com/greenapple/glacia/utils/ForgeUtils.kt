package com.greenapple.glacia.utils

import com.greenapple.glacia.delegate.LazyWithReceiver
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.ForgeRegistry
import net.minecraftforge.registries.ForgeRegistryEntry
import net.minecraftforge.registries.RegistryManager

inline fun <E: Event>IEventBus.addListenerKt(crossinline method : (E)->Any) = addListener<E> {event-> method.invoke(event)}

private val <T: ForgeRegistryEntry<T>, C: Class<T>> C.registry : ForgeRegistry<T> by LazyWithReceiver {RegistryManager.ACTIVE.getRegistry<T>(this) as ForgeRegistry<T>}
val <T: ForgeRegistryEntry<T>> T.registry; get() = this.registryType.registry
val <T: ForgeRegistryEntry<T>> T.id; get() = this.registry.getID(this)

operator fun ResourceLocation.plus(extra: String) = ResourceLocation(namespace, "${path}_$extra")
operator fun ResourceLocation.rem(extra: String) = ResourceLocation(namespace, "$extra/$path")

val TranslationTextComponent?.modKey by lazy {"§5§r§e§e§n§a§7§7§l§e§r"}

//val ModLifecycleEvent.container : ModContainer by ReflectField("container")
//val ModContainer.modInfo : IModInfo by ReflectField("modInfo")

/*var ModInfo.displayNameKt : String by ReflectField("displayName")
var ModInfo.descriptionKt : String by ReflectField("description")
var ModInfo.logoFileKt : Optional<String> by ReflectField("logoFile")*/

/*private final ModFileInfo owningFile;
private final String modId;
private final String namespace;
private final ArtifactVersion version;
private final String displayName;
private final String description;
private final Optional<String> logoFile;
private final URL updateJSONURL;
private final List<ModVersion> dependencies;
private final Map<String, Object> properties;
private final UnmodifiableConfig modConfig;*/