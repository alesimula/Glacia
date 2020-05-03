package com.greenapple.glacia.particle

import net.minecraft.client.particle.FlameParticle
import net.minecraft.client.particle.IAnimatedSprite
import net.minecraft.client.particle.IParticleFactory
import net.minecraft.item.DyeColor
import net.minecraft.particles.BasicParticleType
import net.minecraft.state.IProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object ParticleColoredFlame {
    class Factory(private val spriteSet: IAnimatedSprite) : IParticleFactory<BasicParticleType?> {
        override fun makeParticle(type: BasicParticleType?, world: World, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double) = FlameParticle.Factory(spriteSet).makeParticle(type, world, x, y, z, xSpeed, ySpeed, zSpeed)?.apply {
            val state = world.getBlockState(BlockPos(x, y, z))
            val colors = ((state.properties.find {it.name == "color"} as? IProperty<DyeColor>)?.let {state.get(it)} ?: DyeColor.WHITE).colorComponentValues
            this.setColor(colors[0], colors[1], colors[2])
        }
    }
}

object ParticleFlameBase {
    class Factory(private val spriteSet: IAnimatedSprite) : IParticleFactory<BasicParticleType?> {
        override fun makeParticle(type: BasicParticleType?, world: World, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double) = FlameParticle.Factory(spriteSet).makeParticle(type, world, x, y, z, xSpeed, ySpeed, zSpeed)
    }
}