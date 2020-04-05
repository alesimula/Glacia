package com.greenapple.glacia.entity.model

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.Entity
import net.minecraft.util.math.MathHelper

class ModelGlacialTurtle<T: Entity> : EntityModel<T>() {

    init {
        textureWidth = 88
        textureHeight = 69
    }

    private val turtleShellBase: ModelRenderer = ModelRenderer(this, 0, 29)
    private val shellRidge: ModelRenderer = ModelRenderer(this, 0, 0)
    private val legFrontRight: ModelRenderer = ModelRenderer(this, 5, 12)
    private val legFrontLeft = ModelRenderer(this, 5, 12)
    private val legBackLeft = ModelRenderer(this, 5, 12)
    private val legBackRight = ModelRenderer(this, 5, 12)
    private val neck = ModelRenderer(this, 0, 35)
    private val head = ModelRenderer(this, 56, 33)
    private val spike1 = ModelRenderer(this, 11, 2)
    private val spike2 = ModelRenderer(this, 11, 2)
    private val spike3 = ModelRenderer(this, 11, 2)
    private val spike4 = ModelRenderer(this, 11, 2)
    private val spike5 = ModelRenderer(this, 11, 2)
    private val spike6 = ModelRenderer(this, 11, 2)
    private val spike7 = ModelRenderer(this, 11, 2)
    private val spike8 = ModelRenderer(this, 11, 2)
    private val spike9 = ModelRenderer(this, 11, 2)
    private val spike10 = ModelRenderer(this, 11, 2)
    private val spike11 = ModelRenderer(this, 11, 2)
    private val spike12 = ModelRenderer(this, 11, 2)
    private val spike13 = ModelRenderer(this, 11, 2)
    private val spike14 = ModelRenderer(this, 11, 2)
    private val spike15 = ModelRenderer(this, 11, 2)
    private val spike16 = ModelRenderer(this, 11, 2)

