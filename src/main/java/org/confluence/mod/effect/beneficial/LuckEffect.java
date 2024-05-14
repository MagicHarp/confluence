package org.confluence.mod.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class LuckEffect extends MobEffect {
    public static final UUID LUCK_UUID = UUID.fromString("E39D6772-3C17-15DE-A9AD-2D6423275E67");

    public LuckEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x39C5BB);
    }

    public static void onAdd(MobEffect mobEffect, AttributeMap attributemap, int amplifier) {
        if (mobEffect == ModEffects.LUCK_EFFECT.get()) {
            attributemap.addTransientAttributeModifiers(ImmutableMultimap.of(
                Attributes.LUCK, new AttributeModifier(LUCK_UUID, "Luck", (amplifier + 1) * 0.5, AttributeModifier.Operation.ADDITION)
            ));
        }
    }

    public static void onRemove(LivingEntity living, MobEffect mobEffect, AttributeMap attributemap, int amplifier) {
        if (mobEffect == ModEffects.LUCK_EFFECT.get()) {
            AttributeInstance attributeInstance = attributemap.getInstance(Attributes.LUCK);
            if (attributeInstance != null) attributeInstance.removeModifier(LUCK_UUID);
            if (amplifier > 0) {
                living.addEffect(new MobEffectInstance(mobEffect, 6000, amplifier - 1));
            }
        }
    }
}
