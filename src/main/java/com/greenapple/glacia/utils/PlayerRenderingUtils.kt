package com.greenapple.glacia.utils

import com.greenapple.glacia.delegate.LazyWithReceiver
import com.greenapple.glacia.delegate.ReflectField
import com.greenapple.glacia.delegate.SingletonReceiver
import com.mojang.authlib.minecraft.MinecraftProfileTexture
import net.minecraft.client.entity.player.AbstractClientPlayerEntity
import net.minecraft.client.network.play.NetworkPlayerInfo
import net.minecraft.client.renderer.entity.LivingRenderer
import net.minecraft.client.renderer.entity.PlayerRenderer
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer
import net.minecraft.client.renderer.entity.layers.LayerRenderer
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.entity.model.PlayerModel
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ResourceLocation

private var <T: LivingEntity, M: EntityModel<T>> LivingRenderer<T, M>.entityModelKt : M by ReflectField("field_77045_g")
private var <T: LivingEntity, M: EntityModel<T>> LivingRenderer<T, M>.layerRenderersKt : MutableList<LayerRenderer<T, M>> by ReflectField("field_177097_h")
private var AbstractClientPlayerEntity.playerInfoKt : NetworkPlayerInfo by ReflectField("field_175157_a")
private var NetworkPlayerInfo.playerTexturesKt : MutableMap<MinecraftProfileTexture.Type, ResourceLocation> by ReflectField("field_187107_a")
private var NetworkPlayerInfo.playerTexturesLoadedKt : Boolean by ReflectField("field_178864_d")

private val PlayerRenderer.MODEL_PLAYER_DEFAULT by SingletonReceiver<PlayerRenderer, PlayerModel<AbstractClientPlayerEntity>> {entityModel}
private val BipedArmorLayer<*, *, *>.LAYER_ARMOR_DEFAULT by SingletonReceiver<BipedArmorLayer<*,*,*>, BipedArmorLayer<*,*,*>> {this}
private fun <T: Entity, M: EntityModel<T>> LayerRenderer<*, *>.autoCast() = this as LayerRenderer<T, M>

fun PlayerEntity.morph(renderer: PlayerRenderer, model: PlayerModel<AbstractClientPlayerEntity>?=null, texture: ResourceLocation?=null) = (model?:renderer.MODEL_PLAYER_DEFAULT).let { newModel ->
    val player = this
    renderer.MODEL_PLAYER_DEFAULT
    if (renderer.entityModel !== newModel) {
        (player as? AbstractClientPlayerEntity)?.playerInfoKt?.playerTexturesKt?.apply {
            texture?.also {
                this[MinecraftProfileTexture.Type.SKIN] = texture
            } ?: apply {
                this.remove(MinecraftProfileTexture.Type.SKIN)
                player.playerInfoKt.playerTexturesLoadedKt = false
                this[MinecraftProfileTexture.Type.SKIN] = player.locationSkin
            }
        }
        renderer.layerRenderersKt.let {renderers-> renderers.forEachIndexed {index, layerRenderer ->
            (layerRenderer as? BipedArmorLayer<*, *, *>)?.apply {
                LAYER_ARMOR_DEFAULT
                //if (model != null) renderers[index] = BipedArmorLayer(renderer, modelPlayerSnowManArmor, modelPlayerSnowManArmor2)
                if (model != null) renderers[index] = BipedArmorLayer(renderer, null, null)
                else renderers[index] = LAYER_ARMOR_DEFAULT.autoCast()
                return@forEachIndexed
            }
        }}
        renderer.entityModelKt = newModel
    }
}