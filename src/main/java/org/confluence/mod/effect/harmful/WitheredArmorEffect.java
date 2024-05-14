package org.confluence.mod.effect.harmful;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class WitheredArmorEffect extends MobEffect {    // 枯萎盔甲 护甲值减半
    private static final String WITHERED_UUID = "9609FB9B-8BC6-CDB1-84B5-F281EFFE36CF";

    public WitheredArmorEffect() {
        super(MobEffectCategory.HARMFUL, 0xE0EEE0);
        addAttributeModifier(Attributes.ARMOR, WITHERED_UUID, -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
