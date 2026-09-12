package org.confluence.mod.integration.jei.category;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.block.FunctionalBlocks;
import org.confluence.mod.common.recipe.HellforgeRecipe;

public class HellforgeCategory extends EnhancedForgeCategory<HellforgeRecipe> {
    public static final RecipeType<HellforgeRecipe> TYPE = new RecipeType<>(Confluence.asResource("hellforge"), HellforgeRecipe.class);

    public HellforgeCategory(IJeiHelpers jeiHelpers) {
        super(jeiHelpers, FunctionalBlocks.HELLFORGE.toStack());
    }

    @Override
    public RecipeType<HellforgeRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("title.confluence.hellforge");
    }
}
