@file:Suppress("UNUSED_PARAMETER")
package com.greenapple.glacia

import com.greenapple.glacia.event.BlockEvents
import com.greenapple.glacia.event.PlayerEvents
import com.greenapple.glacia.registry.*
import com.greenapple.glacia.utils.addListenerKt
import com.greenapple.glacia.world.GlaciaDimension
import com.greenapple.glacia.event.RenderingEvents
import com.greenapple.glacia.event.WorldEvents
import com.greenapple.glacia.utils.runClient
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
import org.apache.logging.log4j.LogManager
import java.util.function.BiFunction
import java.util.stream.Collectors

@Mod(Glacia.MODID)
class Glacia {

    class ModDimensionBase(modId: String, name: String, factory: DimensionType.(World)->Dimension) : ModDimension() {
        private val javaFactory = BiFunction {world: World, type: DimensionType -> factory(type, world)}
        override fun getFactory() = javaFactory
        init {setRegistryName(modId, name)}
        val dimensionType by lazy {DimensionType.byName(registryName!!)!!}
    }

    companion object {
        const val MODID = "greenapple_glacia"
        @JvmStatic private val LOGGER = LogManager.getLogger()
        @JvmStatic val DIMENSION = ModDimensionBase(MODID, "glacia") {world -> GlaciaDimension(world, this)}

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
    }

    init {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListenerKt(this::setup)
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListenerKt(this::enqueueIMC)
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListenerKt(this::processIMC)
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListenerKt(this::doClientStuff)

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(RenderingEvents())
        MinecraftForge.EVENT_BUS.register(PlayerEvents())
        MinecraftForge.EVENT_BUS.register(WorldEvents())
        MinecraftForge.EVENT_BUS.register(BlockEvents())
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
