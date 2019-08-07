package com.greenapple.glacia.entity.model

import com.greenapple.glacia.entity.GlaciaMonsterAttributes
import com.greenapple.glacia.entity.isEnum
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.entity.model.RendererModel
import net.minecraft.entity.AgeableEntity

import net.minecraft.util.math.MathHelper
import org.lwjgl.opengl.GL11
import kotlin.random.Random

class ModelReindeer<E: AgeableEntity> : EntityModel<E>() {

    init {
        textureWidth = 46
        textureHeight = 51
    }

    private val bodyMale: RendererModel = RendererModel(this, 0, 0)
    private val neck = RendererModel(this, 20, 20)
    private val head = RendererModel(this, 0, 20)
    private val head2 = RendererModel(this, 36, 20)
    private val nose = RendererModel(this, 39, 24)
    private val corn = RendererModel(this, 40, 0)
    private val corn2 = RendererModel(this, 40, 0)
    private val tail = RendererModel(this, 32, 0)
    private val corn3 = RendererModel(this, 38, 9)
    private val corn4 = RendererModel(this, 38, 9)
    private val foot1p1 = RendererModel(this, 31, 10)
    private val foot1p2 = RendererModel(this, 31, 10)
    private val foot1p3 = RendererModel(this, 40, 12)
    private val foot2p1 = RendererModel(this, 31, 10)
    private val foot2p2 = RendererModel(this, 40, 12)
    private val foot3p1 = RendererModel(this, 31, 10)
    private val foot2p3 = RendererModel(this, 31, 10)
    private val foot3p2 = RendererModel(this, 31, 10)
    private val foot3p3 = RendererModel(this, 40, 12)
    private val foot4p1 = RendererModel(this, 31, 10)
    private val foot4p2 = RendererModel(this, 31, 10)
    private val foot4p3 = RendererModel(this, 40, 12)
    private val ear1 = RendererModel(this, 36, 26)
    private val ear2 = RendererModel(this, 42, 26)
    private val leg1 = RendererModel(this, 3, 1)
    private val leg2 = RendererModel(this, 3, 1)
    private val leg3 = RendererModel(this, 3, 1)
    private val leg4 = RendererModel(this, 3, 1)
    private val corn5 = RendererModel(this, 40, 0)
    private val corn6 = RendererModel(this, 40, 0)
    private val bodyFemale = RendererModel(this, 0, 31)

