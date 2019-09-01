package com.greenapple.glacia.item

import com.greenapple.glacia.Glacia
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemUseContext
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld

class ItemGlacialLighter(registryName: String, unlocalizedName: String) : ItemBase(registryName, unlocalizedName, Glacia.ItemGroup.TOOLS, {maxDamage(64)}) {

    override fun onItemUse(context: ItemUseContext): ActionResultType {
        val player = context.player
        val world = context.world
        val pos = context.pos.offset(context.face)
        if (isValidFirePosition(world.getBlockState(pos), world, pos)) {
            val fireState = Glacia.Blocks.GLACIAL_FIRE.getStateForPlacement(world, pos)
            val itemStack = context.item
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, Item.random.nextFloat() * 0.4f + 0.8f)
            world.setBlockState(pos, fireState, 11)
            if (player is ServerPlayerEntity) {
                CriteriaTriggers.PLACED_BLOCK.trigger(player, pos, itemStack)
                itemStack.damageItem<PlayerEntity>(1, player) {it.sendBreakAnimation(context.hand)}
            }

            return ActionResultType.SUCCESS
        }
        return ActionResultType.FAIL
    }

    fun isValidFirePosition(state: BlockState, world: IWorld, pos: BlockPos): Boolean {
        val fireState = Glacia.Blocks.GLACIAL_FIRE.getStateForPlacement(world, pos)
        val isPortal = Direction.Plane.HORIZONTAL.fold(false) { prev, direction ->
            prev || (world.getBlockState(pos.offset(direction)).block === Glacia.Blocks.GLACIAL_MAGIC_STONE && Glacia.Blocks.GLACIA_PORTAL.isPortal(world, pos) != null)
        }

        return state.isAir && (fireState.isValidPosition(world, pos) || isPortal)
    }
}