package org.confluence.mod.item.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.ModRarity;

import java.util.function.Supplier;

public class EffectPotionItem extends AbstractPotionItem {
    private final Supplier<? extends MobEffect> mobEffect;
    private final int duration;
    private final int amplifier;

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
}
