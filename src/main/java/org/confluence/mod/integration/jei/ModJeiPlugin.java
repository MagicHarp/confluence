package org.confluence.mod.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.fluid.ShimmerItemTransmutationEvent;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.recipe.AmountIngredient;
import org.confluence.mod.recipe.ModRecipes;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(Confluence.MODID, "jei_plugin");
    public static final ResourceLocation ARROW_DOWN = new ResourceLocation(Confluence.MODID, "textures/gui/arrow_down.png");
    public static final ResourceLocation ARROW_RIGHT = new ResourceLocation(Confluence.MODID, "textures/gui/arrow_right.png");
    public static final JeiBackGround FULL_BACKGROUND = new JeiBackGround(128, 128);
    public static final JeiBackGround HALF_BACKGROUND = new JeiBackGround(128, 64);
    public static final JeiBackGround QUARTER_BACKGROUND = new JeiBackGround(128, 32);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        registration.addRecipeCategories(new ShimmerItemTransmutationCategory(jeiHelpers));
        registration.addRecipeCategories(new SkyMillCategory(jeiHelpers));
        registration.addRecipeCategories(new AltarCategory(jeiHelpers));
        registration.addRecipeCategories(new WorkshopCategory(jeiHelpers));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        for (CurioItems curio : CurioItems.values()) {
            BaseCurioItem item = curio.get();
            Component[] information = item.getInformation();
            if (information.length == 0) continue;
            registration.addItemStackInfo(new ItemStack(item), information);
        }
        registration.addRecipes(ShimmerItemTransmutationCategory.TYPE, ShimmerItemTransmutationEvent.ITEM_TRANSMUTATION);
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        RecipeManager recipeManager = level.getRecipeManager();
        registration.addRecipes(SkyMillCategory.TYPE, recipeManager.getAllRecipesFor(ModRecipes.SKY_MILL_TYPE.get()));
        registration.addRecipes(AltarCategory.TYPE, recipeManager.getAllRecipesFor(ModRecipes.ALTAR_TYPE.get()));
        registration.addRecipes(WorkshopCategory.TYPE, recipeManager.getAllRecipesFor(ModRecipes.WORKSHOP_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()), ShimmerItemTransmutationCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SKY_MILL.get()), SkyMillCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DEMON_ALTAR.get()), AltarCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CRIMSON_ALTAR.get()), AltarCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WORKSHOP.get()), WorkshopCategory.TYPE);
    }

    public static void drawArrowDown(GuiGraphics guiGraphics, int x, int y, boolean usable) {
        guiGraphics.blit(ARROW_DOWN, x, y, usable ? 0 : 21, 0, 21, 28, 42, 42);
    }

    public static void drawArrowRight(GuiGraphics guiGraphics, int x, int y, boolean usable) {
        guiGraphics.blit(ARROW_RIGHT, x, y, 0, usable ? 0 : 21, 28, 21, 42, 42);
    }

    public static void addInput(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient) {
        if (!ingredient.isEmpty() && ingredient instanceof AmountIngredient amountIngredient) {
            builder.addSlot(RecipeIngredientRole.INPUT, x, y).addItemStack(amountIngredient.getItemStack());
        }
    }
}
