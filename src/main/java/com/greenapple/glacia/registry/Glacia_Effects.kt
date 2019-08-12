package com.greenapple.glacia.registry

import net.minecraft.entity.LivingEntity
import net.minecraft.potion.Effect
import net.minecraft.potion.EffectType

object Glacia_Effects : IForgeRegistryCollection<Effect> {
    class EffectBase(registryName: String, val unlocalizedName: String, type: EffectType, liquidColor: Int, private val performEffectKt: (LivingEntity.(Int)->Unit)?=null) : Effect(type, liquidColor) {
        init {setRegistryName(registryName)}
        override fun performEffect(entityLivingBaseIn: LivingEntity, amplifier: Int) {performEffectKt?.invoke(entityLivingBaseIn, amplifier)}
        //TODO getDisplayName is not called, use it when it's fixed
        override fun getName(): String = unlocalizedName
    }

    val MORPH_SNOWMAN = EffectBase("morph_snowman", "Snowman Form", EffectType.HARMFUL, 5578058)
}