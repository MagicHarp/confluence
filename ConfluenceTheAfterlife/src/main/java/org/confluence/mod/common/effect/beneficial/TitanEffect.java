package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;

public class TitanEffect extends MobEffect {    //泰坦 攻击加点击退
    public static final ResourceLocation ID = Confluence.asResource("titan");

    public TitanEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xD2B48C);
        addAttributeModifier(Attributes.ATTACK_KNOCKBACK, ID, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
