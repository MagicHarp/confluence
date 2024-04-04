package org.confluence.mod.effect.BeneficialEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class HoneyEffect extends MobEffect {
    /**
     * 蜂蜜是一种增益，当玩家站在深度超过 5/8 格的蜂蜜（填充半物块和斜坡的蜂蜜也计入深度）中、戴着蜂窝或其升级物、或使用瓶装蜂蜜时可获得。
     * <p>
     * 此增益能提供每秒 +1 生命值的生命再生提升，并让自然治疗速率提高到三倍。
     * <p>
     * 此外，它还能将诸如毒液之类的生命流失减益的伤害降低每秒 2 生命值。
     * <p>
     * 在增益持续期间，蜂蜜增益具有黄色的“液滴”粒子效果。
     */
    public HoneyEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFF00);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity living, int amplifier) {
        living.heal(1.0F);
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        return tick % 20 == 0;
    }
}
