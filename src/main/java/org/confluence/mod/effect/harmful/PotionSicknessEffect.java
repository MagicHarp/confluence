package org.confluence.mod.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.HealthAndMana.PhilosophersStone;
import org.confluence.mod.util.CuriosUtils;

public class PotionSicknessEffect extends MobEffect { // 耐药性
    public PotionSicknessEffect() {
        super(MobEffectCategory.HARMFUL, 0xAA0000);
    }

    // 应当使用这个方法来给玩家添加效果
    public static void addTo(LivingEntity living, int duration) {
        if (CuriosUtils.hasCurio(living, PhilosophersStone.class)) duration *= 0.75;
        living.addEffect(new MobEffectInstance(ModEffects.POTION_SICKNESS.get(), duration));
    }
}