    init {
        bodyMale.addBox(0f, 0f, 0f, 8, 5, 15)
        bodyMale.setRotationPoint(-4f, 10f, -8f)
        bodyMale.setTextureSize(64, 64)
        bodyMale.mirror = true
        setRotation(bodyMale, 0f, 0f, 0f)

        neck.addBox(0f, 0f, 0f, 4, 7, 4)
        neck.setRotationPoint(-2f, 5f, -8.5f)
        neck.setTextureSize(64, 64)
        neck.mirror = true
        setRotation(neck, 0f, 0f, 0f)

        head.addBox(0f, 0f, 0f, 5, 4, 5)
        head.setRotationPoint(-2.5f, 2f, -9.4f)
        head.setTextureSize(64, 64)
        head.mirror = true
        setRotation(head, 0f, 0f, 0f)

        head2.addBox(0f, 0f, 0f, 3, 2, 2)
        head2.setRotationPoint(-1.5f, 3.9f, -11f)
        head2.setTextureSize(64, 64)
        head2.mirror = true
        setRotation(head2, 0f, 0f, 0f)

        nose.addBox(0f, 0f, 0f, 1, 1, 1)
        nose.setRotationPoint(-0.5f, 3.8f, -11.5f)
        nose.setTextureSize(64, 64)
        nose.mirror = true
        setRotation(nose, 0f, 0f, 0f)

        corn.addBox(0f, 0f, 0f, 1, 8, 1)
        corn.setRotationPoint(-3f, -4f, -6.5f)
        corn.setTextureSize(64, 64)
        corn.mirror = true
        setRotation(corn, 0f, 0.6320361f, -0.1289891f)

        corn2.addBox(0f, 0f, 0f, 1, 8, 1)
        corn2.setRotationPoint(2f, -4f, -7.1f)
        corn2.setTextureSize(64, 64)
        corn2.mirror = true
        setRotation(corn2, 0f, -0.642054f, 0.1289973f)

        tail.addBox(0f, -0.5f, -1f, 1, 2, 2)
        tail.setRotationPoint(0f, 11f, 7f)
        tail.setTextureSize(64, 64)
        tail.mirror = true
        setRotation(tail, 0f, 1.570796f, 3.149738f)

        corn3.addBox(0f, 0f, 0f, 3, 1, 1)
        corn3.setRotationPoint(-4.9f, -3f, -5.7f)
        corn3.setTextureSize(64, 64)
        corn3.mirror = true
        setRotation(corn3, 0f, 0.4014257f, 0.3141593f)

        corn4.addBox(0f, 0f, 0f, 3, 1, 1)
        corn4.setRotationPoint(1.8f, -2f, -7f)
        corn4.setTextureSize(64, 64)
        corn4.mirror = true
        setRotation(corn4, 0f, -0.4014257f, -0.2792527f)

        foot1p1.addBox(0f, 0f, 0f, 1, 2, 3)
        foot1p1.setRotationPoint(-0.5f, 8f, -0.3f)
        foot1p1.setTextureSize(64, 64)
        foot1p1.mirror = true
        setRotation(foot1p1, 0f, 0.1745329f, 0f)

        foot1p2.addBox(0f, 0f, 0f, 1, 2, 3)
        foot1p2.setRotationPoint(1.5f, 8f, -0.5f)
        foot1p2.setTextureSize(64, 64)
        foot1p2.mirror = true
        setRotation(foot1p2, 0f, -0.1745329f, 0f)

        foot1p3.addBox(0f, 0f, 0f, 2, 2, 1)
        foot1p3.setRotationPoint(0f, 8f, 1.6f)
        foot1p3.setTextureSize(64, 64)
        foot1p3.mirror = true
        setRotation(foot1p3, 0f, 0f, 0f)

        foot2p1.addBox(0f, 0f, 0f, 1, 2, 3)
        foot2p1.setRotationPoint(1.5f, 8f, -0.5f)
        foot2p1.setTextureSize(64, 64)
        foot2p1.mirror = true
        setRotation(foot2p1, 0f, -0.1745329f, 0f)

        foot2p2.addBox(0f, 0f, 0f, 2, 2, 1)
        foot2p2.setRotationPoint(0f, 8f, 1.6f)
        foot2p2.setTextureSize(64, 64)
        foot2p2.mirror = true
        setRotation(foot2p2, 0f, 0f, 0f)

        foot3p1.addBox(0f, 0f, 0f, 1, 2, 3)
        foot3p1.setRotationPoint(1.5f, 8f, -0.5f)
        foot3p1.setTextureSize(64, 64)
        foot3p1.mirror = true
        setRotation(foot3p1, 0f, -0.1745329f, 0f)

        foot2p3.addBox(0f, 0f, 0f, 1, 2, 3)
        foot2p3.setRotationPoint(-0.5f, 8f, -0.3f)
        foot2p3.setTextureSize(64, 64)
        foot2p3.mirror = true
        setRotation(foot2p3, 0f, 0.1745329f, 0f)

        foot3p2.addBox(0f, 0f, 0f, 1, 2, 3)
        foot3p2.setRotationPoint(-0.5f, 8f, -0.3f)
        foot3p2.setTextureSize(64, 64)
        foot3p2.mirror = true
        setRotation(foot3p2, 0f, 0.1745329f, 0f)

        foot3p3.addBox(0f, 0f, 0f, 2, 2, 1)
        foot3p3.setRotationPoint(0f, 8f, 1.6f)
        foot3p3.setTextureSize(64, 64)
        foot3p3.mirror = true
        setRotation(foot3p3, 0f, 0f, 0f)

        foot4p1.addBox(0f, 0f, 0f, 1, 2, 3)
        foot4p1.setRotationPoint(1.5f, 8f, -0.5f)
        foot4p1.setTextureSize(64, 64)
        foot4p1.mirror = true
        setRotation(foot4p1, 0f, -0.1745329f, 0f)

        foot4p2.addBox(0f, 0f, 0f, 1, 2, 3)
        foot4p2.setRotationPoint(-0.5f, 8f, -0.3f)
        foot4p2.setTextureSize(64, 64)
        foot4p2.mirror = true
        setRotation(foot4p2, 0f, 0.1745329f, 0f)

        foot4p3.addBox(0f, 0f, 0f, 2, 2, 1)
        foot4p3.setRotationPoint(0f, 8f, 1.6f)
        foot4p3.setTextureSize(64, 64)
        foot4p3.mirror = true
        setRotation(foot4p3, 0f, 0f, 0f)

        ear1.addBox(0f, 0f, 0f, 1, 2, 1)
        ear1.setRotationPoint(-2.6f, 1.1f, -5.5f)
        ear1.setTextureSize(64, 64)
        ear1.mirror = true
        setRotation(ear1, -0.2602503f, 0f, 0f)

        ear2.addBox(0f, 0f, 0f, 1, 2, 1)
        ear2.setRotationPoint(1.6f, 1.1f, -5.5f)
        ear2.setTextureSize(64, 64)
        ear2.mirror = true
        setRotation(ear2, -0.2602503f, 0f, 0f)

        leg1.addBox(0f, 0f, 0f, 2, 10, 2)
        leg1.setRotationPoint(1.5f, 14f, -7.5f)
        leg1.setTextureSize(64, 64)
        leg1.mirror = true
        leg1.addChild(foot1p1)
        leg1.addChild(foot1p2)
        leg1.addChild(foot1p3)
        setRotation(leg1, 0f, 0f, 0f)

        leg2.addBox(0f, 0f, 0f, 2, 10, 2)
        leg2.setRotationPoint(-3.5f, 14f, -7.5f)
        leg2.setTextureSize(64, 64)
        leg2.mirror = true
        leg2.addChild(foot2p1)
        leg2.addChild(foot2p2)
        leg2.addChild(foot2p3)
        setRotation(leg2, 0f, 0f, 0f)

        leg3.addBox(0f, 0f, 0f, 2, 10, 2)
        leg3.setRotationPoint(1.5f, 14f, 4.5f)
        leg3.setTextureSize(64, 64)
        leg3.mirror = true
        leg3.addChild(foot3p1)
        leg3.addChild(foot3p2)
        leg3.addChild(foot3p3)
        setRotation(leg3, 0f, 0f, 0f)

        leg4.addBox(0f, 0f, 0f, 2, 10, 2)
        leg4.setRotationPoint(-3.5f, 14f, 4.5f)
        leg4.setTextureSize(64, 64)
        leg4.mirror = true
        leg4.addChild(foot4p1)
        leg4.addChild(foot4p2)
        leg4.addChild(foot4p3)
        setRotation(leg4, 0f, 0f, 0f)

        corn5.addBox(0f, 0f, 0f, 1, 1, 1)
        corn5.setRotationPoint(-2f, 1.5f, -7f)
        corn5.setTextureSize(64, 64)
        corn5.mirror = true
        setRotation(corn5, 0f, 0f, 0f)

        corn6.addBox(0f, 0f, 0f, 1, 1, 1)
        corn6.setRotationPoint(1f, 1.5f, -7f)
        corn6.setTextureSize(64, 64)
        corn6.mirror = true
        setRotation(corn6, 0f, 0f, 0f)

        bodyFemale.addBox(0f, 0f, 0f, 8, 5, 15)
        bodyFemale.setRotationPoint(-4f, 10f, -8f)
        bodyFemale.setTextureSize(64, 64)
        bodyFemale.mirror = true
        setRotation(bodyFemale, 0f, 0f, 0f)
    }

