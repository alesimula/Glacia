package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.plus
import com.greenapple.glacia.utils.rem
import net.minecraft.block.BlockState
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.fluid.*
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.state.StateContainer
import net.minecraftforge.fluids.FluidAttributes
import net.minecraft.fluid.IFluidState
import net.minecraft.item.BucketItem
import net.minecraft.particles.ParticleTypes
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*


//TODO add plasma once the fluid system has been properly implemented
object Glacia_Fluids : IForgeRegistryCollection<Fluid> {
    class FluidLavaBase(registryNameStr: String, private val flowing: Boolean, bucketItemProvider: ()->BucketItem?={null}, private val otherFluidProvider: ()->Fluid) : LavaFluid() {
        init {
            setRegistryName(registryNameStr)
        }

        private val bucketItem by lazy {bucketItemProvider()}
        private val resourceBase by lazy {stillFluid.registryName?.rem("fluid")}
        private val resourceStill by lazy {resourceBase?.plus("still")}
        private val resourceFlowing by lazy {resourceBase?.plus("flow")}

        override fun getFlowingFluid() = if (flowing) this else otherFluidProvider()
        override fun getStillFluid() = if (!flowing) this else otherFluidProvider()
        override fun getFilledBucket() : Item = bucketItem ?: super.getFilledBucket()
        override fun fillStateContainer(stateBuilder: StateContainer.Builder<Fluid, IFluidState>) {
            super.fillStateContainer(stateBuilder)
            stateBuilder.add(FlowingFluid.LEVEL_1_8)
        }

        override fun randomTick(world: World, pos: BlockPos, state: IFluidState, random: Random) = Unit
        override fun animateTick(world: World, pos: BlockPos, state: IFluidState, random: Random) {
            val upperPos = pos.up()
            if (world.getBlockState(upperPos).isAir && !world.getBlockState(upperPos).isOpaqueCube(world, upperPos)) {
                if (random.nextInt(100) == 0) {
                    val x = (pos.x + random.nextDouble())
                    val y = (pos.y + 1).toDouble()
                    val z = (pos.z + random.nextDouble())
                    world.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0, 0.0, 0.0)
                    world.playSound(x, y, z, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false)
                }

                if (random.nextInt(200) == 0) {
                    world.playSound(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false)
                }
            }
        }

        override fun getDripParticleData() = ParticleTypes.PORTAL
        override fun getRenderLayer() = BlockRenderLayer.TRANSLUCENT
        override fun isEquivalentTo(fluid: Fluid?) = fluid === stillFluid || fluid === flowingFluid
        override fun isSource(state: IFluidState) = !flowing
        override fun getLevel(state: IFluidState) : Int = if (flowing) state.get(FlowingFluid.LEVEL_1_8) else 8
        override fun createAttributes(): FluidAttributes {
            return FluidAttributes.builder(resourceStill, resourceFlowing).build(this)
        }

        override fun getBlockState(state: IFluidState): BlockState {
            return Glacia.Blocks.PLASMA.defaultState.with(FlowingFluidBlock.LEVEL, getLevelFromState(state))
        }
    }

    val FLOWING_PLASMA : FlowingFluid = FluidLavaBase("plasma_flowing", true, {Glacia.Items.PLASMA_BUCHET}) {PLASMA}
    val PLASMA : FlowingFluid = FluidLavaBase("plasma", false, {Glacia.Items.PLASMA_BUCHET}) {FLOWING_PLASMA}
}