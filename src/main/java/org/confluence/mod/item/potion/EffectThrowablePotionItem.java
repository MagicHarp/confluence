package org.confluence.mod.item.potion;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.projectile.EffectThrownPotion;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class EffectThrowablePotionItem extends EffectPotionItem {
    public EffectThrowablePotionItem(Properties properties, Supplier<? extends MobEffect> mobEffect, int duration, int amplifier) {
        super(properties, mobEffect, duration, amplifier);
    }

    public EffectThrowablePotionItem(Rarity rarity, Supplier<? extends MobEffect> mobEffect, int duration) {
        super(rarity, mobEffect, duration);
    }

    public EffectThrowablePotionItem(Rarity rarity, Supplier<? extends MobEffect> mobEffect, int duration, int amplifier) {
        super(rarity, mobEffect, duration, amplifier);
    }

    public EffectThrowablePotionItem(Supplier<? extends MobEffect> mobEffect, int duration) {
        super(mobEffect, duration);
    }

    public EffectThrowablePotionItem(Supplier<? extends MobEffect> mobEffect, int duration, int amplifier) {
        super(mobEffect, duration, amplifier);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            EffectThrownPotion thrownpotion = new EffectThrownPotion(pPlayer, pLevel);
            thrownpotion.setItem(itemstack);
            thrownpotion.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), -20.0F, 0.5F, 1.0F);
            pLevel.addFreshEntity(thrownpotion);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
