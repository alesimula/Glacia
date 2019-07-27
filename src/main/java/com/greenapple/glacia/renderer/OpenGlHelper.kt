package com.greenapple.glacia.renderer

import net.minecraft.client.GameSettings
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer
import net.minecraft.client.Minecraft
import org.lwjgl.opengl.*

object OpenGlHelper {
    var openGL21: Boolean = false
    /**
     * An OpenGL constant corresponding to GL_TEXTURE0, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    var defaultTexUnit: Int = 0
    /**
     * An OpenGL constant corresponding to GL_TEXTURE1, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    var lightmapTexUnit: Int = 0
    var field_153197_d: Boolean = false
    var field_153198_e: Int = 0
    var field_153199_f: Int = 0
    var field_153200_g: Int = 0
    var field_153201_h: Int = 0
    var field_153202_i: Int = 0
    var field_153203_j: Int = 0
    var field_153204_k: Int = 0
    var field_153205_l: Int = 0
    var field_153206_m: Int = 0
    private var field_153212_w: Int = 0
    var framebufferSupported: Boolean = false
    private var field_153213_x: Boolean = false
    private var field_153214_y: Boolean = false
    var field_153207_o: Int = 0
    var field_153208_p: Int = 0
    var field_153209_q: Int = 0
    var field_153210_r: Int = 0
    var anisotropicFilteringSupported: Boolean = false
    var anisotropicFilteringMax: Int = 0
    private var field_153215_z: Boolean = false
    private var openGL14: Boolean = false
    var field_153211_u: Boolean = false
    var shadersSupported: Boolean = false
    private var field_153196_B = ""
    private val __OBFID = "CL_00001179"

    /* Stores the last values sent into setLightmapTextureCoords */
    var lastBrightnessX = 0.0f
    var lastBrightnessY = 0.0f

    /*TODO ? val isFramebufferEnabled: Boolean
        get() = framebufferSupported && Minecraft.getInstance().gameSettings.fboEnable*/

    /**
     * Initializes the texture constants to be used when rendering lightmap values
     */
    fun initializeTextures() {
        val contextcapabilities = GL.getCapabilities()
        field_153215_z = contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13

        if (field_153215_z) {
            field_153196_B = field_153196_B + "Using multitexturing ARB.\n"
            defaultTexUnit = 33984
            lightmapTexUnit = 33985
        } else {
            field_153196_B = field_153196_B + "Using GL 1.3 multitexturing.\n"
            defaultTexUnit = 33984
            lightmapTexUnit = 33985
        }

        field_153211_u = contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14
        openGL14 = contextcapabilities.OpenGL14 || contextcapabilities.GL_EXT_blend_func_separate
        framebufferSupported = openGL14 && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30)

        if (framebufferSupported) {
            field_153196_B = field_153196_B + "Using framebuffer objects because "

            if (contextcapabilities.OpenGL30) {
                field_153196_B = field_153196_B + "OpenGL 3.0 is supported and separate blending is supported.\n"
                field_153212_w = 0
                field_153198_e = 36160
                field_153199_f = 36161
                field_153200_g = 36064
                field_153201_h = 36096
                field_153202_i = 36053
                field_153203_j = 36054
                field_153204_k = 36055
                field_153205_l = 36059
                field_153206_m = 36060
            } else if (contextcapabilities.GL_ARB_framebuffer_object) {
                field_153196_B = field_153196_B + "ARB_framebuffer_object is supported and separate blending is supported.\n"
                field_153212_w = 1
                field_153198_e = 36160
                field_153199_f = 36161
                field_153200_g = 36064
                field_153201_h = 36096
                field_153202_i = 36053
                field_153204_k = 36055
                field_153203_j = 36054
                field_153205_l = 36059
                field_153206_m = 36060
            } else if (contextcapabilities.GL_EXT_framebuffer_object) {
                field_153196_B = field_153196_B + "EXT_framebuffer_object is supported.\n"
                field_153212_w = 2
                field_153198_e = 36160
                field_153199_f = 36161
                field_153200_g = 36064
                field_153201_h = 36096
                field_153202_i = 36053
                field_153204_k = 36055
                field_153203_j = 36054
                field_153205_l = 36059
                field_153206_m = 36060
            }
        } else {
            field_153196_B = field_153196_B + "Not using framebuffer objects because "
            field_153196_B = field_153196_B + "OpenGL 1.4 is " + (if (contextcapabilities.OpenGL14) "" else "not ") + "supported, "
            field_153196_B = field_153196_B + "EXT_blend_func_separate is " + (if (contextcapabilities.GL_EXT_blend_func_separate) "" else "not ") + "supported, "
            field_153196_B = field_153196_B + "OpenGL 3.0 is " + (if (contextcapabilities.OpenGL30) "" else "not ") + "supported, "
            field_153196_B = field_153196_B + "ARB_framebuffer_object is " + (if (contextcapabilities.GL_ARB_framebuffer_object) "" else "not ") + "supported, and "
            field_153196_B = field_153196_B + "EXT_framebuffer_object is " + (if (contextcapabilities.GL_EXT_framebuffer_object) "" else "not ") + "supported.\n"
        }

