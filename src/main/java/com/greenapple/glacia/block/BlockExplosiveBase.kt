package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.ItemPickaxeBase
import net.minecraft.block.*
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemTier
import net.minecraft.item.PickaxeItem
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Explosion
import net.minecraft.world.World

class BlockExplosiveBase(registryName: String, unlocalizedName: String, droppedItem: Item, minPickaxeTier: ItemTier=ItemTier.WOOD, dropWithSilkTouch: Boolean=true, initializer: (Properties.()->Unit)?=null) : BlockOreBase(registryName, unlocalizedName, droppedItem, minPickaxeTier, dropWithSilkTouch, initializer) {

    override fun onBlockHarvested(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        super.onBlockHarvested(world, pos, state, player)
        val pickaxeItem = player.heldItemMainhand.item.run {if (requiresCustomPickaxe) this as? ItemPickaxeBase else this as? PickaxeItem}
        if (!player.isCreative && (pickaxeItem == null || pickaxeItem.tier.harvestLevel < minPickaxeTier.harvestLevel)) {
            if (player is ServerPlayerEntity) Glacia.Triggers.EXPLOSIVE_SALT.trigger(player)
            (world as? World ?: Minecraft.getInstance().world)?.createExplosion(null, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 4.0F, true, Explosion.Mode.BREAK)
        }
    }
}