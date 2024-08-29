package org.confluence.mod.item.curio.expert;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.misc.ModRarity;
import org.confluence.mod.util.CuriosUtils;
import org.confluence.mod.util.ModUtils;

public class BrainOfConfusion extends BaseCurioItem implements ModRarity.Expert {
    public BrainOfConfusion() {
        super(ModRarity.EXPERT);
    }

    public static float apply(LivingEntity living, RandomSource randomSource, float amount) {
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
            int duration = randomSource.nextInt((int) (90 + amount / 3), (int) (300 + amount / 2));
            living.level().getEntities(living, new AABB(living.getOnPos()).inflate(range), entity -> entity instanceof Enemy).forEach(enemy -> {
                if (enemy instanceof LivingEntity living1) {
                    living1.addEffect(new MobEffectInstance(ModEffects.CONFUSED.get(), duration));
                }
            });
        }
        if (randomSource.nextFloat() < 0.1667F && !living.hasEffect(ModEffects.CEREBRAL_MINDTRICK.get())) {
            living.addEffect(new MobEffectInstance(ModEffects.CEREBRAL_MINDTRICK.get(), 80));
            return 0.0F;
        }
        return amount;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.brain_of_confusion.info")
        };
    }
}
