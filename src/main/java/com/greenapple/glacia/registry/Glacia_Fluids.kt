package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.rem
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.fluid.FlowingFluid
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.fluid.IFluidState
import net.minecraft.state.StateContainer
import net.minecraft.world.IWorldReader
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.registries.GameData

object Glacia_Fluids : IForgeRegistryCollection<Fluid> {
    class FluidBase constructor(registryNameStr: String, private val properties: Properties, private val flowing: Boolean=false) : ForgeFlowingFluid(properties) {
        @Suppress("FunctionName")
        companion object {
            fun Still(registryNameStr: String, flowingFluidProvider: ()->FluidBase, blockProvider: ()->FlowingFluidBlock, properties: (Properties.()->Unit)?=null, attributes: (FluidAttributes.Builder.()->Unit)?=null) : FluidBase {
                var stillFluid : FluidBase? = null
                stillFluid = FluidBase(registryNameStr, Properties({stillFluid}, flowingFluidProvider, FluidAttributes.builder(GameData.checkPrefix("${registryNameStr}_still", true) % "fluid", GameData.checkPrefix("${registryNameStr}_flow" , true)  % "fluid").apply {attributes?.invoke(this)}).block(blockProvider).apply {properties?.invoke(this)}, false)
                return stillFluid
            }
            fun Flowing(registryNameStr: String, stillFluid : FluidBase) = FluidBase(registryNameStr, stillFluid.properties, true)
        }
        init {setRegistryName(registryNameStr)}

        override fun getTickRate(world: IWorldReader) = tickRateFromViscosity
        private val tickRateFromViscosity: Int by lazy {
            val current = attributes.viscosity.toFloat()
            val water = Fluids.WATER.attributes.viscosity.toFloat()
            val lava = Fluids.LAVA.attributes.viscosity.toFloat()
            if (current<=0) 0 else ((current * (current-lava)) / (water * (water-lava)) * 5F + (current * (current-water)) / (lava * (lava-water)) * 30F).toInt()
        }

        override fun isSource(state: IFluidState) = !flowing
        override fun getLevel(state: IFluidState) : Int = if (flowing) state.get(FlowingFluid.LEVEL_1_8) else 8
        override fun fillStateContainer(stateBuilder: StateContainer.Builder<Fluid, IFluidState>) {
            super.fillStateContainer(stateBuilder)
            stateBuilder.add(FlowingFluid.LEVEL_1_8)
        }
    }

    val PLASMA : FluidBase = FluidBase.Still("plasma", {PLASMA_FLOWING}, {Glacia.Blocks.PLASMA}, {bucket {Glacia.Items.PLASMA_BUCKET}}) {luminosity(10).density(Fluids.LAVA.attributes.density).viscosity(Fluids.LAVA.attributes.viscosity)}
    val PLASMA_FLOWING : FluidBase = FluidBase.Flowing("plasma_flowing", PLASMA)
}