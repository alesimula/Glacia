package com.greenapple.glacia.utils

import com.greenapple.glacia.delegate.ReflectField
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.play.server.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Teleporter
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.server.ServerWorld
import net.minecraft.world.storage.WorldInfo
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fml.hooks.BasicEventHooks

var ServerPlayerEntity.lastExperienceKt : Int by ReflectField("field_71144_ck")
var ServerPlayerEntity.lastHealthKt : Float by ReflectField("field_71149_ch")
var ServerPlayerEntity.lastFoodLevelKt : Int by ReflectField("field_71146_ci")

fun ServerPlayerEntity.changeDim(pos: BlockPos, destination: DimensionType, teleporter: Teleporter) = changeDim(pos, destination) {teleporter}

fun ServerPlayerEntity.changeDim(pos: BlockPos, destination: DimensionType, teleporterProvider: ServerWorld.()->Teleporter) {
    if (!ForgeHooks.onTravelToDimension(this, destination)) return
    val origin = dimension

    val serverWorld = server.getWorld(origin)
    dimension = destination
    val serverWorld1 = server.getWorld(destination)
    val worldInfo: WorldInfo = serverWorld1.worldInfo
    connection.sendPacket(SRespawnPacket(destination, WorldInfo.byHashing(worldInfo.seed), worldInfo.generator, interactionManager.gameType))
    connection.sendPacket(SServerDifficultyPacket(worldInfo.difficulty, worldInfo.isDifficultyLocked))
    val playerList = server.playerList
    playerList.updatePermissionLevel(this)
    serverWorld.removeEntity(this, true)
    revive()

    val pitch = rotationPitch
    val yaw = rotationYaw

    serverWorld.profiler.startSection("moving")
    val moveFactor = serverWorld.getDimension().movementFactor / serverWorld1.getDimension().movementFactor

    setLocationAndAngles(pos.x * moveFactor + .5, pos.y + .5, pos.z * moveFactor + .5, yaw, pitch)
    serverWorld.profiler.endSection()
    serverWorld.profiler.startSection("placing")
    setLocationAndAngles(pos.x * moveFactor + .5, pos.y + .5, pos.z * moveFactor + .5, yaw, pitch)

    teleporterProvider(serverWorld1).let {teleporter ->
        if (!teleporter.placeInPortal(this, rotationYaw)) {
            teleporter.makePortal(this)
            teleporter.placeInPortal(this, rotationYaw)
        }
    }

    serverWorld.profiler.endSection()
    setWorld(serverWorld1)
    serverWorld1.func_217447_b(this)

    CriteriaTriggers.CHANGED_DIMENSION.trigger(this, origin, destination)
    connection.setPlayerLocation(posX, posY, posZ, yaw, pitch)
    //player.connection.setPlayerLocation(pos.x * moveFactor + .5, pos.y + .5, pos.z * moveFactor + .5, yaw, pitch)
    interactionManager.setWorld(serverWorld1)
    connection.sendPacket(SPlayerAbilitiesPacket(abilities))
    playerList.sendWorldInfo(this, serverWorld1)
    playerList.sendInventory(this)

    for(potionEffect in activePotionEffects)
        connection.sendPacket(SPlayEntityEffectPacket(entityId, potionEffect))

    connection.sendPacket(SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false))
    this.lastExperienceKt = -1
    this.lastHealthKt = -1F
    this.lastFoodLevelKt = -1
    BasicEventHooks.firePlayerChangedDimensionEvent(this, origin, destination)
}