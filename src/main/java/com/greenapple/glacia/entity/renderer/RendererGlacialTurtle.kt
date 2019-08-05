package com.greenapple.glacia.entity.renderer

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.entity.EntityGlacialTurtle
import com.greenapple.glacia.entity.model.ModelGlacialTurtle
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.util.ResourceLocation

/**
 * Third parameter = shadow size
 */
class RendererGlacialTurtle(renderManager: EntityRendererManager) : MobRenderer<EntityGlacialTurtle, ModelGlacialTurtle<EntityGlacialTurtle>>(renderManager, ModelGlacialTurtle(), 0.7F) {

    companion object {
        private val COW_TEXTURES = ResourceLocation(Glacia.MODID, "textures/entity/glacial_turtle.png")
    }

    override fun getEntityTexture(entity: EntityGlacialTurtle): ResourceLocation? = COW_TEXTURES
}