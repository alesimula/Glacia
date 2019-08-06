package com.greenapple.glacia.entity.model

import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.entity.model.RendererModel
import net.minecraft.client.renderer.model.ModelBox
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.util.math.MathHelper

class ModelSaberToothedCat<T : TameableEntity> : EntityModel<T>() {

    init {
        textureWidth = 49
        textureHeight = 52
    }

    private val main = RendererModel(this)
    private val neck_upper = RendererModel(this)
    private val neck = RendererModel(this)
    private val head = RendererModel(this)
    private val head_bone = RendererModel(this)
    private val ear_right = RendererModel(this)
    private val ear_left = RendererModel(this)
    private val nose = RendererModel(this)
    private val teeth = RendererModel(this)
    private val body = RendererModel(this)
    private val butt_lower = RendererModel(this)
    private val cheeck_lower_left = RendererModel(this)
    private val cheeck_lower_right = RendererModel(this)
    private val tail_upper = RendererModel(this)
    private val tail_lower = RendererModel(this)
    private val perineum = RendererModel(this)
    private val leg_hind_left = RendererModel(this)
    private val thigh_left = RendererModel(this)
    private val thigh_lower_left = RendererModel(this)
    private val leg_hind_lower_left = RendererModel(this)
    private val leg_hind_right = RendererModel(this)
    private val thigh_right = RendererModel(this)
    private val thigh_lower_right = RendererModel(this)
    private val leg_hind_lower_right = RendererModel(this)
    private val leg_front_right = RendererModel(this)
    private val leg_front_right_upper = RendererModel(this)
    private val leg_front_right_lower = RendererModel(this)
    private val leg_front_left = RendererModel(this)
    private val leg_front_left_upper = RendererModel(this)
    private val leg_front_left_lower = RendererModel(this)

