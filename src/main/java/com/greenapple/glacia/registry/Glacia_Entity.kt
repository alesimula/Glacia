package com.greenapple.glacia.registry

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaDirt
import com.greenapple.glacia.entity.*
import com.greenapple.glacia.entity.model.*
import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.block.Blocks
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.entity.*
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer
import net.minecraft.client.renderer.entity.model.EntityModel
import net.minecraft.entity.*
import net.minecraft.entity.EntitySpawnPlacementRegistry.PlacementType
import net.minecraft.entity.monster.MonsterEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IWorld
import net.minecraft.world.World
import net.minecraft.world.gen.Heightmap
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.client.registry.RenderingRegistry
import java.util.*

object Glacia_Entity : IForgeRegistryCollection<EntityType<*>> {
    // <editor-fold defaultstate="collapsed" desc="Registration util methods">
    @Suppress("TYPE_PARAMETER_OF_PROPERTY_NOT_USED_IN_RECEIVER", "UNCHECKED_CAST")
    private inline val <E: Entity> EntityType<*>.type; get() = this as EntityType<E>
    private inline fun <reified E: Entity> entityType(registryName: String, classification: EntityClassification, crossinline provider: EntityType<*>.(world: World)->E) : EntityType<E> = EntityType.Builder.create({ type: EntityType<E>, world -> provider(type, world)}, classification).build("${ModLoadingContext.get().activeNamespace}:$registryName").apply {setRegistryName(registryName)}
    private inline fun <reified E: MobEntity> EntityType<E>.registerSpawn(placementType: PlacementType, heithMapType: Heightmap.Type, noinline spawnPlacementPredicate: (EntityType<E>.(world: IWorld, spawnReason: SpawnReason, blockPos: BlockPos, random: Random)->Boolean)?=null)
            = EntitySpawnPlacementRegistry.register(this, placementType, heithMapType, EntitySpawnPlacementRegistry.IPlacementPredicate(spawnPlacementPredicate ?: {world, _, pos, _ -> world.getBlockState(pos.down()).run {(block is BlockGlaciaDirt && get(BlockGlaciaDirt.SNOWY)) || block == Blocks.GRASS_BLOCK} && world.getLightSubtracted(pos, 0) > 8;}))
    private fun <E: Entity> EntityType<E>.registerRenderer(renderer: EntityRendererManager.()->EntityRenderer<E>) = RenderingRegistry.registerEntityRenderingHandler(this) {manager -> renderer(manager)}
    private fun <E: MobEntity> EntityType<E>.registerRenderer(model: EntityModel<E>, scale: Float, texture: String?=this.registryName?.path) = registerRenderer {object : MobRenderer<E, EntityModel<E>>(this, model, scale) {
        private val TEXTURE = ResourceLocation(registryName?.namespace ?: Glacia.MODID, "textures/entity/$texture.png")
        override fun getEntityTexture(entity: E) = TEXTURE
        override fun render(entity: E, p_225623_2_: Float, p_225623_3_: Float, p_225623_4_: MatrixStack, p_225623_5_: IRenderTypeBuffer, p_225623_6_: Int) {
            (entityModel as? IModelExtra<E>)?.entity = entity
            super.render(entity, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_)
        }
    }}
    private inline fun <reified E: MobEntity> EntityType<E>.registerRendererBiped(zombieArms: Boolean, texture: String?=this.registryName?.path) = registerRenderer {object : BipedRenderer<E, ModelBipedBase<E>>(this, ModelBipedBase(), 0F) {
        init {
            this.addLayer(BipedArmorLayer(this, ModelBipedBase(0.5f, true), ModelBipedBase(1F, zombieArms)))
        }
        private val TEXTURE = ResourceLocation(registryName?.namespace ?: Glacia.MODID, "textures/entity/$texture.png")
        override fun getEntityTexture(entity: E) = TEXTURE
    }}
    // </editor-fold>

    val Attributes; get() = GlaciaMonsterAttributes

    val GLACIAL_TURTLE = entityType("glacial_turtle", EntityClassification.CREATURE) {world ->  EntityGlacialTurtle(type, world)}
    val SABER_TOOTHED_CAT = entityType("saber_toothed_cat", EntityClassification.CREATURE) {world -> EntitySaberToothedCat(type, world)}
    val REINDEER = entityType("reindeer", EntityClassification.CREATURE) {world ->  EntityReindeer(type, world)}
    val PENGUIN = entityType("penguin", EntityClassification.CREATURE) {world ->  EntityPenguin(type, world)}
    val GLACIAL_SEEKER = entityType("glacial_seeker", EntityClassification.MONSTER) {world -> EntityGlacialSeeker(type, world) }

    fun registerProperties() {
        GLACIAL_TURTLE.registerSpawn(PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES)
        GLACIAL_TURTLE.registerRenderer(ModelGlacialTurtle(), 1F)
        SABER_TOOTHED_CAT.registerSpawn(PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES)
        SABER_TOOTHED_CAT.registerRenderer(ModelSaberToothedCat(), 0.5F)
        REINDEER.registerSpawn(PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES)
        REINDEER.registerRenderer(ModelReindeer(), 0.4f)
        PENGUIN.registerSpawn(PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES)
        PENGUIN.registerRenderer(ModelPenguin(), 0.35f)
        GLACIAL_SEEKER.registerSpawn(PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::canMonsterSpawnInLight)
        GLACIAL_SEEKER.registerRendererBiped(true)
    }
}