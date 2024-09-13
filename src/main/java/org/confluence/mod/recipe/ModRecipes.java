package org.confluence.mod.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;

public final class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Confluence.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Confluence.MODID);

    public static final RegistryObject<RecipeType<AltarRecipe>> ALTAR_TYPE = TYPES.register("altar_type", AltarRecipe.Type::new);
    public static final RegistryObject<RecipeSerializer<AltarRecipe>> ALTAR_SERIALIZER = SERIALIZERS.register("altar", AltarRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<SkyMillRecipe>> SKY_MILL_TYPE = TYPES.register("sky_mill_type", SkyMillRecipe.Type::new);
    public static final RegistryObject<RecipeSerializer<SkyMillRecipe>> SKY_MILL_SERIALIZER = SERIALIZERS.register("sky_mill", SkyMillRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<WorkshopRecipe>> WORKSHOP_TYPE = TYPES.register("workshop_type", WorkshopRecipe.Type::new);
    public static final RegistryObject<RecipeSerializer<WorkshopRecipe>> WORKSHOP_SERIALIZER = SERIALIZERS.register("workshop", WorkshopRecipe.Serializer::new);

    public static final RegistryObject<RecipeSerializer<ExtraStepStoolRecipe>> EXTRA_STEP_STOOL_SERIALIZER = SERIALIZERS.register("extra_step_stool", () -> new SimpleSmithingTransformRecipeSerializer<>(ExtraStepStoolRecipe::new));

    public static void registerSerializers(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> {
            CraftingHelper.register(Confluence.asResource("amount"), AmountIngredient.Serializer.INSTANCE);
        });
    }

    public static void register(IEventBus bus) {
        SERIALIZERS.register(bus);
        TYPES.register(bus);
    }
}
