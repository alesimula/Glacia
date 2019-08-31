package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import net.minecraft.advancements.criterion.EnchantmentPredicate
import net.minecraft.advancements.criterion.ItemPredicate
import net.minecraft.advancements.criterion.MinMaxBounds
import net.minecraft.block.*
import net.minecraft.block.material.Material
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.storage.loot.*
import net.minecraft.world.storage.loot.conditions.ILootCondition
import net.minecraft.world.storage.loot.conditions.MatchTool
import net.minecraft.world.storage.loot.functions.ApplyBonus
import net.minecraft.world.storage.loot.functions.ExplosionDecay

/**
 * Don't forget to add the block to the 'fences' tag group
 */
class BlockOreBase(registryName: String, unlocalizedName: String, private val droppedItem: Item, private val dropWithSilkTouch: Boolean=true, initializer: (Properties.()->Unit)?=null) : BlockBase(registryName, unlocalizedName, Glacia.ItemGroup.BLOCKS, Material.ROCK, initializer) {

    override fun getExpDrop(state: BlockState, reader: net.minecraft.world.IWorldReader, pos: BlockPos, fortune: Int, silktouch: Int): Int {
        return if (silktouch == 0) MathHelper.nextInt(RANDOM, 3, 7); else 0
    }

    override fun getDrops(state: BlockState, builder: LootContext.Builder) = generateOreDrops(state, builder, droppedItem, dropWithSilkTouch)
}

private val CONDITION_SILKTOUCH by lazy {MatchTool.builder(ItemPredicate.Builder.create().enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))}
private val CONDITION_FALSE by lazy {ILootCondition.IBuilder {ILootCondition {false}}}

fun Block.generateOreDrops(state: BlockState, builder: LootContext.Builder, droppedItem: Item, dropWithSilkTouch: Boolean=true): MutableList<ItemStack>? {
    val lootContext = builder.withParameter(LootParameters.BLOCK_STATE, state).build(LootParameterSets.BLOCK)
    val firstCondition = if (dropWithSilkTouch) CONDITION_SILKTOUCH else CONDITION_FALSE

    val lootEntryFortune = ItemLootEntry.builder(droppedItem).acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE)).acceptFunction(ExplosionDecay.func_215863_b())
    val lootEntryTouchOrFortune = ItemLootEntry.builder(this).acceptCondition(firstCondition).func_216080_a(lootEntryFortune)
    val lootPool = LootPool.builder().rolls(ConstantRange.of(1)).addEntry(lootEntryTouchOrFortune)
    val lootTableBuilder = LootTable.Builder().addLootPool(lootPool).build()
    return lootTableBuilder.generate(lootContext)
}