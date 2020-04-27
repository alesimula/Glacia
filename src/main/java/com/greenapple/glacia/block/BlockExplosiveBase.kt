package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.ItemPickaxeBase
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemTier
import net.minecraft.item.PickaxeItem
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Explosion
import net.minecraft.world.World
import kotlin.random.Random

class BlockExplosiveBase(registryName: String, unlocalizedName: String, droppedItem: Item, minPickaxeTier: ItemTier=ItemTier.WOOD, dropWithSilkTouch: Boolean=true, initializer: (Properties.()->Unit)?=null) : BlockOreBase(registryName, unlocalizedName, droppedItem, minPickaxeTier, dropWithSilkTouch, initializer) {

    override fun onBlockHarvested(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        super.onBlockHarvested(world, pos, state, player)
        val pickaxeItem = player.heldItemMainhand.item.run {if (requiresCustomPickaxe) this as? ItemPickaxeBase else this as? PickaxeItem}
        if (!player.isCreative && (pickaxeItem == null || pickaxeItem.tier.harvestLevel < minPickaxeTier.harvestLevel)) {
            if (player is ServerPlayerEntity) Glacia.Triggers.EXPLOSIVE_SALT.trigger(player)
            (world as? World ?: Minecraft.getInstance().world)?.createPlasmaExplosion(null, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 4.0F, Explosion.Mode.BREAK)
        }
    }
}

fun World.createPlasmaExplosion(entity: Entity?, x: Double, y: Double, z: Double, radius: Float, mode: Explosion.Mode) = createExplosion(entity, x, y, z, radius, false, mode).apply {
    for (pos in this.affectedBlockPositions) if (Random.nextInt(3) == 0 && getBlockState(pos).isAir && getBlockState(pos.down()).isOpaqueCube(this@createPlasmaExplosion, pos.down()))
        setBlockState(pos, Glacia.Blocks.GLACIAL_FIRE.defaultState)
}