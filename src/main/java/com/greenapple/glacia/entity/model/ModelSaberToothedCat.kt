package com.greenapple.glacia.entity.model

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.util.math.MathHelper

class ModelSaberToothedCat<T : TameableEntity> : EntityModel<T>() {

    init {
        textureWidth = 49
        textureHeight = 52
    }

    private val main = ModelRenderer(this)
    private val neck_upper = ModelRenderer(this)
    private val neck = ModelRenderer(this)
    private val head = ModelRenderer(this)
    private val head_bone = ModelRenderer(this)
    private val ear_right = ModelRenderer(this)
    private val ear_left = ModelRenderer(this)
    private val nose = ModelRenderer(this)
    private val teeth = ModelRenderer(this)
    private val body = ModelRenderer(this)
    private val butt_lower = ModelRenderer(this)
    private val cheeck_lower_left = ModelRenderer(this)
    private val cheeck_lower_right = ModelRenderer(this)
    private val tail_upper = ModelRenderer(this)
    private val tail_lower = ModelRenderer(this)
    private val perineum = ModelRenderer(this)
    private val leg_hind_left = ModelRenderer(this)
    private val thigh_left = ModelRenderer(this)
    private val thigh_lower_left = ModelRenderer(this)
    private val leg_hind_lower_left = ModelRenderer(this)
    private val leg_hind_right = ModelRenderer(this)
    private val thigh_right = ModelRenderer(this)
    private val thigh_lower_right = ModelRenderer(this)
    private val leg_hind_lower_right = ModelRenderer(this)
    private val leg_front_right = ModelRenderer(this)
    private val leg_front_right_upper = ModelRenderer(this)
    private val leg_front_right_lower = ModelRenderer(this)
    private val leg_front_left = ModelRenderer(this)
    private val leg_front_left_upper = ModelRenderer(this)
    private val leg_front_left_lower = ModelRenderer(this)

