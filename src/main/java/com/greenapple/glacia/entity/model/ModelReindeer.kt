package com.greenapple.glacia.entity.model

import com.greenapple.glacia.entity.isMale
import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.vertex.IVertexBuilder
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity

import net.minecraft.util.math.MathHelper
import org.lwjgl.opengl.GL11

class ModelReindeer<E: LivingEntity> : EntityModel<E>(), IModelExtra<E> {

    override var entity: E? = null

    init {
        textureWidth = 46
        textureHeight = 51
    }

    /*var _isMale : Boolean? = null
    fun isMale(entity: E?=null) : Boolean {
        if (entity!=null) _isMale = entity.isMale
        return _isMale==true
    }*/

    private val bodyMale: ModelRenderer = ModelRenderer(this, 0, 0)
    private val neck = ModelRenderer(this, 20, 20)
    private val head = ModelRenderer(this, 0, 20)
    private val head2 = ModelRenderer(this, 36, 20)
    private val nose = ModelRenderer(this, 39, 24)
    private val corn = ModelRenderer(this, 40, 0)
    private val corn2 = ModelRenderer(this, 40, 0)
    private val tail = ModelRenderer(this, 32, 0)
    private val corn3 = ModelRenderer(this, 38, 9)
    private val corn4 = ModelRenderer(this, 38, 9)
    private val foot1p1 = ModelRenderer(this, 31, 10)
    private val foot1p2 = ModelRenderer(this, 31, 10)
    private val foot1p3 = ModelRenderer(this, 40, 12)
    private val foot2p1 = ModelRenderer(this, 31, 10)
    private val foot2p2 = ModelRenderer(this, 40, 12)
    private val foot3p1 = ModelRenderer(this, 31, 10)
    private val foot2p3 = ModelRenderer(this, 31, 10)
    private val foot3p2 = ModelRenderer(this, 31, 10)
    private val foot3p3 = ModelRenderer(this, 40, 12)
    private val foot4p1 = ModelRenderer(this, 31, 10)
    private val foot4p2 = ModelRenderer(this, 31, 10)
    private val foot4p3 = ModelRenderer(this, 40, 12)
    private val ear1 = ModelRenderer(this, 36, 26)
    private val ear2 = ModelRenderer(this, 42, 26)
    private val leg1 = ModelRenderer(this, 3, 1)
    private val leg2 = ModelRenderer(this, 3, 1)
    private val leg3 = ModelRenderer(this, 3, 1)
    private val leg4 = ModelRenderer(this, 3, 1)
    private val corn5 = ModelRenderer(this, 40, 0)
    private val corn6 = ModelRenderer(this, 40, 0)
    private val bodyFemale = ModelRenderer(this, 0, 31)

