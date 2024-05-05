package org.confluence.mod.item.food;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public class PotionFoodItem extends Item {
    public PotionFoodItem(FoodProperties foodProperties) {
        super(new Properties().food(foodProperties).craftRemainder(GLASS_BOTTLE).stacksTo(64));
    }
    public int getUseDuration(ItemStack p_41360_) {
        return 20;
    }

    public UseAnim getUseAnimation(ItemStack p_41358_) {
        return UseAnim.DRINK;
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.WANDERING_TRADER_DRINK_POTION;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.WANDERING_TRADER_DRINK_POTION;
    }


}