    init {
        turtleShellBase.addBox(0f, 0f, 0f, 16f, 16f, 24f)
        turtleShellBase.setRotationPoint(-8f, 4f, -12f)
        turtleShellBase.mirror = true
        turtleShellBase.setRotation(0f, 0f, 0f)

        shellRidge.addBox(0f, 0f, -2f, 18f, 3f, 26f)
        shellRidge.setRotationPoint(-9f, 13f, -11f)
        shellRidge.mirror = true
        shellRidge.setRotation(0f, 0f, 0f)

        legFrontRight.addBox(0f, 0f, 0f, 4f, 8f, 4f)
        legFrontRight.setRotationPoint(-8.5f, 16f, -12.5f)
        legFrontRight.mirror = true
        legFrontRight.setRotation(0f, 0f, 0f)

        legFrontLeft.addBox(0f, 0f, 0f, 4f, 8f, 4f)
        legFrontLeft.setRotationPoint(4.5f, 16f, -12.5f)
        legFrontLeft.mirror = true
        legFrontLeft.setRotation(0f, 0f, 0f)

        legBackLeft.addBox(0f, 0f, 0f, 4f, 8f, 4f)
        legBackLeft.setRotationPoint(4.5f, 16f, 8.5f)
        legBackLeft.mirror = true
        legBackLeft.setRotation(0f, 0f, 0f)

        legBackRight.addBox(0f, 0f, 0f, 4f, 8f, 4f)
        legBackRight.setRotationPoint(-8.5f, 16f, 8.5f)
        legBackRight.mirror = true
        legBackRight.setRotation(0f, 0f, 0f)

        neck.addBox(0f, 0f, 0f, 4f, 4f, 8f)
        neck.setRotationPoint(-2f, 13f, -16f)
        neck.mirror = true
        neck.setRotation(-0.2230717f, 0f, 0f)

        head.addBox(0f, 0f, -1f, 8f, 8f, 8f)
        head.setRotationPoint(-4f, 10f, -21f)
        head.mirror = true
        head.setRotation(0f, 0f, 0f)

        spike1.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike1.setRotationPoint(-7f, 0f, 0f)
        spike1.mirror = true
        spike1.setRotation(0f, 0f, -0.3346075f)

        spike2.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike2.setRotationPoint(0f, 0f, -7f)
        spike2.mirror = true
        spike2.setRotation(0f, 0f, 0f)

        spike3.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike3.setRotationPoint(0f, 0f, 0f)
        spike3.mirror = true
        spike3.setRotation(0f, 0f, 0.1858931f)

        spike4.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike4.setRotationPoint(-5f, 0f, -7f)
        spike4.mirror = true
        spike4.setRotation(0f, 0f, -0.1487144f)

        spike5.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike5.setRotationPoint(11f, 6f, 7f)
        spike5.mirror = true
        spike5.setRotation(0f, 0f, 1.003822f)

        spike6.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike6.setRotationPoint(11f, 6f, -1f)
        spike6.mirror = true
        spike6.setRotation(0f, 0f, 1.003822f)

        spike7.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike7.setRotationPoint(11f, 6f, -8f)
        spike7.mirror = true
        spike7.setRotation(0f, 0f, 1.003822f)

        spike8.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike8.setRotationPoint(5f, 0f, 5f)
        spike8.mirror = true
        spike8.setRotation(0f, 0f, 0.4461433f)

        spike9.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike9.setRotationPoint(-5f, 0f, 8f)
        spike9.mirror = true
        spike9.setRotation(-0.4089647f, 0f, 0f)

        spike10.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike10.setRotationPoint(-11f, 6f, 7f)
        spike10.mirror = true
        spike10.setRotation(0f, 0f, -1.003826f)

        spike11.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike11.setRotationPoint(-11f, 6f, 0f)
        spike11.mirror = true
        spike11.setRotation(0f, 0f, -1.003826f)

        spike12.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike12.setRotationPoint(-11f, 6f, -7f)
        spike12.mirror = true
        spike12.setRotation(0f, 0f, -1.003826f)

        spike13.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike13.setRotationPoint(3f, 0f, 11f)
        spike13.mirror = true
        spike13.setRotation(-0.4089647f, 0f, 0f)

        spike14.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike14.setRotationPoint(4f, 0f, -10f)
        spike14.mirror = true
        spike14.setRotation(0f, 0f, 0.2230717f)

        spike15.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike15.setRotationPoint(4f, 0f, -3f)
        spike15.mirror = true
        spike15.setRotation(0f, 0f, 0.4461433f)

        spike16.addBox(0f, 0f, 0f, 1f, 6f, 1f)
        spike16.setRotationPoint(1f, 0f, 7f)
        spike16.mirror = true
        spike16.setRotation(0f, 0f, 0.4461433f)
    }

    private fun ModelRenderer.setRotation(x: Float, y: Float, z: Float) {
        this.rotateAngleX = x
        this.rotateAngleY = y
        this.rotateAngleZ = z
    }


    override fun render(stack: MatrixStack, vertexBuilder: IVertexBuilder, limbSwing: Int, limbSwingAmount: Int, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        //TODO setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        fun ModelRenderer.render() = this.render(stack, vertexBuilder, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        turtleShellBase.render()
        shellRidge.render()
        legFrontRight.render()
        legFrontLeft.render()
        legBackLeft.render()
        legBackRight.render()
        neck.render()
        head.render()
        spike1.render()
        spike2.render()
        spike3.render()
        spike4.render()
        spike5.render()
        spike6.render()
        spike7.render()
        spike8.render()
        spike9.render()
        spike10.render()
        spike11.render()
        spike12.render()
        spike13.render()
        spike14.render()
        spike15.render()
        spike16.render()
    }

    override fun setRotationAngles(entity: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 0.5f * limbSwingAmount
        this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 0.5f * limbSwingAmount
        this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 0.5f * limbSwingAmount
        this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 0.5f * limbSwingAmount
    }
}