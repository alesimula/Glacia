package com.greenapple.glacia.renderer

import com.mojang.blaze3d.platform.GLX
import com.mojang.blaze3d.platform.GlStateManager
import org.lwjgl.opengl.GL11

object GL11Utils {
    @JvmStatic @Synchronized
    fun generateDisplayLists(range: Int): Int {
        val id: Int = GL11.glGenLists(range);
        return if (id == 0) {
            val errorCode = GlStateManager.getError()
            val error = if (errorCode != 0) GLX.getErrorString(errorCode) else "No error code reported"
            throw IllegalStateException("glGenLists returned an ID of 0 for a count of $range, GL error ($errorCode): $error")
        } else {
            id
        }
    }

    fun color3f(red: Float, green: Float, blue: Float) {
        GlStateManager.color4f(red, green, blue, 1.0f)
    }

    fun newList(id: Int, mode: Int) {
        GL11.glNewList(id, mode)
    }

    fun endList() {
        GL11.glEndList()
    }

    fun callList(id: Int) {
        GL11.glCallList(id)
    }
}