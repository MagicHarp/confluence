package org.confluence.mod.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class WorkshopRecipe extends AbstractAmountRecipe {
    public WorkshopRecipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pResult, pIngredients);
    }

    @Override
    public @NotNull String getGroup() {
        return "workshop";
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.WORKSHOP.get().asItem());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.WORKSHOP_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.WORKSHOP_TYPE.get();
    }

    public static class Serializer extends AbstractAmountRecipe.Serializer<WorkshopRecipe> {
        @Override
        protected WorkshopRecipe newInstance(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
            return new WorkshopRecipe(pId, pResult, pIngredients);
        }
    }

    public static class Type implements RecipeType<WorkshopRecipe> {
        @Override
        public String toString() {
            return "confluence:workshop_type";
        }
    }
}
