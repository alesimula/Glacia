package com.greenapple.glacia.event

import com.greenapple.glacia.Glacia
import net.minecraft.block.Blocks
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class BlockEvents {
    @SubscribeEvent
    fun onNeighbourUpdate(event: BlockEvent.NeighborNotifyEvent) = event.run {
        if (state.block == Blocks.FIRE && world.dimension.type.id == Glacia.DIMENSION.type.id) {
            world.removeBlock(pos, false)
        }
    }
}