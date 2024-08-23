package org.confluence.mod.effect.neutral;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.confluence.mod.entity.ModAttributes;

public class CerebralMindtrickEffect extends MobEffect {
    public static final String CRIT_UUID = "181304C7-2A0A-5D46-D4CF-28DC955532FA";

    public CerebralMindtrickEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFA885);
        addAttributeModifier(ModAttributes.getCriticalChance(), CRIT_UUID, 0.04, AttributeModifier.Operation.ADDITION);
    }
}
