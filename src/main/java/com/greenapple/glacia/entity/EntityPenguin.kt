package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.CowEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.DamageSource
import net.minecraft.world.World

class EntityPenguin(type: EntityType<CowEntity>, world: World) : AnimalEntity(type, world) {

    companion object {
        val BREEDING_ITEMS = Ingredient.fromItems(Items.COD, Items.SALMON)
    }

    override fun registerGoals() {
        this.goalSelector.addGoal(0, SwimGoal(this))
        this.goalSelector.addGoal(1, PanicGoal(this, 2.0))
        this.goalSelector.addGoal(2, BreedGoal(this, 1.0))
        this.goalSelector.addGoal(3, TemptGoal(this, 1.25, BREEDING_ITEMS, false))
        this.goalSelector.addGoal(4, FollowParentGoal(this, 1.25))
        this.goalSelector.addGoal(5, WaterAvoidingRandomWalkingGoal(this, 1.0))
        this.goalSelector.addGoal(6, LookAtGoal(this, PlayerEntity::class.java, 6.0f))
        this.goalSelector.addGoal(7, LookRandomlyGoal(this))
    }

    override fun spawnDrops(damageSource: DamageSource) {
        entityDropItem(ItemStack(Glacia.Items.PENGUIN_FEATHERS))
        super.spawnDrops(damageSource)
    }

    override fun registerAttributes() {
        super.registerAttributes()
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 10.0
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.20000000298023224
    }

    override fun isBreedingItem(stack: ItemStack) = BREEDING_ITEMS.test(stack)
    override fun createChild(p_90011_1_: AgeableEntity) = type.create(world) as? AgeableEntity
}