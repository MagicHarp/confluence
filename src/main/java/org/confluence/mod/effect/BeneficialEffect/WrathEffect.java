package org.confluence.mod.effect.BeneficialEffect;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class WrathEffect extends MobEffect {    //怒气 攻击+10
    public WrathEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF8C00);
    }

    private static final UUID WRATH_UUID = UUID.fromString("E503A08A-1667-F60A-735F-5C3CDC8C90CC");
    private static final ImmutableMultimap<Attribute, AttributeModifier> WRATH = ImmutableMultimap.of(
        Attributes.ATTACK_DAMAGE, new AttributeModifier(WRATH_UUID, "Wrath", 10, AttributeModifier.Operation.ADDITION)
    );

    public static void onAdd(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.WRATH.get()) {
            attributeMap.addTransientAttributeModifiers(WRATH);
        }
    }

    public static void onRemove(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.WRATH.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.ATTACK_DAMAGE);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(WRATH_UUID);
            }
        }
    }
}
