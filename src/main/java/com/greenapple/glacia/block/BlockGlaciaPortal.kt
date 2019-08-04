package com.greenapple.glacia.block

import com.greenapple.glacia.Glacia
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.DyeColor
import net.minecraft.util.math.BlockPos
import net.minecraft.world.dimension.DimensionType
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.world.gen.Heightmap
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.util.Hand
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.network.play.server.*
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fml.hooks.BasicEventHooks


open class BlockGlaciaPortal : BlockBase {

    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, materialColor: MaterialColor =material.color, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, materialColor, initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, dyeColor: DyeColor, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, dyeColor, initializer)
    constructor(registryName: String, name: String, itemGroup: ItemGroup?, material: Material, initializer: (Properties.()->Unit)?=null) : super(registryName, name, itemGroup, material, initializer)

    companion object {
        fun changeDim(player: ServerPlayerEntity, pos: BlockPos, destination: DimensionType) {
            if (!ForgeHooks.onTravelToDimension(player, destination)) return
            val origin = player.dimension

            val serverWorld = player.server.getWorld(origin)
            player.dimension = destination
            val serverWorld1 = player.server.getWorld(destination)
            val worldInfo = player.world.worldInfo
            player.connection.sendPacket(SRespawnPacket(destination, worldInfo.generator, player.interactionManager.gameType))
            player.connection.sendPacket(SServerDifficultyPacket(worldInfo.difficulty, worldInfo.isDifficultyLocked))
            val playerList = player.server.playerList
            playerList.updatePermissionLevel(player)
            serverWorld.removeEntity(player, true)
            player.revive()

            val pitch = player.rotationPitch
            val yaw = player.rotationYaw

            player.setLocationAndAngles(pos.x + .5, pos.y + .5, pos.z + .5, yaw, pitch)
            serverWorld.profiler.endSection()
            serverWorld.profiler.startSection("placing")
            player.setLocationAndAngles(pos.x + .5, pos.y + .5, pos.z + .5, yaw, pitch)

            serverWorld.profiler.endSection()
            player.setWorld(serverWorld1)
            serverWorld1.func_217447_b(player)
            player.connection.setPlayerLocation(pos.x + .5, pos.y + .5, pos.z + .5, yaw, pitch)
            player.interactionManager.setWorld(serverWorld1);
            player.connection.sendPacket(SPlayerAbilitiesPacket(player.abilities));
            playerList.sendWorldInfo(player, serverWorld1)
            playerList.sendInventory(player)

            for(potionEffect in player.activePotionEffects)
                player.connection.sendPacket(SPlayEntityEffectPacket(player.entityId, potionEffect))

            player.connection.sendPacket(SPlaySoundEventPacket(1032, BlockPos.ZERO, 0, false))
            /* TODO access transformer
            player.lastExperience = -1
            player.lastHealth = -1F
            player.lastFoodLevel = -1*/
            BasicEventHooks.firePlayerChangedDimensionEvent(player, origin, destination)
        }
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, playerIn: PlayerEntity, hand: Hand, rts: BlockRayTraceResult): Boolean {
        if (!worldIn.isRemote) worldIn.server?.let {server ->
            val glacia = Glacia.DIMENSION.dimensionType

            //FROM OVERWORLD TO MINING DIM
            if (worldIn.dimension.type.id == DimensionType.OVERWORLD.id) {
                val otherWorld = server.getWorld(glacia)
                otherWorld.getBlockState(pos)
                var otherWorldPos = otherWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos)
                var foundBlock = false
                val mutableBlockPos = BlockPos.MutableBlockPos(0, 0, 0)

                for (y in 0..255) {
                    for (x in pos.x - 6 until pos.x + 6) {
                        for (z in pos.z - 6 until pos.z + 6) {
                            mutableBlockPos.setPos(x, y, z)
                            if (otherWorld.getBlockState(mutableBlockPos).block === this) {
                                otherWorldPos = BlockPos(x, y + 1, z)
                                foundBlock = true
                                break
                            }
                        }
                    }
                }
                if (foundBlock) {
                    changeDim(playerIn as ServerPlayerEntity, otherWorldPos, glacia)
                }
                if (!foundBlock) {
                    otherWorld.setBlockState(otherWorldPos.down(), this.defaultState)
                    changeDim(playerIn as ServerPlayerEntity, otherWorldPos, glacia)
                }
            }

            //FROM MINING DIM TO OVERWORLD
            if (worldIn.getDimension().type === glacia) {
                val overWorld = server.getWorld(DimensionType.OVERWORLD)
                overWorld.getBlockState(pos)
                var overWorldPos = overWorld.getHeight(Heightmap.Type.WORLD_SURFACE, pos)
                var foundBlock = false
                val mutableBlockPos = BlockPos.MutableBlockPos(0, 0, 0)

                for (y in 0..255) {
                    for (x in pos.x - 6 until pos.x + 6) {
                        for (z in pos.z - 6 until pos.z + 6) {
                            mutableBlockPos.setPos(x, y, z)
                            if (overWorld.getBlockState(mutableBlockPos).block === this) {
                                overWorldPos = BlockPos(x, y + 1, z)
                                foundBlock = true
                                break
                            }
                        }
                    }
                }
                if (foundBlock) {
                    changeDim(playerIn as ServerPlayerEntity, overWorldPos, DimensionType.OVERWORLD)
                }
                if (!foundBlock) {
                    overWorld.setBlockState(overWorldPos.down(), this.defaultState)
                    changeDim(playerIn as ServerPlayerEntity, overWorldPos, DimensionType.OVERWORLD)
                }
            }
            return true
        }
        return false
    }
}