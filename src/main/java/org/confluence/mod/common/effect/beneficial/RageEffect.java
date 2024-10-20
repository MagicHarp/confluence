package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.confluence.mod.Confluence;

// todo
public class RageEffect extends MobEffect {
    public static final ResourceLocation ID = Confluence.asResource("rage");

    public RageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF4500);
        //addAttributeModifier(ModAttributes.getCriticalChance(), ID, 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
