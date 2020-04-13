package com.greenapple.glacia.registry

import com.greenapple.glacia.advancement.BaseCriterionTrigger
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.ICriterionTrigger

object Glacia_Triggers : ICustomRegistryCollection<ICriterionTrigger<*>> ({CriteriaTriggers.register(this)}) {
    val TURTLE_DISGUISE = BaseCriterionTrigger("turtle_disguise")
    val EXPLOSIVE_SALT = BaseCriterionTrigger("explosive_salt")
}