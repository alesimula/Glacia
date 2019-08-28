package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.monster.CreeperEntity
import net.minecraft.entity.monster.GhastEntity
import net.minecraft.entity.passive.*
import net.minecraft.entity.passive.horse.AbstractHorseEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.AbstractArrowEntity
import net.minecraft.item.*
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.DamageSource
import net.minecraft.util.Hand
import net.minecraft.world.World
import kotlin.experimental.and
import kotlin.experimental.or

class EntitySaberToothedCat(val entityType: EntityType<TameableEntity>, world: World) : TameableEntity(entityType, world) {

    init {
        isTamed = false
    }

    private val PlayerEntity.isHoldingFood; get() = canEat(heldItemMainhand.item) || canEat(heldItemOffhand.item)

    override fun registerGoals() {
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
        this.goalSelector.addGoal(11, LookRandomlyGoal(this))
        this.targetSelector.addGoal(1, OwnerHurtByTargetGoal(this))
        this.targetSelector.addGoal(2, OwnerHurtTargetGoal(this))
        this.targetSelector.addGoal(3, HurtByTargetGoal(this).setCallsForHelp())
        this.targetSelector.addGoal(4, NonTamedTargetGoal(this, PlayerEntity::class.java, true) {(it as? PlayerEntity)?.isHoldingFood == false})
        this.targetSelector.addGoal(4, NonTamedTargetGoal(this, AnimalEntity::class.java, true) {it.type === Glacia.Entity.REINDEER})
        //this.targetSelector.addGoal(4, NonTamedTargetGoal(this, AnimalEntity::class.java, false) {it === EntityType.SHEEP || it === EntityType.RABBIT})
        //this.targetSelector.addGoal(4, NonTamedTargetGoal(this, TurtleEntity::class.java, false, TurtleEntity.TARGET_DRY_BABY))
        //this.targetSelector.addGoal(5, NearestAttackableTargetGoal(this, AbstractSkeletonEntity::class.java, false))
    }

    companion object {
        val COLLAR_COLOR by lazy {EntityDataManager.createKey(WolfEntity::class.java, DataSerializers.VARINT)}
    }

    override fun createChild(ageable: AgeableEntity) = EntitySaberToothedCat(entityType, world)
    override fun getVerticalFaceSpeed() = if (this.isSitting) 20 else super.getVerticalFaceSpeed()
    override fun registerData() {
        super.registerData()
        this.dataManager.register(COLLAR_COLOR, DyeColor.RED.id)
    }

    private fun canEat(item: Item) : Boolean = item.food?.isMeat ?: false && item !== Glacia.Items.SABER_TOOTHED_CAT_MEAT && item !== Glacia.Items.SABER_TOOTHED_CAT_MEAT_COOKED

    var collarColor: DyeColor
        get() = DyeColor.byId(dataManager.get(COLLAR_COLOR))
        set(color) = dataManager.set(COLLAR_COLOR, color.id)

    //TODO breeding
    override fun isBreedingItem(itemStack: ItemStack): Boolean {
        return false
    }

    /**
     * Determines whether this wolf is angry or not.
     */
    fun isAngry(): Boolean {
        return this.dataManager.get(TAMED).toInt() and 2 != 0
    }

    /**
     * Sets whether this entity is angry or not.
     */
    fun setAngry(angry: Boolean) {
        val tamed = this.dataManager.get(TAMED)
        if (angry) {
            this.dataManager.set(TAMED, (tamed or 2))
        } else {
            this.dataManager.set(TAMED, (tamed and -3))
        }
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    override fun setAttackTarget(entitylivingbaseIn: LivingEntity?) {
        super.setAttackTarget(entitylivingbaseIn)
        if (entitylivingbaseIn == null) {
            this.setAngry(false)
        } else if (!this.isTamed) {
            this.setAngry(true)
        }
    }

    override fun attackEntityAsMob(entityIn: Entity): Boolean {
        val flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).value.toInt().toFloat())
        if (flag) {
            this.applyEnchantments(this, entityIn)
        }

        return flag
    }

    override fun processInteract(player: PlayerEntity, hand: Hand): Boolean {
        val itemStack = player.getHeldItem(hand)
        val item = itemStack.item
        if (isTamed) {
            if (!itemStack.isEmpty) {
                item.food?.apply {
                    if (canEat(item) && health < maxHealth) {
                        if (!player.abilities.isCreativeMode) itemStack.shrink(1)
                        heal(healing.toFloat())
                        return true
                    }
                } ?: (item as? DyeItem)?.apply {
                    if (dyeColor != collarColor) {
                        if (!player.abilities.isCreativeMode) itemStack.shrink(1)
                        collarColor = dyeColor
                        return true
                    }
                }
            }

            if (isOwner(player) && !world.isRemote && !isBreedingItem(itemStack)) {
                sitGoal.setSitting(!isSitting)
                isJumping = false
                navigator.clearPath()
                attackTarget = null
                return true
            }
            //TODO angry
        } else if (canEat(item) /*&& !isAngry()*/) {
            if (!player.abilities.isCreativeMode) itemStack.shrink(1)

            if (!world.isRemote) {
                if (rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    setTamedBy(player)
                    navigator.clearPath()
                    attackTarget = null
                    sitGoal.setSitting(true)
                    health = 20.0f
                    playTameEffect(true)
                    world.setEntityState(this, 7.toByte())
                } else {
                    playTameEffect(false)
                    world.setEntityState(this, 6.toByte())
                }
            }
            return true
        }

        return super.processInteract(player, hand)
    }

    override fun attackEntityFrom(source: DamageSource, amount: Float): Boolean {
        var newAmount = amount
        return if (isInvulnerableTo(source)) false
        else {
            val entity = source.trueSource
            sitGoal?.setSitting(false)

            if (entity != null && entity !is PlayerEntity && entity !is AbstractArrowEntity) {
                newAmount = (newAmount + 1.0f) / 2.0f
            }

            super.attackEntityFrom(source, newAmount)
        }
    }

    override fun shouldAttackEntity(target: LivingEntity, owner: LivingEntity): Boolean {
        if (target !is CreeperEntity && target !is GhastEntity) {
            if (target is TameableEntity) {
                if (target.isTamed && target.owner === owner) {
                    return false
                }
            }

            return if (target is PlayerEntity && owner is PlayerEntity && !owner.canAttackPlayer(target)) {
                false
            } else if (target is PlayerEntity && !isTamed) {
                !target.isHoldingFood
            }
            else if (target is AbstractHorseEntity && target.isTame) {
                false
            } else {
                target !is CatEntity || !target.isTamed
            }
        } else {
            return false
        }
    }

    override fun registerAttributes() {
        super.registerAttributes()
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.3
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