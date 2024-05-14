package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.capability.ability.AbilityProvider;

public class FishingEffect extends MobEffect {  //钓鱼药水
    public FishingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00BFFF);
    }

    public static void onAdd(LivingEntity entity) {
        if (entity instanceof Player && !entity.isSpectator()) {
            entity.getCapability(AbilityProvider.CAPABILITY)
                .ifPresent(playerAbility -> playerAbility.increaseFishingPower(1.0F));
        }
    }

    public static void onRemove(LivingEntity entity) {
        if (entity instanceof Player && !entity.isSpectator()) {
            entity.getCapability(AbilityProvider.CAPABILITY)
                .ifPresent(playerAbility -> playerAbility.increaseFishingPower(-1.0F));
        }
    }
}

