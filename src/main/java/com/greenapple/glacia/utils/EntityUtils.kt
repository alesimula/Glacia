package com.greenapple.glacia.utils

import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.network.play.server.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Teleporter
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fml.hooks.BasicEventHooks

fun ServerPlayerEntity.changeDim(pos: BlockPos, destination: DimensionType, teleporter: Teleporter) = changeDim(pos, destination) {teleporter}

fun ServerPlayerEntity.changeDim(pos: BlockPos, destination: DimensionType, teleporterProvider: ServerWorld.()->Teleporter) {
    if (!ForgeHooks.onTravelToDimension(this, destination)) return
    val origin = dimension

    val serverWorld = server.getWorld(origin)
    dimension = destination
    val serverWorld1 = server.getWorld(destination)
    val worldInfo = world.worldInfo
    connection.sendPacket(SRespawnPacket(destination, worldInfo.generator, interactionManager.gameType))
    connection.sendPacket(SServerDifficultyPacket(worldInfo.difficulty, worldInfo.isDifficultyLocked))
    val playerList = server.playerList
    playerList.updatePermissionLevel(this)
    serverWorld.removeEntity(this, true)
    revive()

    val pitch = rotationPitch
    val yaw = rotationYaw

    val moveFactor = serverWorld.getDimension().movementFactor / serverWorld1.getDimension().movementFactor

    setLocationAndAngles(pos.x * moveFactor + .5, pos.y + .5, pos.z * moveFactor + .5, yaw, pitch)
    serverWorld.profiler.endSection()
    serverWorld.profiler.startSection("placing")
    setLocationAndAngles(pos.x * moveFactor + .5, pos.y + .5, pos.z * moveFactor + .5, yaw, pitch)

    teleporterProvider(serverWorld1).let {teleporter ->
        if (!teleporter.func_222268_a(this, rotationYaw)) {
            teleporter.makePortal(this)
            teleporter.func_222268_a(this, rotationYaw)
        }
    }

    serverWorld.profiler.endSection()
    setWorld(serverWorld1)
    serverWorld1.func_217447_b(this)

    CriteriaTriggers.CHANGED_DIMENSION.trigger(this, origin, destination)
    //player.connection.setPlayerLocation(pos.x * moveFactor + .5, pos.y + .5, pos.z * moveFactor + .5, yaw, pitch)
    interactionManager.setWorld(serverWorld1)
    connection.sendPacket(SPlayerAbilitiesPacket(abilities))
    playerList.sendWorldInfo(this, serverWorld1)
    playerList.sendInventory(this)

    for(potionEffect in activePotionEffects)
        connection.sendPacket(SPlayEntityEffectPacket(entityId, potionEffect))

    connection.sendPacket(SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false))
    /* TODO access transformer
    player.lastExperience = -1
    player.lastHealth = -1F
    player.lastFoodLevel = -1*/
    BasicEventHooks.firePlayerChangedDimensionEvent(this, origin, destination)
}