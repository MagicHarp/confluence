package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;


public class LifeForceEffect extends MobEffect {    // 生命力 最大生命提升20%
    public static final ResourceLocation ID = Confluence.asResource("life_force");

    public LifeForceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFC0CB);
        addAttributeModifier(Attributes.MAX_HEALTH, ID, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
