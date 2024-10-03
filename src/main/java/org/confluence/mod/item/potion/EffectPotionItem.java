package org.confluence.mod.item.potion;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class EffectPotionItem extends AbstractPotionItem {
    public final Supplier<? extends MobEffect> mobEffect;
    public final int duration;
    public final int amplifier;

    public EffectPotionItem(Properties properties, Supplier<? extends MobEffect> mobEffect, int duration, int amplifier) {
        super(properties);
        this.mobEffect = mobEffect;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public EffectPotionItem(Rarity rarity, Supplier<? extends MobEffect> mobEffect, int duration) {
        this(new Properties().rarity(rarity), mobEffect, duration, 0);
    }

    public EffectPotionItem(Rarity rarity, Supplier<? extends MobEffect> mobEffect, int duration, int amplifier) {
        this(new Properties().rarity(rarity), mobEffect, duration, amplifier);
    }

    public EffectPotionItem(Supplier<? extends MobEffect> mobEffect, int duration) {
        this(new Properties().rarity(ModRarity.BLUE), mobEffect, duration, 0);
    }

    public EffectPotionItem(Supplier<? extends MobEffect> mobEffect, int duration, int amplifier) {
        this(new Properties().rarity(ModRarity.BLUE), mobEffect, duration, amplifier);
    }

    @Override
    protected void apply(ItemStack itemStack, Level level, LivingEntity living) {
        living.addEffect(new MobEffectInstance(mobEffect.get(), duration, amplifier));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("info.confluence.potion_mana", mobEffect.get().getColor()));
    }
}
