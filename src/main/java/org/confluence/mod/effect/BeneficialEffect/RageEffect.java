package org.confluence.mod.effect.BeneficialEffect;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;


public class RageEffect extends MobEffect { //暴怒 暴击率增加10%
    private static final UUID RAGE_UUID = UUID.fromString("C3018361-D622-37F2-CECD-F397B8B67349");
    private static final ImmutableMultimap<Attribute, AttributeModifier> RAGE = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(RAGE_UUID, "Rage", 10, AttributeModifier.Operation.ADDITION)
    );

    public RageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500);
    }

    public void onAdd(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.RAGE.get()) {
            attributeMap.addTransientAttributeModifiers(RAGE);
        }
    }

    public void onRemove(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.RAGE.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.ATTACK_DAMAGE);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(RAGE_UUID);
            }
        }
    }
}
