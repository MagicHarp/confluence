package org.confluence.mod.item.potion;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.harmful.PotionSicknessEffect;
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
        PotionSicknessEffect.addTo(living, 1200);
        return super.finishUsingItem(itemStack, level, living);
    }

    @Override
    protected boolean canUse(Level level, Player player, InteractionHand hand) {
        return !player.hasEffect(ModEffects.POTION_SICKNESS.get());
    }
}
