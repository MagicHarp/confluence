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
    private static final ResourceLocation BG_LOCATION = new ResourceLocation(Confluence.MODID, "textures/gui/container/workshop.png");

    public WorkshopScreen(WorkshopMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BG_LOCATION, leftPos, topPos, 0, 0, 176, 166);
    }
}
