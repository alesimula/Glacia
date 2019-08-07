package com.greenapple.glacia.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.CowEntity
import net.minecraft.world.World

class EntityReindeer(type: EntityType<out CowEntity>, world: World) : CowEntity(type, world) {
}