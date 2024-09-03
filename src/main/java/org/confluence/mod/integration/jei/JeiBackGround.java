package org.confluence.mod.integration.jei;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public class JeiBackGround implements IDrawable {
    private final int width;
    private final int height;

    public JeiBackGround(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void draw(@NotNull GuiGraphics guiGraphics, int i, int i1) {

    }
}
