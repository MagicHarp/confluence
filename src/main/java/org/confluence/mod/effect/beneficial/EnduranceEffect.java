package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EnduranceEffect extends MobEffect {    //耐力 加一点点抗性提升
    public static final String ENDURANCE_UUID = "20370222-C4CC-9E85-40FB-CA5BC1F74F2F";

    public EnduranceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x708090);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ENDURANCE_UUID, 1, AttributeModifier.Operation.ADDITION);
    }
}
