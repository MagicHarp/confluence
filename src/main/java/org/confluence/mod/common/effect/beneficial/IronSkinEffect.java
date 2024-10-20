package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;

public class IronSkinEffect extends MobEffect {
    public static final ResourceLocation ID = Confluence.asResource("iron_skin");

    public IronSkinEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x184F5);
        addAttributeModifier(Attributes.ARMOR, ID, 8, AttributeModifier.Operation.ADD_VALUE);
    }
}

