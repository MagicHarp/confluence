package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ExquisitelyStuffedEffect extends MobEffect {   //吃得好/很满意/酒足饭饱
    public static final String SPEED_UUID = "3043D990-320B-2E80-FD32-FDB6BBCDC503";

    public ExquisitelyStuffedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFF00);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, SPEED_UUID, 0.05, AttributeModifier.Operation.ADDITION);
    }
}
