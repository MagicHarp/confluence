package org.confluence.mod.effect.HarmfulEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import org.confluence.mod.event.EffectEvents;

public class BleedingEffect extends MobEffect { //流血 不能自然恢复生命
    EffectEvents effectEvents = new EffectEvents();
    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0xA52A2A);
    }

    public void onAdd(LivingEntity entity) {
        if (entity instanceof Player && !entity.isSpectator()) {
            effectEvents.noHealthBoost(new LivingHealEvent(entity, 1));
        }
    }
}