    //TODO fix ugly stuff maybe?
    init {
        main.setRotationPoint(-0.5044f, 14.0f, -5.0f)
        main.setTextureOffset(0, 15)
        main.addBox(-3.9956f, -4.0f, -2.0f, 9f, 8f, 4f, 0.0f, false)
        main.setTextureOffset(0, 0)
        main.addBox(-3.4956f, -3.5f, -3.0f, 8f, 7f, 8f, 0.0f, false)

        neck_upper.setRotationPoint(0.5044f, 9.0f, 6.0f)
        neck_upper.setRotationAngle(0.0873f, 0.0f, 0.0f)
        main.addChild(neck_upper)
        neck_upper.setTextureOffset(24, 3)
        neck_upper.addBox(-3.0f, -11.9052f, -8.8295f, 6f, 4f, 1f, 0.0f, false)

        neck.setRotationPoint(0.5044f, 6.0f, 8.0f)
        neck.setRotationAngle(0.3491f, 0.0f, 0.0f)
        main.addChild(neck)
        neck.setTextureOffset(37, 0)
        neck.addBox(-2.0f, -11.5374f, -9.3763f, 4f, 3f, 1f, 0.0f, false)
        neck.setTextureOffset(38, 0)
        neck.addBox(-1.5f, -11.3075f, -9.8445f, 3f, 3f, 1f, 0.0f, false)

        head.setRotationPoint(0.5044f, 0.0f, -5.0f)
        head.setRotationAngle(0.0873f, 0.0f, 0.0f)
        main.addChild(head)

        head_bone.setRotationPoint(0.0f, 0.0f, 0.0f)
        head.addChild(head_bone)
        head_bone.setTextureOffset(35, 5)
        head_bone.addBox(-2.0f, -2.0373f, -2.3017f, 4f, 4f, 3f, 0.0f, false)
        head_bone.setTextureOffset(37, 12)
        head_bone.addBox(-1.5f, -0.1244f, -4.2979f, 3f, 2f, 2f, 0.0f, false)
        head_bone.setTextureOffset(36, 16)
        head_bone.addBox(-1.8044f, 1.2484f, -4.0504f, 1f, 1f, 2f, 0.0f, true)
        head_bone.addBox(0.7956f, 1.2484f, -4.0504f, 1f, 1f, 2f, 0.0f, false)

        ear_right.setRotationPoint(-0.5044f, 8.8373f, 11.1775f)
        ear_right.setRotationAngle(0.1745f, 0.0f, 0.0f)
        head_bone.addChild(ear_right)
        ear_right.setTextureOffset(23, 15)
        ear_right.addBox(-1.9956f, -13.0961f, -9.831f, 1f, 1f, 2f, 0.0f, true)

        ear_left.setRotationPoint(3.4956f, 8.8373f, 11.1775f)
        ear_left.setRotationAngle(0.1745f, 0.0f, 0.0f)
        head_bone.addChild(ear_left)
        ear_left.setTextureOffset(23, 15)
        ear_left.addBox(-1.9956f, -13.0961f, -9.831f, 1f, 1f, 2f, 0.0f, false)

        nose.setRotationPoint(1.0f, 2.8373f, 14.1775f)
        nose.setRotationAngle(0.3491f, 0.0f, 0.0f)
        head_bone.addChild(nose)
        nose.setTextureOffset(26, 15)
        nose.addBox(-2.0f, -9.6646f, -16.9807f, 2f, 2f, 3f, 0.0f, false)

        teeth.setRotationPoint(0.0f, 4.8373f, 10.1775f)
        teeth.setRotationAngle(0.4363f, 0.0f, 0.0f)
        head_bone.addChild(teeth)
        teeth.setTextureOffset(42, 16)
        teeth.addBox(-1.9f, -8.174f, -11.3114f, 1f, 2f, 1f, 0.0f, true)
        teeth.addBox(0.9f, -8.174f, -11.3114f, 1f, 2f, 1f, 0.0f, false)

        body.setRotationPoint(0.5044f, -2.5f, 6.0f)
        main.addChild(body)
        body.setTextureOffset(0, 27)
        body.addBox(-3.5f, 0.5f, -1.0f, 7f, 5f, 9f, 0.0f, false)
        body.setTextureOffset(23, 31)
        body.addBox(-2.0f, -0.25f, -1.0f, 4f, 1f, 4f, 0.0f, false)
        body.setTextureOffset(0, 49)
        body.addBox(-1.5f, 1.5f, 7.5f, 3f, 2f, 1f, 0.0f, false)
        body.setTextureOffset(18, 41)
        body.addBox(1.75f, 0.25f, 4.25f, 2f, 4f, 4f, 0.0f, false)
        body.addBox(-3.75f, 0.25f, 4.25f, 2f, 4f, 4f, 0.0f, true)

        butt_lower.setRotationPoint(0.0f, 11.5f, -2.0f)
        butt_lower.setRotationAngle(-0.0873f, 0.0f, 0.0f)
        body.addChild(butt_lower)
        butt_lower.setTextureOffset(8, 50)
        butt_lower.addBox(-1.0f, -10.1304f, 9.0016f, 2f, 1f, 1f, 0.0f, false)

        cheeck_lower_left.setRotationPoint(0.0f, 11.5f, -1.0f)
        cheeck_lower_left.setRotationAngle(-0.0873f, 0.0f, 0.0873f)
        body.addChild(cheeck_lower_left)
        cheeck_lower_left.setTextureOffset(24, 43)
        cheeck_lower_left.addBox(1.3199f, -9.0281f, 2.8226f, 2f, 3f, 6f, 0.0f, false)

        cheeck_lower_right.setRotationPoint(0.0f, 12.5f, -1.0f)
        cheeck_lower_right.setRotationAngle(-0.0873f, 0.0f, -0.0873f)
        body.addChild(cheeck_lower_right)
        cheeck_lower_right.setTextureOffset(24, 43)
        cheeck_lower_right.addBox(-3.2498f, -9.9417f, 2.8309f, 2f, 3f, 6f, 0.0f, true)

        tail_upper.setRotationPoint(0.0f, 14.5f, 1.0f)
        tail_upper.setRotationAngle(0.4363f, 0.0f, 0.0f)
        body.addChild(tail_upper)
        tail_upper.setTextureOffset(14, 49)
        tail_upper.addBox(-0.5f, -8.6273f, 11.2427f, 1f, 2f, 1f, 0.0f, false)

        tail_lower.setRotationPoint(0.0f, 18.5f, 3.0f)
        tail_lower.setRotationAngle(0.7854f, 0.0f, 0.0f)
        body.addChild(tail_lower)
        tail_lower.setTextureOffset(14, 49)
        tail_lower.addBox(-0.5f, -6.7563f, 14.1517f, 1f, 2f, 1f, 0.0f, false)

        perineum.setRotationPoint(0.0f, 5.2f, 6.9f)
        perineum.setRotationAngle(-0.0873f, 0.0f, 0.0f)
        body.addChild(perineum)
        perineum.setTextureOffset(8, 41)
        perineum.addBox(-2.5f, -3.0834f, -2.591f, 5f, 4f, 4f, 0.0f, false)

        leg_hind_left.setRotationPoint(3.0f, 6.5f, 7.0f)
        body.addChild(leg_hind_left)

        thigh_left.setRotationPoint(-3.0f, 5.0f, -8.0f)
        thigh_left.setRotationAngle(0.0524f, 0.0f, 0.0f)
        leg_hind_left.addChild(thigh_left)
        thigh_left.setTextureOffset(34, 42)
        thigh_left.addBox(2.0f, -5.9477f, 4.9986f, 2f, 3f, 4f, 0.0f, true)

        thigh_lower_left.setRotationPoint(0.0f, 1.0f, 0.0f)
        thigh_lower_left.setRotationAngle(-0.1047f, 0.0f, 0.0f)
        thigh_left.addChild(thigh_lower_left)
        thigh_lower_left.setTextureOffset(34, 42)
        thigh_lower_left.addBox(1.85f, -6.0523f, 4.9986f, 2f, 2f, 4f, 0.0f, true)
        thigh_lower_left.setTextureOffset(35, 42)
        thigh_lower_left.addBox(1.845f, -4.5634f, 6.2486f, 2f, 1f, 3f, 0.0f, true)

        leg_hind_lower_left.setRotationPoint(0.0f, 2.0f, 0.0f)
        leg_hind_left.addChild(leg_hind_lower_left)
        leg_hind_lower_left.setTextureOffset(36, 36)
        leg_hind_lower_left.addBox(-1.3f, 0.0f, -0.2f, 2f, 4f, 2f, 0.0f, true)

        leg_hind_right.setRotationPoint(-3.0f, 6.5f, 7.0f)
        body.addChild(leg_hind_right)

        thigh_right.setRotationPoint(3.0f, 5.0f, -8.0f)
        thigh_right.setRotationAngle(0.0524f, 0.0f, 0.0f)
        leg_hind_right.addChild(thigh_right)
        thigh_right.setTextureOffset(34, 42)
        thigh_right.addBox(-4.0f, -5.9477f, 4.9986f, 2f, 3f, 4f, 0.0f, false)

        thigh_lower_right.setRotationPoint(0.0f, 1.0f, 0.0f)
        thigh_lower_right.setRotationAngle(-0.1047f, 0.0f, 0.0f)
        thigh_right.addChild(thigh_lower_right)
        thigh_lower_right.setTextureOffset(34, 42)
        thigh_lower_right.addBox(-3.85f, -6.0523f, 4.9986f, 2f, 2f, 4f, 0.0f, false)
        thigh_lower_right.setTextureOffset(35, 42)
        thigh_lower_right.addBox(-3.845f, -4.5634f, 6.2486f, 2f, 1f, 3f, 0.0f, false)

        leg_hind_lower_right.setRotationPoint(0.0f, 2.0f, 0.0f)
        leg_hind_right.addChild(leg_hind_lower_right)
        leg_hind_lower_right.setTextureOffset(36, 36)
        leg_hind_lower_right.addBox(-0.7f, 0.0f, -0.2f, 2f, 4f, 2f, 0.0f, false)

        leg_front_right.setRotationPoint(-2.4956f, 2.5f, 2.0f)
        main.addChild(leg_front_right)
        leg_front_right.setTextureOffset(38, 21)
        leg_front_right.addBox(-1.1f, 1.5f, -1.25f, 2f, 2f, 3f, 0.0f, true)

        leg_front_right_upper.setRotationPoint(3.0f, 6.5f, 3.0f)
        leg_front_right_upper.setRotationAngle(0.1222f, 0.0f, 0.0f)
        leg_front_right.addChild(leg_front_right_upper)
        leg_front_right_upper.setTextureOffset(26, 20)
        leg_front_right_upper.addBox(-4.25f, -7.8088f, -4.3819f, 2f, 3f, 4f, 0.0f, true)

        leg_front_right_lower.setRotationPoint(0.0f, 3.5f, 0.0f)
        leg_front_right_lower.setRotationAngle(-0.0524f, 0.0f, 0.0f)
        leg_front_right.addChild(leg_front_right_lower)
        leg_front_right_lower.setTextureOffset(39, 26)
        leg_front_right_lower.addBox(-1.0f, -0.203f, -1.0482f, 2f, 4f, 2f, 0.0f, true)

        leg_front_left.setRotationPoint(4.5044f, 2.5f, 2.0f)
        main.addChild(leg_front_left)
        leg_front_left.setTextureOffset(38, 21)
        leg_front_left.addBox(-1.9f, 1.5f, -1.25f, 2f, 2f, 3f, 0.0f, false)

        leg_front_left_upper.setRotationPoint(3.0f, 6.5f, 3.0f)
        leg_front_left_upper.setRotationAngle(0.1222f, 0.0f, 0.0f)
        leg_front_left.addChild(leg_front_left_upper)
        leg_front_left_upper.setTextureOffset(26, 20)
        leg_front_left_upper.addBox(-4.75f, -7.8088f, -4.3819f, 2f, 3f, 4f, 0.0f, false)

        leg_front_left_lower.setRotationPoint(-1.0f, 3.5f, 0.0f)
        leg_front_left_lower.setRotationAngle(-0.0524f, 0.0f, 0.0f)
        leg_front_left.addChild(leg_front_left_lower)
        leg_front_left_lower.setTextureOffset(39, 26)
        leg_front_left_lower.addBox(-1.0f, -0.203f, -1.0482f, 2f, 4f, 2f, 0.0f, false)
    }

