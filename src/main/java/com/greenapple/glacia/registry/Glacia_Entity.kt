package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.entity.EntityGlacialTurtle
import com.greenapple.glacia.entity.EntityReindeer
import com.greenapple.glacia.entity.EntitySaberToothedCat
import com.greenapple.glacia.entity.model.ModelGlacialTurtle
import com.greenapple.glacia.entity.model.ModelReindeer
import com.greenapple.glacia.entity.model.ModelSaberToothedCat
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.entity.MobEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.client.registry.RenderingRegistry

object Glacia_Entity : IForgeRegistryCollection<EntityType<*>> {
    private inline fun <reified E: Entity> entityType(registryName: String, classification: EntityClassification, crossinline provider: EntityType<E>.(world: World)->E) : EntityType<E> = EntityType.Builder.create({ type: EntityType<E>, world -> provider(type, world)}, classification).build(registryName).apply {setRegistryName(registryName)}
    private inline fun <reified E: Entity> EntityType<E>.registerRenderer(crossinline renderer: EntityRendererManager.()->EntityRenderer<E>) = RenderingRegistry.registerEntityRenderingHandler(E::class.java) {manager -> renderer(manager)}
    private inline fun <reified E: MobEntity> EntityType<E>.registerRenderer(model: EntityModel<E>, scale: Float, texture: String?=this.registryName?.path) = registerRenderer {object : MobRenderer<E, EntityModel<E>>(this, model, scale) {
        private val TEXTURE = ResourceLocation(registryName?.namespace ?: Glacia.MODID, "textures/entity/$texture.png")
        override fun getEntityTexture(entity: E) = TEXTURE
    }}

    val GLACIAL_TURTLE = entityType<EntityGlacialTurtle>("glacial_turtle", EntityClassification.CREATURE) {world ->  EntityGlacialTurtle(this, world)}
    val SABER_TOOTHED_CAT = entityType<EntitySaberToothedCat>("saber_toothed_cat", EntityClassification.CREATURE) {world -> EntitySaberToothedCat(this, world)}
    val REINDEER = entityType<EntityReindeer>("reindeer", EntityClassification.CREATURE) {world ->  EntityReindeer(this, world)}

    fun registerRenderers() {
        GLACIAL_TURTLE.registerRenderer(ModelGlacialTurtle(), 0.4F)
        SABER_TOOTHED_CAT.registerRenderer(ModelSaberToothedCat(), 0.2F)
        REINDEER.registerRenderer(ModelReindeer(), 0.33f)
    }

    /*init {
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialTurtle::class.java) { manager -> RendererGlacialTurtle(manager)}
    }*/
}