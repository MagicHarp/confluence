package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class WrathEffect extends MobEffect {    //怒气 攻击+10
    public static final String WRATH_UUID = "E503A08A-1667-F60A-735F-5C3CDC8C90CC";

    public WrathEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF8C00);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, WRATH_UUID, 10, AttributeModifier.Operation.ADDITION);
    }
}
