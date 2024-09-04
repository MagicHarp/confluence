package org.confluence.mod.client.renderer.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.confluence.mod.Confluence;
import org.confluence.mod.menu.WorkshopMenu;
import org.jetbrains.annotations.NotNull;

public class WorkshopScreen extends AbstractContainerScreen<WorkshopMenu> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Confluence.MODID, "textures/gui/container/workshop.png");
    private static final ResourceLocation BLANK = new ResourceLocation(Confluence.MODID, "textures/gui/container/blank.png");

    public WorkshopScreen(WorkshopMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = -3;
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        int top = topPos - 9;
        int blankHeight = font.lineHeight + 3;
        pGuiGraphics.blit(BACKGROUND, leftPos, top, 0, 0, imageWidth, 4);
        pGuiGraphics.blit(BLANK, leftPos, top += 4, 0, 0, imageWidth, blankHeight, imageWidth, 1);
        pGuiGraphics.blit(BACKGROUND, leftPos, top + blankHeight, 0, 7, imageWidth, imageHeight - 7);
    }
}
