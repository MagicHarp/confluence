package org.confluence.mod.client.renderer.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.confluence.mod.client.ClientConfigs;
import org.confluence.mod.client.handler.InformationHandler;

public class InfoHudOverlay implements IGuiOverlay {
    private static final int background = (0x90 << 24) + 0x505050;
    private static final int textColor = 0xE0E0E0;

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (gui.getMinecraft().options.hideGui || gui.getMinecraft().options.renderDebug) return;
        gui.setupOverlayRenderState(true, false);
        gui.getMinecraft().getProfiler().push("info");

        int top = (int) (screenHeight * ClientConfigs.informationHudTop);
        Font font = gui.getFont();
        for (Component info : InformationHandler.getInformation()) {
            int left;
            int w = font.width(info);
            if (ClientConfigs.informationIsLeft) {
                left = 2;
            } else {
                left = screenWidth - 2 - w;
            }
            guiGraphics.fill(left - 1, top - 1, left + w + 1, top + font.lineHeight - 1, background);
            guiGraphics.drawString(font, info, left, top, textColor, false);
            top += font.lineHeight;
        }

        gui.getMinecraft().getProfiler().pop();
    }
}
