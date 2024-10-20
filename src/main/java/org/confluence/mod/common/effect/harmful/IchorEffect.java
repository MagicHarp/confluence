package org.confluence.mod.common.effect.harmful;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModEffects;

public class IchorEffect extends MobEffect {    //灵液 护甲值-5
    public static final ResourceLocation ID = Confluence.asResource("ichor");
    private static final ImmutableMultimap<Holder<Attribute>, AttributeModifier> ICHOR_MODIFIER = ImmutableMultimap.of(
        Attributes.ARMOR, new AttributeModifier(ID, -5, AttributeModifier.Operation.ADD_VALUE)
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
                attributeInstance.removeModifier(ID);
            }
        }
    }
}
