package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.*
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

class EntitySaberToothedCat(world: World) : TameableEntity(Glacia.Entity.SABER_TOOTHED_CAT, world) {

    init {
        this.sitGoal = SitGoal(this)
        this.goalSelector.addGoal(1, SwimGoal(this))
        this.goalSelector.addGoal(2, this.sitGoal)
        //this.goalSelector.addGoal(3, WolfEntity.AvoidEntityGoal(this, LlamaEntity::class.java, 24.0f, 1.5, 1.5))
        this.goalSelector.addGoal(4, LeapAtTargetGoal(this, 0.4f))
        this.goalSelector.addGoal(5, MeleeAttackGoal(this, 1.0, true))
        this.goalSelector.addGoal(6, FollowOwnerGoal(this, 1.0, 10.0f, 2.0f))
        this.goalSelector.addGoal(7, BreedGoal(this, 1.0))
        this.goalSelector.addGoal(8, WaterAvoidingRandomWalkingGoal(this, 1.0))
        //this.goalSelector.addGoal(9, BegGoal(this, 8.0f))
        this.goalSelector.addGoal(10, LookAtGoal(this, PlayerEntity::class.java, 8.0f))
        this.goalSelector.addGoal(10, LookRandomlyGoal(this))
        this.targetSelector.addGoal(1, OwnerHurtByTargetGoal(this))
        this.targetSelector.addGoal(2, OwnerHurtTargetGoal(this))
        this.targetSelector.addGoal(3, HurtByTargetGoal(this).setCallsForHelp())
        //this.targetSelector.addGoal(4, NonTamedTargetGoal(this, AnimalEntity::class.java, false) {it === EntityType.SHEEP || it === EntityType.RABBIT})
        //this.targetSelector.addGoal(4, NonTamedTargetGoal(this, TurtleEntity::class.java, false, TurtleEntity.TARGET_DRY_BABY))
        //this.targetSelector.addGoal(5, NearestAttackableTargetGoal(this, AbstractSkeletonEntity::class.java, false))
    }

    override fun createChild(ageable: AgeableEntity) = EntitySaberToothedCat(world)
    override fun getVerticalFaceSpeed() = if (this.isSitting) 20 else super.getVerticalFaceSpeed()

    override fun registerAttributes() {
        super.registerAttributes()
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.5
        if (this.isTamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 30.0
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 15.0
        }

        this.attributes.registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue = 2.0
    }

    /*@OnlyIn(Dist.CLIENT)
    fun getInterestedAngle(tick: Float): Float {
        return MathHelper.lerp(tick, this.headRotationCourseOld, this.headRotationCourse) * 0.15f * Math.PI.toFloat()
    }*/
}