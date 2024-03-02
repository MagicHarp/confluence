package org.confluence.mod.item;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ConfluenceTiers implements Tier {
    COPPER(250, 4, 1, 1, 5, () -> Ingredient.of(Items.COPPER_INGOT)),
    TIN(270, 4, 1, 1, 5, () -> Ingredient.of(ConfluenceItems.TIN_INGOT.get())),
    WOLFRAM(286, 6, 2, 2, 14, () -> Ingredient.of(ConfluenceItems.WOLFRAM_INGOT.get())),
    SILVER(304, 6, 2, 2, 14, () -> Ingredient.of(ConfluenceItems.SILVER_INGOT.get())),
    PLATINUM(1661, 12, 0, 0, 22, () -> Ingredient.of(ConfluenceItems.PLATINUM_INGOT.get()));

    private final int uses;
    private final float speed;
    private final float damage;
    private final int level;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ConfluenceTiers(int uses, float speed, float damage, int level, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.level = level;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