    override fun render(stack: MatrixStack, vertexBuilder: IVertexBuilder, limbSwing: Int, limbSwingAmount: Int, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        //setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        fun ModelRenderer.render() = this.render(stack, vertexBuilder, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        main.render()
    }

    private fun ModelRenderer.setRotationAngle(x: Float, y: Float, z: Float) {
        this.rotateAngleX = x
        this.rotateAngleY = y
        this.rotateAngleZ = z
    }

    override fun setLivingAnimations(entityIn: T, limbSwing: Float, limbSwingAmount: Float, partialTick: Float) {
        if (entityIn.isSitting) {
            this.main.rotateAngleX = -Math.PI.toFloat() / 12f
            this.head_bone.rotateAngleX = -main.rotateAngleX
            this.body.rotateAngleX = -Math.PI.toFloat() / 6f
            this.leg_hind_lower_right.rotateAngleX = -1f
            this.leg_hind_lower_left.rotateAngleX = -1f
            this.leg_front_right_lower.rotateAngleX = -main.rotateAngleX
            this.leg_front_left_lower.rotateAngleX = -main.rotateAngleX
        } else {
            main.rotateAngleX = 0f
            head_bone.rotateAngleX = 0f
            body.rotateAngleX = 0f
            leg_hind_lower_right.rotateAngleX = 0f
            leg_hind_lower_left.rotateAngleX = 0f
            leg_front_right_lower.rotateAngleX = -0.0524f
            leg_front_left_lower.rotateAngleX = -0.0524f

            this.leg_hind_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
            this.leg_hind_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + Math.PI.toFloat()) * 1.4f * limbSwingAmount
            this.leg_front_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + Math.PI.toFloat()) * 1.4f * limbSwingAmount
            this.leg_front_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
        }
    }

    override fun setRotationAngles(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        //super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        this.head.rotateAngleX = headPitch * (Math.PI.toFloat() / 180f)
        this.head.rotateAngleY = netHeadYaw * (Math.PI.toFloat() / 180f)
    }
}

