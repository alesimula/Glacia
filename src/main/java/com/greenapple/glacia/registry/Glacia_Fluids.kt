package com.greenapple.glacia.registry

import net.minecraft.fluid.*
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.state.StateContainer

//TODO add plasma once the fluid system has been properly implemented
object Glacia_Fluids : IForgeRegistryCollection<Fluid> {
    class FluidLavaBase(registryName: String, private val flowing: Boolean, private val otherFluidProvider: ()->Fluid) : LavaFluid() {
        init {
            setRegistryName(registryName)
        }

        override fun getFlowingFluid() = if (flowing) this else otherFluidProvider()
        override fun getStillFluid() = if (!flowing) this else otherFluidProvider()
        override fun getFilledBucket() : Item = Items.LAVA_BUCKET

        override fun fillStateContainer(stateBuilder: StateContainer.Builder<Fluid, IFluidState>) {
            super.fillStateContainer(stateBuilder)
            stateBuilder.add(FlowingFluid.LEVEL_1_8)
        }
        override fun isSource(state: IFluidState) = !flowing
        override fun getLevel(state: IFluidState) : Int = if (flowing) state.get(FlowingFluid.LEVEL_1_8) else 8
    }

    val FLOWING_PLASMA : FlowingFluid = FluidLavaBase("flowing_plasma", true) {PLASMA}
    val PLASMA : FlowingFluid = FluidLavaBase("plasma", false) {FLOWING_PLASMA}
}