package com.greenapple.glacia.renderer

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer
import java.util.Arrays
import java.util.PriorityQueue
import com.greenapple.glacia.renderer.QuadComparator
import net.minecraft.client.renderer.GLAllocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import org.lwjgl.opengl.GL11

@OnlyIn(Dist.CLIENT)
class TesselatorVertexState(val rawBuffer: IntArray, val rawBufferIndex: Int, val vertexCount: Int, val hasTexture: Boolean, val hasBrightness: Boolean, val hasNormals: Boolean, val hasColor: Boolean)

@OnlyIn(Dist.CLIENT)
class TessellatorCompat {
    private val nativeBufferSize = 0x200000
    private val trivertsInBuffer = nativeBufferSize / 48 * 6
    var renderingWorldRenderer = false
    var defaultTexture = false
    private var rawBufferSize = 0
    var textureID = 0

    companion object {
        private val nativeBufferSize = 0x200000
        private val trivertsInBuffer = nativeBufferSize / 48 * 6
        var renderingWorldRenderer = false

        /** The byte buffer used for GL allocation.  */
        private val byteBuffer = GLAllocation.createDirectByteBuffer(nativeBufferSize * 4)
        /** The same memory as byteBuffer, but referenced as an integer buffer.  */
        private val intBuffer = byteBuffer.asIntBuffer()
        /** The same memory as byteBuffer, but referenced as an float buffer.  */
        private val floatBuffer = byteBuffer.asFloatBuffer()
        /** The same memory as byteBuffer, but referenced as an short buffer.  */
        private val shortBuffer = byteBuffer.asShortBuffer()
        /** The static instance of the Tessellator.  */
        @JvmStatic val instance = TessellatorCompat(2097152).apply {defaultTexture = true}
        private val __OBFID = "CL_00000960"
    }

    /** The byte buffer used for GL allocation.  */
    private val byteBuffer = GLAllocation.createDirectByteBuffer(nativeBufferSize * 4)
    /** The same memory as byteBuffer, but referenced as an integer buffer.  */
    private val intBuffer = byteBuffer.asIntBuffer()
    /** The same memory as byteBuffer, but referenced as an float buffer.  */
    private val floatBuffer = byteBuffer.asFloatBuffer()
    /** The same memory as byteBuffer, but referenced as an short buffer.  */
    private val shortBuffer = byteBuffer.asShortBuffer()
    /** Raw integer array.  */
    private var rawBuffer: IntArray? = null
    /** The number of vertices to be drawn in the next draw call. Reset to 0 between draw calls.  */
    private var vertexCount: Int = 0
    /** The first coordinate to be used for the texture.  */
    private var textureU: Double = 0.toDouble()
    /** The second coordinate to be used for the texture.  */
    private var textureV: Double = 0.toDouble()
    private var brightness: Int = 0
    /** The color (RGBA) value to be used for the following draw call.  */
    private var color: Int = 0
    /** Whether the current draw object for this tessellator has color values.  */
    private var hasColor: Boolean = false
    /** Whether the current draw object for this tessellator has texture coordinates.  */
    private var hasTexture: Boolean = false
    private var hasBrightness: Boolean = false
    /** Whether the current draw object for this tessellator has normal values.  */
    private var hasNormals: Boolean = false
    /** The index into the raw buffer to be used for the next data.  */
    private var rawBufferIndex: Int = 0
    /**
     * The number of vertices manually added to the given draw call. This differs from vertexCount because it adds extra
     * vertices when converting quads to triangles.
     */
    private var addedVertices: Int = 0
    /** Disables all color information for the following draw call.  */
    private var isColorDisabled: Boolean = false
    /** The draw mode currently being used by the tessellator.  */
    private var drawMode: Int = 0
    /** An offset to be applied along the x-axis for all vertices in this draw call.  */
    private var xOffset: Double = 0.toDouble()
    /** An offset to be applied along the y-axis for all vertices in this draw call.  */
    private var yOffset: Double = 0.toDouble()
    /** An offset to be applied along the z-axis for all vertices in this draw call.  */
    private var zOffset: Double = 0.toDouble()
    /** The normal to be applied to the face being drawn.  */
    private var normal: Int = 0
    /** Whether this tessellator is currently in draw mode.  */
    private var isDrawing: Boolean = false
    /** The size of the buffers used (in integers).  */
    private val bufferSize: Int = 0

