package com.greenapple.glacia.event

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.overrideLightValue
import com.greenapple.glacia.utils.runClient
import net.minecraft.block.Blocks
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class WorldEvents {
    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) = event.runClient {
        if (world.dimension.type.id == Glacia.DIMENSION.dimensionType.id) {
            Blocks.TORCH.overrideLightValue(0)
            Blocks.WALL_TORCH.overrideLightValue(0)
        }
    }

    @SubscribeEvent
    fun onWorldUnload(event: WorldEvent.Unload) = event.runClient {
        if (world.dimension.type.id == Glacia.DIMENSION.dimensionType.id) {
            Blocks.TORCH.overrideLightValue()
            Blocks.WALL_TORCH.overrideLightValue()
        }
    }
}