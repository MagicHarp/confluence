package org.confluence.mod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.RecipeMatcher;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class AltarRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final String group;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients;

    public AltarRecipe(ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.group = pGroup;
        this.result = pResult;
        this.ingredients = pIngredients;
    }

    @Override
    public boolean matches(@NotNull Container pContainer, @NotNull Level pLevel) {
        if (pContainer instanceof Inventory inventory) return matchInventory(inventory);

        ArrayList<ItemStack> inputs = new ArrayList<>();
        int i = 0;
        for (int j = 0; j < pContainer.getContainerSize(); j++) {
            ItemStack itemstack = pContainer.getItem(j);
            if (!itemstack.isEmpty()) {
                inputs.add(itemstack);
                i++;
            }
        }
        return i == ingredients.size() && RecipeMatcher.findMatches(inputs, ingredients) != null;
    }

    public boolean matchInventory(Inventory inventory) {
        for (Ingredient ingredient : ingredients) {
            boolean matches = false;
            for (ItemStack itemStack : inventory.items) {
                if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                    matches = true;
                    break;
                }
            }
            if (!matches) return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    public ItemStack assembleAndShrink(ArrayList<ItemStack> itemStacks) {
        for (int index : RecipeMatcher.findMatches(itemStacks, ingredients)) {
            ItemStack itemStack = itemStacks.get(index);
            itemStack.shrink(((AmountIngredient) ingredients.get(index)).getCount());
            if (itemStack.isEmpty()) itemStacks.remove(index);
        }
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
        return result;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.ALTAR_SERIALIZER.get();
    }

    @Override
    public @NotNull String getGroup() {
        return group;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipes.ALTAR_TYPE.get();
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.CORRUPT_GRASS_BLOCK.get());
    }

    public static class Serializer implements RecipeSerializer<AltarRecipe> {
        @Override
        public @NotNull AltarRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pJson) {
            String group = GsonHelper.getAsString(pJson, "group", "");
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pJson, "result"), true, true);
            JsonArray ingredients = GsonHelper.getAsJsonArray(pJson, "ingredients");
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(ingredients.size(), AmountIngredient.EMPTY);
            for (int i = 0; i < ingredients.size(); ++i) {
                JsonElement jsonElement = ingredients.get(i);
                JsonObject json = GsonHelper.convertToJsonObject(jsonElement.getAsJsonObject(), "confluence:amount");
                nonNullList.replaceAll(ignored -> AmountIngredient.Serializer.INSTANCE.parse(json));
            }
            if (nonNullList.isEmpty()) throw new JsonParseException("No ingredients for altar recipe");
            return new AltarRecipe(pRecipeId, group, result, nonNullList);
        }

        @Override
        public @Nullable AltarRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            int size = pBuffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, AmountIngredient.EMPTY);
            ingredients.replaceAll(ignored -> AmountIngredient.Serializer.INSTANCE.parse(pBuffer));
            ItemStack result = pBuffer.readItem();
            return new AltarRecipe(pRecipeId, group, result, ingredients);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, @NotNull AltarRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for (Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.result);
        }
    }

    public static class Type implements RecipeType<AltarRecipe> {
        @Override
        public String toString() {
            return "confluence:altar";
        }
    }
}
