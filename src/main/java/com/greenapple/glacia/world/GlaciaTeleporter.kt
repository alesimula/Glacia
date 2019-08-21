package com.greenapple.glacia.world

import com.google.common.collect.Maps
import com.greenapple.glacia.Glacia
import com.greenapple.glacia.block.BlockGlaciaPortal
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
import kotlin.collections.Map.Entry
import net.minecraft.block.Blocks
import net.minecraft.block.pattern.BlockPattern
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.Direction
import net.minecraft.util.math.*
import net.minecraft.world.Teleporter
import net.minecraft.world.server.ServerWorld
import net.minecraft.world.server.TicketType
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.util.Supplier

class GlaciaTeleporter(world: ServerWorld, private val portalBlock: BlockGlaciaPortal) : Teleporter(world) {
    private val LOGGER = LogManager.getLogger()
    private val destinationCoordinateCacheKt: MutableMap<ColumnPos, PortalPosition> = Maps.newHashMapWithExpectedSize(4096)
    private val field_222275_f = Object2LongOpenHashMap<ColumnPos>()

    override fun func_222268_a(p_222268_1_: Entity, p_222268_2_: Float): Boolean {
        val lvt_3_1_ = p_222268_1_.lastPortalVec
        val lvt_4_1_ = p_222268_1_.teleportDirection
        val lvt_5_1_ = this.func_222272_a(BlockPos(p_222268_1_), p_222268_1_.motion, lvt_4_1_, lvt_3_1_.x, lvt_3_1_.y, p_222268_1_ is PlayerEntity)
        if (lvt_5_1_ == null) {
            return false
        } else {
            val lvt_6_1_ = lvt_5_1_.field_222505_a
            val lvt_7_1_ = lvt_5_1_.field_222506_b
            p_222268_1_.motion = lvt_7_1_
            p_222268_1_.rotationYaw = p_222268_2_ + lvt_5_1_.field_222507_c.toFloat()
            if (p_222268_1_ is ServerPlayerEntity) {
                p_222268_1_.connection.setPlayerLocation(lvt_6_1_.x, lvt_6_1_.y, lvt_6_1_.z, p_222268_1_.rotationYaw, p_222268_1_.rotationPitch)
                p_222268_1_.connection.captureCurrentPosition()
            } else {
                p_222268_1_.setLocationAndAngles(lvt_6_1_.x, lvt_6_1_.y, lvt_6_1_.z, p_222268_1_.rotationYaw, p_222268_1_.rotationPitch)
            }

            return true
        }
    }

    override fun func_222272_a(p_222272_1_: BlockPos, p_222272_2_: Vec3d, p_222272_3_: Direction, p_222272_4_: Double, p_222272_6_: Double, p_222272_8_: Boolean): BlockPattern.PortalInfo? {
        var lvt_10_1_ = true
        var lvt_11_1_: BlockPos? = null
        val lvt_12_1_ = ColumnPos(p_222272_1_)
        if (!p_222272_8_ && this.field_222275_f.containsKey(lvt_12_1_)) {
            return null
        } else {
            val lvt_13_1_ = destinationCoordinateCacheKt[lvt_12_1_]
            if (lvt_13_1_ != null) {
                lvt_11_1_ = lvt_13_1_.field_222267_a
                lvt_13_1_.lastUpdateTime = this.world.gameTime
                lvt_10_1_ = false
            } else {
                var lvt_14_1_ = 1.7976931348623157E308

                for (lvt_16_1_ in -128..128) {
                    var lvt_19_1_: BlockPos
                    for (lvt_17_1_ in -128..128) {
                        var lvt_18_1_ = p_222272_1_.add(lvt_16_1_, this.world.actualHeight - 1 - p_222272_1_.y, lvt_17_1_)
                        while (lvt_18_1_.y >= 0) {
                            lvt_19_1_ = lvt_18_1_.down()
                            if (this.world.getBlockState(lvt_18_1_).block === portalBlock) {
                                lvt_19_1_ = lvt_18_1_.down()
                                while (this.world.getBlockState(lvt_19_1_).block === portalBlock) {
                                    lvt_18_1_ = lvt_19_1_
                                    lvt_19_1_ = lvt_19_1_.down()
                                }

                                val lvt_20_1_ = lvt_18_1_.distanceSq(p_222272_1_)
                                if (lvt_14_1_ < 0.0 || lvt_20_1_ < lvt_14_1_) {
                                    lvt_14_1_ = lvt_20_1_
                                    lvt_11_1_ = lvt_18_1_
                                }
                            }
                            lvt_18_1_ = lvt_19_1_
                        }
                    }
                }
            }

            if (lvt_11_1_ == null) {
                val lvt_14_2_ = this.world.gameTime + 300L
                this.field_222275_f[lvt_12_1_] = lvt_14_2_
                return null
            } else {
                if (lvt_10_1_) {
                    this.destinationCoordinateCacheKt[lvt_12_1_] = PortalPosition(lvt_11_1_, this.world.gameTime)
                    val var10000 = LOGGER
                    val var10002 = arrayOfNulls<Supplier<*>>(2)
                    val var10005 = this.world.getDimension()
                    var10002[0] = Supplier {var10005.type}
                    var10002[1] = Supplier {lvt_12_1_}
                    var10000.debug("Adding nether portal ticket for {}:{}", *var10002)
                    this.world.chunkProvider.func_217228_a(TicketType.PORTAL, ChunkPos(lvt_11_1_), 3, lvt_12_1_)
                }

                val lvt_14_3_ = portalBlock.createPatternHelper(this.world, lvt_11_1_)
                return lvt_14_3_.func_222504_a(p_222272_3_, lvt_11_1_, p_222272_6_, p_222272_2_, p_222272_4_)
            }
        }
    }

