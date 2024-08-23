package org.confluence.mod.effect.beneficial;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.confluence.mod.misc.ModAttributes;


public class RageEffect extends MobEffect {
    public static final String CRIT_UUID = "2BC823DC-FEA6-347E-1B35-5F30CB27845C";

    public RageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500);
        addAttributeModifier(ModAttributes.getCriticalChance(), CRIT_UUID, 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
