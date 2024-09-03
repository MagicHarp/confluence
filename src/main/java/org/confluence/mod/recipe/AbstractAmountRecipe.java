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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public abstract class AbstractAmountRecipe implements Recipe<Container> {
    protected final ResourceLocation id;
    protected final ItemStack result;
    protected final NonNullList<Ingredient> ingredients;

    protected AbstractAmountRecipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.result = pResult;
        this.ingredients = pIngredients;
    }

    @Override
    public boolean matches(@NotNull Container pContainer, @NotNull Level pLevel) {
        if (pContainer instanceof Inventory inventory) {
            found:
            for (Ingredient ingredient : ingredients) {
                for (ItemStack itemStack : inventory.items) {
                    if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                        continue found;
                    }
                }
                return false;
            }
            return true;
        }

        found:
        for (Ingredient ingredient : ingredients) {
            for (int index = 0; index < pContainer.getContainerSize(); index++) {
                ItemStack itemStack = pContainer.getItem(index);
                if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                    continue found;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container pContainer, @NotNull RegistryAccess pRegistryAccess) {
        if (pContainer instanceof Inventory inventory) {
            for (Ingredient ingredient : ingredients) {
                for (int index = 0; index < inventory.items.size(); index++) {
                    ItemStack itemStack = inventory.items.get(index);
                    if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                        pContainer.removeItem(index, ((AmountIngredient) ingredient).getCount());
                        break;
                    }
                }
            }
        } else {
            for (Ingredient ingredient : ingredients) {
                for (int index = 0; index < pContainer.getContainerSize(); index++) {
                    ItemStack itemStack = pContainer.getItem(index);
                    if (!itemStack.isEmpty() && ingredient.test(itemStack)) {
                        pContainer.removeItem(index, ((AmountIngredient) ingredient).getCount());
                        break;
                    }
                }
            }
        }
        return result.copy();
    }

    public ItemStack assemble(Container container, Level level) {
        return assemble(container, level.registryAccess());
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@Nullable RegistryAccess registryAccess) {
        return result;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    protected abstract int maxIngredientSize();

    public static abstract class Serializer<R extends AbstractAmountRecipe> implements RecipeSerializer<R> {
        protected abstract R newInstance(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients);

        @Override
        public @NotNull R fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pJson) {
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(pJson, "result"), true, true);
            JsonArray ingredients = GsonHelper.getAsJsonArray(pJson, "ingredients");
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(ingredients.size(), AmountIngredient.EMPTY);
            HashSet<Item> items = new HashSet<>();
            for (int i = 0; i < ingredients.size(); ++i) {
                JsonElement jsonElement = ingredients.get(i);
                JsonObject json = GsonHelper.convertToJsonObject(jsonElement.getAsJsonObject(), "confluence:amount");
                AmountIngredient ingredient = AmountIngredient.Serializer.INSTANCE.parse(json);
                Item item = ingredient.getItem();
                if (items.add(item)) {
                    nonNullList.set(i, ingredient);
                } else {
                    throw new IllegalArgumentException("Duplicate ingredient " + item);
                }
            }
            if (nonNullList.isEmpty()) throw new JsonParseException("No ingredients for " + pRecipeId);
            R recipe = newInstance(pRecipeId, result, nonNullList);
            if (ingredients.size() > recipe.maxIngredientSize()) throw new IndexOutOfBoundsException("The ingredient size of '" + pRecipeId + "' is out of 3");
            return recipe;
        }

        @Override
        public @Nullable R fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
            int size = pBuffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, AmountIngredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));
            ItemStack result = pBuffer.readItem();
            return newInstance(pRecipeId, result, ingredients);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, @NotNull R pRecipe) {
            pBuffer.writeVarInt(pRecipe.ingredients.size());
            for (Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.result);
        }
    }
}
