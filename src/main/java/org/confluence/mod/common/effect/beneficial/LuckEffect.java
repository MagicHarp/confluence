package org.confluence.mod.common.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModEffects;

public class LuckEffect extends MobEffect {
    public static final ResourceLocation ID = Confluence.asResource("luck");

    public LuckEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x39C5BB);
    }

    public static void onAdd(Holder<MobEffect> mobEffect, AttributeMap attributemap, int amplifier) {
        if (mobEffect == ModEffects.LUCK_EFFECT) {
            attributemap.addTransientAttributeModifiers(ImmutableMultimap.of(
                Attributes.LUCK, new AttributeModifier(ID, (amplifier + 1) * 0.5, AttributeModifier.Operation.ADD_VALUE)
            ));
        }
    }

    public static void onRemove(LivingEntity living, Holder<MobEffect> mobEffect, AttributeMap attributemap, int amplifier) {
        if (mobEffect == ModEffects.LUCK_EFFECT) {
            AttributeInstance attributeInstance = attributemap.getInstance(Attributes.LUCK);
            if (attributeInstance != null) attributeInstance.removeModifier(ID);
            if (amplifier > 0) {
                living.addEffect(new MobEffectInstance(mobEffect, 6000, amplifier - 1));
            }
        }
    }
}
