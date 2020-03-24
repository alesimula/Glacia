package com.greenapple.glacia.utils

import com.greenapple.glacia.delegate.ReflectField
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementProgress
import net.minecraft.advancements.DisplayInfo
import net.minecraft.client.gui.advancements.AdvancementEntryGui
import net.minecraft.client.gui.advancements.AdvancementTabGui
import net.minecraft.client.gui.advancements.AdvancementsScreen
import net.minecraft.client.multiplayer.ClientAdvancementManager

val AdvancementsScreen.tabsKt : Map<Advancement, AdvancementTabGui> by ReflectField("field_191947_i", true)
val AdvancementsScreen.clientAdvancementManagerKt : ClientAdvancementManager by ReflectField("field_191946_h", true)
val AdvancementsScreen.selectedTabKt : AdvancementTabGui by ReflectField("field_191940_s")
val AdvancementsScreen.tabPageKt : Int by ReflectField("tabPage") //Not in McpToSrg?

/*var AdvancementTabGui.screenKt : AdvancementsScreen by ReflectField("field_193938_f")

var AdvancementEntryGui.displayInfoKt : DisplayInfo by ReflectField("field_191830_h")
var AdvancementEntryGui.advancementProgressKt : AdvancementProgress? by ReflectField("field_191836_n")
var AdvancementEntryGui.childrenKt : List<AdvancementEntryGui> by ReflectField("field_191835_m")*/