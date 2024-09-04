package org.confluence.mod.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.recipe.AbstractAmountRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AmountResultSlot extends Slot {
    private final CraftingContainer crafting;
    private @Nullable AbstractAmountRecipe recipe;

    public AmountResultSlot(CraftingContainer crafting, Container pContainer, int pSlot, int pX, int pY) {
        super(pContainer, pSlot, pX, pY);
        this.crafting = crafting;
    }

    public void setCurrentRecipe(@Nullable AbstractAmountRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack pStack) {
        return false;
    }

    @Override
    public void onTake(@NotNull Player pPlayer, @NotNull ItemStack pStack) {
        if (recipe != null) AbstractAmountRecipe.extractIngredients(crafting, recipe.getIngredients());
    }
}
