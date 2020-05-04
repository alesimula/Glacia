package com.greenapple.glacia.crafting

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.item.crafting.Ingredient
import net.minecraft.network.PacketBuffer
import net.minecraft.util.JSONUtils
import net.minecraftforge.common.crafting.CraftingHelper
import java.util.stream.Stream

class IngredientNostack private constructor(itemLists: Ingredient): Ingredient(itemLists.matchingStacks.map {SingleItemList(it)}.stream()) {
    companion object {
        /*class NonStackableItemList(val itemList: SingleItemList): IItemList by itemList {
            init {
                if (itemList.stacks.first().maxStackSize > 1) throw JsonParseException("Ingredients tagged as nostack must not be stackable")
            }
        }*/
        open fun read(buffer: PacketBuffer): Ingredient? {
            val i = buffer.readVarInt()
            return if (i == -1) {
                CraftingHelper.getIngredient(buffer.readResourceLocation(), buffer)
            } else fromItemListStream(Stream.generate { SingleItemList(buffer.readItemStack()) }.limit(i.toLong()))
        }

        private fun Ingredient.toNonStackableSafe(json: JsonObject): Ingredient {
            return if (json.has("nostack") && JSONUtils.getString(json, "nostack").toBoolean()) {
                if (!json.has("item")) {
                    return IngredientNostack(this)
                }
                else throw JsonParseException("Only ingredient tagged as item can have the nostack flag")
            }
            else this
        }

        private fun Ingredient.toNonStackableSafe(json: JsonElement?) =if (json is JsonObject) toNonStackableSafe(json) else this

        /*fun IItemList.toNonStackableSafe(json: JsonObject): IItemList {
            return if (json.has("nostack") && JSONUtils.getString(json, "nostack").toBoolean()) {
                if (this is SingleItemList) {
                    return NonStackableItemList(this)
                }
                else throw JsonParseException("Only ingredient tagged as item can have the nostack flag")
            }
            else this
        }*/

        //private fun deserializeItemList(json: JsonObject) = Ingredient.deserializeItemList(json).toNonStackableSafe(json)

        fun deserialize(json: JsonElement?) = Ingredient.deserialize(json).toNonStackableSafe(json)
    }
}