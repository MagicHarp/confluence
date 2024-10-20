package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;

public class EnduranceEffect extends MobEffect {    //耐力 加一点点抗性提升
    public static final ResourceLocation ID = Confluence.asResource("endurance");

    public EnduranceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x708090);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ID, 1, AttributeModifier.Operation.ADD_VALUE);
    }
}
