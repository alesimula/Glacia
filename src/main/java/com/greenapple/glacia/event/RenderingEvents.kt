package com.greenapple.glacia.event

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.entity.model.ModelPlayerSnowMan
import com.greenapple.glacia.utils.durationKt
import com.greenapple.glacia.utils.morph
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.PlayerRenderer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.potion.EffectInstance
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderHandEvent
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

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
        event.renderer.onRenderPlayer(event.entityPlayer)
    }

    @SubscribeEvent
    fun onRenderPlayerFirstPerson(event: RenderHandEvent) = Minecraft.getInstance().apply {
        val renderer = renderManager.getRenderer(player) as PlayerRenderer
        renderer.onRenderPlayer(player)
    }
}