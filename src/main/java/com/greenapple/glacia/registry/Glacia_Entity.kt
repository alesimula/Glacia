package com.greenapple.glacia.registry

import com.greenapple.glacia.entity.EntityGlacialTurtle
import com.greenapple.glacia.entity.EntitySaberToothedCat
import com.greenapple.glacia.entity.renderer.RendererGlacialTurtle
import com.greenapple.glacia.entity.renderer.RendererSaberToothedCat
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererManager
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.registries.ForgeRegistries

object Glacia_Entity : IForgeRegistryCollection<EntityType<*>> {
    inline fun <reified T: Entity> EntityType<T>.registerRenderer(crossinline renderer: (manager: EntityRendererManager)->EntityRenderer<T>) = RenderingRegistry.registerEntityRenderingHandler(T::class.java) {manager -> renderer(manager)}

    val GLACIAL_TURTLE = EntityGlacialTurtle.entityType.build("glacial_turtle").apply {setRegistryName("glacial_turtle")}
    val SABER_TOOTHED_CAT = EntitySaberToothedCat.entityType.build("glacial_turtle").apply {setRegistryName("saber_toothed_cat")}

    fun registerRenderers() {
        GLACIAL_TURTLE.registerRenderer {RendererGlacialTurtle(it)}
        SABER_TOOTHED_CAT.registerRenderer {RendererSaberToothedCat(it)}
    }

    /*init {
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialTurtle::class.java) { manager -> RendererGlacialTurtle(manager)}
    }*/
}