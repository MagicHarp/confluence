package org.confluence.mod.common.effect.harmful;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.confluence.mod.Confluence;

public class WitheredArmorEffect extends MobEffect {    // 枯萎盔甲 护甲值减半
    private static final ResourceLocation ID = Confluence.asResource("withered_armor");

    public WitheredArmorEffect() {
        super(MobEffectCategory.HARMFUL, 0xE0EEE0);
        addAttributeModifier(Attributes.ARMOR, ID, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
