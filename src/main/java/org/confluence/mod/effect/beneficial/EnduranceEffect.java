package org.confluence.mod.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class EnduranceEffect extends MobEffect {    //耐力 加一点点抗性提升
    private static final UUID ENDURANCE_UUID = UUID.fromString("20370222-C4CC-9E85-40FB-CA5BC1F74F2F");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ENDURANCE = ImmutableMultimap.of(
        Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(ENDURANCE_UUID, "Endurance", 1, AttributeModifier.Operation.ADDITION)
    );

    public EnduranceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x708090);
    }

    public static void onAdd(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.ENDURANCE.get()) {
            attributeMap.addTransientAttributeModifiers(ENDURANCE);
        }
    }

    public static void onRemove(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.ENDURANCE.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.KNOCKBACK_RESISTANCE);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(ENDURANCE_UUID);
            }
        }
    }
}