    private inline fun shouldRenderChild(scale: Float, childSizePt: Float, crossinline renderer: () -> Unit) {
        if (isChild) {
            GL11.glPushMatrix()
            GL11.glScalef(childSizePt, childSizePt, childSizePt)
            GL11.glTranslatef(0.0f, 24.0f * scale, 0.0f)
        }
        renderer()
        if (isChild) GL11.glPopMatrix()
    }

    override fun render(entity: E, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        //EntityGlacialRainDeer entityraindeer = (EntityGlacialRainDeer)entity;
        //val randgender = Random.nextInt(2 - 0) + 0
        (entity.getAttribute(GlaciaMonsterAttributes.GENDER) isEnum GlaciaMonsterAttributes.Gender.MALE).let {isMale -> shouldRenderChild (scale, 0.5f) {
            super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
            setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
            neck.render(scale)
            head.render(scale)
            head2.render(scale)
            nose.render(scale)
            ear1.render(scale)
            ear2.render(scale)
            if (isMale) {
                bodyMale.render(scale)
                if (!isChild) {
                    corn.render(scale)
                    corn2.render(scale)
                    corn3.render(scale)
                    corn4.render(scale)
                }
            }
            else {
                bodyFemale.render(scale)
                if (!isChild) {
                    corn5.render(scale)
                    corn6.render(scale)
                }
            }
            tail.render(scale)
            leg1.render(scale)
            leg2.render(scale)
            leg3.render(scale)
            leg4.render(scale)
        }}
    }

    private fun setRotation(model: RendererModel, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }

    override fun setRotationAngles(entityIn: E, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scaleFactor: Float) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor)
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 1.0f * limbSwingAmount
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 1.0f * limbSwingAmount
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 1.0f * limbSwingAmount
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 1.0f * limbSwingAmount
        this.tail.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 1.0f * limbSwingAmount
    }
}