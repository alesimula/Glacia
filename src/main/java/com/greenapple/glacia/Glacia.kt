@file:Suppress("UNUSED_PARAMETER")
package com.greenapple.glacia

import com.greenapple.glacia.event.BlockEvents
import com.greenapple.glacia.event.PlayerEvents
import com.greenapple.glacia.registry.*
import com.greenapple.glacia.world.GlaciaDimension
import com.greenapple.glacia.event.RenderingEvents
import com.greenapple.glacia.event.WorldEvents
import com.greenapple.glacia.renderer.*
import com.greenapple.glacia.utils.*
import net.minecraft.block.TorchBlock
import net.minecraft.block.WallTorchBlock
import net.minecraft.world.World
import net.minecraft.world.dimension.Dimension
import net.minecraft.world.dimension.DimensionType
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.ModDimension
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import net.minecraftforge.fml.event.server.FMLServerStartingEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import java.util.function.BiFunction
import java.util.stream.Collectors

@Mod(Glacia.MODID)
class Glacia {

    class ModDimensionBase(modId: String, name: String, factory: World.(DimensionType)->Dimension) : ModDimension() {
        private val javaFactory = BiFunction {world: World, type: DimensionType -> dimensionType = type; factory(world, type).also {dimension = it}}
        override fun getFactory() = javaFactory
        init {setRegistryName(modId, name)}
        private var dimensionType: DimensionType? = null; get() = field ?: DimensionType.byName(registryName!!)?.also {field = it}
        var dimension: Dimension? = null; private set
        val type get() = dimensionType!!
        //val type by lazy {DimensionType.byName(registryName!!)!!}
    }

    companion object {
        const val MODID = "greenapple_glacia"
        @JvmStatic private val LOGGER = LogManager.getLogger()
        @JvmStatic val DIMENSION = ModDimensionBase(MODID, "glacia", ::GlaciaDimension)

        @JvmStatic val Fluids; get() = Glacia_Fluids
        @JvmStatic val Blocks; get() = Glacia_Blocks
        @JvmStatic val Items; get() = Glacia_Items
        @JvmStatic val ItemGroup; get() = Glacia_ItemGroup
        @JvmStatic val Biomes; get() = Glacia_Biomes
        @JvmStatic val Entity; get() = Glacia_Entity
        @JvmStatic val Feature; get() = Glacia_Feature
        @JvmStatic val WorldCarver; get() = Glacia_WorldCarver
        @JvmStatic val Effects; get() = Glacia_Effects
        @JvmStatic val Triggers; get() = Glacia_Triggers
        @JvmStatic val Particles; get() = Glacia_Particles
        @JvmStatic val RecipeSerializer; get() = Glacia_RecipeSerializer
    }

    init {
        // Listeners
        FMLJavaModLoadingContext.get().modEventBus.let {bus ->
            // Register the setup method for modloading
            bus.addListenerKt(::setup)
            // Register the enqueueIMC method for modloading
            bus.addListenerKt(::enqueueIMC)
            // Register the processIMC method for modloading
            bus.addListenerKt(::processIMC)
            // Register the doClientStuff method for modloading
            bus.addListenerKt(::doClientStuff)
        }
        // Common events
        MinecraftForge.EVENT_BUS.run {
            register(this@Glacia)
            register(PlayerEvents())
            register(WorldEvents())
            register(BlockEvents())
        }
        // Client side events
        MinecraftForge.EVENT_BUS.runClient {
            register(RenderingEvents())
        }
    }

    private fun setup(event: FMLCommonSetupEvent) {
        runClient {
            Entity.registerProperties()
        }
        ///BiomeManager.addBiome(GlaciaLayerUtils.BIOME_TYPE_GLACIA, BiomeEntry(Glacia.Biomes.PLAINS, 3))
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.registryName)
        /*(event.container.modInfo as ModInfo).apply {
            LOGGER.info("DIRT BLOCK >> {}", ""+description)
            TextFormatting.GREEN.toString()
            displayNameKt = ""+TextFormatting.BLUE+""+TextFormatting.BOLD + "Glacia";
            //descriptionKt = "Test descrizione"
            LOGGER.info("DIRT BLOCK >> {}", ""+displayName)
        }*/
    }

    private fun doClientStuff(event: FMLClientSetupEvent) {
        // do something that can only be done on the client
        //LOGGER.info("Got game settings {}", event.minecraftSupplier.get().gameSettings)
    }

    private fun enqueueIMC(event: InterModEnqueueEvent) {
        // some example code to dispatch IMC to another mod
        /*InterModComms.sendTo("examplemod", "helloworld") {
            LOGGER.info("Hello world from the MDK")
            "Hello world"
        }*/
    }

    private fun processIMC(event: InterModProcessEvent) {
        // some example code to receive and process InterModComms from other mods
        val eventList = event.imcStream.map { m -> m.getMessageSupplier<Any>().get() }.collect(Collectors.toList())
        //LOGGER.info("Got IMC {}", eventList)
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    fun onServerStarting(event: FMLServerStartingEvent) {
        // do something when the server starts
        //LOGGER.info("HELLO from server starting")
    }
}
