package org.confluence.mod.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class ExquisitelyStuffedEffect extends MobEffect {   //吃得好/很满意/酒足饭饱
    private static final UUID SPEED_UUID = UUID.fromString("3043D990-320B-2E80-FD32-FDB6BBCDC503");
    private static final ImmutableMultimap<Attribute, AttributeModifier> SPEED_MODIFIER = ImmutableMultimap.of(
        Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Exquisitely Stuffed", 0.05, AttributeModifier.Operation.ADDITION)
    );

    public ExquisitelyStuffedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFF00);
    }

    public static void onAdd(LivingEntity living, MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.EXQUISITELY_STUFFED.get()) {
            attributeMap.addTransientAttributeModifiers(SPEED_MODIFIER);
            ModEffects.heal(living, 1);
            living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1, 1, false, false, false));
        }
    }

    public static void onRemove(LivingEntity living, MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.EXQUISITELY_STUFFED.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(SPEED_UUID);
            }
        }
    }
}
