package org.confluence.mod.common.item.food;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

public class BaseFoodItem extends Item {
    public BaseFoodItem(FoodProperties foodProperties) {
        super(new Item.Properties().food(foodProperties).stacksTo(64));
    }

    public static class FireproofFoodItem extends Item {
        public FireproofFoodItem(FoodProperties foodProperties) {
            super(new Properties().food(foodProperties).stacksTo(64).fireResistant());
        }
    }

    public static class ContainerFoodItem extends Item {
        public ContainerFoodItem(FoodProperties foodProperties, Item craftingRemainingItem) {
            super(new Properties().food(foodProperties).craftRemainder(craftingRemainingItem).stacksTo(64));
        }

        @Override
        public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
            return 20;
        }

        @Override
        public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
            return UseAnim.DRINK;
        }

        @Override
        public @NotNull SoundEvent getDrinkingSound() {
            return SoundEvents.HONEY_DRINK;
        }

        @Override
        public @NotNull SoundEvent getEatingSound() {
            return SoundEvents.HONEY_DRINK;
        }
    }
}