    init {
        bodyMale.addBox(0f, 0f, 0f, 8f, 5f, 15f)
        bodyMale.setRotationPoint(-4f, 10f, -8f)
        bodyMale.setTextureSize(64, 64)
        bodyMale.mirror = true
        setRotation(bodyMale, 0f, 0f, 0f)

        neck.addBox(0f, 0f, 0f, 4f, 7f, 4f)
        neck.setRotationPoint(-2f, 5f, -8.5f)
        neck.setTextureSize(64, 64)
        neck.mirror = true
        setRotation(neck, 0f, 0f, 0f)

        head.addBox(0f, 0f, 0f, 5f, 4f, 5f)
        head.setRotationPoint(-2.5f, 2f, -9.4f)
        head.setTextureSize(64, 64)
        head.mirror = true
        setRotation(head, 0f, 0f, 0f)

        head2.addBox(0f, 0f, 0f, 3f, 2f, 2f)
        head2.setRotationPoint(-1.5f, 3.9f, -11f)
        head2.setTextureSize(64, 64)
        head2.mirror = true
        setRotation(head2, 0f, 0f, 0f)

        nose.addBox(0f, 0f, 0f, 1f, 1f, 1f)
        nose.setRotationPoint(-0.5f, 3.8f, -11.5f)
        nose.setTextureSize(64, 64)
        nose.mirror = true
        setRotation(nose, 0f, 0f, 0f)

        corn.addBox(0f, 0f, 0f, 1f, 8f, 1f)
        corn.setRotationPoint(-3f, -4f, -6.5f)
        corn.setTextureSize(64, 64)
        corn.mirror = true
        setRotation(corn, 0f, 0.6320361f, -0.1289891f)

        corn2.addBox(0f, 0f, 0f, 1f, 8f, 1f)
        corn2.setRotationPoint(2f, -4f, -7.1f)
        corn2.setTextureSize(64, 64)
        corn2.mirror = true
        setRotation(corn2, 0f, -0.642054f, 0.1289973f)

        tail.addBox(0f, -0.5f, -1f, 1f, 2f, 2f)
        tail.setRotationPoint(0f, 11f, 7f)
        tail.setTextureSize(64, 64)
        tail.mirror = true
        setRotation(tail, 0f, 1.570796f, 3.149738f)

        corn3.addBox(0f, 0f, 0f, 3f, 1f, 1f)
        corn3.setRotationPoint(-4.9f, -3f, -5.7f)
        corn3.setTextureSize(64, 64)
        corn3.mirror = true
        setRotation(corn3, 0f, 0.4014257f, 0.3141593f)

        corn4.addBox(0f, 0f, 0f, 3f, 1f, 1f)
        corn4.setRotationPoint(1.8f, -2f, -7f)
        corn4.setTextureSize(64, 64)
        corn4.mirror = true
        setRotation(corn4, 0f, -0.4014257f, -0.2792527f)

        foot1p1.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot1p1.setRotationPoint(-0.5f, 8f, -0.3f)
        foot1p1.setTextureSize(64, 64)
        foot1p1.mirror = true
        setRotation(foot1p1, 0f, 0.1745329f, 0f)

        foot1p2.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot1p2.setRotationPoint(1.5f, 8f, -0.5f)
        foot1p2.setTextureSize(64, 64)
        foot1p2.mirror = true
        setRotation(foot1p2, 0f, -0.1745329f, 0f)

        foot1p3.addBox(0f, 0f, 0f, 2f, 2f, 1f)
        foot1p3.setRotationPoint(0f, 8f, 1.6f)
        foot1p3.setTextureSize(64, 64)
        foot1p3.mirror = true
        setRotation(foot1p3, 0f, 0f, 0f)

        foot2p1.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot2p1.setRotationPoint(1.5f, 8f, -0.5f)
        foot2p1.setTextureSize(64, 64)
        foot2p1.mirror = true
        setRotation(foot2p1, 0f, -0.1745329f, 0f)

        foot2p2.addBox(0f, 0f, 0f, 2f, 2f, 1f)
        foot2p2.setRotationPoint(0f, 8f, 1.6f)
        foot2p2.setTextureSize(64, 64)
        foot2p2.mirror = true
        setRotation(foot2p2, 0f, 0f, 0f)

        foot3p1.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot3p1.setRotationPoint(1.5f, 8f, -0.5f)
        foot3p1.setTextureSize(64, 64)
        foot3p1.mirror = true
        setRotation(foot3p1, 0f, -0.1745329f, 0f)

        foot2p3.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot2p3.setRotationPoint(-0.5f, 8f, -0.3f)
        foot2p3.setTextureSize(64, 64)
        foot2p3.mirror = true
        setRotation(foot2p3, 0f, 0.1745329f, 0f)

        foot3p2.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot3p2.setRotationPoint(-0.5f, 8f, -0.3f)
        foot3p2.setTextureSize(64, 64)
        foot3p2.mirror = true
        setRotation(foot3p2, 0f, 0.1745329f, 0f)

        foot3p3.addBox(0f, 0f, 0f, 2f, 2f, 1f)
        foot3p3.setRotationPoint(0f, 8f, 1.6f)
        foot3p3.setTextureSize(64, 64)
        foot3p3.mirror = true
        setRotation(foot3p3, 0f, 0f, 0f)

        foot4p1.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot4p1.setRotationPoint(1.5f, 8f, -0.5f)
        foot4p1.setTextureSize(64, 64)
        foot4p1.mirror = true
        setRotation(foot4p1, 0f, -0.1745329f, 0f)

        foot4p2.addBox(0f, 0f, 0f, 1f, 2f, 3f)
        foot4p2.setRotationPoint(-0.5f, 8f, -0.3f)
        foot4p2.setTextureSize(64, 64)
        foot4p2.mirror = true
        setRotation(foot4p2, 0f, 0.1745329f, 0f)

        foot4p3.addBox(0f, 0f, 0f, 2f, 2f, 1f)
        foot4p3.setRotationPoint(0f, 8f, 1.6f)
        foot4p3.setTextureSize(64, 64)
        foot4p3.mirror = true
        setRotation(foot4p3, 0f, 0f, 0f)

        ear1.addBox(0f, 0f, 0f, 1f, 2f, 1f)
        ear1.setRotationPoint(-2.6f, 1.1f, -5.5f)
        ear1.setTextureSize(64, 64)
        ear1.mirror = true
        setRotation(ear1, -0.2602503f, 0f, 0f)

        ear2.addBox(0f, 0f, 0f, 1f, 2f, 1f)
        ear2.setRotationPoint(1.6f, 1.1f, -5.5f)
        ear2.setTextureSize(64, 64)
        ear2.mirror = true
        setRotation(ear2, -0.2602503f, 0f, 0f)

        leg1.addBox(0f, 0f, 0f, 2f, 10f, 2f)
        leg1.setRotationPoint(1.5f, 14f, -7.5f)
        leg1.setTextureSize(64, 64)
        leg1.mirror = true
        leg1.addChild(foot1p1)
        leg1.addChild(foot1p2)
        leg1.addChild(foot1p3)
        setRotation(leg1, 0f, 0f, 0f)

        leg2.addBox(0f, 0f, 0f, 2f, 10f, 2f)
        leg2.setRotationPoint(-3.5f, 14f, -7.5f)
        leg2.setTextureSize(64, 64)
        leg2.mirror = true
        leg2.addChild(foot2p1)
        leg2.addChild(foot2p2)
        leg2.addChild(foot2p3)
        setRotation(leg2, 0f, 0f, 0f)

        leg3.addBox(0f, 0f, 0f, 2f, 10f, 2f)
        leg3.setRotationPoint(1.5f, 14f, 4.5f)
        leg3.setTextureSize(64, 64)
        leg3.mirror = true
        leg3.addChild(foot3p1)
        leg3.addChild(foot3p2)
        leg3.addChild(foot3p3)
        setRotation(leg3, 0f, 0f, 0f)

        leg4.addBox(0f, 0f, 0f, 2f, 10f, 2f)
        leg4.setRotationPoint(-3.5f, 14f, 4.5f)
        leg4.setTextureSize(64, 64)
        leg4.mirror = true
        leg4.addChild(foot4p1)
        leg4.addChild(foot4p2)
        leg4.addChild(foot4p3)
        setRotation(leg4, 0f, 0f, 0f)

        corn5.addBox(0f, 0f, 0f, 1f, 1f, 1f)
        corn5.setRotationPoint(-2f, 1.5f, -7f)
        corn5.setTextureSize(64, 64)
        corn5.mirror = true
        setRotation(corn5, 0f, 0f, 0f)

        corn6.addBox(0f, 0f, 0f, 1f, 1f, 1f)
        corn6.setRotationPoint(1f, 1.5f, -7f)
        corn6.setTextureSize(64, 64)
        corn6.mirror = true
        setRotation(corn6, 0f, 0f, 0f)

        bodyFemale.addBox(0f, 0f, 0f, 8f, 5f, 15f)
        bodyFemale.setRotationPoint(-4f, 10f, -8f)
        bodyFemale.setTextureSize(64, 64)
        bodyFemale.mirror = true
        setRotation(bodyFemale, 0f, 0f, 0f)
    }

