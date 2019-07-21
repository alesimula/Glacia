package com.greenapple.glacia

import com.greenapple.glacia.block.BlockAnvilTest
import com.greenapple.glacia.block.BlockBase
import com.greenapple.glacia.block.toBlockItem
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object RegistryEvents {
    private val LOGGER = LogManager.getLogger()

    val testBlock2 = BlockAnvilTest("Whatever", Material.WOOD).apply {setRegistryName("test_block_fence")}
    val testBlock = BlockBase("Weird dirt", Material.BAMBOO).apply {setRegistryName("test_block")}

    // The value here should match an entry in the META-INF/mods.toml file
    @JvmStatic @SubscribeEvent
    fun onBlocksRegistry(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(
                testBlock,
                testBlock2
        )
    }

    @JvmStatic @SubscribeEvent
    fun onItemsRegistry(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                testBlock.toBlockItem(ItemGroup.BREWING),
                testBlock2.toBlockItem(ItemGroup.BREWING)
        )
    }
}