package org.confluence.mod.effect.HarmfulEffect;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class WitheredArmorEffect extends MobEffect {    //破碎盔甲 护甲值减半
    private static final UUID WITHERED_UUID = UUID.fromString("9609FB9B-8BC6-CDB1-84B5-F281EFFE36CF");
    private static final ImmutableMultimap<Attribute, AttributeModifier> WITHERED_MODIFIER = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(WITHERED_UUID, "Withered armor", -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public WitheredArmorEffect() {
        super(MobEffectCategory.HARMFUL, 0xE0EEE0);
    }

    public void onAdd(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.WITHERED_ARMOR.get()) {
            attributeMap.addTransientAttributeModifiers(WITHERED_MODIFIER);
        }
    }

    public void onRemove(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.WITHERED_ARMOR.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.ARMOR);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(WITHERED_UUID);
            }
        }
    }
}
