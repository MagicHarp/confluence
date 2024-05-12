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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

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

    public ItemStack assemble(Container container, Level level) {
        return assemble(container, level.registryAccess());
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
            if (nonNullList.isEmpty()) throw new JsonParseException("No ingredients for altar recipe");
            return new AltarRecipe(pRecipeId, group, result, nonNullList);
        }

        @Override
        public @Nullable AltarRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
//            String group = pBuffer.readUtf();
//            int size = pBuffer.readVarInt();
//            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, AmountIngredient.EMPTY);
//            ingredients.replaceAll(ignored -> AmountIngredient.Serializer.INSTANCE.parse(pBuffer));
//            ItemStack result = pBuffer.readItem();
//            return new AltarRecipe(pRecipeId, group, result, ingredients);
            return null;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf pBuffer, @NotNull AltarRecipe pRecipe) {
//            pBuffer.writeUtf(pRecipe.group);
//            pBuffer.writeVarInt(pRecipe.ingredients.size());
//            for (Ingredient ingredient : pRecipe.ingredients) {
//                ingredient.toNetwork(pBuffer);
//            }
//            pBuffer.writeItem(pRecipe.result);
        }
    }

    public static class Type implements RecipeType<AltarRecipe> {
        @Override
        public String toString() {
            return "confluence:altar_type";
        }
    }
}
