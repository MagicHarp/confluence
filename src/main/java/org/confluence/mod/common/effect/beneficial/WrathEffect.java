package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;

public class WrathEffect extends MobEffect {    //怒气 攻击+10
    public static final ResourceLocation ID = Confluence.asResource("wrath");

    public WrathEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF8C00);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, ID, 10, AttributeModifier.Operation.ADD_VALUE);
    }
}
