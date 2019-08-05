package com.greenapple.glacia.registry

import com.greenapple.glacia.entity.EntityGlacialTurtle
import com.greenapple.glacia.entity.renderer.RendererGlacialTurtle
import net.minecraft.entity.EntityType
import net.minecraftforge.fml.client.registry.RenderingRegistry

object Glacia_Entity : IForgeRegistryCollection<EntityType<*>> {
    fun registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialTurtle::class.java) { manager -> RendererGlacialTurtle(manager)}
    }

    val GLACIAL_TURTLE = EntityGlacialTurtle.entityType.build("glacial_turtle").apply {setRegistryName("glacial_turtle")}

    /*init {
        RenderingRegistry.registerEntityRenderingHandler(EntityGlacialTurtle::class.java) { manager -> RendererGlacialTurtle(manager)}
    }*/
}