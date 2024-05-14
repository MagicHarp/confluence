package org.confluence.mod.effect.beneficial;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;

public class ObsidianSkinEffect extends MobEffect {
    public ObsidianSkinEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x660066);
    }

    public static boolean isInvul(LivingEntity living, DamageSource damageSource) {
        return damageSource.is(DamageTypeTags.IS_FIRE) && living.hasEffect(ModEffects.OBSIDIAN_SKIN.get());
    }
}
