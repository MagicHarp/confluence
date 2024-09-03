package org.confluence.mod.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public class AltarRecipe extends AbstractAmountRecipe {
    public AltarRecipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        super(pId, pResult, pIngredients);
    }

    @Override
    protected int maxIngredientSize() {
        return 4;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALTAR_SERIALIZER.get();
    }

    @Override
    public @NotNull String getGroup() {
        return "altar";
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.ALTAR_TYPE.get();
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.CORRUPT_GRASS_BLOCK.get());
    }

    public static class Serializer extends AbstractAmountRecipe.Serializer<AltarRecipe> {
        @Override
        protected AltarRecipe newInstance(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
            return new AltarRecipe(pId, pResult, pIngredients);
        }
    }

    public static class Type implements RecipeType<AltarRecipe> {
        @Override
        public String toString() {
            return "confluence:altar_type";
        }
    }
}
