package org.confluence.mod.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;

public class SimpleSmithingTransformRecipeSerializer<T extends SmithingTransformRecipe> implements RecipeSerializer<T> {
    private final Factory<T> constructor;

    public SimpleSmithingTransformRecipeSerializer(Factory<T> pConstructor) {
        this.constructor = pConstructor;
    }

    public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return constructor.create(pRecipeId);
    }

    public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        return constructor.create(pRecipeId);
    }

    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        pBuffer.writeResourceLocation(pRecipe.getId());
    }

    @FunctionalInterface
    public interface Factory<T extends SmithingTransformRecipe> {
        T create(ResourceLocation pId);
    }
}
