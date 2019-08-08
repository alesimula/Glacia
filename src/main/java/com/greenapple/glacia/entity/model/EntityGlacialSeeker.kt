package com.greenapple.glacia.entity.model

import net.minecraft.entity.EntityType
import net.minecraft.entity.monster.ZombieEntity
import net.minecraft.world.World

class EntityGlacialSeeker(type: EntityType<ZombieEntity>, world: World) : ZombieEntity(type, world) {
    override fun shouldBurnInDay() = false
}