    override fun makePortal(p_85188_1_: Entity): Boolean {
        var lvt_3_1_ = -1.0
        val lvt_5_1_ = MathHelper.floor(p_85188_1_.posX)
        val lvt_6_1_ = MathHelper.floor(p_85188_1_.posY)
        val lvt_7_1_ = MathHelper.floor(p_85188_1_.posZ)
        var lvt_8_1_ = lvt_5_1_
        var lvt_9_1_ = lvt_6_1_
        var lvt_10_1_ = lvt_7_1_
        var lvt_11_1_ = 0
        val lvt_12_1_ = this.random.nextInt(4)
        val lvt_13_1_ = BlockPos.MutableBlockPos()

        var lvt_14_2_: Int
        var lvt_15_2_: Double
        var lvt_17_2_: Int
        var lvt_18_2_: Double
        var lvt_20_2_: Int
        var lvt_21_2_: Int
        var lvt_22_2_: Int
        var lvt_23_2_: Int
        var lvt_24_3_: Int
        var lvt_25_2_: Int
        var lvt_26_3_: Int
        var lvt_27_2_: Int
        var lvt_28_2_: Int
        var lvt_24_4_: Double
        var lvt_26_4_: Double
        lvt_14_2_ = lvt_5_1_ - 16
        while (lvt_14_2_ <= lvt_5_1_ + 16) {
            lvt_15_2_ = lvt_14_2_.toDouble() + 0.5 - p_85188_1_.posX

            lvt_17_2_ = lvt_7_1_ - 16
            while (lvt_17_2_ <= lvt_7_1_ + 16) {
                lvt_18_2_ = lvt_17_2_.toDouble() + 0.5 - p_85188_1_.posZ

                lvt_20_2_ = this.world.actualHeight - 1
                label276@ while (lvt_20_2_ >= 0) {
                    if (this.world.isAirBlock(lvt_13_1_.setPos(lvt_14_2_, lvt_20_2_, lvt_17_2_))) {
                        while (lvt_20_2_ > 0 && this.world.isAirBlock(lvt_13_1_.setPos(lvt_14_2_, lvt_20_2_ - 1, lvt_17_2_))) {
                            --lvt_20_2_
                        }

                        lvt_21_2_ = lvt_12_1_
                        while (lvt_21_2_ < lvt_12_1_ + 4) {
                            lvt_22_2_ = lvt_21_2_ % 2
                            lvt_23_2_ = 1 - lvt_22_2_
                            if (lvt_21_2_ % 4 >= 2) {
                                lvt_22_2_ = -lvt_22_2_
                                lvt_23_2_ = -lvt_23_2_
                            }

                            lvt_24_3_ = 0
                            while (lvt_24_3_ < 3) {
                                lvt_25_2_ = 0
                                while (lvt_25_2_ < 4) {
                                    lvt_26_3_ = -1
                                    while (lvt_26_3_ < 4) {
                                        lvt_27_2_ = lvt_14_2_ + (lvt_25_2_ - 1) * lvt_22_2_ + lvt_24_3_ * lvt_23_2_
                                        lvt_28_2_ = lvt_20_2_ + lvt_26_3_
                                        val lvt_29_1_ = lvt_17_2_ + (lvt_25_2_ - 1) * lvt_23_2_ - lvt_24_3_ * lvt_22_2_
                                        lvt_13_1_.setPos(lvt_27_2_, lvt_28_2_, lvt_29_1_)
                                        if (lvt_26_3_ < 0 && !this.world.getBlockState(lvt_13_1_).getMaterial().isSolid() || lvt_26_3_ >= 0 && !this.world.isAirBlock(lvt_13_1_)) {
                                            --lvt_20_2_
                                            continue@label276
                                        }
                                        ++lvt_26_3_
                                    }
                                    ++lvt_25_2_
                                }
                                ++lvt_24_3_
                            }

                            lvt_24_4_ = lvt_20_2_.toDouble() + 0.5 - p_85188_1_.posY
                            lvt_26_4_ = lvt_15_2_ * lvt_15_2_ + lvt_24_4_ * lvt_24_4_ + lvt_18_2_ * lvt_18_2_
                            if (lvt_3_1_ < 0.0 || lvt_26_4_ < lvt_3_1_) {
                                lvt_3_1_ = lvt_26_4_
                                lvt_8_1_ = lvt_14_2_
                                lvt_9_1_ = lvt_20_2_
                                lvt_10_1_ = lvt_17_2_
                                lvt_11_1_ = lvt_21_2_ % 4
                            }
                            ++lvt_21_2_
                        }
                    }
                    --lvt_20_2_
                }
                ++lvt_17_2_
            }
            ++lvt_14_2_
        }

        if (lvt_3_1_ < 0.0) {
            lvt_14_2_ = lvt_5_1_ - 16
            while (lvt_14_2_ <= lvt_5_1_ + 16) {
                lvt_15_2_ = lvt_14_2_.toDouble() + 0.5 - p_85188_1_.posX

                lvt_17_2_ = lvt_7_1_ - 16
                while (lvt_17_2_ <= lvt_7_1_ + 16) {
                    lvt_18_2_ = lvt_17_2_.toDouble() + 0.5 - p_85188_1_.posZ

                    lvt_20_2_ = this.world.actualHeight - 1
                    label214@ while (lvt_20_2_ >= 0) {
                        if (this.world.isAirBlock(lvt_13_1_.setPos(lvt_14_2_, lvt_20_2_, lvt_17_2_))) {
                            while (lvt_20_2_ > 0 && this.world.isAirBlock(lvt_13_1_.setPos(lvt_14_2_, lvt_20_2_ - 1, lvt_17_2_))) {
                                --lvt_20_2_
                            }

                            lvt_21_2_ = lvt_12_1_
                            while (lvt_21_2_ < lvt_12_1_ + 2) {
                                lvt_22_2_ = lvt_21_2_ % 2
                                lvt_23_2_ = 1 - lvt_22_2_

                                lvt_24_3_ = 0
                                while (lvt_24_3_ < 4) {
                                    lvt_25_2_ = -1
                                    while (lvt_25_2_ < 4) {
                                        lvt_26_3_ = lvt_14_2_ + (lvt_24_3_ - 1) * lvt_22_2_
                                        lvt_27_2_ = lvt_20_2_ + lvt_25_2_
                                        lvt_28_2_ = lvt_17_2_ + (lvt_24_3_ - 1) * lvt_23_2_
                                        lvt_13_1_.setPos(lvt_26_3_, lvt_27_2_, lvt_28_2_)
                                        if (lvt_25_2_ < 0 && !this.world.getBlockState(lvt_13_1_).getMaterial().isSolid() || lvt_25_2_ >= 0 && !this.world.isAirBlock(lvt_13_1_)) {
                                            --lvt_20_2_
                                            continue@label214
                                        }
                                        ++lvt_25_2_
                                    }
                                    ++lvt_24_3_
                                }

                                lvt_24_4_ = lvt_20_2_.toDouble() + 0.5 - p_85188_1_.posY
                                lvt_26_4_ = lvt_15_2_ * lvt_15_2_ + lvt_24_4_ * lvt_24_4_ + lvt_18_2_ * lvt_18_2_
                                if (lvt_3_1_ < 0.0 || lvt_26_4_ < lvt_3_1_) {
                                    lvt_3_1_ = lvt_26_4_
                                    lvt_8_1_ = lvt_14_2_
                                    lvt_9_1_ = lvt_20_2_
                                    lvt_10_1_ = lvt_17_2_
                                    lvt_11_1_ = lvt_21_2_ % 2
                                }
                                ++lvt_21_2_
                            }
                        }
                        --lvt_20_2_
                    }
                    ++lvt_17_2_
                }
                ++lvt_14_2_
            }
        }

        val lvt_15_3_ = lvt_8_1_
        var lvt_16_1_ = lvt_9_1_
        lvt_17_2_ = lvt_10_1_
        var lvt_18_3_ = lvt_11_1_ % 2
        var lvt_19_1_ = 1 - lvt_18_3_
        if (lvt_11_1_ % 4 >= 2) {
            lvt_18_3_ = -lvt_18_3_
            lvt_19_1_ = -lvt_19_1_
        }

        if (lvt_3_1_ < 0.0) {
            lvt_9_1_ = MathHelper.clamp(lvt_9_1_, 70, this.world.actualHeight - 10)
            lvt_16_1_ = lvt_9_1_

            lvt_20_2_ = -1
            while (lvt_20_2_ <= 1) {
                lvt_21_2_ = 1
                while (lvt_21_2_ < 3) {
                    lvt_22_2_ = -1
                    while (lvt_22_2_ < 3) {
                        lvt_23_2_ = lvt_15_3_ + (lvt_21_2_ - 1) * lvt_18_3_ + lvt_20_2_ * lvt_19_1_
                        lvt_24_3_ = lvt_16_1_ + lvt_22_2_
                        lvt_25_2_ = lvt_17_2_ + (lvt_21_2_ - 1) * lvt_19_1_ - lvt_20_2_ * lvt_18_3_
                        val lvt_26_5_ = lvt_22_2_ < 0
                        lvt_13_1_.setPos(lvt_23_2_, lvt_24_3_, lvt_25_2_)
                        this.world.setBlockState(lvt_13_1_, if (lvt_26_5_) Glacia.Blocks.GLACIAL_MAGIC_STONE.defaultState else Blocks.AIR.defaultState)
                        ++lvt_22_2_
                    }
                    ++lvt_21_2_
                }
                ++lvt_20_2_
            }
        }

        lvt_20_2_ = -1
        while (lvt_20_2_ < 3) {
            lvt_21_2_ = -1
            while (lvt_21_2_ < 4) {
                if (lvt_20_2_ == -1 || lvt_20_2_ == 2 || lvt_21_2_ == -1 || lvt_21_2_ == 3) {
                    lvt_13_1_.setPos(lvt_15_3_ + lvt_20_2_ * lvt_18_3_, lvt_16_1_ + lvt_21_2_, lvt_17_2_ + lvt_20_2_ * lvt_19_1_)
                    this.world.setBlockState(lvt_13_1_, Glacia.Blocks.GLACIAL_MAGIC_STONE.defaultState, 3)
                }
                ++lvt_21_2_
            }
            ++lvt_20_2_
        }

        val lvt_20_5_ = portalBlock.defaultState.with(BlockGlaciaPortal.AXIS, if (lvt_18_3_ == 0) Direction.Axis.Z else Direction.Axis.X)

        lvt_21_2_ = 0
        while (lvt_21_2_ < 2) {
            lvt_22_2_ = 0
            while (lvt_22_2_ < 3) {
                lvt_13_1_.setPos(lvt_15_3_ + lvt_21_2_ * lvt_18_3_, lvt_16_1_ + lvt_22_2_, lvt_17_2_ + lvt_21_2_ * lvt_19_1_)
                this.world.setBlockState(lvt_13_1_, lvt_20_5_, 18)
                ++lvt_22_2_
            }
            ++lvt_21_2_
        }

        return true
    }

