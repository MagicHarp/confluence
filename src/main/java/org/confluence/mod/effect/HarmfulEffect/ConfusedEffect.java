package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ConfusedEffect extends MobEffect { //困惑 反向移动
    public ConfusedEffect() {
        super(MobEffectCategory.HARMFUL, 0x8B008B);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity living, int amplifier) {
        /* 未完成 */
    }
}
