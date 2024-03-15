package org.confluence.mod.item.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HealingPotion extends AbstractPotion {
    private final int amount;

    public HealingPotion(int amount, Properties properties) {
        super(properties);
        this.amount = amount;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity living) {
        if (level.isClientSide) return itemStack;
        living.heal(amount);
        return super.finishUsingItem(itemStack, level, living);
    }
}
