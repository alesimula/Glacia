package com.greenapple.glacia.entity.model

import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.entity.model.RendererModel
import net.minecraft.entity.Entity
import net.minecraft.util.math.MathHelper

class ModelGlacialTurtle<T: Entity> : EntityModel<T>() {

    init {
        textureWidth = 88
        textureHeight = 69
    }

    private val turtleShellBase: RendererModel = RendererModel(this, 0, 29)
    private val shellRidge: RendererModel = RendererModel(this, 0, 0)
    private val legFrontRight: RendererModel = RendererModel(this, 5, 12)
    private val legFrontLeft = RendererModel(this, 5, 12)
    private val legBackLeft = RendererModel(this, 5, 12)
    private val legBackRight = RendererModel(this, 5, 12)
    private val neck = RendererModel(this, 0, 35)
    private val head = RendererModel(this, 56, 33)
    private val spike1 = RendererModel(this, 11, 2)
    private val spike2 = RendererModel(this, 11, 2)
    private val spike3 = RendererModel(this, 11, 2)
    private val spike4 = RendererModel(this, 11, 2)
    private val spike5 = RendererModel(this, 11, 2)
    private val spike6 = RendererModel(this, 11, 2)
    private val spike7 = RendererModel(this, 11, 2)
    private val spike8 = RendererModel(this, 11, 2)
    private val spike9 = RendererModel(this, 11, 2)
    private val spike10 = RendererModel(this, 11, 2)
    private val spike11 = RendererModel(this, 11, 2)
    private val spike12 = RendererModel(this, 11, 2)
    private val spike13 = RendererModel(this, 11, 2)
    private val spike14 = RendererModel(this, 11, 2)
    private val spike15 = RendererModel(this, 11, 2)
    private val spike16 = RendererModel(this, 11, 2)

