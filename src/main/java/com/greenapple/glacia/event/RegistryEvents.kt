package com.greenapple.glacia.event

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.registry.register
import com.greenapple.glacia.registry.registerBlockItems
import com.greenapple.glacia.registry.registerFluidTextures
import net.minecraft.block.Block
import net.minecraft.entity.EntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.potion.Effect
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.common.ModDimension
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.carver.WorldCarver
import net.minecraft.world.gen.feature.Feature
import net.minecraftforge.client.event.TextureStitchEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object RegistryEvents {
    private val LOGGER = LogManager.getLogger()

    @JvmStatic @SubscribeEvent
    fun onDimensionModRegistry(event: RegistryEvent.Register<ModDimension>) {
        LOGGER.info("AAAAAA: Registering dimension")
        event.registry.register(Glacia.DIMENSION)
        DimensionManager.registerDimension(Glacia.DIMENSION.registryName, Glacia.DIMENSION, null, true)
        LOGGER.info("AAAAAA: Dimension registered")
    }

    @JvmStatic @SubscribeEvent
    fun onItemsRegistry(event: RegistryEvent.Register<Item>) = event.registry.run {
        registerBlockItems(Glacia.Blocks)
        register(Glacia.Items)
    }

    @JvmStatic @SubscribeEvent
    fun onFluidsRegistry(event: RegistryEvent.Register<Fluid>) = event.registry.register(Glacia.Fluids)

    @JvmStatic @SubscribeEvent
    fun onBlocksRegistry(event: RegistryEvent.Register<Block>) = event.registry.register(Glacia.Blocks)

    @JvmStatic @SubscribeEvent
    fun onBiomesRegistry(event: RegistryEvent.Register<Biome>) = event.registry.register(Glacia.Biomes)

    @JvmStatic @SubscribeEvent
    fun onEntityRegistry(event: RegistryEvent.Register<EntityType<*>>) = event.registry.register(Glacia.Entity)

    @JvmStatic @SubscribeEvent
    fun onFeatureRegistry(event: RegistryEvent.Register<Feature<*>>) = event.registry.register(Glacia.Feature)

    @JvmStatic @SubscribeEvent
    fun onWorldCarverRegistry(event: RegistryEvent.Register<WorldCarver<*>>) = event.registry.register(Glacia.WorldCarver)

    @JvmStatic @SubscribeEvent
    fun onPotionEffectRegistry(event: RegistryEvent.Register<Effect>) = event.registry.register(Glacia.Effects)

    @JvmStatic @SubscribeEvent
    fun onPotionEffectRegistry(event: TextureStitchEvent.Pre) = event.registerFluidTextures(Glacia.Fluids)
}