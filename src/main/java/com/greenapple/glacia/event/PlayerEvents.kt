package com.greenapple.glacia.event

import com.greenapple.glacia.block.BlockFireBase
import net.minecraft.util.ActionResultType
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent

class PlayerEvents {
    /**
     * Code similar to World.extinguishFire
     */
    @SubscribeEvent
    fun onRenderPlayerThirdPerson(event: PlayerInteractEvent.LeftClickBlock) = event.run {
        val blockPos = face?.run {pos.offset(this)} ?: pos
        if (world.getBlockState(blockPos).block is BlockFireBase) {
            world.playEvent(player, 1009, blockPos, 0)
            world.removeBlock(blockPos, false)
            isCanceled = true
            cancellationResult = ActionResultType.FAIL
        }
    }
}