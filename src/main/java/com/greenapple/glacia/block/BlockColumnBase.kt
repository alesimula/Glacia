package com.greenapple.glacia.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.DyeColor
import net.minecraft.state.EnumProperty
import net.minecraft.state.StateContainer
import net.minecraft.util.Direction
import net.minecraft.util.IStringSerializable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.util.math.shapes.VoxelShapes
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorld
import net.minecraft.world.IWorldReader

open class BlockColumnBase : BlockBase, IBlockNamed {

    constructor(registryName: String, name: String, material: Material, materialColor: MaterialColor =material.color, initializer: (Properties.()->Unit)?=null) : super(registryName, name, material, materialColor, initializer.init)
    constructor(registryName: String, name: String, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : super(registryName, name, material, dyeColor, initializer.init)
    constructor(registryName: String, name: String, material: Material, initializer: (Properties.()->Unit)?=null) : super(registryName, name, material, initializer.init)

    init {
        defaultState = stateContainer.baseState.with(PLACEMENT, Placement.SINGLE)
    }

    enum class Placement : IStringSerializable {
        SINGLE, TOP, BOTTOM, CENTER;
        val lowercaseName = name.toLowerCase()
        override fun getName() = lowercaseName
    }

    companion object {
        val PLACEMENT = EnumProperty.create("placement", Placement::class.java)
        val (Properties.()->Unit)?.init : Properties.() -> Unit; get() = {
            tickRandomly()
            this@init?.invoke(this)
        }

        private val BODY_TOP = makeCuboidShape(3.5, 0.0, 3.5, 12.5, 12.0, 12.5)
        private val BODY_BOTTOM = makeCuboidShape(3.5, 4.0, 3.5, 12.5, 16.0, 12.5)
        private val BODY_SINGLE = makeCuboidShape(3.5, 4.0, 3.5, 12.5, 12.0, 12.5)
        private val BASE = makeCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0)
        private val TOP = makeCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 16.0)

        private val SHAPE_CENTER = makeCuboidShape(3.5, 0.0, 3.5, 12.5, 16.0, 12.5)
        private val SHAPE_SINGLE = VoxelShapes.or(BODY_SINGLE, BASE, TOP)
        private val SHAPE_TOP = VoxelShapes.or(BODY_TOP, TOP)
        private val SHAPE_BOTTOM = VoxelShapes.or(BODY_BOTTOM, BASE)
    }

    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext?) = when(state.get(PLACEMENT)) {
        Placement.SINGLE -> SHAPE_SINGLE
        Placement.TOP -> SHAPE_TOP
        Placement.BOTTOM -> SHAPE_BOTTOM
        else -> SHAPE_CENTER
    }

    override fun isValidPosition(state: BlockState, world: IWorldReader, pos: BlockPos): Boolean {
        val blockpos = pos.down()
        val blockstate = world.getBlockState(blockpos)
        /** See DoorBlock.isValidPosition **/
        return blockstate.func_224755_d(world, blockpos, Direction.UP)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        return if (context.world.getBlockState(context.pos.down()).block !== this) defaultState else defaultState.with(PLACEMENT, Placement.TOP)
    }

    override fun updatePostPlacement(state: BlockState, facing: Direction, facingState: BlockState, world: IWorld, currentPos: BlockPos, facingPos: BlockPos): BlockState {
        val lowerBlock = world.getBlockState(currentPos.down()).block
        if (lowerBlock === Blocks.AIR) return world.destroyBlock(currentPos, true).let {Blocks.AIR.defaultState}
        val upperBlock = world.getBlockState(currentPos.up()).block

        if (lowerBlock === this && upperBlock === this) world.setBlockState(currentPos, state.with(PLACEMENT, Placement.CENTER), 16)
        else if (lowerBlock === this) world.setBlockState(currentPos, state.with(PLACEMENT, Placement.TOP), 16)
        else if (upperBlock === this) world.setBlockState(currentPos, state.with(PLACEMENT, Placement.BOTTOM), 16)
        else world.setBlockState(currentPos, state.with(PLACEMENT, Placement.SINGLE), 16)

        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(PLACEMENT)
    }
}