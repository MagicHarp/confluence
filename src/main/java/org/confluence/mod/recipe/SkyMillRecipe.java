package org.confluence.mod.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class SkyMillRecipe extends AbstractAmountRecipe {
    public SkyMillRecipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pResult, pIngredients);
    }

    @Override
    protected int maxIngredientSize() {
        return 3;
    }

    @Override
    public @NotNull String getGroup() {
        return "sky_mill";
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.SKY_MILL.get().asItem());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.SKY_MILL_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.SKY_MILL_TYPE.get();
    }

    public static class Serializer extends AbstractAmountRecipe.Serializer<SkyMillRecipe> {
        @Override
        protected SkyMillRecipe newInstance(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
            return new SkyMillRecipe(pId, pResult, pIngredients);
        }
    }

    public static class Type implements RecipeType<SkyMillRecipe> {
        @Override
        public String toString() {
            return "confluence:sky_mill_type";
        }
    }
}