    /*private inline fun shouldRenderChild(scale: Float, childSizePt: Float, crossinline renderer: () -> Unit) {
        if (isChild) {
            GL11.glPushMatrix()
            GL11.glScalef(childSizePt, childSizePt, childSizePt)
            GL11.glTranslatef(0.0f, 24.0f * scale, 0.0f)
        }
        renderer()
        if (isChild) GL11.glPopMatrix()
    }*/

    override fun setLivingAnimations(entity: E, limbSwing: Float, limbSwingAmount: Float, partialTick: Float) {
        super.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTick)
    }

    override fun render(stack: MatrixStack, vertexBuilder: IVertexBuilder, limbSwing: Int, limbSwingAmount: Int, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
        if (isChild) {
            stack.scale(0.5F, 0.5F, 0.5F)
            stack.translate(0.0, 1.5, 0.0)
        }
        val isMale = entity?.isMale ?: false
        fun ModelRenderer.render() = this.render(stack, vertexBuilder, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        //super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        //setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
        neck.render()
        head.render()
        head2.render()
        nose.render()
        ear1.render()
        ear2.render()
        if (isMale) {
            bodyMale.render()
            if (!isChild) {
                corn.render()
                corn2.render()
                corn3.render()
                corn4.render()
            }
        }
        else {
            bodyFemale.render()
            if (!isChild) {
                corn5.render()
                corn6.render()
            }
        }
        tail.render()
        leg1.render()
        leg2.render()
        leg3.render()
        leg4.render()
    }

    private fun setRotation(model: ModelRenderer, x: Float, y: Float, z: Float) {
        model.rotateAngleX = x
        model.rotateAngleY = y
        model.rotateAngleZ = z
    }

    override fun setRotationAngles(entityIn: E, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float) {
        //super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch)
        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 1.0f * limbSwingAmount
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 1.0f * limbSwingAmount
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.7f) * 1.0f * limbSwingAmount
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 1.0f * limbSwingAmount
        this.tail.rotateAngleX = MathHelper.cos(limbSwing * 0.7f + Math.PI.toFloat()) * 1.0f * limbSwingAmount
    }
}