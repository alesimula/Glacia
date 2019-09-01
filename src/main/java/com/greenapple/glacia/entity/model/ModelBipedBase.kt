package com.greenapple.glacia.entity.model

import net.minecraft.client.renderer.entity.model.BipedModel
import net.minecraft.entity.MobEntity
import net.minecraft.util.math.MathHelper
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
open class ModelBipedBase<T : MobEntity>(modelSize: Float=0F, private val zombieArms: Boolean=true) : BipedModel<T>(modelSize) {

    override fun setRotationAngles(entityIn: T, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scaleFactor: Float) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor)
        if (zombieArms) {
            val flag = entityIn.isAggressive
            val f = MathHelper.sin(this.swingProgress * Math.PI.toFloat())
            val f1 = MathHelper.sin((1.0f - (1.0f - this.swingProgress) * (1.0f - this.swingProgress)) * Math.PI.toFloat())
            this.bipedRightArm.rotateAngleZ = 0.0f
            this.bipedLeftArm.rotateAngleZ = 0.0f
            this.bipedRightArm.rotateAngleY = -(0.1f - f * 0.6f)
            this.bipedLeftArm.rotateAngleY = 0.1f - f * 0.6f
            val f2 = -Math.PI.toFloat() / if (flag) 1.5f else 2.25f
            this.bipedRightArm.rotateAngleX = f2
            this.bipedLeftArm.rotateAngleX = f2
            this.bipedRightArm.rotateAngleX += f * 1.2f - f1 * 0.4f
            this.bipedLeftArm.rotateAngleX += f * 1.2f - f1 * 0.4f
            this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f
            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09f) * 0.05f + 0.05f
            this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067f) * 0.05f
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067f) * 0.05f
        }
    }
}