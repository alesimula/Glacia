package com.greenapple.glacia.entity

import com.greenapple.glacia.Glacia
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.AgeableEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.CowEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.apache.logging.log4j.LogManager
import kotlin.random.Random

class EntityReindeer(type: EntityType<CowEntity>, world: World) : CowEntity(type, world) {
    override fun getAmbientSound() = null
    override fun getHurtSound(damageSource: DamageSource) = null
    override fun getDeathSound() = null
    override fun isBreedingItem(stack: ItemStack) = stack.item === Glacia.Items.GLACIAL_BERRY
    override fun registerData() {
        super.registerData()
        this.dataManager.register(IS_MALE, Random.nextBoolean())
    }

    override fun createChild(p_90011_1_: AgeableEntity) = type.create(world) as? EntityReindeer

    override fun writeAdditional(compound: CompoundNBT) {
        super.writeAdditional(compound)
        compound.putBoolean("is_male", isMale)
    }

    override fun readAdditional(compound: CompoundNBT) {
        super.readAdditional(compound)
        isMale = compound.getBoolean("is_male")
    }

    override fun playStepSound(pos: BlockPos, blockIn: BlockState) {
        if (!blockIn.material.isLiquid) {
            val blockstate = this.world.getBlockState(pos.up())
            val soundtype = if (blockstate.block === Blocks.SNOW) blockstate.soundType else blockIn.getSoundType(world, pos, this)
            this.playSound(soundtype.stepSound, soundtype.getVolume() * 0.15f, soundtype.getPitch())
        }
    }

    override fun canMateWith(other: AnimalEntity) = isMale xor other.isMale
}