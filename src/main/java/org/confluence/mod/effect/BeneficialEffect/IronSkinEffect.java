package org.confluence.mod.effect.BeneficialEffect;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class IronSkinEffect extends MobEffect {
    public static final UUID ARMOR_UUID = UUID.fromString("33D41FDC-F153-0F90-0624-E9B2CB9C751B");
    private static final ImmutableMultimap<Attribute, AttributeModifier> ARMOR = ImmutableMultimap.of(
            Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Iron Skin", 1, AttributeModifier.Operation.ADDITION)
    );

    public IronSkinEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x184F5);
    }

    public static void onAdd(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.IRON_SKIN.get()) {
            attributeMap.addTransientAttributeModifiers(ARMOR);
        }
    }

    public static void onRemove(MobEffect mobEffect, AttributeMap attributeMap){
        if (mobEffect == ModEffects.IRON_SKIN.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.ARMOR);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(ARMOR_UUID);
            }
        }
    }
}

