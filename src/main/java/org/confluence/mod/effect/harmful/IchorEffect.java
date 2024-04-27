package org.confluence.mod.effect.harmful;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class IchorEffect extends MobEffect {    //灵液 护甲值-5
    private static final UUID ICHOR_UUID = UUID.fromString("72D74B38-2335-75D7-2221-E8EB46B9D8F8");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ICHOR_MODIFIER = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ICHOR_UUID, "Ichor", -5, AttributeModifier.Operation.ADDITION)
    );

    public IchorEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFD700);
    }

    public static void onAdd(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.ICHOR.get()) {
            attributeMap.addTransientAttributeModifiers(ICHOR_MODIFIER);
        }
    }

    public static void onRemove(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.ICHOR.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.ARMOR);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(ICHOR_UUID);
            }
        }
    }
}
