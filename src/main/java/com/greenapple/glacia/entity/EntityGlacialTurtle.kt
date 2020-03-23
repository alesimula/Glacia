package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.CowEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.DamageSource
import net.minecraft.world.World
import kotlin.random.Random

class EntityGlacialTurtle(type: EntityType<CowEntity>, world: World) : AnimalEntity(type, world) {

    companion object {
        val LivingEntity.isValidTarget get() = when {
            (this is PlayerEntity) -> armorInventoryList.foldIndexed(false) {index, last, itemStack -> itemStack.run {when(index) {
                0 -> (item !== Glacia.Items.TURTLE_PAWS) || last
                1 -> (item !== Glacia.Items.TURTLE_LIMBS) || last
                2 -> (item !== Glacia.Items.TURTLE_SHELL) || last
                3 -> ((item !== Glacia.Items.TURTLE_HEAD) || last)
                else -> last
            }}}.also {isTarget-> if (!isTarget && this is ServerPlayerEntity) Glacia.Triggers.TURTLE_DISGUISE.trigger(this)}
            else -> false
        }
    }

    override fun registerGoals() {
        this.goalSelector.addGoal(0, SwimGoal(this))
        this.goalSelector.addGoal(1, LeapAtTargetGoal(this, 0.4f))
        this.goalSelector.addGoal(2, FollowParentGoal(this, 1.25))
        this.goalSelector.addGoal(3, MeleeAttackGoal(this, 1.0, true))
        this.goalSelector.addGoal(4, WaterAvoidingRandomWalkingGoal(this, 1.0))
        this.goalSelector.addGoal(5, LookAtGoal(this, PlayerEntity::class.java, 6.0f))
        this.goalSelector.addGoal(6, LookRandomlyGoal(this))
        this.targetSelector.addGoal(0, HurtByTargetGoal(this).setCallsForHelp())
        this.targetSelector.addGoal(1, NearestAttackableTargetGoal(this, PlayerEntity::class.java, 10, true, false) {it.isValidTarget})
    }

    override fun registerAttributes() {
        super.registerAttributes()
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 40.0
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.2
        this.attributes.registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue = 5.0
    }

    override fun spawnDrops(damageSource: DamageSource) {
        when(Random.nextInt(20)) {
            0 -> Glacia.Items.TURTLE_PAWS
            1 -> Glacia.Items.TURTLE_LIMBS
            2 -> Glacia.Items.TURTLE_SHELL
            3 -> Glacia.Items.TURTLE_HEAD
            else -> null
        }?.apply {entityDropItem(ItemStack(this))}
        super.spawnDrops(damageSource)
    }

    override fun isBreedingItem(stack: ItemStack) = false
    override fun createChild(p_90011_1_: AgeableEntity) = type.create(world) as? AgeableEntity
}