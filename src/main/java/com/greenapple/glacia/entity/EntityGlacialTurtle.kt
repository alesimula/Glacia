package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.CowEntity
import net.minecraft.world.World

class EntityGlacialTurtle(world: World) : CowEntity(Glacia.Entity.GLACIAL_TURTLE, world) {
    companion object {
        val entityType = EntityType.Builder.create(EntityType.IFactory {type : EntityType<EntityGlacialTurtle>, world-> EntityGlacialTurtle(world)}, EntityClassification.CREATURE)
    }
}