    init {
        turtleShellBase.addBox(0f, 0f, 0f, 16, 16, 24)
        turtleShellBase.setRotationPoint(-8f, 4f, -12f)
        turtleShellBase.mirror = true
        turtleShellBase.setRotation(0f, 0f, 0f)

        shellRidge.addBox(0f, 0f, -2f, 18, 3, 26)
        shellRidge.setRotationPoint(-9f, 13f, -11f)
        shellRidge.mirror = true
        shellRidge.setRotation(0f, 0f, 0f)

        legFrontRight.addBox(0f, 0f, 0f, 4, 8, 4)
        legFrontRight.setRotationPoint(-8.5f, 16f, -12.5f)
        legFrontRight.mirror = true
        legFrontRight.setRotation(0f, 0f, 0f)

        legFrontLeft.addBox(0f, 0f, 0f, 4, 8, 4)
        legFrontLeft.setRotationPoint(4.5f, 16f, -12.5f)
        legFrontLeft.mirror = true
        legFrontLeft.setRotation(0f, 0f, 0f)

        legBackLeft.addBox(0f, 0f, 0f, 4, 8, 4)
        legBackLeft.setRotationPoint(4.5f, 16f, 8.5f)
        legBackLeft.mirror = true
        legBackLeft.setRotation(0f, 0f, 0f)

        legBackRight.addBox(0f, 0f, 0f, 4, 8, 4)
        legBackRight.setRotationPoint(-8.5f, 16f, 8.5f)
        legBackRight.mirror = true
        legBackRight.setRotation(0f, 0f, 0f)

        neck.addBox(0f, 0f, 0f, 4, 4, 8)
        neck.setRotationPoint(-2f, 13f, -16f)
        neck.mirror = true
        neck.setRotation(-0.2230717f, 0f, 0f)

        head.addBox(0f, 0f, -1f, 8, 8, 8)
        head.setRotationPoint(-4f, 10f, -21f)
        head.mirror = true
        head.setRotation(0f, 0f, 0f)

        spike1.addBox(0f, 0f, 0f, 1, 6, 1)
        spike1.setRotationPoint(-7f, 0f, 0f)
        spike1.mirror = true
        spike1.setRotation(0f, 0f, -0.3346075f)

        spike2.addBox(0f, 0f, 0f, 1, 6, 1)
        spike2.setRotationPoint(0f, 0f, -7f)
        spike2.mirror = true
        spike2.setRotation(0f, 0f, 0f)

        spike3.addBox(0f, 0f, 0f, 1, 6, 1)
        spike3.setRotationPoint(0f, 0f, 0f)
        spike3.mirror = true
        spike3.setRotation(0f, 0f, 0.1858931f)

        spike4.addBox(0f, 0f, 0f, 1, 6, 1)
        spike4.setRotationPoint(-5f, 0f, -7f)
        spike4.mirror = true
        spike4.setRotation(0f, 0f, -0.1487144f)

        spike5.addBox(0f, 0f, 0f, 1, 6, 1)
        spike5.setRotationPoint(11f, 6f, 7f)
        spike5.mirror = true
        spike5.setRotation(0f, 0f, 1.003822f)

        spike6.addBox(0f, 0f, 0f, 1, 6, 1)
        spike6.setRotationPoint(11f, 6f, -1f)
        spike6.mirror = true
        spike6.setRotation(0f, 0f, 1.003822f)

        spike7.addBox(0f, 0f, 0f, 1, 6, 1)
        spike7.setRotationPoint(11f, 6f, -8f)
        spike7.mirror = true
        spike7.setRotation(0f, 0f, 1.003822f)

        spike8.addBox(0f, 0f, 0f, 1, 6, 1)
        spike8.setRotationPoint(5f, 0f, 5f)
        spike8.mirror = true
        spike8.setRotation(0f, 0f, 0.4461433f)

        spike9.addBox(0f, 0f, 0f, 1, 6, 1)
        spike9.setRotationPoint(-5f, 0f, 8f)
        spike9.mirror = true
        spike9.setRotation(-0.4089647f, 0f, 0f)

        spike10.addBox(0f, 0f, 0f, 1, 6, 1)
        spike10.setRotationPoint(-11f, 6f, 7f)
        spike10.mirror = true
        spike10.setRotation(0f, 0f, -1.003826f)

        spike11.addBox(0f, 0f, 0f, 1, 6, 1)
        spike11.setRotationPoint(-11f, 6f, 0f)
        spike11.mirror = true
        spike11.setRotation(0f, 0f, -1.003826f)

        spike12.addBox(0f, 0f, 0f, 1, 6, 1)
        spike12.setRotationPoint(-11f, 6f, -7f)
        spike12.mirror = true
        spike12.setRotation(0f, 0f, -1.003826f)

        spike13.addBox(0f, 0f, 0f, 1, 6, 1)
        spike13.setRotationPoint(3f, 0f, 11f)
        spike13.mirror = true
        spike13.setRotation(-0.4089647f, 0f, 0f)

        spike14.addBox(0f, 0f, 0f, 1, 6, 1)
        spike14.setRotationPoint(4f, 0f, -10f)
        spike14.mirror = true
        spike14.setRotation(0f, 0f, 0.2230717f)

        spike15.addBox(0f, 0f, 0f, 1, 6, 1)
        spike15.setRotationPoint(4f, 0f, -3f)
        spike15.mirror = true
        spike15.setRotation(0f, 0f, 0.4461433f)

        spike16.addBox(0f, 0f, 0f, 1, 6, 1)
        spike16.setRotationPoint(1f, 0f, 7f)
        spike16.mirror = true
        spike16.setRotation(0f, 0f, 0.4461433f)
    }

    private fun RendererModel.setRotation(x: Float, y: Float, z: Float) {
        this.rotateAngleX = x
        this.rotateAngleY = y
        this.rotateAngleZ = z
    }

    override fun render(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        turtleShellBase.render(scale)
        shellRidge.render(scale)
        legFrontRight.render(scale)
        legFrontLeft.render(scale)
        legBackLeft.render(scale)
        legBackRight.render(scale)
        neck.render(scale)
        head.render(scale)
        spike1.render(scale)
        spike2.render(scale)
        spike3.render(scale)
        spike4.render(scale)
        spike5.render(scale)
        spike6.render(scale)
        spike7.render(scale)
        spike8.render(scale)
        spike9.render(scale)
        spike10.render(scale)
        spike11.render(scale)
        spike12.render(scale)
        spike13.render(scale)
        spike14.render(scale)
        spike15.render(scale)
        spike16.render(scale)
    }

    override fun setRotationAngles(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scaleFactor: Float) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor)
        this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 0.5f * limbSwingAmount
        this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 0.5f * limbSwingAmount
        this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 0.5f * limbSwingAmount
        this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 0.5f * limbSwingAmount
    }
}