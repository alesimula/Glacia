package com.greenapple.glacia.crafting

import com.google.gson.JsonObject
import com.greenapple.glacia.utils.iterable
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient
import net.minecraft.item.crafting.ShapedRecipe
import net.minecraft.network.PacketBuffer
import net.minecraft.util.NonNullList
import net.minecraft.util.ResourceLocation
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class ShapedRecipeNoStack(id: ResourceLocation, group: String, recipeWidth: Int, recipeHeight: Int, recipeItems: NonNullList<Ingredient>, recipeOutput: ItemStack): ShapedRecipe(id, group, recipeWidth, recipeHeight, recipeItems, recipeOutput) {

    /*override fun getCraftingResult(inv: CraftingInventory) =
            super.getCraftingResult(inv).apply {count = inv.iterable.fold(64) {count, stack-> min(count, if(!stack.isEmpty && stack.isStackable) stack.count else 64)}}*/

    private var consumePercent: Int = 0
    private val shouldConsume get() = Random.nextInt(1,101) in 0..consumePercent

    override fun getRemainingItems(inv: CraftingInventory): NonNullList<ItemStack>? {
        val list = NonNullList.withSize(inv.sizeInventory, ItemStack.EMPTY)
        inv.iterable.forEachIndexed {index, stack ->
            list[index] = if (stack.isEmpty || stack.isStackable || shouldConsume) stack.containerItem else ItemStack(stack.item)
        }
        return list
    }

    class Serializer(name: String) : ShapedRecipe.Serializer() {
        init {setRegistryName(name)}
        private val ShapedRecipe.nostack get() = ShapedRecipeNoStack(id, group, recipeWidth, recipeHeight, ingredients, recipeOutput)
        override fun read(recipeId: ResourceLocation, json: JsonObject) = super.read(recipeId, json).nostack.apply {
            consumePercent = min(max(runCatching {json.get("consumePt").asInt}.getOrDefault(0), 0), 100)
        }
        override fun read(recipeId: ResourceLocation, buffer: PacketBuffer) = super.read(recipeId, buffer)?.nostack
    }
}