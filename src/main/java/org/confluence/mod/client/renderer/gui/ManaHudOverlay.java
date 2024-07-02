package org.confluence.mod.client.renderer.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.confluence.mod.client.handler.ClientPacketHandler;

import static org.confluence.mod.Confluence.MODID;

public class ManaHudOverlay implements IGuiOverlay {
    public static final ResourceLocation STAR_SINGLE = new ResourceLocation(MODID, "textures/gui/screens/star_single.png");
    public static final ResourceLocation STAR_A = new ResourceLocation(MODID, "textures/gui/screens/star_a.png");
    public static final ResourceLocation STAR_C = new ResourceLocation(MODID, "textures/gui/screens/star_c.png");
    public static final ResourceLocation STAR_B = new ResourceLocation(MODID, "textures/gui/screens/star_b.png");
    public static final ResourceLocation STAR_FILL = new ResourceLocation(MODID, "textures/gui/screens/star_fill.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if (gui.getMinecraft().options.hideGui || !gui.shouldDrawSurvivalElements()) return;
        gui.setupOverlayRenderState(true, false);
        gui.getMinecraft().getProfiler().push("mana");

        int currentMana = ClientPacketHandler.getCurrentMana();
        int maxManaCount = ClientPacketHandler.getMaxMana() / 20;
        int currentManaCount = currentMana / 20;
        double lastMana = (currentMana - currentManaCount * 20.0) / 20.0;
        int manaCount = -1;
        if (maxManaCount >= 2) {
            manaCount = maxManaCount - 2;
        }
        if (maxManaCount == 1) {//如果只有一个魔力格
            guiGraphics.blit(STAR_SINGLE, screenWidth - 20, 10, 0, 0, 15, 16, 15, 16);
        } else {//如果不止一个魔力格
            //绘制魔力条顶部
            guiGraphics.blit(STAR_A, screenWidth - 20, 10, 0, 0, 15, 13, 15, 13);

            //绘制魔力格底部
            guiGraphics.blit(STAR_C, screenWidth - 20, 23 + manaCount * 11, 0, 0, 15, 14, 15, 14);

            //绘制魔力格中部
            while (manaCount > 0) {
                guiGraphics.blit(STAR_B, screenWidth - 20, 12 + manaCount * 11, 0, 0, 15, 11, 15, 11);
                manaCount -= 1;
            }
        }

        //绘制最下方不完整的星
        guiGraphics.blit(STAR_FILL, screenWidth - 18 + (int) ((11 - 11 * lastMana) / 2 + 0.5d), 12 + currentManaCount * 11 + (int) ((12 - 12 * lastMana) / 2 + 0.5d), 0, 0, ((int) (11 * lastMana)), ((int) (12 * lastMana)), ((int) (11 * lastMana)), ((int) (12 * lastMana)));

        //绘制完整的星
        while (currentManaCount > 0) {
            guiGraphics.blit(STAR_FILL, screenWidth - 18, 1 + currentManaCount * 11, 0, 0, 11, 12, 11, 12);
            currentManaCount -= 1;
        }

        gui.getMinecraft().getProfiler().pop();
    }
}
