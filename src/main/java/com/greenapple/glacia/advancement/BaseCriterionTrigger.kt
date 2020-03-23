package com.greenapple.glacia.advancement

import com.google.common.collect.Lists
import com.google.common.collect.Sets
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import net.minecraft.advancements.ICriterionInstance
import net.minecraft.advancements.ICriterionTrigger
import net.minecraft.advancements.PlayerAdvancements
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.GameData
import java.util.*

class BaseCriterionTrigger(name: String) : ICriterionTrigger<ICriterionInstance> {
    private val id = GameData.checkPrefix(name, false)
    private val listeners = hashMapOf<PlayerAdvancements?, Listeners?>()

    override fun getId() : ResourceLocation = id

    override fun addListener(playerAdvancementsIn: PlayerAdvancements, listener: ICriterionTrigger.Listener<ICriterionInstance>) {
        var listenerBundle = listeners[playerAdvancementsIn]
        if (listenerBundle == null) {
            listenerBundle = Listeners(playerAdvancementsIn)
            listeners[playerAdvancementsIn] = listenerBundle
        }
        listenerBundle.add(listener)
    }

    override fun removeListener(playerAdvancementsIn: PlayerAdvancements, listener: ICriterionTrigger.Listener<ICriterionInstance>?) {
        val listenerBundle = listeners[playerAdvancementsIn]
        if (listenerBundle != null) {
            listenerBundle.remove(listener)
            if (listenerBundle.isEmpty) {
                listeners.remove(playerAdvancementsIn)
            }
        }
    }

    override fun removeAllListeners(playerAdvancementsIn: PlayerAdvancements) {
        listeners.remove(playerAdvancementsIn)
    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     *
     * @param json the json
     * @param context the context
     * @return the tame bird trigger. instance
     */
    override fun deserializeInstance(json: JsonObject, context: JsonDeserializationContext): Instance? {
        return Instance(id)
    }

    /**
     * Trigger.
     *
     * @param parPlayer the player
     */
    fun trigger(parPlayer: ServerPlayerEntity) {
        val listenerBundle = listeners[parPlayer.advancements]
        listenerBundle?.trigger(parPlayer)
    }

    /**
     * Instantiates a new instance.
     */
    class Instance(private val id: ResourceLocation) : ICriterionInstance {
        override fun getId() = id
        override fun toString() = "AbstractCriterionInstance{criterion=$id}"
        fun test() = true
    }

    /**
     * Instantiates a new listeners.
     *
     * @param playerAdvancementsIn the player advancements in
     */
    internal class Listeners(private val playerAdvancements: PlayerAdvancements) {
        private val listeners: MutableSet<ICriterionTrigger.Listener<*>> = Sets.newHashSet()
        /**
         * Checks if is empty.
         *
         * @return true, if is empty
         */
        val isEmpty: Boolean get() = listeners.isEmpty()

        /**
         * Adds the listener.
         *
         * @param listener the listener
         */
        fun add(listener: ICriterionTrigger.Listener<*>) {
            listeners.add(listener)
        }

        /**
         * Removes the listener.
         *
         * @param listener the listener
         */
        fun remove(listener: ICriterionTrigger.Listener<*>?) {
            listeners.remove(listener)
        }

        /**
         * Trigger.
         *
         * @param player the player
         */
        fun trigger(player: ServerPlayerEntity?) {
            var list: ArrayList<ICriterionTrigger.Listener<*>>? = null
            for (listener in listeners) {
                if ((listener.criterionInstance as? Instance)?.test() == true) {
                    if (list == null) {
                        list = Lists.newArrayList()
                    }
                    list?.add(listener)
                }
            }
            if (list != null) {
                for (listener1 in list) {
                    listener1.grantCriterion(playerAdvancements)
                }
            }
        }
    }
}