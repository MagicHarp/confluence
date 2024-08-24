package org.confluence.mod.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.curio.movement.StepStool;

public class ExtraStepStoolRecipe extends SmithingTransformRecipe {
    public ExtraStepStoolRecipe(ResourceLocation pId) {
        super(pId, Ingredient.EMPTY, Ingredient.EMPTY, Ingredient.of(CurioItems.STEP_STOOL.get()), new ItemStack(CurioItems.STEP_STOOL.get()));
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        ItemStack base = pContainer.getItem(1);
        ItemStack addition = pContainer.getItem(2);
        if (isBaseIngredient(base) && isAdditionIngredient(addition)) {
            return base.getOrCreateTag().getInt("extraStep") + addition.getOrCreateTag().getInt("extraStep") < 15;
        }
        return false;
    }

    @Override
    public boolean isBaseIngredient(ItemStack pStack) {
        return pStack.getItem() instanceof StepStool && pStack.getOrCreateTag().getInt("extraStep") < 15;
    }

    @Override
    public boolean isIncomplete() {
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        ItemStack base = pContainer.getItem(1).copyWithCount(1);
        int additional = pContainer.getItem(2).getOrCreateTag().getInt("extraStep");
        CompoundTag tag = base.getOrCreateTag();
        tag.putInt("extraStep", tag.getInt("extraStep") + additional + 1);
        return base;
    }
}
