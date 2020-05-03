package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.item.BlockItemBase
import com.greenapple.glacia.registry.renderType
import com.greenapple.glacia.utils.RenderTypeBase
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.item.DyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.particles.ParticleTypes
import net.minecraft.state.EnumProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.storage.loot.LootContext
import net.minecraftforge.registries.IForgeRegistry
import java.util.*

private val COLOR = EnumProperty.create("color", DyeColor::class.java)

class BlockTorchBase(registryName: String, private val torchItemProvider: () -> Item) : TorchBlock(Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0F).lightValue(14).sound(SoundType.WOOD)), IBlockBase {

    init {
        setRegistryName(registryName)
        renderType = RenderTypeBase.CUTOUT
    }

    override val unlocalizedName: String = registryName
    override val itemGroup: ItemGroup? = null
    override var blockItem: BlockItemBase? = null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    private val drops by lazy {mutableListOf(ItemStack {torchItemProvider()})}
    override fun getDrops(state: BlockState, builder: LootContext.Builder) = drops
    override fun animateTick(state: BlockState, world: World, pos: BlockPos, rand: Random?) = flameParticle(state, world, pos)
}

class BlockWallTorchBase(registryName: String, private val torchItemProvider: () -> Item) : WallTorchBlock(Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0F).lightValue(14).sound(SoundType.WOOD)), IBlockBase {

    init {
        setRegistryName(registryName)
        renderType = RenderTypeBase.CUTOUT
    }

    override val unlocalizedName: String = registryName
    override val itemGroup: ItemGroup? = null
    override var blockItem: BlockItemBase ?= null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    private val drops by lazy {mutableListOf(ItemStack {torchItemProvider()})}
    override fun getDrops(state: BlockState, builder: LootContext.Builder) = drops
    override fun animateTick(state: BlockState, world: World, pos: BlockPos, rand: Random?) = flameParticle(state, world, pos, true)
}

private fun flameParticle(state: BlockState, world: World, pos: BlockPos, onWall: Boolean = false) {
    val direction = if (onWall) state.get(WallTorchBlock.HORIZONTAL_FACING).opposite else null
    val x = pos.x.toDouble() + 0.5 + (direction?.xOffset?.toDouble() ?: 0.0) * 0.27
    val y = pos.y.toDouble() + 0.7 + if (onWall) 0.22 else 0.0
    val z = pos.z.toDouble() + 0.5 + (direction?.zOffset?.toDouble() ?: 0.0) * 0.27
    world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.0, 0.0)
    world.addParticle(Glacia.Particles.PLASMA_FLAME, x, y, z, 0.0, 0.0, 0.0)
}