        anisotropicFilteringSupported = contextcapabilities.GL_EXT_texture_filter_anisotropic
        anisotropicFilteringMax = (if (anisotropicFilteringSupported) GL11.glGetFloat(34047) else 0.0f).toInt()
        field_153196_B = field_153196_B + "Anisotropic filtering is " + (if (anisotropicFilteringSupported) "" else "not ") + "supported"

        if (anisotropicFilteringSupported) {
            field_153196_B = "$field_153196_B and maximum anisotropy is $anisotropicFilteringMax.\n"
        } else {
            field_153196_B = "$field_153196_B.\n"
        }

        ///TODO ? GameSettings.ANISOTROPIC_FILTERING.setValueMax(anisotropicFilteringMax.toFloat())
        openGL21 = contextcapabilities.OpenGL21
        field_153213_x = openGL21 || contextcapabilities.GL_ARB_vertex_shader && contextcapabilities.GL_ARB_fragment_shader && contextcapabilities.GL_ARB_shader_objects
        field_153196_B = field_153196_B + "Shaders are " + (if (field_153213_x) "" else "not ") + "available because "

        if (field_153213_x) {
            if (contextcapabilities.OpenGL21) {
                field_153196_B = field_153196_B + "OpenGL 2.1 is supported.\n"
                field_153214_y = false
                field_153207_o = 35714
                field_153208_p = 35713
                field_153209_q = 35633
                field_153210_r = 35632
            } else {
                field_153196_B = field_153196_B + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n"
                field_153214_y = true
                field_153207_o = 35714
                field_153208_p = 35713
                field_153209_q = 35633
                field_153210_r = 35632
            }
        } else {
            field_153196_B = field_153196_B + "OpenGL 2.1 is " + (if (contextcapabilities.OpenGL21) "" else "not ") + "supported, "
            field_153196_B = field_153196_B + "ARB_shader_objects is " + (if (contextcapabilities.GL_ARB_shader_objects) "" else "not ") + "supported, "
            field_153196_B = field_153196_B + "ARB_vertex_shader is " + (if (contextcapabilities.GL_ARB_vertex_shader) "" else "not ") + "supported, and "
            field_153196_B = field_153196_B + "ARB_fragment_shader is " + (if (contextcapabilities.GL_ARB_fragment_shader) "" else "not ") + "supported.\n"
        }

