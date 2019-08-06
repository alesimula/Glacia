package com.greenapple.glacia.entity.renderer

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.entity.EntityGlacialTurtle
import com.greenapple.glacia.entity.EntitySaberToothedCat
import com.greenapple.glacia.entity.model.ModelGlacialTurtle
import com.greenapple.glacia.entity.model.ModelSaberToothedCat
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.util.ResourceLocation

/**
 * Third parameter = shadow size
 */
class RendererSaberToothedCat(renderManager: EntityRendererManager) : MobRenderer<EntitySaberToothedCat, ModelSaberToothedCat<EntitySaberToothedCat>>(renderManager, ModelSaberToothedCat(), 0.7F) {
    companion object {
        private val COW_TEXTURES = ResourceLocation(Glacia.MODID, "textures/entity/saber_toothed_cat.png")
    }

    override fun getEntityTexture(entity: EntitySaberToothedCat): ResourceLocation? = COW_TEXTURES
}