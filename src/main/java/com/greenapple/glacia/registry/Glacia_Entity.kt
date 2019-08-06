package com.greenapple.glacia.registry

import com.greenapple.glacia.entity.EntityGlacialTurtle
import com.greenapple.glacia.entity.EntitySaberToothedCat
import com.greenapple.glacia.entity.renderer.RendererGlacialTurtle
import com.greenapple.glacia.entity.renderer.RendererSaberToothedCat
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.world.World
import net.minecraftforge.fml.client.registry.RenderingRegistry

object Glacia_Entity : IForgeRegistryCollection<EntityType<*>> {
    private inline fun <reified E: Entity> entityType(registryName: String, classification: EntityClassification, crossinline provider: EntityType<E>.(world: World)->E) : EntityType<E> = EntityType.Builder.create({ type: EntityType<E>, world -> provider(type, world)}, classification).build(registryName).apply {setRegistryName(registryName)}
    private inline fun <reified E: Entity> EntityType<E>.registerRenderer(crossinline renderer: EntityRendererManager.()->EntityRenderer<E>) = RenderingRegistry.registerEntityRenderingHandler(E::class.java) {manager -> renderer(manager)}

    val GLACIAL_TURTLE = entityType<EntityGlacialTurtle>("glacial_turtle", EntityClassification.CREATURE) {world ->  EntityGlacialTurtle(world)}
    val SABER_TOOTHED_CAT = entityType<EntitySaberToothedCat>("saber_toothed_cat", EntityClassification.CREATURE) {world -> EntitySaberToothedCat(world)}

    fun registerRenderers() {
        GLACIAL_TURTLE.registerRenderer {RendererGlacialTurtle(this)}
        SABER_TOOTHED_CAT.registerRenderer {RendererSaberToothedCat(this)}
    }

    /*init {
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialTurtle::class.java) { manager -> RendererGlacialTurtle(manager)}
    }*/
}