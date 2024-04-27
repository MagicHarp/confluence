package org.confluence.mod.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;


public class LifeForceEffect extends MobEffect {    //生命力 最大生命提升20
    private static final UUID LIFE_FORCE_UUID = UUID.fromString("4E8527FE-6578-26CD-EADF-7F12D5CA585A");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LIFE_FORCE = ImmutableMultimap.of(
        Attributes.MAX_HEALTH, new AttributeModifier(LIFE_FORCE_UUID, "Life Force", 20, AttributeModifier.Operation.ADDITION)
    );

    public LifeForceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFC0CB);
    }

    public static void onAdd(MobEffect mobEffect, AttributeMap attributemap) {
        if (mobEffect == ModEffects.LIFE_FORCE.get()) {
            attributemap.addTransientAttributeModifiers(LIFE_FORCE);
        }
    }

    public static void onRemove(MobEffect mobEffect, AttributeMap attributemap) {
        if (mobEffect == ModEffects.LIFE_FORCE.get()) {
            AttributeInstance attributeInstance = attributemap.getInstance(Attributes.MAX_HEALTH);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(LIFE_FORCE_UUID);
            }
        }
    }
}
