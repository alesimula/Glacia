package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.entity.EntityType
import net.minecraft.entity.monster.ZombieEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.util.DamageSource
import net.minecraft.world.World
import kotlin.random.Random

class EntityGlacialSeeker(type: EntityType<ZombieEntity>, world: World) : ZombieEntity(type, world) {

    companion object {
        private var EntityGlacialSeeker.isVariantMysterious by entityData(DataSerializers.BOOLEAN, {Random.nextInt(10)==0}) {value ->
            (armorInventoryList as MutableList)[2] = if (value) ItemStack(Glacia.Items.MYSTERIOUS_CHESTPLATE) else ItemStack.EMPTY
        }
    }

    init {
        isVariantMysterious
    }

    override fun getAmbientSound() = null
    override fun getHurtSound(damageSourceIn: DamageSource) = null
    override fun getDeathSound() = null

    override fun writeAdditional(compound: CompoundNBT) {
        super.writeAdditional(compound)
        compound.putBoolean("variant_mysterious", isVariantMysterious)
    }

    override fun readAdditional(compound: CompoundNBT) {
        super.readAdditional(compound)
        isVariantMysterious = compound.getBoolean("variant_mysterious")
    }

    override fun shouldBurnInDay() = false

    override fun spawnDrops(damageSource: DamageSource) {
        val itemstack = if (isVariantMysterious && Random.nextInt(5) == 0) ItemStack(Glacia.Items.MYSTERIUS_ARMOR_SHARD) else ItemStack.EMPTY
        if (!itemstack.isEmpty) entityDropItem(itemstack)

        super.spawnDrops(damageSource)
    }
}