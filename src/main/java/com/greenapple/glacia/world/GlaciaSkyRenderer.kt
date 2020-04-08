package com.greenapple.glacia.world

import java.util.Random

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.renderer.GL11Utils
import com.greenapple.glacia.renderer.TessellatorCompat
import com.mojang.blaze3d.platform.GlStateManager
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GLAllocation
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.Vector3f
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.IRenderHandler
import org.lwjgl.opengl.GL11
import net.minecraft.client.world.ClientWorld
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class GlaciaSkyRenderer : IRenderHandler {

    private companion object {
        val moonTexture = ResourceLocation(Glacia.MODID, "environment/moon.png")
        val sunTexture1 = ResourceLocation(Glacia.MODID, "environment/sun_green.png")
        val sunTexture2 = ResourceLocation(Glacia.MODID, "environment/sun_blue.png")
    }

    private var starGLCallList = GL11Utils.generateDisplayLists(3)
    private var glSkyList: Int = 0
    private var glSkyList2: Int = 0

    init {
        GlStateManager.pushMatrix()
        GL11Utils.newList(this.starGLCallList, GL11.GL_COMPILE)
        this.renderStars()
        GL11Utils.endList()
        GlStateManager.popMatrix()
        val tessellator = TessellatorCompat.instance
        this.glSkyList = this.starGLCallList + 1
        GL11Utils.newList(this.glSkyList, GL11.GL_COMPILE)
        val byte2: Byte = 64
        val i = 256 / byte2 + 2
        var f = 16f

        var j = -byte2 * i
        while (j <= byte2 * i) {
            var l = -byte2 * i
            while (l <= byte2 * i) {
                tessellator.startDrawingQuads()
                tessellator.addVertex((j + 0).toDouble(), f.toDouble(), (l + 0).toDouble())
                tessellator.addVertex((j + byte2).toDouble(), f.toDouble(), (l + 0).toDouble())
                tessellator.addVertex((j + byte2).toDouble(), f.toDouble(), (l + byte2).toDouble())
                tessellator.addVertex((j + 0).toDouble(), f.toDouble(), (l + byte2).toDouble())
                tessellator.draw()
                l += byte2.toInt()
            }
            j += byte2.toInt()
        }

        GL11Utils.endList()
        this.glSkyList2 = this.starGLCallList + 2
        GL11Utils.newList(this.glSkyList2, GL11.GL_COMPILE)
        f = -16f
        tessellator.startDrawingQuads()

        var k = -byte2 * i
        while (k <= byte2 * i) {
            var i1 = -byte2 * i
            while (i1 <= byte2 * i) {
                tessellator.addVertex((k + byte2).toDouble(), f.toDouble(), (i1 + 0).toDouble())
                tessellator.addVertex((k + 0).toDouble(), f.toDouble(), (i1 + 0).toDouble())
                tessellator.addVertex((k + 0).toDouble(), f.toDouble(), (i1 + byte2).toDouble())
                tessellator.addVertex((k + byte2).toDouble(), f.toDouble(), (i1 + byte2).toDouble())
                i1 += byte2.toInt()
            }
            k += byte2.toInt()
        }

        tessellator.draw()
        GL11Utils.endList()
    }

    fun getSkyColor(world: ClientWorld, cameraPos: BlockPos?, partialTicks: Float) = world.getSkyColor(cameraPos, partialTicks).run {
        Vec3d(x*0.55, y*0.15, z*0.7)
    }

    override fun render(ticks: Int, partialTicks: Float, world: ClientWorld, mc: Minecraft) = (world.dimension as? GlaciaDimension)?.let {dimension ->
        val var10: Float
        val var11: Float
        var var12: Float
        val tessellator= TessellatorCompat.instance

        GlStateManager.pushMatrix()

        //Cheap fix for sky following the camera
        val camera = mc.getRenderViewEntity()
        val pitch = camera?.rotationPitch ?: 0F
        val yaw = camera?.rotationYaw ?: 0F
        GlStateManager.rotatef(pitch, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotatef(yaw+180, 0.0f, 1.0f, 0.0f)

        GlStateManager.pushMatrix()
        GlStateManager.disableTexture()

        val renderViewPos = if (mc.renderViewEntity != null) mc.renderViewEntity!!.position else BlockPos(0, 0, 0)
        val skyColor = getSkyColor(world, mc.gameRenderer.activeRenderInfo.blockPos, partialTicks)
        val skyColorX = skyColor.x.toFloat()
        val skyColorY = skyColor.y.toFloat()
        val skyColorZ = skyColor.z.toFloat()
        var f4: Float

        GL11Utils.color3f(skyColorX, skyColorY, skyColorZ)
        val tessellator1 = TessellatorCompat.instance
        GlStateManager.depthMask(false)
        GlStateManager.enableFog()
        GL11Utils.color3f(skyColorX, skyColorY, skyColorZ)
        GL11Utils.callList(this.glSkyList)
        GlStateManager.disableFog()
        GlStateManager.disableAlphaTest()
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        RenderHelper.disableStandardItemLighting()

        var f7: Float
        var f8: Float
        var f9: Float
        var f10: Float
        val afloat = world.dimension.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks)

        if (afloat != null) {
            GlStateManager.disableTexture()
            GlStateManager.shadeModel(GL11.GL_SMOOTH)
            GlStateManager.pushMatrix()
            GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f)
            GlStateManager.rotatef(if (MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0f) 180.0f else 0.0f, 0.0f, 0.0f, 1.0f)
            GlStateManager.rotatef(90.0f, 0.0f, 0.0f, 1.0f)
            f4 = afloat[0]
            f7 = afloat[1]
            f8 = afloat[2]
            var f11: Float

            /* NO 3D
                if (mc.gameSettings.anaglyph)
                {
                    f9 = (f4 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
                    f10 = (f4 * 30.0F + f7 * 70.0F) / 100.0F;
                    f11 = (f4 * 30.0F + f8 * 70.0F) / 100.0F;
                    f4 = f9;
                    f7 = f10;
                    f8 = f11;
                }*/

            tessellator1.startDrawing(6)
            tessellator1.setColorRGBA_F(f4, f7, f8, afloat[3])
            tessellator1.addVertex(0.0, 100.0, 0.0)
            val b0: Byte = 16
            tessellator1.setColorRGBA_F(afloat[0], afloat[1], afloat[2], 0.0f)

            for (j in 0..b0) {
                f11 = j.toFloat() * Math.PI.toFloat() * 2.0f / b0.toFloat()
                val f12 = MathHelper.sin(f11)
                val f13 = MathHelper.cos(f11)
                tessellator1.addVertex((f12 * 120.0f).toDouble(), (f13 * 120.0f).toDouble(), (-f13 * 40.0f * afloat[3]).toDouble())
            }

            tessellator1.draw()
            GlStateManager.popMatrix()
            GlStateManager.shadeModel(GL11.GL_FLAT)
        }

        val starBrightness = world.getStarBrightness(partialTicks)

        GlStateManager.enableBlend()

        GlStateManager.enableTexture()

        //Stars
        if (starBrightness > 0.0f) {
            GlStateManager.disableTexture()
            GlStateManager.enableBlend()
            GlStateManager.enableAlphaTest()
            //GlStateManager.depthMask(true)
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
            GlStateManager.alphaFunc(GL11.GL_LEQUAL, 1F)
            GlStateManager.color4f(starBrightness, starBrightness, starBrightness, starBrightness)
            GL11Utils.callList(this.starGLCallList)
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F)
            GlStateManager.disableAlphaTest()
            GlStateManager.enableTexture()
        }
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)

        //Sun blue:

        GlStateManager.pushMatrix()
        f4 = 1.0f
        f10 = 30.0f

        GlStateManager.color4f(1.0f, 1.0f, 1.0f, f4)
        GlStateManager.translatef(0f, -6f, -3f)
        GlStateManager.rotatef(-90.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotatef(world.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f)
        mc.getTextureManager().bindTexture(sunTexture2)
        tessellator1.startDrawingQuads()
        tessellator1.addVertexWithUV((-f10).toDouble(), 50.0, (-f10).toDouble(), 0.0, 0.0)
        tessellator1.addVertexWithUV(f10.toDouble(), 50.0, (-f10).toDouble(), 1.0, 0.0)
        tessellator1.addVertexWithUV(f10.toDouble(), 50.0, f10.toDouble(), 1.0, 1.0)
        tessellator1.addVertexWithUV((-f10).toDouble(), 50.0, f10.toDouble(), 0.0, 1.0)
        tessellator1.draw()

        GlStateManager.popMatrix()

        GlStateManager.disableBlend()

        // Sun green:
        GlStateManager.enableTexture()
        GlStateManager.pushMatrix()
        f7 = 0.0f
        f8 = 0.0f
        f9 = 3.0f
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1f)
        GlStateManager.translatef(f7, f8, f9)
        GlStateManager.rotatef(-90.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotatef(world.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f)
        f10 = 4.0f
        mc.getTextureManager().bindTexture(sunTexture1)
        tessellator1.startDrawingQuads()
        tessellator1.addVertexWithUV((-f10).toDouble(), 50.0, (-f10).toDouble(), 0.0, 0.0)
        tessellator1.addVertexWithUV(f10.toDouble(), 50.0, (-f10).toDouble(), 1.0, 0.0)
        tessellator1.addVertexWithUV(f10.toDouble(), 50.0, f10.toDouble(), 1.0, 1.0)
        tessellator1.addVertexWithUV((-f10).toDouble(), 50.0, f10.toDouble(), 0.0, 1.0)
        tessellator1.draw()

        GlStateManager.popMatrix()


        GlStateManager.pushMatrix()

        GlStateManager.disableBlend()

        //Moon
        var12 = 10.5f
        GlStateManager.rotatef(-90.0f, 0.0f, 1.0f, 0.0f)
        GlStateManager.rotatef(world.getCelestialAngle(partialTicks) * 360.0f, 1.0f, 0.0f, 0.0f)
        mc.getTextureManager().bindTexture(moonTexture)
        GlStateManager.color4f(0.8f, 0.8f, 0.8f, 1.0f)
        tessellator.startDrawingQuads()
        tessellator.addVertexWithUV((-var12).toDouble(), -100.0, var12.toDouble(), 0.0, 1.0)
        tessellator.addVertexWithUV(var12.toDouble(), -100.0, var12.toDouble(), 1.0, 1.0)
        tessellator.addVertexWithUV(var12.toDouble(), -100.0, (-var12).toDouble(), 1.0, 0.0)
        tessellator.addVertexWithUV((-var12).toDouble(), -100.0, (-var12).toDouble(), 0.0, 0.0)
        tessellator.draw()

        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.disableBlend()
        GlStateManager.enableAlphaTest()
        GlStateManager.enableFog()
        GlStateManager.popMatrix()

        //Stuff
        GlStateManager.enableBlend()
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)

        GlStateManager.disableBlend()
        GlStateManager.enableAlphaTest()
        GlStateManager.enableFog()
        GlStateManager.disableTexture()
        GL11Utils.color3f(0.0f, 0.0f, 0.0f)
        val var25 = (mc.player?.getEyePosition(partialTicks)?.y ?: 0.0) - world.horizonHeight

        if (var25 < 0.0) {
            GlStateManager.pushMatrix()
            GlStateManager.translatef(0.0f, 12.0f, 0.0f)
            GL11Utils.callList(this.glSkyList2)
            GlStateManager.popMatrix()
            var10 = 1.0f
            var11 = -(var25 + 65.0).toFloat()
            var12 = -var10
            tessellator.startDrawingQuads()
            tessellator.setColorRGBA_I(0, 255)
            tessellator.addVertex((-var10).toDouble(), var11.toDouble(), var10.toDouble())
            tessellator.addVertex(var10.toDouble(), var11.toDouble(), var10.toDouble())
            tessellator.addVertex(var10.toDouble(), var12.toDouble(), var10.toDouble())
            tessellator.addVertex((-var10).toDouble(), var12.toDouble(), var10.toDouble())
            tessellator.addVertex((-var10).toDouble(), var12.toDouble(), (-var10).toDouble())
            tessellator.addVertex(var10.toDouble(), var12.toDouble(), (-var10).toDouble())
            tessellator.addVertex(var10.toDouble(), var11.toDouble(), (-var10).toDouble())
            tessellator.addVertex((-var10).toDouble(), var11.toDouble(), (-var10).toDouble())
            tessellator.addVertex(var10.toDouble(), var12.toDouble(), (-var10).toDouble())
            tessellator.addVertex(var10.toDouble(), var12.toDouble(), var10.toDouble())
            tessellator.addVertex(var10.toDouble(), var11.toDouble(), var10.toDouble())
            tessellator.addVertex(var10.toDouble(), var11.toDouble(), (-var10).toDouble())
            tessellator.addVertex((-var10).toDouble(), var11.toDouble(), (-var10).toDouble())
            tessellator.addVertex((-var10).toDouble(), var11.toDouble(), var10.toDouble())
            tessellator.addVertex((-var10).toDouble(), var12.toDouble(), var10.toDouble())
            tessellator.addVertex((-var10).toDouble(), var12.toDouble(), (-var10).toDouble())
            tessellator.addVertex((-var10).toDouble(), var12.toDouble(), (-var10).toDouble())
            tessellator.addVertex((-var10).toDouble(), var12.toDouble(), var10.toDouble())
            tessellator.addVertex(var10.toDouble(), var12.toDouble(), var10.toDouble())
            tessellator.addVertex(var10.toDouble(), var12.toDouble(), (-var10).toDouble())
            tessellator.draw()
        }

        GL11Utils.color3f(70f / 256f, 70f / 256f, 70f / 256f)

        GlStateManager.pushMatrix()
        GlStateManager.translatef(0.0f, -(var25 - 16.0).toFloat(), 0.0f)
        GL11Utils.callList(this.glSkyList2)
        GlStateManager.popMatrix()
        GlStateManager.enableTexture()
        GlStateManager.depthMask(true)
        GlStateManager.popMatrix()
        GlStateManager.popMatrix()
    } ?: Unit

    private fun renderStars() {
        val var1 = Random(10842L)
        val var2 = TessellatorCompat.instance
        var2.startDrawingQuads()

        val starMoltitude = false

        for (var3 in 0 until if (starMoltitude) 35000 else 6000) {
            var var4 = (var1.nextFloat() * 2.0f - 1.0f).toDouble()
            var var6 = (var1.nextFloat() * 2.0f - 1.0f).toDouble()
            var var8 = (var1.nextFloat() * 2.0f - 1.0f).toDouble()
            val var10 = (0.15f + var1.nextFloat() * 0.1f).toDouble()
            var var12 = var4 * var4 + var6 * var6 + var8 * var8

            if (var12 < 1.0 && var12 > 0.01) {
                var12 = 1.0 / sqrt(var12)
                var4 *= var12
                var6 *= var12
                var8 *= var12
                val var14 = var4 * if (starMoltitude) var1.nextDouble() * 150.0 + 130.0 else 100.0
                val var16 = var6 * if (starMoltitude) var1.nextDouble() * 150.0 + 130.0 else 100.0
                val var18 = var8 * if (starMoltitude) var1.nextDouble() * 150.0 + 130.0 else 100.0
                val var20 = atan2(var4, var8)
                val var22 = sin(var20)
                val var24 = cos(var20)
                val var26 = atan2(sqrt(var4 * var4 + var8 * var8), var6)
                val var28 = sin(var26)
                val var30 = cos(var26)
                val var32 = var1.nextDouble() * Math.PI * 2.0
                val var34 = sin(var32)
                val var36 = cos(var32)

                for (var38 in 0..3) {
                    val var39 = 0.0
                    val var41 = ((var38 and 2) - 1) * var10
                    val var43 = ((var38 + 1 and 2) - 1) * var10
                    val var47 = var41 * var36 - var43 * var34
                    val var49 = var43 * var36 + var41 * var34
                    val var53 = var47 * var28 + var39 * var30
                    val var55 = var39 * var28 - var47 * var30
                    val var57 = var55 * var22 - var49 * var24
                    val var61 = var49 * var22 + var55 * var24
                    var2.addVertex(var14 + var57, var16 + var53, var18 + var61)
                }
            }
        }

        var2.draw()
    }

    fun getSkyBrightness(par1: Float): Float {
        val var2 = Minecraft.getInstance().world?.getCelestialAngle(par1) ?: 0F
        var var3 = 1.0f - (MathHelper.sin(var2 * Math.PI.toFloat() * 2.0f) * 2.0f + 0.25f)

        if (var3 < 0.0f) {
            var3 = 0.0f
        }

        if (var3 > 1.0f) {
            var3 = 1.0f
        }

        return var3 * var3 * 1f
    }
}