    private constructor(p_i1250_1_: Int) {}

    constructor() {}

    /**
     * Draws the data set up in this tessellator and resets the state to prepare for new drawing.
     */
    fun draw(): Int {
        if (!this.isDrawing) {
            throw IllegalStateException("Not tesselating!")
        } else {
            this.isDrawing = false

            var offs = 0
            while (offs < vertexCount) {
                val vtc = Math.min(vertexCount - offs, nativeBufferSize shr 5)
                this.intBuffer.clear()
                this.intBuffer.put(this.rawBuffer!!, offs * 8, vtc * 8)
                this.byteBuffer.position(0)
                this.byteBuffer.limit(vtc * 32)
                offs += vtc

                if (this.hasTexture) {
                    this.floatBuffer.position(3)
                    GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, this.floatBuffer)
                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
                }

                if (this.hasBrightness) {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit)
                    this.shortBuffer.position(14)
                    GL11.glTexCoordPointer(2, GL11.GL_SHORT, 32, this.shortBuffer)
                    GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit)
                }

                if (this.hasColor) {
                    this.byteBuffer.position(20)
                    GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 32, this.byteBuffer)
                    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY)
                }

                if (this.hasNormals) {
                    this.byteBuffer.position(24)
                    GL11.glNormalPointer(32, GL11.GL_BYTE, this.byteBuffer)
                    GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY)
                }

                this.floatBuffer.position(0)
                GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, this.floatBuffer)
                GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY)
                GL11.glDrawArrays(this.drawMode, 0, vtc)
                GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY)

                if (this.hasTexture) {
                    GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
                }

                if (this.hasBrightness) {
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit)
                    GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY)
                    OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit)
                }

                if (this.hasColor) {
                    GL11.glDisableClientState(GL11.GL_COLOR_ARRAY)
                }

                if (this.hasNormals) {
                    GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY)
                }
            }

            if (rawBufferSize > 0x20000 && rawBufferIndex < rawBufferSize shl 3) {
                rawBufferSize = 0x10000
                rawBuffer = IntArray(rawBufferSize)
            }

            val i = this.rawBufferIndex * 4
            this.reset()
            return i
        }
    }

    fun getVertexState(p_147564_1_: Float, p_147564_2_: Float, p_147564_3_: Float): TesselatorVertexState {
        val aint = IntArray(this.rawBufferIndex)
        val priorityqueue = PriorityQueue(this.rawBufferIndex, QuadComparator(this.rawBuffer!!, p_147564_1_ + this.xOffset.toFloat(), p_147564_2_ + this.yOffset.toFloat(), p_147564_3_ + this.zOffset.toFloat()))
        val b0: Byte = 32
        var i: Int

        i = 0
        while (i < this.rawBufferIndex) {
            priorityqueue.add(Integer.valueOf(i))
            i += b0.toInt()
        }

        i = 0
        while (!priorityqueue.isEmpty()) {
            val j = (priorityqueue.remove() as Int).toInt()

            for (k in 0 until b0) {
                aint[i + k] = this.rawBuffer!![j + k]
            }
            i += b0.toInt()
        }

        System.arraycopy(aint, 0, this.rawBuffer!!, 0, aint.size)
        return TesselatorVertexState(aint, this.rawBufferIndex, this.vertexCount, this.hasTexture, this.hasBrightness, this.hasNormals, this.hasColor)
    }

    fun setVertexState(p_147565_1_: TesselatorVertexState) {
        while (p_147565_1_.rawBuffer.size > rawBufferSize && rawBufferSize > 0) {
            rawBufferSize = rawBufferSize shl 1
        }
        if (rawBufferSize > rawBuffer!!.size) {
            rawBuffer = IntArray(rawBufferSize)
        }
        System.arraycopy(p_147565_1_.rawBuffer, 0, this.rawBuffer!!, 0, p_147565_1_.rawBuffer.size)
        this.rawBufferIndex = p_147565_1_.rawBufferIndex
        this.vertexCount = p_147565_1_.vertexCount
        this.hasTexture = p_147565_1_.hasTexture
        this.hasBrightness = p_147565_1_.hasBrightness
        this.hasColor = p_147565_1_.hasColor
        this.hasNormals = p_147565_1_.hasNormals
    }

    /**
     * Clears the tessellator state in preparation for new drawing.
     */
    private fun reset() {
        this.vertexCount = 0
        this.byteBuffer.clear()
        this.rawBufferIndex = 0
        this.addedVertices = 0
    }

    /**
     * Sets draw mode in the tessellator to draw quads.
     */
    fun startDrawingQuads() {
        this.startDrawing(7)
    }

    /**
     * Resets tessellator state and prepares for drawing (with the specified draw mode).
     */
    fun startDrawing(p_78371_1_: Int) {
        if (this.isDrawing) {
            throw IllegalStateException("Already tesselating!")
        } else {
            this.isDrawing = true
            this.reset()
            this.drawMode = p_78371_1_
            this.hasNormals = false
            this.hasColor = false
            this.hasTexture = false
            this.hasBrightness = false
            this.isColorDisabled = false
        }
    }

    /**
     * Sets the texture coordinates.
     */
    fun setTextureUV(p_78385_1_: Double, p_78385_3_: Double) {
        this.hasTexture = true
        this.textureU = p_78385_1_
        this.textureV = p_78385_3_
    }

    fun setBrightness(p_78380_1_: Int) {
        this.hasBrightness = true
        this.brightness = p_78380_1_
    }

    /**
     * Sets the RGB values as specified, converting from floats between 0 and 1 to integers from 0-255.
     */
    fun setColorOpaque_F(p_78386_1_: Float, p_78386_2_: Float, p_78386_3_: Float) {
        this.setColorOpaque((p_78386_1_ * 255.0f).toInt(), (p_78386_2_ * 255.0f).toInt(), (p_78386_3_ * 255.0f).toInt())
    }

    /**
     * Sets the RGBA values for the color, converting from floats between 0 and 1 to integers from 0-255.
     */
    fun setColorRGBA_F(p_78369_1_: Float, p_78369_2_: Float, p_78369_3_: Float, p_78369_4_: Float) {
        this.setColorRGBA((p_78369_1_ * 255.0f).toInt(), (p_78369_2_ * 255.0f).toInt(), (p_78369_3_ * 255.0f).toInt(), (p_78369_4_ * 255.0f).toInt())
    }

    /**
     * Sets the RGB values as specified, and sets alpha to opaque.
     */
    fun setColorOpaque(p_78376_1_: Int, p_78376_2_: Int, p_78376_3_: Int) {
        this.setColorRGBA(p_78376_1_, p_78376_2_, p_78376_3_, 255)
    }

    /**
     * Sets the RGBA values for the color. Also clamps them to 0-255.
     */
    fun setColorRGBA(p_78370_1_: Int, p_78370_2_: Int, p_78370_3_: Int, p_78370_4_: Int) {
        var p_78370_1_ = p_78370_1_
        var p_78370_2_ = p_78370_2_
        var p_78370_3_ = p_78370_3_
        var p_78370_4_ = p_78370_4_
        if (!this.isColorDisabled) {
            if (p_78370_1_ > 255) {
                p_78370_1_ = 255
            }

            if (p_78370_2_ > 255) {
                p_78370_2_ = 255
            }

            if (p_78370_3_ > 255) {
                p_78370_3_ = 255
            }

            if (p_78370_4_ > 255) {
                p_78370_4_ = 255
            }

            if (p_78370_1_ < 0) {
                p_78370_1_ = 0
            }

            if (p_78370_2_ < 0) {
                p_78370_2_ = 0
            }

            if (p_78370_3_ < 0) {
                p_78370_3_ = 0
            }

            if (p_78370_4_ < 0) {
                p_78370_4_ = 0
            }

            this.hasColor = true

            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                this.color = p_78370_4_ shl 24 or (p_78370_3_ shl 16) or (p_78370_2_ shl 8) or p_78370_1_
            } else {
                this.color = p_78370_1_ shl 24 or (p_78370_2_ shl 16) or (p_78370_3_ shl 8) or p_78370_4_
            }
        }
    }

    fun func_154352_a(p_154352_1_: Byte, p_154352_2_: Byte, p_154352_3_: Byte) {
        this.setColorOpaque(p_154352_1_.toInt() and 255, p_154352_2_.toInt() and 255, p_154352_3_.toInt() and 255)
    }

    /**
     * Adds a vertex specifying both x,y,z and the texture u,v for it.
     */
    fun addVertexWithUV(p_78374_1_: Double, p_78374_3_: Double, p_78374_5_: Double, p_78374_7_: Double, p_78374_9_: Double) {
        this.setTextureUV(p_78374_7_, p_78374_9_)
        this.addVertex(p_78374_1_, p_78374_3_, p_78374_5_)
    }

    /**
     * Adds a vertex with the specified x,y,z to the current draw call. It will trigger a draw() if the buffer gets
     * full.
     */
    fun addVertex(p_78377_1_: Double, p_78377_3_: Double, p_78377_5_: Double) {
        if (rawBufferIndex >= rawBufferSize - 32) {
            if (rawBufferSize == 0) {
                rawBufferSize = 0x10000
                rawBuffer = IntArray(rawBufferSize)
            } else {
                rawBufferSize *= 2
                rawBuffer?.let { prevBuffer -> rawBuffer = (prevBuffer).copyOf(rawBufferSize) }
            }
        }
        ++this.addedVertices

        this.rawBuffer?.let { rawBuffer ->
            if (this.hasTexture) {
                rawBuffer[this.rawBufferIndex + 3] = java.lang.Float.floatToRawIntBits(this.textureU.toFloat())
                rawBuffer[this.rawBufferIndex + 4] = java.lang.Float.floatToRawIntBits(this.textureV.toFloat())
            }

            if (this.hasBrightness) {
                rawBuffer[this.rawBufferIndex + 7] = this.brightness
            }

            if (this.hasColor) {
                rawBuffer[this.rawBufferIndex + 5] = this.color
            }

            if (this.hasNormals) {
                rawBuffer[this.rawBufferIndex + 6] = this.normal
            }

            rawBuffer[this.rawBufferIndex + 0] = java.lang.Float.floatToRawIntBits((p_78377_1_ + this.xOffset).toFloat())
            rawBuffer[this.rawBufferIndex + 1] = java.lang.Float.floatToRawIntBits((p_78377_3_ + this.yOffset).toFloat())
            rawBuffer[this.rawBufferIndex + 2] = java.lang.Float.floatToRawIntBits((p_78377_5_ + this.zOffset).toFloat())
        }
        this.rawBufferIndex += 8
        ++this.vertexCount
    }

    /**
     * Sets the color to the given opaque value (stored as byte values packed in an integer).
     */
    fun setColorOpaque_I(p_78378_1_: Int) {
        val j = p_78378_1_ shr 16 and 255
        val k = p_78378_1_ shr 8 and 255
        val l = p_78378_1_ and 255
        this.setColorOpaque(j, k, l)
    }

    /**
     * Sets the color to the given color (packed as bytes in integer) and alpha values.
     */
    fun setColorRGBA_I(p_78384_1_: Int, p_78384_2_: Int) {
        val k = p_78384_1_ shr 16 and 255
        val l = p_78384_1_ shr 8 and 255
        val i1 = p_78384_1_ and 255
        this.setColorRGBA(k, l, i1, p_78384_2_)
    }

    /**
     * Disables colors for the current draw call.
     */
    fun disableColor() {
        this.isColorDisabled = true
    }

    /**
     * Sets the normal for the current draw call.
     */
    fun setNormal(p_78375_1_: Float, p_78375_2_: Float, p_78375_3_: Float) {
        this.hasNormals = true
        val b0 = (p_78375_1_ * 127.0f).toInt().toByte()
        val b1 = (p_78375_2_ * 127.0f).toInt().toByte()
        val b2 = (p_78375_3_ * 127.0f).toInt().toByte()
        this.normal = b0.toInt() and 255 or (b1.toInt() and 255 shl 8) or (b2.toInt() and 255 shl 16)
    }

    /**
     * Sets the translation for all vertices in the current draw call.
     */
    fun setTranslation(p_78373_1_: Double, p_78373_3_: Double, p_78373_5_: Double) {
        this.xOffset = p_78373_1_
        this.yOffset = p_78373_3_
        this.zOffset = p_78373_5_
    }

    /**
     * Offsets the translation for all vertices in the current draw call.
     */
    fun addTranslation(p_78372_1_: Float, p_78372_2_: Float, p_78372_3_: Float) {
        this.xOffset += p_78372_1_.toDouble()
        this.yOffset += p_78372_2_.toDouble()
        this.zOffset += p_78372_3_.toDouble()
    }
}