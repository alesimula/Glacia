package com.greenapple.glacia.advancement

import com.greenapple.glacia.Glacia
import com.greenapple.glacia.utils.selectedTabKt
import com.greenapple.glacia.utils.tabPageKt
import com.greenapple.glacia.utils.tabsKt
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.advancements.AdvancementsScreen
import net.minecraft.client.multiplayer.ClientAdvancementManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.resources.I18n
import net.minecraft.util.ResourceLocation

class GuiGlaciaAdvancementScreen(clientAdvancementManager: ClientAdvancementManager) : AdvancementsScreen(clientAdvancementManager) {
    companion object {
        private val WINDOW = ResourceLocation(Glacia.MODID, "textures/gui/advancements/window.png")
        private val TABS = ResourceLocation(Glacia.MODID, "textures/gui/advancements/tabs.png")
    }

    override fun renderWindow(p_191934_1_: Int, p_191934_2_: Int) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.enableBlend()
        minecraft!!.getTextureManager().bindTexture(WINDOW)
        this.blit(p_191934_1_, p_191934_2_, 0, 0, 252, 140)
        if (tabsKt.size > 1) {
            minecraft!!.getTextureManager().bindTexture(TABS)
            for (advancementtabgui in tabsKt.values) {
                if (advancementtabgui.page == tabPageKt) advancementtabgui.drawTab(p_191934_1_, p_191934_2_, advancementtabgui === selectedTabKt)
            }
            RenderSystem.enableRescaleNormal()
            RenderSystem.defaultBlendFunc()
            for (advancementtabgui1 in tabsKt.values) {
                if (advancementtabgui1.page == tabPageKt) advancementtabgui1.drawIcon(p_191934_1_, p_191934_2_, itemRenderer)
            }
            RenderSystem.disableBlend()
        }
        font.drawString(I18n.format("gui.advancements"), (p_191934_1_ + 8).toFloat(), (p_191934_2_ + 6).toFloat(), 4210752)
    }
}