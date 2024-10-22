package org.confluence.mod.client.renderer.gui;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import org.confluence.mod.client.handler.InformationHandler;

public class InfoHudOverlay implements LayeredDraw.Layer {
    private static final int background = (0x90 << 24) + 0x505050;
    private static final int textColor = 0xE0E0E0;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui || minecraft.getDebugOverlay().showDebugScreen()) return;
        GuiHelper.setupOverlayRenderState(true, false);
        minecraft.getProfiler().push("info");

        int screenWidth = guiGraphics.guiWidth();
        int top = guiGraphics.guiHeight() / 2;
        Font font = minecraft.font;
        for (Component info : InformationHandler.getInformation()) {
            int w = font.width(info);
            int left = screenWidth - 2 - w;
            guiGraphics.fill(left - 1, top - 1, left + w + 1, top + font.lineHeight - 1, background);
            guiGraphics.drawString(font, info, left, top, textColor, false);
            top += font.lineHeight;
        }

        minecraft.getProfiler().pop();
    }
}
