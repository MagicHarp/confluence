package org.confluence.mod.item.potion;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.util.PlayerUtils;
import org.jetbrains.annotations.NotNull;

public class ManaPotion extends AbstractPotion {
    private final int amount;

    public ManaPotion(int amount, Properties properties) {
        super(properties);
        this.amount = amount;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity living) {
        if (level.isClientSide) return itemStack;
        if (living instanceof ServerPlayer serverPlayer) PlayerUtils.receiveMana(serverPlayer, () -> amount);
        return super.finishUsingItem(itemStack, level, living);
    }
}
