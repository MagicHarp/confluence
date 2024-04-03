package org.confluence.mod.effect;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.item.magic.IMagicAttack;

public class ManaIssueEffect extends MobEffect {
    public ManaIssueEffect() {
        super(MobEffectCategory.HARMFUL, 0x0000FF);
    }

    public static float apply(DamageSource damageSource, float amount) {
        float multiply = 1.0F;
        if (damageSource.getEntity() instanceof Player player) {
            MobEffectInstance manaIssue = player.getEffect(ModEffects.MANA_ISSUE.get());
            if (manaIssue != null && (damageSource.is(DamageTypes.MAGIC) || player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof IMagicAttack)) {
                int duration = manaIssue.getDuration();
                if (duration == -1) {
                    multiply = 0.5F;
                } else if (duration <= 100) {
                    multiply = 0.75F;
                } else {
                    multiply = 0.75F - 0.05F * Math.round((duration - 100) / 20.0F);
                }
            }
        }
        return amount * multiply;
    }
}
