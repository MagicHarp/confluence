package org.confluence.mod.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public final class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Confluence.MODID);

    public static final RegistryObject<RecipeSerializer<ExtraStepStoolRecipe>> EXTRA_STEP_STOOL_SERIALIZER = SERIALIZERS.register("extra_step_stool", () -> new SimpleSmithingTransformRecipeSerializer<>(ExtraStepStoolRecipe::new));
}
