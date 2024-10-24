package org.confluence.mod.common.effect.beneficial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.confluence.mod.Confluence;
import org.confluence.mod.terra_curio.common.init.ModAttributes;

public class MagicPowerEffect extends MobEffect {
    public static final ResourceLocation ID = Confluence.asResource("magic_power");

    public MagicPowerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xCC00CC);
        addAttributeModifier(ModAttributes.getMagicDamage(), ID, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