    init {
        main.setRotationPoint(-0.5044f, 14.0f, -5.0f)
        main.cubeList.add(ModelBox(main, 0, 15, -3.9956f, -4.0f, -2.0f, 9, 8, 4, 0.0f, false))
        main.cubeList.add(ModelBox(main, 0, 0, -3.4956f, -3.5f, -3.0f, 8, 7, 8, 0.0f, false))

        neck_upper.setRotationPoint(0.5044f, 9.0f, 6.0f)
        neck_upper.setRotationAngle(0.0873f, 0.0f, 0.0f)
        main.addChild(neck_upper)
        neck_upper.cubeList.add(ModelBox(neck_upper, 24, 3, -3.0f, -11.9052f, -8.8295f, 6, 4, 1, 0.0f, false))

        neck.setRotationPoint(0.5044f, 6.0f, 8.0f)
        neck.setRotationAngle(0.3491f, 0.0f, 0.0f)
        main.addChild(neck)
        neck.cubeList.add(ModelBox(neck, 37, 0, -2.0f, -11.5374f, -9.3763f, 4, 3, 1, 0.0f, false))
        neck.cubeList.add(ModelBox(neck, 38, 0, -1.5f, -11.3075f, -9.8445f, 3, 3, 1, 0.0f, false))

        head.setRotationPoint(0.5044f, 0.0f, -5.0f)
        head.setRotationAngle(0.0873f, 0.0f, 0.0f)
        main.addChild(head)

        head_bone.setRotationPoint(0.0f, 0.0f, 0.0f)
        head.addChild(head_bone)
        head_bone.cubeList.add(ModelBox(head_bone, 35, 5, -2.0f, -2.0373f, -2.3017f, 4, 4, 3, 0.0f, false))
        head_bone.cubeList.add(ModelBox(head_bone, 37, 12, -1.5f, -0.1244f, -4.2979f, 3, 2, 2, 0.0f, false))
        head_bone.cubeList.add(ModelBox(head_bone, 36, 16, -1.8044f, 1.2484f, -4.0504f, 1, 1, 2, 0.0f, true))
        head_bone.cubeList.add(ModelBox(head_bone, 36, 16, 0.7956f, 1.2484f, -4.0504f, 1, 1, 2, 0.0f, false))

        ear_right.setRotationPoint(-0.5044f, 8.8373f, 11.1775f)
        ear_right.setRotationAngle(0.1745f, 0.0f, 0.0f)
        head_bone.addChild(ear_right)
        ear_right.cubeList.add(ModelBox(ear_right, 23, 15, -1.9956f, -13.0961f, -9.831f, 1, 1, 2, 0.0f, true))

        ear_left.setRotationPoint(3.4956f, 8.8373f, 11.1775f)
        ear_left.setRotationAngle(0.1745f, 0.0f, 0.0f)
        head_bone.addChild(ear_left)
        ear_left.cubeList.add(ModelBox(ear_left, 23, 15, -1.9956f, -13.0961f, -9.831f, 1, 1, 2, 0.0f, false))

        nose.setRotationPoint(1.0f, 2.8373f, 14.1775f)
        nose.setRotationAngle(0.3491f, 0.0f, 0.0f)
        head_bone.addChild(nose)
        nose.cubeList.add(ModelBox(nose, 26, 15, -2.0f, -9.6646f, -16.9807f, 2, 2, 3, 0.0f, false))

        teeth.setRotationPoint(0.0f, 4.8373f, 10.1775f)
        teeth.setRotationAngle(0.4363f, 0.0f, 0.0f)
        head_bone.addChild(teeth)
        teeth.cubeList.add(ModelBox(teeth, 42, 16, -1.9f, -8.174f, -11.3114f, 1, 2, 1, 0.0f, true))
        teeth.cubeList.add(ModelBox(teeth, 42, 16, 0.9f, -8.174f, -11.3114f, 1, 2, 1, 0.0f, false))

        body.setRotationPoint(0.5044f, -2.5f, 6.0f)
        main.addChild(body)
        body.cubeList.add(ModelBox(body, 0, 27, -3.5f, 0.5f, -1.0f, 7, 5, 9, 0.0f, false))
        body.cubeList.add(ModelBox(body, 23, 31, -2.0f, -0.25f, -1.0f, 4, 1, 4, 0.0f, false))
        body.cubeList.add(ModelBox(body, 0, 49, -1.5f, 1.5f, 7.5f, 3, 2, 1, 0.0f, false))
        body.cubeList.add(ModelBox(body, 18, 41, 1.75f, 0.25f, 4.25f, 2, 4, 4, 0.0f, false))
        body.cubeList.add(ModelBox(body, 18, 41, -3.75f, 0.25f, 4.25f, 2, 4, 4, 0.0f, true))

        butt_lower.setRotationPoint(0.0f, 11.5f, -2.0f)
        butt_lower.setRotationAngle(-0.0873f, 0.0f, 0.0f)
        body.addChild(butt_lower)
        butt_lower.cubeList.add(ModelBox(butt_lower, 8, 50, -1.0f, -10.1304f, 9.0016f, 2, 1, 1, 0.0f, false))

        cheeck_lower_left.setRotationPoint(0.0f, 11.5f, -1.0f)
        cheeck_lower_left.setRotationAngle(-0.0873f, 0.0f, 0.0873f)
        body.addChild(cheeck_lower_left)
        cheeck_lower_left.cubeList.add(ModelBox(cheeck_lower_left, 24, 43, 1.3199f, -9.0281f, 2.8226f, 2, 3, 6, 0.0f, false))

        cheeck_lower_right.setRotationPoint(0.0f, 12.5f, -1.0f)
        cheeck_lower_right.setRotationAngle(-0.0873f, 0.0f, -0.0873f)
        body.addChild(cheeck_lower_right)
        cheeck_lower_right.cubeList.add(ModelBox(cheeck_lower_right, 24, 43, -3.2498f, -9.9417f, 2.8309f, 2, 3, 6, 0.0f, true))

        tail_upper.setRotationPoint(0.0f, 14.5f, 1.0f)
        tail_upper.setRotationAngle(0.4363f, 0.0f, 0.0f)
        body.addChild(tail_upper)
        tail_upper.cubeList.add(ModelBox(tail_upper, 14, 49, -0.5f, -8.6273f, 11.2427f, 1, 2, 1, 0.0f, false))

        tail_lower.setRotationPoint(0.0f, 18.5f, 3.0f)
        tail_lower.setRotationAngle(0.7854f, 0.0f, 0.0f)
        body.addChild(tail_lower)
        tail_lower.cubeList.add(ModelBox(tail_lower, 14, 49, -0.5f, -6.7563f, 14.1517f, 1, 2, 1, 0.0f, false))

        perineum.setRotationPoint(0.0f, 5.2f, 6.9f)
        perineum.setRotationAngle(-0.0873f, 0.0f, 0.0f)
        body.addChild(perineum)
        perineum.cubeList.add(ModelBox(perineum, 0, 41, -2.5f, -3.0834f, -2.591f, 5, 4, 4, 0.0f, false))

        leg_hind_left.setRotationPoint(3.0f, 6.5f, 7.0f)
        body.addChild(leg_hind_left)

        thigh_left.setRotationPoint(-3.0f, 5.0f, -8.0f)
        thigh_left.setRotationAngle(0.0524f, 0.0f, 0.0f)
        leg_hind_left.addChild(thigh_left)
        thigh_left.cubeList.add(ModelBox(thigh_left, 34, 42, 2.0f, -5.9477f, 4.9986f, 2, 3, 4, 0.0f, true))

        thigh_lower_left.setRotationPoint(0.0f, 1.0f, 0.0f)
        thigh_lower_left.setRotationAngle(-0.1047f, 0.0f, 0.0f)
        thigh_left.addChild(thigh_lower_left)
        thigh_lower_left.cubeList.add(ModelBox(thigh_lower_left, 34, 42, 1.85f, -6.0523f, 4.9986f, 2, 2, 4, 0.0f, true))
        thigh_lower_left.cubeList.add(ModelBox(thigh_lower_left, 35, 43, 1.845f, -4.5634f, 6.2486f, 2, 1, 3, 0.0f, true))

        leg_hind_lower_left.setRotationPoint(0.0f, 2.0f, 0.0f)
        leg_hind_left.addChild(leg_hind_lower_left)
        leg_hind_lower_left.cubeList.add(ModelBox(leg_hind_lower_left, 36, 36, -1.3f, 0.0f, -0.2f, 2, 4, 2, 0.0f, true))

        leg_hind_right.setRotationPoint(-3.0f, 6.5f, 7.0f)
        body.addChild(leg_hind_right)

        thigh_right.setRotationPoint(3.0f, 5.0f, -8.0f)
        thigh_right.setRotationAngle(0.0524f, 0.0f, 0.0f)
        leg_hind_right.addChild(thigh_right)
        thigh_right.cubeList.add(ModelBox(thigh_right, 34, 42, -4.0f, -5.9477f, 4.9986f, 2, 3, 4, 0.0f, false))

        thigh_lower_right.setRotationPoint(0.0f, 1.0f, 0.0f)
        thigh_lower_right.setRotationAngle(-0.1047f, 0.0f, 0.0f)
        thigh_right.addChild(thigh_lower_right)
        thigh_lower_right.cubeList.add(ModelBox(thigh_lower_right, 34, 42, -3.85f, -6.0523f, 4.9986f, 2, 2, 4, 0.0f, false))
        thigh_lower_right.cubeList.add(ModelBox(thigh_lower_right, 35, 43, -3.845f, -4.5634f, 6.2486f, 2, 1, 3, 0.0f, false))

        leg_hind_lower_right.setRotationPoint(0.0f, 2.0f, 0.0f)
        leg_hind_right.addChild(leg_hind_lower_right)
        leg_hind_lower_right.cubeList.add(ModelBox(leg_hind_lower_right, 36, 36, -0.7f, 0.0f, -0.2f, 2, 4, 2, 0.0f, false))

        leg_front_right.setRotationPoint(-2.4956f, 2.5f, 2.0f)
        main.addChild(leg_front_right)
        leg_front_right.cubeList.add(ModelBox(leg_front_right, 38, 21, -1.1f, 1.5f, -1.25f, 2, 2, 3, 0.0f, true))

        leg_front_right_upper.setRotationPoint(3.0f, 6.5f, 3.0f)
        leg_front_right_upper.setRotationAngle(0.1222f, 0.0f, 0.0f)
        leg_front_right.addChild(leg_front_right_upper)
        leg_front_right_upper.cubeList.add(ModelBox(leg_front_right_upper, 26, 20, -4.25f, -7.8088f, -4.3819f, 2, 3, 4, 0.0f, true))

        leg_front_right_lower.setRotationPoint(0.0f, 3.5f, 0.0f)
        leg_front_right_lower.setRotationAngle(-0.0524f, 0.0f, 0.0f)
        leg_front_right.addChild(leg_front_right_lower)
        leg_front_right_lower.cubeList.add(ModelBox(leg_front_right_lower, 39, 26, -1.0f, -0.203f, -1.0482f, 2, 4, 2, 0.0f, true))

        leg_front_left.setRotationPoint(4.5044f, 2.5f, 2.0f)
        main.addChild(leg_front_left)
        leg_front_left.cubeList.add(ModelBox(leg_front_left, 38, 21, -1.9f, 1.5f, -1.25f, 2, 2, 3, 0.0f, false))

        leg_front_left_upper.setRotationPoint(3.0f, 6.5f, 3.0f)
        leg_front_left_upper.setRotationAngle(0.1222f, 0.0f, 0.0f)
        leg_front_left.addChild(leg_front_left_upper)
        leg_front_left_upper.cubeList.add(ModelBox(leg_front_left_upper, 26, 20, -4.75f, -7.8088f, -4.3819f, 2, 3, 4, 0.0f, false))

        leg_front_left_lower.setRotationPoint(-1.0f, 3.5f, 0.0f)
        leg_front_left_lower.setRotationAngle(-0.0524f, 0.0f, 0.0f)
        leg_front_left.addChild(leg_front_left_lower)
        leg_front_left_lower.cubeList.add(ModelBox(leg_front_left_lower, 39, 26, -1.0f, -0.203f, -1.0482f, 2, 4, 2, 0.0f, false))
    }

    override fun render(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        main.render(scale)
    }

    private fun RendererModel.setRotationAngle(x: Float, y: Float, z: Float) {
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
            this.leg_hind_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
            this.leg_hind_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + Math.PI.toFloat()) * 1.4f * limbSwingAmount
            this.leg_front_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f + Math.PI.toFloat()) * 1.4f * limbSwingAmount
            this.leg_front_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount
        }
    }

    override fun setRotationAngles(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scaleFactor: Float) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor)
        this.head.rotateAngleX = headPitch * (Math.PI.toFloat() / 180f)
        this.head.rotateAngleY = netHeadYaw * (Math.PI.toFloat() / 180f)
    }
}

