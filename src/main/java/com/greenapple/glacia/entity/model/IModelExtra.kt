package com.greenapple.glacia.entity.model

import net.minecraft.client.renderer.model.ModelRenderer
import net.minecraft.entity.LivingEntity
import java.util.function.Consumer

interface IModelExtra<E: LivingEntity> : Consumer<ModelRenderer> {
    var entity: E?
}