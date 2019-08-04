package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.DyeColor
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.shapes.ISelectionContext
import net.minecraft.util.math.shapes.VoxelShape
import net.minecraft.world.IBlockReader
import net.minecraftforge.registries.IForgeRegistry

class BlockPlantFacingBase private constructor(registryName: String, override val unlocalizedName: String, override val itemGroup: ItemGroup?, properties: Properties, val width: Double=16.0, val height: Double=16.0, initializer: (Properties.()->Unit)?=null) : BushBlock(properties.apply {initializer?.invoke(this)}), IBlockBase {

    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, materialColor: MaterialColor =material.color, width: Double=16.0, height: Double=16.0, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, Properties.create(material, materialColor), width, height, initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, dyeColor: DyeColor, width: Double=16.0, height: Double=16.0, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, Properties.create(material, dyeColor), width, height, initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, width: Double=16.0, height: Double=16.0, initializer: (Properties.()->Unit)?=null) : this(registryName, name, itemGroup, material, material.color, width, height, initializer)

    init {
        setRegistryName(registryName)
        defaultState = defaultState.with(OVERWORLD, false)
    }

    companion object {
        val FACING = HorizontalBlock.HORIZONTAL_FACING
        val OVERWORLD = BooleanProperty.create("overworld")
    }

    override var blockItem: BlockItemBase?=null
    override var itemVariantProvider: (BlockItemBase.(IForgeRegistry<Item>) -> BlockItemBase)? = null

    private val shape = (width/2).let {halfDim -> Block.makeCuboidShape(8-halfDim, 0.0, 8-halfDim, 8+halfDim, height, 8+halfDim)}

    override fun getShape(state: BlockState, worldIn: IBlockReader, pos: BlockPos, context: ISelectionContext): VoxelShape {
        val vec3d = state.getOffset(worldIn, pos)
        return shape.withOffset(vec3d.x, vec3d.y, vec3d.z)
    }

    override fun rotate(state: BlockState, rot: Rotation): BlockState {
        return state.with(FACING, rot.rotate(state.get(FACING)))
    }

    override fun isValidGround(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean = super.isValidGround(state, worldIn, pos).let {isVanillaValid ->
        val block = state.block
        isVanillaValid || block === Glacia.Blocks.GLACIAL_DIRT
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(FACING, OVERWORLD)
    }

    /**
     * Cast no shadows
     */
    override fun func_220080_a(state: BlockState, world: IBlockReader, pos: BlockPos): Float = 1.0F

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        val notVanilla = (context.world.getBlockState(context.pos.down()).block === Glacia.Blocks.GLACIAL_DIRT)
        return this.defaultState.with(FACING, context.placementHorizontalFacing).with(OVERWORLD, !notVanilla)
    }
}