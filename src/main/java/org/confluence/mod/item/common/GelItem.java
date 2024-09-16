package org.confluence.mod.item.common;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class GelItem extends ColoredItem{
    public GelItem(Properties pProperties) {
        super(pProperties);
    }

    public GelItem(Rarity rarity) {
        this(new Properties().rarity(rarity));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 100;
    }
}
