package com.greenapple.glacia.event

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockFireBase
import net.minecraft.block.Block
import net.minecraft.util.ActionResultType
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraft.entity.Entity
import net.minecraft.util.DamageSource
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.eventbus.api.EventPriority

class PlayerEvents {
    /**
     * Code similar to World.extinguishFire
     */
    @SubscribeEvent
    fun onPlayerLeftClickBlock(event: PlayerInteractEvent.LeftClickBlock) = event.apply {
        val blockPos = face?.run {pos.offset(this)} ?: pos
        if (world.getBlockState(blockPos).block is BlockFireBase) {
            world.playEvent(player, 1009, blockPos, 0)
            world.removeBlock(blockPos, false)
            isCanceled = true
            cancellationResult = ActionResultType.FAIL
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    fun onPlayerTickEvent(event: PlayerTickEvent) = event.player.takeIf {event.phase === TickEvent.Phase.START && it.world.isRemote}?.apply {
        if (isInFluid(Glacia.Blocks.PLASMA)) attackEntityFrom(DamageSource.LAVA, 1.0F)
    }

    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    fun onWorldTickEvent(event: TickEvent.WorldTickEvent) = (event.world as? ServerWorld)?.takeIf {event.phase !== TickEvent.Phase.END}?.entities?.forEach {entity ->
        if (entity?.isInFluid(Glacia.Blocks.PLASMA) == true) entity.attackEntityFrom(DamageSource.LAVA, 1.0F)
    }

    fun Entity.isInFluid(block: Block): Boolean {
        val axisAlignedBB = boundingBox.shrink(0.001)
        val i = MathHelper.floor(axisAlignedBB.minX)
        val j = MathHelper.ceil(axisAlignedBB.maxX)
        val k = MathHelper.floor(axisAlignedBB.minY)
        val l = MathHelper.ceil(axisAlignedBB.maxY)
        val i1 = MathHelper.floor(axisAlignedBB.minZ)
        val j1 = MathHelper.ceil(axisAlignedBB.maxZ)

        if (world.isAreaLoaded(i, k, i1, j, l, j1)) {
            BlockPos.PooledMutableBlockPos.retain().use {mutableBlockPos ->
                for (l1 in i until j) {
                    for (i2 in k until l) {
                        for (j2 in i1 until j1) {
                            mutableBlockPos.setPos(l1, i2, j2)
                            val fluidState = this.world.getFluidState(mutableBlockPos)
                            if (fluidState.blockState.block === block) {
                                val d1 = (i2.toFloat() + fluidState.func_215679_a(this.world, mutableBlockPos)).toDouble()
                                if (d1 >= axisAlignedBB.minY) {
                                    return true
                                }
                            }
                        }
                    }
                }
            }
        }
        return false
    }
}