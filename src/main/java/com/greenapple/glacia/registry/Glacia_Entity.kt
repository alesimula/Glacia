package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.entity.EntityGlacialTurtle
import com.greenapple.glacia.entity.EntityPenguin
import com.greenapple.glacia.entity.EntityReindeer
import com.greenapple.glacia.entity.EntitySaberToothedCat
import com.greenapple.glacia.entity.model.ModelGlacialTurtle
import com.greenapple.glacia.entity.model.ModelPenguin
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
    @Suppress("TYPE_PARAMETER_OF_PROPERTY_NOT_USED_IN_RECEIVER", "UNCHECKED_CAST")
    private inline val <E: Entity> EntityType<*>.type; get() = this as EntityType<E>
    private inline fun <reified E: Entity> entityType(registryName: String, classification: EntityClassification, crossinline provider: EntityType<*>.(world: World)->E) : EntityType<E> = EntityType.Builder.create({ type: EntityType<E>, world -> provider(type, world)}, classification).build(registryName).apply {setRegistryName(registryName)}
    private inline fun <reified E: Entity> EntityType<E>.registerRenderer(crossinline renderer: EntityRendererManager.()->EntityRenderer<E>) = RenderingRegistry.registerEntityRenderingHandler(E::class.java) {manager -> renderer(manager)}
    private inline fun <reified E: MobEntity> EntityType<E>.registerRenderer(model: EntityModel<E>, scale: Float, texture: String?=this.registryName?.path) = registerRenderer {object : MobRenderer<E, EntityModel<E>>(this, model, scale) {
        private val TEXTURE = ResourceLocation(registryName?.namespace ?: Glacia.MODID, "textures/entity/$texture.png")
        override fun getEntityTexture(entity: E) = TEXTURE
    }}

    val GLACIAL_TURTLE = entityType("glacial_turtle", EntityClassification.CREATURE) {world ->  EntityGlacialTurtle(type, world)}
    val SABER_TOOTHED_CAT = entityType("saber_toothed_cat", EntityClassification.CREATURE) {world -> EntitySaberToothedCat(type, world)}
    val REINDEER = entityType("reindeer", EntityClassification.CREATURE) {world ->  EntityReindeer(type, world)}
    val PENGUIN = entityType("penguin", EntityClassification.CREATURE) {world ->  EntityPenguin(type, world)}

    fun registerRenderers() {
        GLACIAL_TURTLE.registerRenderer(ModelGlacialTurtle(), 1F)
        SABER_TOOTHED_CAT.registerRenderer(ModelSaberToothedCat(), 0.2F)
        REINDEER.registerRenderer(ModelReindeer(), 0.33f)
        PENGUIN.registerRenderer(ModelPenguin(), 0.35f)
    }
}