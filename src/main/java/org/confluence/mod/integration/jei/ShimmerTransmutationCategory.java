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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.handler.ClientPacketHandler;
import org.confluence.mod.fluid.ShimmerTransmutationEvent;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

public class ShimmerTransmutationCategory implements IRecipeCategory<ShimmerTransmutationEvent.Transmutation> {
    public static final RecipeType<ShimmerTransmutationEvent.Transmutation> TYPE = RecipeType.create(Confluence.MODID, "shimmer_transmutation", ShimmerTransmutationEvent.Transmutation.class);
    private static final Component TITLE = Component.translatable("title.confluence.shimmer_transmutation");
    private static final ResourceLocation ARROW = new ResourceLocation(Confluence.MODID, "textures/gui/arrow.png");
    private final IJeiHelpers jeiHelpers;

    public ShimmerTransmutationCategory(IJeiHelpers jeiHelpers) {
        this.jeiHelpers = jeiHelpers;
    }

    @Override
    public @NotNull RecipeType<ShimmerTransmutationEvent.Transmutation> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return TITLE;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return Background.INSTANCE;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return jeiHelpers.getGuiHelper().createDrawableItemStack(new ItemStack(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()));
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, ShimmerTransmutationEvent.@NotNull Transmutation recipe, @NotNull IFocusGroup focuses) {
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
        builder.addSlot(RecipeIngredientRole.OUTPUT, 56, 88)
            .addItemStacks(recipe.target());
    }

    @Override
    public void draw(ShimmerTransmutationEvent.@NotNull Transmutation recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (ClientPacketHandler.getGamePhase().ordinal() < recipe.gamePhase().ordinal()) {
            guiGraphics.blit(ARROW, 54, 46, 22, 0, 21, 28, 42, 42);
            if (mouseX >= 54 && mouseX <= 75 && mouseY >= 46 && mouseY <= 74) {
                MutableComponent text = Component.translatable("condition.confluence.shimmer_transmutation", recipe.gamePhase()).withStyle(style -> style.withColor(ChatFormatting.RED));
                guiGraphics.renderTooltip(Minecraft.getInstance().font, text, (int) mouseX, (int) mouseY);
            }
        } else {
            guiGraphics.blit(ARROW, 54, 46, 0, 0, 21, 28, 42, 42);
        }
    }

    public static class Background implements IDrawable {
        public static final Background INSTANCE = new Background();

        @Override
        public int getWidth() {
            return 128;
        }

        @Override
        public int getHeight() {
            return 128;
        }

        @Override
        public void draw(@NotNull GuiGraphics guiGraphics, int xOffset, int yOffset) {}
    }
}
