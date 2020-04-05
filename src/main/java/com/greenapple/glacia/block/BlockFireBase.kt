package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.BlockItemBase
import com.greenapple.glacia.registry.renderType
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.client.renderer.RenderType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry

/**
 * Don't forget to add the block to the 'fences' tag group
 */
class BlockFireBase(registryName: String, override val unlocalizedName: String) : FireBlock(Properties.create(Material.FIRE, MaterialColor.TNT).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).lightValue(15).sound(SoundType.CLOTH).noDrops()), IBlockBase {

    init {
        setRegistryName(registryName)
        renderType = RenderType.getCutout()
    }

    override val itemGroup = null
    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    override fun getDrops(state: BlockState, builder: LootContext.Builder): MutableList<ItemStack>? = arrayListOf()
    override fun isBurning(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = true


    /**
     * Custom portal spawning behaviour
     */
    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        if (oldState.block !== state.block) {
            if (world.dimension.type !== DimensionType.OVERWORLD && world.dimension.type !== Glacia.DIMENSION.dimensionType || !Glacia.Blocks.GLACIA_PORTAL.trySpawnPortal(world, pos)) {
                if (!state.isValidPosition(world, pos)) {
                    world.removeBlock(pos, false)
                } else {
                    world.pendingBlockTicks.scheduleTick(pos, this, this.tickRate(world) + world.rand.nextInt(10))
                }
            }
        }
    }
}