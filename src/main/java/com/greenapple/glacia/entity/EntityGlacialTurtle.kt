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
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class EntityGlacialTurtle(type: EntityType<CowEntity>, world: World) : AnimalEntity(type, world) {

    companion object {
        val LivingEntity.isValidTarget get() = when {
            (this is PlayerEntity) -> runCatching{armorInventoryList.forEachIndexed {index, itemStack -> itemStack.apply {when(index) {
                0 -> if (item !== Glacia.Items.TURTLE_PAWS) throw Exception()
                1 -> if (item !== Glacia.Items.TURTLE_LIMBS) throw Exception()
                2 -> if (item !== Glacia.Items.TURTLE_SHELL) throw Exception()
                3 -> if (item !== Glacia.Items.TURTLE_HEAD) throw Exception()
            }}}}.getOrNull()?.run {false} ?: true
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

    override fun isBreedingItem(stack: ItemStack) = false
    override fun createChild(p_90011_1_: AgeableEntity) = type.create(world) as? AgeableEntity
}