package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;


public class LifeForceEffect extends MobEffect {    // 生命力 最大生命提升20%
    public static final String LIFE_FORCE_UUID = "4E8527FE-6578-26CD-EADF-7F12D5CA585A";

    public LifeForceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFC0CB);
        addAttributeModifier(Attributes.MAX_HEALTH, LIFE_FORCE_UUID, 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
