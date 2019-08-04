package com.greenapple.glacia.block

import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.state.StateContainer
import net.minecraft.util.Direction
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.IBlockReader
import net.minecraftforge.registries.IForgeRegistry

class BlockAnvilTest(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, material: Material) : FallingBlock(Block.Properties.create(material).apply {}), IBlockBase {

    companion object {
        val FACING = HorizontalBlock.HORIZONTAL_FACING
        private val PART_BASE = Block.makeCuboidShape(2.0, 0.0, 2.0, 14.0, 4.0, 14.0)
        private val PART_LOWER_X = Block.makeCuboidShape(3.0, 4.0, 4.0, 13.0, 5.0, 12.0)
        private val PART_MID_X = Block.makeCuboidShape(4.0, 5.0, 6.0, 12.0, 10.0, 10.0)
        private val PART_UPPER_X = Block.makeCuboidShape(0.0, 10.0, 3.0, 16.0, 16.0, 13.0)
        private val PART_LOWER_Z = Block.makeCuboidShape(4.0, 4.0, 3.0, 12.0, 5.0, 13.0)
        private val PART_MID_Z = Block.makeCuboidShape(6.0, 5.0, 4.0, 10.0, 10.0, 12.0)
        private val PART_UPPER_Z = Block.makeCuboidShape(3.0, 10.0, 0.0, 13.0, 16.0, 16.0)
        private val X_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_X, PART_MID_X, PART_UPPER_X)
        private val Z_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_Z, PART_MID_Z, PART_UPPER_Z)
        private val field_220273_k = TranslationTextComponent("container.repair")
    }

    init {
        setRegistryName(registryName)
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    init {
        this.defaultState = this.stateContainer.baseState.with(FACING, Direction.NORTH)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.with(FACING, rot.rotate(state.get(FACING)))
    }

    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext?): VoxelShape {
        val direction = state.get(FACING)
        return if (direction.axis === Direction.Axis.X) X_AXIS_AABB else Z_AXIS_AABB
    }

    override fun getRenderShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos): VoxelShape {
        return this.getShape(state, worldIn, pos, null)
    }
}