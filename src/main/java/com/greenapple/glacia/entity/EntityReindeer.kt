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
import net.minecraft.item.crafting.Ingredient
import net.minecraft.nbt.CompoundNBT
import net.minecraft.world.World

class EntityReindeer(type: EntityType<CowEntity>, world: World) : AnimalEntity(type, world) {

    override fun isBreedingItem(stack: ItemStack) = stack.item === Glacia.Items.GLACIAL_BERRY

    override fun registerGoals() {
        this.goalSelector.addGoal(0, SwimGoal(this))
        this.goalSelector.addGoal(1, PanicGoal(this, 2.0))
        this.goalSelector.addGoal(2, BreedGoal(this, 1.0))
        this.goalSelector.addGoal(3, TemptGoal(this, 1.25, Ingredient.fromItems(Glacia.Items.GLACIAL_BERRY), false))
        this.goalSelector.addGoal(4, FollowParentGoal(this, 1.25))
        this.goalSelector.addGoal(5, WaterAvoidingRandomWalkingGoal(this, 1.0))
        this.goalSelector.addGoal(6, LookAtGoal(this, PlayerEntity::class.java, 6.0f))
        this.goalSelector.addGoal(7, LookRandomlyGoal(this))
    }

    override fun registerData() {
        super.registerData()
    }

    override fun registerAttributes() {
        super.registerAttributes()
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 10.0
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.20000000298023224
    }

    override fun createChild(p_90011_1_: AgeableEntity) = type.create(world) as? EntityReindeer

    override fun writeAdditional(compound: CompoundNBT) {
        super.writeAdditional(compound)
        compound.putBoolean("is_male", isMale)
    }

    override fun readAdditional(compound: CompoundNBT) {
        super.readAdditional(compound)
        if (compound.contains("is_male")) isMale = compound.getBoolean("is_male")
    }

    override fun canMateWith(other: AnimalEntity) = runCatching {isMale xor (other as EntityReindeer).isMale}.getOrElse {false}
}