package org.confluence.mod.integration.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.recipe.WorkshopRecipe;
import org.jetbrains.annotations.NotNull;

public class WorkshopCategory implements IRecipeCategory<WorkshopRecipe> {
    public static final RecipeType<WorkshopRecipe> TYPE = RecipeType.create(Confluence.MODID, "workshop", WorkshopRecipe.class);
    private static final Component TITLE = Component.translatable("title.confluence.workshop");
    private final IDrawable icon;

    public WorkshopCategory(IJeiHelpers jeiHelpers) {
        this.icon = jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(ModBlocks.WORKSHOP.get()));
    }

    @Override
    public @NotNull RecipeType<WorkshopRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return ModJeiPlugin.HALF_BACKGROUND;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull WorkshopRecipe recipe, @NotNull IFocusGroup focusGroup) {
        // input
        int size = recipe.getIngredients().size();
        int line = size / 3;
        boolean remain = size % 3 != 0;
        int x = 0;
        int y = 0;
        if (line == 0) {
            y = 24;
        } else if (line == 1) {
            y = remain ? 16 : 24;
        } else if (line == 2) {
            y = remain ? 8 : 16;
        } else if (line == 3) {
            y = remain ? 0 : 8;
        }
        for (Ingredient ingredient : recipe.getIngredients()) {
            ModJeiPlugin.addInput(builder, x, y, ingredient);
            x += 16;
            if (x == 48) {
                x = 0;
                y += 16;
            }
        }
        // output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 24).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(@NotNull WorkshopRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        ModJeiPlugin.drawArrowRight(guiGraphics, 50, 22, true);
    }
}
