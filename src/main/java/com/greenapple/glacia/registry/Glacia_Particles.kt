package com.greenapple.glacia.registry

import com.greenapple.glacia.delegate.fieldProperty
import com.greenapple.glacia.particle.ParticleColoredFlame
import com.greenapple.glacia.particle.ParticleFlameBase
import com.greenapple.glacia.utils.runClient
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.IAnimatedSprite
import net.minecraft.client.particle.IParticleFactory
import net.minecraft.client.particle.ParticleManager
import net.minecraft.particles.BasicParticleType
import net.minecraft.particles.IParticleData
import net.minecraft.particles.ParticleType
import kotlin.reflect.KFunction1

private var <T: IParticleData> ParticleType<T>.factoryProvider: ParticleManager.IParticleMetaFactory<T>? by fieldProperty {null}

object Glacia_Particles : IForgeDeferredRegistryCollection<ParticleType<*>> ({runClient {this as BasicParticleType; Glacia_Particles.particleManager.registerFactory(this, factoryProvider!!)}}) {
    // <editor-fold defaultstate="collapsed" desc="Registration util methods">
    private val particleManager by lazy {Minecraft.getInstance().particles}
    private fun <T: IParticleData?> particleType(name: String, factoryProvider: KFunction1<IAnimatedSprite, IParticleFactory<T>>, alwaysShow: Boolean = false) = BasicParticleType(alwaysShow).also {
        it.setRegistryName(name).factoryProvider = ParticleManager.IParticleMetaFactory(factoryProvider)
    }
    // </editor-fold>

    val COLORED_FLAME = particleType("colored_flame", ParticleColoredFlame::Factory)
    val PLASMA_FLAME = particleType("plasma_flame", ParticleFlameBase::Factory)
}