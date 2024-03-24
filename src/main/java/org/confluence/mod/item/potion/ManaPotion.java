package org.confluence.mod.item.potion;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.effect.ModEffects;
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
        if (living instanceof ServerPlayer serverPlayer) {
            PlayerUtils.receiveMana(serverPlayer, () -> amount);
            MobEffectInstance instance = serverPlayer.getEffect(ModEffects.MANA_ISSUE.get());
            if (instance == null) {
                serverPlayer.addEffect(new MobEffectInstance(ModEffects.MANA_ISSUE.get(), 100));
            } else {
                instance.mapDuration(raw -> Math.min(raw + 100, 200));
                serverPlayer.addEffect(instance);
            }
        }
        return super.finishUsingItem(itemStack, level, living);
    }
}
