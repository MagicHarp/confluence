package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.misc.ModDamageTypes;

public class FrostburnEffect extends MobEffect {    //霜冻：缓慢损失生命 每秒损失1.5点生命
    public FrostburnEffect() {
        super(MobEffectCategory.HARMFUL, 0xBBFFFF);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        living.hurt(ModDamageTypes.of(living.level(), ModDamageTypes.FROST_BURN), 1.5F);
    }
}
