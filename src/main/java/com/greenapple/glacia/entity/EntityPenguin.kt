package com.greenapple.glacia.entity

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.passive.CowEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class EntityPenguin(type: EntityType<CowEntity>, world: World) : CowEntity(type, world) {

    companion object {
        val BREEDING_ITEMS = Ingredient.fromItems(Items.COD, Items.SALMON)
    }

    override fun getAmbientSound() = null
    override fun getHurtSound(damageSource: DamageSource) = null
    override fun getDeathSound() = null

    override fun isBreedingItem(stack: ItemStack) = BREEDING_ITEMS.test(stack)

    override fun playStepSound(pos: BlockPos, blockIn: BlockState) {
        if (!blockIn.material.isLiquid) {
            val blockstate = this.world.getBlockState(pos.up())
            val soundtype = if (blockstate.block === Blocks.SNOW) blockstate.soundType else blockIn.getSoundType(world, pos, this)
            this.playSound(soundtype.stepSound, soundtype.getVolume() * 0.15f, soundtype.getPitch())
        }
    }
}