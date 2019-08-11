package com.greenapple.glacia.event

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.entity.model.ModelPlayerSnowMan
import com.greenapple.glacia.utils.morph
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.PlayerRenderer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderHandEvent
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class RenderingEvents () {
    private val MODEL_SNOWMAN = ModelPlayerSnowMan(0)
    private val TEXTURE_SNOWMAN = ResourceLocation(Glacia.MODID, "textures/entity/player_snowman.png")

    @SubscribeEvent
    fun onRenderPlayerThirdPerson(event: RenderPlayerEvent.Pre) {
        event.renderer.morph(event.entityPlayer, MODEL_SNOWMAN, TEXTURE_SNOWMAN)
    }

    @SubscribeEvent
    fun onRenderPlayerFirstPerson(event: RenderHandEvent) = Minecraft.getInstance().apply {
        val renderer = renderManager.getRenderer(player) as PlayerRenderer
        renderer.morph(player, MODEL_SNOWMAN, TEXTURE_SNOWMAN)
    }
}