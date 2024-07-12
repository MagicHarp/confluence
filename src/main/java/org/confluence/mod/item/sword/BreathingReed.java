package org.confluence.mod.item.sword;

import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class BreathingReed extends ShortSwordItem {
    public BreathingReed() {
        super(new Tier() {
            @Override
            public int getUses() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 0;
            }

            @Override
            public float getAttackDamageBonus() {
                return 0;
            }

            @Override
            public int getLevel() {
                return 0;
            }

            @Override
            public int getEnchantmentValue() {
                return 0;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }
        }, 0, 0, new Properties().durability(0)); // todo
    }

    public static boolean hasReed(LivingEntity living) {
        return living.getItemInHand(InteractionHand.MAIN_HAND).is(Swords.BREATHING_REED.get()) || living.getItemInHand(InteractionHand.OFF_HAND).is(Swords.BREATHING_REED.get());
    }

    public static int getDecrease(RandomSource randomSource) {
        return randomSource.nextInt(3) > 0 ? 0 : 1;
    }

    public static float apply(LivingEntity living, DamageSource damageSource, float amount) {
        if (damageSource.is(DamageTypes.DROWN) && hasReed(living)) {
            return amount / 2.0F;
        }
        return amount;
    }
}
