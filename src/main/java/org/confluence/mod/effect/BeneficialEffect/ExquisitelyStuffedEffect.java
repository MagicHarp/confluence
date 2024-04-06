package org.confluence.mod.effect.BeneficialEffect;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class ExquisitelyStuffedEffect extends MobEffect {   //吃得好/很满意/酒足饭饱
    private static final UUID SPEED_UUID = UUID.fromString("3043D990-320B-2E80-FD32-FDB6BBCDC503");
    private static final ImmutableMultimap<Attribute, AttributeModifier> SPEED_MODIFIER = ImmutableMultimap.of(
            Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Exquisitely Stuffed Speed", 0.05, AttributeModifier.Operation.ADDITION)
    );

    public ExquisitelyStuffedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFF00);
    }

    public void onAdd(LivingEntity entity, MobEffect mobEffect, AttributeMap attributeMap, int time, int amplifier) {
        if (entity instanceof Player && mobEffect == ModEffects.EXQUISITELY_STUFFED.get()) {
            attributeMap.addTransientAttributeModifiers(SPEED_MODIFIER);
            entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, time, amplifier, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, time, amplifier, false, false));
        }
    }

    public void onRemove(LivingEntity entity, MobEffect mobEffect, AttributeMap attributeMap) {
        if (entity instanceof Player && mobEffect == ModEffects.EXQUISITELY_STUFFED.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(SPEED_UUID);
                entity.removeEffect(ModEffects.EXQUISITELY_STUFFED.get());
            }
        }
    }
}
