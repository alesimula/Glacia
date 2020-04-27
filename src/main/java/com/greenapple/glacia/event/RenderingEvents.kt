package com.greenapple.glacia.event

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.advancement.GuiGlaciaAdvancementScreen
import com.greenapple.glacia.delegate.reflectField
import com.greenapple.glacia.entity.model.ModelPlayerSnowMan
import com.greenapple.glacia.utils.*
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementRewards
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.advancements.AdvancementsScreen
import net.minecraft.client.renderer.entity.PlayerRenderer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.palette.PalettedContainer
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderHandEvent
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.registries.GameData

class RenderingEvents {
    private val MODEL_SNOWMAN = ModelPlayerSnowMan(0)
    private val TEXTURE_SNOWMAN = ResourceLocation(Glacia.MODID, "textures/entity/player_snowman.png")

    private fun PlayerRenderer.onRenderPlayer(player: PlayerEntity) {
        if (!player.isInWater) player.getActivePotionEffect(Glacia.Effects.MORPH_SNOWMAN)?.also {effect ->
            effect.durationKt = 72011
            morph(player, MODEL_SNOWMAN, TEXTURE_SNOWMAN)
        }
        else if (morph(player)) player.removePotionEffect(Glacia.Effects.MORPH_SNOWMAN)
    }

    @SubscribeEvent
    fun onRenderPlayerThirdPerson(event: RenderPlayerEvent.Pre) {
        event.renderer.onRenderPlayer(event.player)
    }

    @SubscribeEvent
    fun onRenderPlayerFirstPerson(event: RenderHandEvent) = Minecraft.getInstance().apply {player?.let {player->
        val renderer = renderManager.getRenderer(player) as PlayerRenderer
        renderer.onRenderPlayer(player)
    }}

    val glaciaAdvancement = Advancement(GameData.checkPrefix("glacia/root", false), null, null, AdvancementRewards.EMPTY, mapOf(), arrayOf())

    @SubscribeEvent
    fun onRenderGui(event: GuiScreenEvent) = (event.gui as? AdvancementsScreen)?.apply {
        val glaciaTabGui = tabsKt[glaciaAdvancement]
        if (glaciaTabGui != null && selectedTabKt == glaciaTabGui && this !is GuiGlaciaAdvancementScreen) {
            Minecraft.getInstance().displayGuiScreen(GuiGlaciaAdvancementScreen(glaciaTabGui.screen.clientAdvancementManagerKt))
        }
        else if (glaciaTabGui != null && selectedTabKt != glaciaTabGui && this is GuiGlaciaAdvancementScreen) {
            Minecraft.getInstance().displayGuiScreen(AdvancementsScreen(glaciaTabGui.screen.clientAdvancementManagerKt))
        }
    }
}