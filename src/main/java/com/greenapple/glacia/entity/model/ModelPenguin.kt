package com.greenapple.glacia.entity.model

import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.entity.model.RendererModel
import net.minecraft.entity.Entity
import net.minecraft.util.math.MathHelper

class ModelPenguin<E : Entity> : EntityModel<E>() {

    init {
        textureWidth = 42
        textureHeight = 41
    }

    private val body: RendererModel = RendererModel(this, 0, 0)
    private val leg: RendererModel = RendererModel(this, 0, 35)
    private val leg2: RendererModel = RendererModel(this, 16, 35)
    private val bipedHead: RendererModel = RendererModel(this, 20, 20)
    private val arm: RendererModel = RendererModel(this, 0, 20)
    private val arm2: RendererModel = RendererModel(this, 10, 20)

    init {
        this.body.addBox(0f, 0f, 0f, 8, 15, 5)
        this.body.setRotationPoint(-4f, 8f, -1f)
        this.body.mirror = true
        this.body.setRotation(0f, 0f, 0f)

        this.leg.addBox(0f, 0f, 0f, 3, 1, 5)
        this.leg.setRotationPoint(-1f, 23f, 1f)
        this.leg.mirror = true
        this.leg.setRotation(0f, 3.141593f, 0f)

        this.leg2.addBox(0f, 0f, 0f, 3, 1, 5)
        this.leg2.setRotationPoint(4f, 23f, 1f)
        this.leg2.mirror = true
        this.leg2.setRotation(0f, 3.141593f, 0f)

        this.bipedHead.addBox(0f, 0f, 0f, 6, 5, 5)
        this.bipedHead.setRotationPoint(-3f, 3f, -2f)
        this.bipedHead.mirror = true
        this.bipedHead.setTextureOffset(24, 30).addBox(1f, 3f, -3f, 4, 1, 3)
        this.bipedHead.setRotation(0f, 0f, 0f)

        this.arm.addBox(0f, 0f, 0f, 1, 11, 4)
        this.arm.setRotationPoint(-5f, 8f, -1f)
        this.arm.mirror = true
        this.arm.setRotation(0f, 0f, 0.1396263f)

        this.arm2.addBox(0f, 0f, 0f, 1, 11, 4)
        this.arm2.setRotationPoint(4f, 8f, -1f)
        this.arm2.mirror = true
        this.arm2.setRotation(0f, 0f, -0.1396263f)
    }

    override fun render(entity: E, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {
        super.render(entity, f, f1, f2, f3, f4, f5)
        this.setRotationAngles(entity, f, f1, f2, f3, f4, f5)
        this.body.render(f5)
        this.leg.render(f5)
        this.leg2.render(f5)
        this.bipedHead.render(f5)
        this.arm.render(f5)
        this.arm2.render(f5)
    }

    private fun RendererModel.setRotation(x: Float, y: Float, z: Float) {
        rotateAngleX = x
        rotateAngleY = y
        rotateAngleZ = z
    }

    override fun setRotationAngles(entity: E, f: Float, f1: Float, f2: Float, f3: Float, f4: Float, f5: Float) {
        this.bipedHead.rotateAngleY = f4 / (290f / Math.PI.toFloat())
        this.bipedHead.rotateAngleX = f5 / (290f / Math.PI.toFloat())
        super.setRotationAngles(entity, f, f1, f2, f3, f4, f5)
        this.leg.rotateAngleX = MathHelper.cos(f * 0.4f) * 1.0f * f1
        this.leg2.rotateAngleX = MathHelper.cos(f * 0.4f + Math.PI.toFloat()) * 1.0f * f1
        this.arm.rotateAngleZ = MathHelper.cos(f * 0.6f) * 0.3f * f1
        this.arm2.rotateAngleZ = MathHelper.cos(f * 0.6f + Math.PI.toFloat()) * 0.3f * f1
    }
}