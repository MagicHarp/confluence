package org.confluence.mod.item.food;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class BottleFoodItem extends Item {
    public BottleFoodItem(FoodProperties foodProperties) {
        super(new Properties().food(foodProperties).craftRemainder(GLASS_BOTTLE).stacksTo(64));
    }
    public int getUseDuration(ItemStack p_41360_) {
        return 20;
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }
}
