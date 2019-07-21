package com.greenapple.glacia.utils

import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.IEventBus

inline fun <E: Event>IEventBus.addListenerKt(crossinline method : (E)->Any) = addListener<E> {event-> method.invoke(event)}


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