        shadersSupported = framebufferSupported && field_153213_x
        field_153197_d = GL11.glGetString(GL11.GL_VENDOR)!!.toLowerCase().contains("nvidia")
    }

    fun func_153193_b(): Boolean {
        return shadersSupported
    }

    fun func_153172_c(): String {
        return field_153196_B
    }

    fun func_153175_a(p_153175_0_: Int, p_153175_1_: Int): Int {
        return if (field_153214_y) ARBShaderObjects.glGetObjectParameteriARB(p_153175_0_, p_153175_1_) else GL20.glGetProgrami(p_153175_0_, p_153175_1_)
    }

    fun func_153178_b(p_153178_0_: Int, p_153178_1_: Int) {
        if (field_153214_y) {
            ARBShaderObjects.glAttachObjectARB(p_153178_0_, p_153178_1_)
        } else {
            GL20.glAttachShader(p_153178_0_, p_153178_1_)
        }
    }

    fun func_153180_a(p_153180_0_: Int) {
        if (field_153214_y) {
            ARBShaderObjects.glDeleteObjectARB(p_153180_0_)
        } else {
            GL20.glDeleteShader(p_153180_0_)
        }
    }

    fun func_153195_b(p_153195_0_: Int): Int {
        return if (field_153214_y) ARBShaderObjects.glCreateShaderObjectARB(p_153195_0_) else GL20.glCreateShader(p_153195_0_)
    }

    fun func_153169_a(p_153169_0_: Int, p_153169_1_: ByteBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glShaderSourceARB(p_153169_0_, p_153169_1_.asCharBuffer())
        } else {
            GL20.glShaderSource(p_153169_0_, p_153169_1_.asCharBuffer())
        }
    }

    fun func_153170_c(p_153170_0_: Int) {
        if (field_153214_y) {
            ARBShaderObjects.glCompileShaderARB(p_153170_0_)
        } else {
            GL20.glCompileShader(p_153170_0_)
        }
    }

    fun func_153157_c(p_153157_0_: Int, p_153157_1_: Int): Int {
        return if (field_153214_y) ARBShaderObjects.glGetObjectParameteriARB(p_153157_0_, p_153157_1_) else GL20.glGetShaderi(p_153157_0_, p_153157_1_)
    }

    fun func_153158_d(p_153158_0_: Int, p_153158_1_: Int): String {
        return if (field_153214_y) ARBShaderObjects.glGetInfoLogARB(p_153158_0_, p_153158_1_) else GL20.glGetShaderInfoLog(p_153158_0_, p_153158_1_)
    }

    fun func_153166_e(p_153166_0_: Int, p_153166_1_: Int): String {
        return if (field_153214_y) ARBShaderObjects.glGetInfoLogARB(p_153166_0_, p_153166_1_) else GL20.glGetProgramInfoLog(p_153166_0_, p_153166_1_)
    }

    fun func_153161_d(p_153161_0_: Int) {
        if (field_153214_y) {
            ARBShaderObjects.glUseProgramObjectARB(p_153161_0_)
        } else {
            GL20.glUseProgram(p_153161_0_)
        }
    }

    fun func_153183_d(): Int {
        return if (field_153214_y) ARBShaderObjects.glCreateProgramObjectARB() else GL20.glCreateProgram()
    }

    fun func_153187_e(p_153187_0_: Int) {
        if (field_153214_y) {
            ARBShaderObjects.glDeleteObjectARB(p_153187_0_)
        } else {
            GL20.glDeleteProgram(p_153187_0_)
        }
    }

    fun func_153179_f(p_153179_0_: Int) {
        if (field_153214_y) {
            ARBShaderObjects.glLinkProgramARB(p_153179_0_)
        } else {
            GL20.glLinkProgram(p_153179_0_)
        }
    }

    fun func_153194_a(p_153194_0_: Int, p_153194_1_: CharSequence): Int {
        return if (field_153214_y) ARBShaderObjects.glGetUniformLocationARB(p_153194_0_, p_153194_1_) else GL20.glGetUniformLocation(p_153194_0_, p_153194_1_)
    }

    fun func_153181_a(p_153181_0_: Int, p_153181_1_: IntBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform1ivARB(p_153181_0_, p_153181_1_)
        } else {
            GL20.glUniform1iv(p_153181_0_, p_153181_1_)
        }
    }

    fun func_153163_f(p_153163_0_: Int, p_153163_1_: Int) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform1iARB(p_153163_0_, p_153163_1_)
        } else {
            GL20.glUniform1i(p_153163_0_, p_153163_1_)
        }
    }

    fun func_153168_a(p_153168_0_: Int, p_153168_1_: FloatBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform1fvARB(p_153168_0_, p_153168_1_)
        } else {
            GL20.glUniform1fv(p_153168_0_, p_153168_1_)
        }
    }

    fun func_153182_b(p_153182_0_: Int, p_153182_1_: IntBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform2ivARB(p_153182_0_, p_153182_1_)
        } else {
            GL20.glUniform2iv(p_153182_0_, p_153182_1_)
        }
    }

    fun func_153177_b(p_153177_0_: Int, p_153177_1_: FloatBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform2fvARB(p_153177_0_, p_153177_1_)
        } else {
            GL20.glUniform2fv(p_153177_0_, p_153177_1_)
        }
    }

    fun func_153192_c(p_153192_0_: Int, p_153192_1_: IntBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform3ivARB(p_153192_0_, p_153192_1_)
        } else {
            GL20.glUniform3iv(p_153192_0_, p_153192_1_)
        }
    }

    fun func_153191_c(p_153191_0_: Int, p_153191_1_: FloatBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform3fvARB(p_153191_0_, p_153191_1_)
        } else {
            GL20.glUniform3fv(p_153191_0_, p_153191_1_)
        }
    }

    fun func_153162_d(p_153162_0_: Int, p_153162_1_: IntBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform4ivARB(p_153162_0_, p_153162_1_)
        } else {
            GL20.glUniform4iv(p_153162_0_, p_153162_1_)
        }
    }

    fun func_153159_d(p_153159_0_: Int, p_153159_1_: FloatBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniform4fvARB(p_153159_0_, p_153159_1_)
        } else {
            GL20.glUniform4fv(p_153159_0_, p_153159_1_)
        }
    }

    fun func_153173_a(p_153173_0_: Int, p_153173_1_: Boolean, p_153173_2_: FloatBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniformMatrix2fvARB(p_153173_0_, p_153173_1_, p_153173_2_)
        } else {
            GL20.glUniformMatrix2fv(p_153173_0_, p_153173_1_, p_153173_2_)
        }
    }

    fun func_153189_b(p_153189_0_: Int, p_153189_1_: Boolean, p_153189_2_: FloatBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniformMatrix3fvARB(p_153189_0_, p_153189_1_, p_153189_2_)
        } else {
            GL20.glUniformMatrix3fv(p_153189_0_, p_153189_1_, p_153189_2_)
        }
    }

    fun func_153160_c(p_153160_0_: Int, p_153160_1_: Boolean, p_153160_2_: FloatBuffer) {
        if (field_153214_y) {
            ARBShaderObjects.glUniformMatrix4fvARB(p_153160_0_, p_153160_1_, p_153160_2_)
        } else {
            GL20.glUniformMatrix4fv(p_153160_0_, p_153160_1_, p_153160_2_)
        }
    }

    fun func_153164_b(p_153164_0_: Int, p_153164_1_: CharSequence): Int {
        return if (field_153214_y) ARBVertexShader.glGetAttribLocationARB(p_153164_0_, p_153164_1_) else GL20.glGetAttribLocation(p_153164_0_, p_153164_1_)
    }

    fun func_153171_g(p_153171_0_: Int, p_153171_1_: Int) {
        if (framebufferSupported) {
            when (field_153212_w) {
                0 -> GL30.glBindFramebuffer(p_153171_0_, p_153171_1_)
                1 -> ARBFramebufferObject.glBindFramebuffer(p_153171_0_, p_153171_1_)
                2 -> EXTFramebufferObject.glBindFramebufferEXT(p_153171_0_, p_153171_1_)
            }
        }
    }

    fun func_153176_h(p_153176_0_: Int, p_153176_1_: Int) {
        if (framebufferSupported) {
            when (field_153212_w) {
                0 -> GL30.glBindRenderbuffer(p_153176_0_, p_153176_1_)
                1 -> ARBFramebufferObject.glBindRenderbuffer(p_153176_0_, p_153176_1_)
                2 -> EXTFramebufferObject.glBindRenderbufferEXT(p_153176_0_, p_153176_1_)
            }
        }
    }

    fun func_153184_g(p_153184_0_: Int) {
        if (framebufferSupported) {
            when (field_153212_w) {
                0 -> GL30.glDeleteRenderbuffers(p_153184_0_)
                1 -> ARBFramebufferObject.glDeleteRenderbuffers(p_153184_0_)
                2 -> EXTFramebufferObject.glDeleteRenderbuffersEXT(p_153184_0_)
            }
        }
    }

    fun func_153174_h(p_153174_0_: Int) {
        if (framebufferSupported) {
            when (field_153212_w) {
                0 -> GL30.glDeleteFramebuffers(p_153174_0_)
                1 -> ARBFramebufferObject.glDeleteFramebuffers(p_153174_0_)
                2 -> EXTFramebufferObject.glDeleteFramebuffersEXT(p_153174_0_)
            }
        }
    }

    fun func_153165_e(): Int {
        return if (!framebufferSupported) {
            -1
        } else {
            when (field_153212_w) {
                0 -> GL30.glGenFramebuffers()
                1 -> ARBFramebufferObject.glGenFramebuffers()
                2 -> EXTFramebufferObject.glGenFramebuffersEXT()
                else -> -1
            }
        }
    }

    fun func_153185_f(): Int {
        return if (!framebufferSupported) {
            -1
        } else {
            when (field_153212_w) {
                0 -> GL30.glGenRenderbuffers()
                1 -> ARBFramebufferObject.glGenRenderbuffers()
                2 -> EXTFramebufferObject.glGenRenderbuffersEXT()
                else -> -1
            }
        }
    }

    fun func_153186_a(p_153186_0_: Int, p_153186_1_: Int, p_153186_2_: Int, p_153186_3_: Int) {
        if (framebufferSupported) {
            when (field_153212_w) {
                0 -> GL30.glRenderbufferStorage(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_)
                1 -> ARBFramebufferObject.glRenderbufferStorage(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_)
                2 -> EXTFramebufferObject.glRenderbufferStorageEXT(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_)
            }
        }
    }

    fun func_153190_b(p_153190_0_: Int, p_153190_1_: Int, p_153190_2_: Int, p_153190_3_: Int) {
        if (framebufferSupported) {
            when (field_153212_w) {
                0 -> GL30.glFramebufferRenderbuffer(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_)
                1 -> ARBFramebufferObject.glFramebufferRenderbuffer(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_)
                2 -> EXTFramebufferObject.glFramebufferRenderbufferEXT(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_)
            }
        }
    }

    fun func_153167_i(p_153167_0_: Int): Int {
        return if (!framebufferSupported) {
            -1
        } else {
            when (field_153212_w) {
                0 -> GL30.glCheckFramebufferStatus(p_153167_0_)
                1 -> ARBFramebufferObject.glCheckFramebufferStatus(p_153167_0_)
                2 -> EXTFramebufferObject.glCheckFramebufferStatusEXT(p_153167_0_)
                else -> -1
            }
        }
    }

    fun func_153188_a(p_153188_0_: Int, p_153188_1_: Int, p_153188_2_: Int, p_153188_3_: Int, p_153188_4_: Int) {
        if (framebufferSupported) {
            when (field_153212_w) {
                0 -> GL30.glFramebufferTexture2D(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_)
                1 -> ARBFramebufferObject.glFramebufferTexture2D(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_)
                2 -> EXTFramebufferObject.glFramebufferTexture2DEXT(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_)
            }
        }
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    fun setActiveTexture(p_77473_0_: Int) {
        if (field_153215_z) {
            ARBMultitexture.glActiveTextureARB(p_77473_0_)
        } else {
            GL13.glActiveTexture(p_77473_0_)
        }
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    fun setClientActiveTexture(p_77472_0_: Int) {
        if (field_153215_z) {
            ARBMultitexture.glClientActiveTextureARB(p_77472_0_)
        } else {
            GL13.glClientActiveTexture(p_77472_0_)
        }
    }

    /**
     * Sets the current coordinates of the given lightmap texture
     */
    fun setLightmapTextureCoords(p_77475_0_: Int, p_77475_1_: Float, p_77475_2_: Float) {
        if (field_153215_z) {
            ARBMultitexture.glMultiTexCoord2fARB(p_77475_0_, p_77475_1_, p_77475_2_)
        } else {
            GL13.glMultiTexCoord2f(p_77475_0_, p_77475_1_, p_77475_2_)
        }

        if (p_77475_0_ == lightmapTexUnit) {
            lastBrightnessX = p_77475_1_
            lastBrightnessY = p_77475_2_
        }
    }

    fun glBlendFunc(p_148821_0_: Int, p_148821_1_: Int, p_148821_2_: Int, p_148821_3_: Int) {
        if (openGL14) {
            if (field_153211_u) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_)
            } else {
                GL14.glBlendFuncSeparate(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_)
            }
        } else {
            GL11.glBlendFunc(p_148821_0_, p_148821_1_)
        }
    }
}