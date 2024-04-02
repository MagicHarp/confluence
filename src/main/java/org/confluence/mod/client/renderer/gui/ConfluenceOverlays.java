package org.confluence.mod.client.renderer.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.client.handler.InformationHandler;

import static org.confluence.mod.Confluence.MODID;

@OnlyIn(Dist.CLIENT)
public class ConfluenceOverlays {
    private static final ResourceLocation MANA_BAR = new ResourceLocation(MODID, "textures/gui/mana_bar.png");

    public static final IGuiOverlay HUD_MANA = (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        if (gui.getMinecraft().options.hideGui || !gui.shouldDrawSurvivalElements()) return;
        gui.setupOverlayRenderState(true, false);
        gui.getMinecraft().getProfiler().push("mana");
        RenderSystem.enableBlend();

        int left = screenWidth / 2 - 91;
        int top = screenHeight - gui.leftHeight;
        int currentMana = ClientPacketHandler.getCurrentMana();
        guiGraphics.blit(MANA_BAR, left, top, 0, 0, 82, 7, 82, 35);
        if (currentMana <= 82) {
            guiGraphics.blit(MANA_BAR, left, top, 0, 7, currentMana, 7, 82, 35);
        } else if (currentMana <= 164) {
            guiGraphics.blit(MANA_BAR, left, top, 0, 14, currentMana - 82, 7, 82, 35);
        } else if (currentMana <= 246) {
            guiGraphics.blit(MANA_BAR, left, top, 0, 21, currentMana - 164, 7, 82, 35);
        } else {
            int width = (int) Math.ceil((double) currentMana / ClientPacketHandler.getMaxMana()) * 82;
            guiGraphics.blit(MANA_BAR, left, top, 0, 28, width, 7, 82, 35);
        }

        RenderSystem.disableBlend();
        gui.getMinecraft().getProfiler().pop();
    };

    private static final int background = (0x90 << 24) + 0x505050;
    private static final int textColor = 0xE0E0E0;
    public static final IGuiOverlay INFO_HUD = (gui, guiGraphics, partialTicks, screenWidth, screenHeight) -> {
        if (gui.getMinecraft().options.hideGui) return;
        gui.setupOverlayRenderState(true, false);
        gui.getMinecraft().getProfiler().push("info");
        RenderSystem.enableBlend();

        int top = screenHeight / 2;
        Font font = gui.getFont();
        for (String info : InformationHandler.getInformation()) {
            if (info == null || info.isEmpty()) continue;
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
