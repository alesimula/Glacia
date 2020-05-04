package com.greenapple.glacia.registry

import com.greenapple.glacia.crafting.ShapedRecipeNoStack
import net.minecraft.item.crafting.IRecipeSerializer

object Glacia_RecipeSerializer: IForgeRegistryCollection<IRecipeSerializer<*>> {
    val CRAFTING_SHAPED_NOSTCK = ShapedRecipeNoStack.Serializer("crafting_shaped_nostack")
}