package org.confluence.mod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.confluence.mod.client.handler.InformationHandler;

@OnlyIn(Dist.CLIENT)
public class ConfluenceOverlays {
    private static final int background = (0x90 << 24) + 0x505050;
    private static final int textColor = 0xE0E0E0;
    public static final IGuiOverlay INFO_HUD = (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        if (gui.getMinecraft().options.hideGui || gui.getMinecraft().options.renderDebug) return;
        gui.setupOverlayRenderState(true, false);
        gui.getMinecraft().getProfiler().push("info");
        RenderSystem.enableBlend();

        int top = screenHeight / 2;
        Font font = gui.getFont();
        for (Component info : InformationHandler.getInformation()) {
            int w = font.width(info);
            int left = screenWidth - 2 - w;
            guiGraphics.fill(left - 1, top - 1, left + w + 1, top + font.lineHeight - 1, background);
            guiGraphics.drawString(font, info, left, top, textColor, false);
            top += font.lineHeight;
        }

        RenderSystem.disableBlend();
        gui.getMinecraft().getProfiler().pop();
    };
}
