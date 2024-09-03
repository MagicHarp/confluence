package org.confluence.mod.integration.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.fluid.ShimmerItemTransmutationEvent;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class ShimmerItemTransmutationCategory implements IRecipeCategory<ShimmerItemTransmutationEvent.ItemTransmutation> {
    public static final RecipeType<ShimmerItemTransmutationEvent.ItemTransmutation> TYPE = RecipeType.create(Confluence.MODID, "item_transmutation", ShimmerItemTransmutationEvent.ItemTransmutation.class);
    private static final Component SHIMMER_TRANSMUTATION_TITLE = Component.translatable("title.confluence.shimmer_transmutation");
    private final IDrawable icon;

    public ShimmerItemTransmutationCategory(IJeiHelpers jeiHelpers) {
        this.icon = jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()));
    }

    @Override
    public @NotNull RecipeType<ShimmerItemTransmutationEvent.ItemTransmutation> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return SHIMMER_TRANSMUTATION_TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return ModJeiPlugin.FULL_BACKGROUND;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, ShimmerItemTransmutationEvent.@NotNull ItemTransmutation recipe, @NotNull IFocusGroup focuses) {
        // input
        ItemStack[] items = recipe.source().getItems();
        IRecipeSlotBuilder inputSlot = builder.addSlot(RecipeIngredientRole.INPUT, 56, 16);
        if (items.length > 1) {
            inputSlot.addIngredients(recipe.source());
        } else {
            ItemStack input = items.length == 0 ? new ItemStack(Items.BARRIER) : items[0].copy();
            input.setCount(recipe.shrink());
            inputSlot.addItemStack(input);
        }
        // output
        builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 88).addItemStacks(recipe.target());
    }

    @Override
    public void draw(ShimmerItemTransmutationEvent.@NotNull ItemTransmutation recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (ClientPacketHandler.getGamePhase().ordinal() < recipe.gamePhase().ordinal()) {
            ModJeiPlugin.drawArrowDown(guiGraphics, 54, 46, false);
            if (mouseX >= 54 && mouseX <= 75 && mouseY >= 46 && mouseY <= 74) {
                MutableComponent text = Component.translatable("condition.confluence.shimmer_transmutation", recipe.gamePhase()).withStyle(style -> style.withColor(ChatFormatting.RED));
                guiGraphics.renderTooltip(Minecraft.getInstance().font, text, (int) mouseX, (int) mouseY);
            }
        } else {
            ModJeiPlugin.drawArrowDown(guiGraphics, 54, 46, true);
        }
    }
}
