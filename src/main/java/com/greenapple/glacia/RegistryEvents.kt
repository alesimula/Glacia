package com.greenapple.glacia

import com.greenapple.glacia.block.BlockAnvilTest
import com.greenapple.glacia.block.BlockBase
import com.greenapple.glacia.block.BlockGlaciaDirt
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

    // The value here should match an entry in the META-INF/mods.toml file
    @JvmStatic @SubscribeEvent
    fun onBlocksRegistry(event: RegistryEvent.Register<Block>) {
        event.registry.registerAll(
                Glacia.Blocks.GLACIAL_DIRT,

                Glacia.Blocks.GLACIAL_BEDROCK,
                Glacia.Blocks.GLACIAL_STONE,
                Glacia.Blocks.GLACIAL_COBBLESTONE,
                Glacia.Blocks.GLACIAL_MAGIC_STONE,
                Glacia.Blocks.GLACIAL_TREE_LOG,
                Glacia.Blocks.GLACIAL_TREE_LEAVES,
                Glacia.Blocks.GLACIAL_PLANKS,
                Glacia.Blocks.GLACIAL_STAIRS,
                Glacia.Blocks.GLACIAL_CRYSTAL_ORE,
                Glacia.Blocks.GLACIAL_ICE_ORE,
                Glacia.Blocks.SNOWY_SAND,
                Glacia.Blocks.GLACIAL_BERRY,
                Glacia.Blocks.MAGIC_ICE,
                Glacia.Blocks.COMPACTED_ICE
        )
    }

    @JvmStatic @SubscribeEvent
    fun onItemsRegistry(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(
                /** Block items **/
                Glacia.Blocks.GLACIAL_DIRT.toBlockItem(Glacia_ItemGroup.BLOCKS)
                        .addVariant(event, "snowy", "Glacial snowy dirt") {with(BlockGlaciaDirt.SNOWY, true)},

                Glacia.Blocks.GLACIAL_BEDROCK.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_STONE.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_COBBLESTONE.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_MAGIC_STONE.toBlockItem(Glacia_ItemGroup.BREWING),
                Glacia.Blocks.GLACIAL_TREE_LOG.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_TREE_LEAVES.toBlockItem(Glacia_ItemGroup.DECORATIONS),
                Glacia.Blocks.GLACIAL_PLANKS.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_STAIRS.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_CRYSTAL_ORE.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_ICE_ORE.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.SNOWY_SAND.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.GLACIAL_BERRY.toBlockItem(Glacia_ItemGroup.DECORATIONS),
                Glacia.Blocks.MAGIC_ICE.toBlockItem(Glacia_ItemGroup.BLOCKS),
                Glacia.Blocks.COMPACTED_ICE.toBlockItem(Glacia_ItemGroup.BLOCKS)
                //GLACIAL_DIRT.defaultState.with(BlockGlaciaDirt.SNOWY, true).toBlockItem("Glacial Grass", "snowy", ItemGroup.BREWING)
        )
    }
}