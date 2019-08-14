package com.greenapple.glacia.item

import com.greenapple.glacia.Glacia
import net.minecraft.item.Food

class ItemFoodBase(registryName: String, unlocalizedName: String, hunger: Int=1, saturation: Float=0.1F, initializer: (Food.Builder.()->Unit)?=null) : ItemBase(registryName, unlocalizedName, Glacia.ItemGroup.FOOD, {food(Food.Builder().apply {initializer?.invoke(this)}.hunger(hunger).saturation(saturation).build())}) {

    /*companion object {
        fun (Food.Builder.()->Unit)?.init(hunger: Int, saturation: Float) : Properties.() -> Unit = {
            food(Food.Builder().apply {this@init?.invoke(this)}.hunger(hunger).saturation(saturation).build())
        }
    }*/
}