package org.confluence.mod.item.curio.expert;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.misc.ModTags;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrainOfConfusion extends BaseCurioItem implements ModRarity.Expert {
    public BrainOfConfusion() {
        super(ModRarity.EXPERT);
    }

    public static float apply(LivingEntity living, RandomSource randomSource, DamageSource damageSource, float amount) {
        if (damageSource.is(ModTags.HARMFUL_EFFECT)) return amount;
        if (CuriosUtils.noSameCurio(living, CurioItems.BRAIN_OF_CONFUSION.get())) return amount;
        if (randomSource.nextFloat() < 0.6F + amount * 0.02F) {
            float rangeMin, rangeMax;
            if (amount <= 120) rangeMin = amount * 0.5F + 200;
            else if (amount <= 266.6F) rangeMin = amount * 0.375F + 275;
            else if (amount <= 440) rangeMin = amount * 0.1875F + 487.5F;
            else rangeMin = amount * 0.046875F + 796.875F;
            if (amount <= 20) rangeMax = amount * 2 + 300;
            else if (amount <= 46.6F) rangeMax = amount * 1.5F + 350;
            else if (amount <= 100) rangeMax = amount * 0.75F + 525;
            else rangeMax = amount * 0.1875F + 806.25F;
            float range = ModUtils.nextFloat(randomSource, rangeMin, rangeMax) / 24;
            int duration = randomSource.nextInt((int) ((90 + amount / 3) / 50), (int) ((300 + amount / 2) / 50));
            living.level().getEntitiesOfClass(Monster.class, new AABB(living.getOnPos()).inflate(range))
                .forEach(monster -> monster.addEffect(new MobEffectInstance(ModEffects.CONFUSED.get(), duration)));
        }
        if (randomSource.nextFloat() < 0.1667F && !living.hasEffect(ModEffects.CEREBRAL_MINDTRICK.get())) {
            living.addEffect(new MobEffectInstance(ModEffects.CEREBRAL_MINDTRICK.get(), 80));
            return 0.0F;
        }
        return amount;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.brain_of_confusion.tooltip"));
        list.add(Component.translatable("item.confluence.brain_of_confusion.tooltip2"));
        list.add(Component.translatable("item.confluence.brain_of_confusion.tooltip3"));
    }
}
