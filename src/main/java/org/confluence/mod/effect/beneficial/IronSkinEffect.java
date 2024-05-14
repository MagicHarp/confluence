package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IronSkinEffect extends MobEffect {
    public static final String ARMOR_UUID = "33D41FDC-F153-0F90-0624-E9B2CB9C751B";

    public IronSkinEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x184F5);
        addAttributeModifier(Attributes.ARMOR, ARMOR_UUID, 8, AttributeModifier.Operation.ADDITION);
    }
}