    override fun tick(p_85189_1_: Long) {
        if (p_85189_1_ % 100L == 0L) {
            this.func_222270_b(p_85189_1_)
            this.func_222269_c(p_85189_1_)
        }
    }

    private fun func_222270_b(p_222270_1_: Long) {
        val lvt_3_1_ = this.field_222275_f.values.iterator()

        while (lvt_3_1_.hasNext()) {
            val lvt_4_1_ = lvt_3_1_.nextLong()
            if (lvt_4_1_ <= p_222270_1_) {
                lvt_3_1_.remove()
            }
        }
    }

    private fun func_222269_c(p_222269_1_: Long) {
        val lvt_3_1_ = p_222269_1_ - 300L
        val lvt_5_1_ = this.destinationCoordinateCacheKt.entries.iterator()

        while (lvt_5_1_.hasNext()) {
            val lvt_6_1_ = lvt_5_1_.next() as Entry<*, *>
            val lvt_7_1_ = lvt_6_1_.value as PortalPosition
            if (lvt_7_1_.lastUpdateTime < lvt_3_1_) {
                val lvt_8_1_ = lvt_6_1_.key as ColumnPos?
                val var10000 = LOGGER
                val var10002 = arrayOfNulls<Supplier<*>>(2)
                val var10005 = this.world.getDimension()
                var10002[0] = Supplier {var10005.type}
                var10002[1] = Supplier {lvt_8_1_}
                var10000.debug("Removing nether portal ticket for {}:{}", *var10002)
                this.world.chunkProvider.func_217222_b(TicketType.PORTAL, ChunkPos(lvt_7_1_.field_222267_a), 3, lvt_8_1_)
                lvt_5_1_.remove()
            }
        }
    }

    internal class PortalPosition(val field_222267_a: BlockPos, var lastUpdateTime: Long)
}