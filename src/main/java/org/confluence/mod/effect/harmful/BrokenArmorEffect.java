package org.confluence.mod.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BrokenArmorEffect extends MobEffect {
    public static final String UUID = "33EDDBBA-C03F-0DED-25CC-15DBC8C42BC2";

    public BrokenArmorEffect() {
        super(MobEffectCategory.HARMFUL, 0x330088);
        addAttributeModifier(Attributes.ARMOR, UUID, -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
