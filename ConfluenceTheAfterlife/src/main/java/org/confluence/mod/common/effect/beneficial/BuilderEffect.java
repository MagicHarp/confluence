package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;

public class BuilderEffect extends MobEffect {  //建筑工 增加触及距离
    public static final ResourceLocation ID = Confluence.asResource("builder");

    public BuilderEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8B6914);
        addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, ID, 3, AttributeModifier.Operation.ADD_VALUE);
    }
}
