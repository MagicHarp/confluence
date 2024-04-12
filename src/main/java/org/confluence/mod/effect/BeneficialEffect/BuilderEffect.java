package org.confluence.mod.effect.BeneficialEffect;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class BuilderEffect extends MobEffect {  //建筑工 增加触及距离
    private static final UUID BUILDER_UUID = UUID.fromString("02B12F05-C426-AC6F-953C-AC0DBE1CEB57");
    private static final ImmutableMultimap<Attribute, AttributeModifier> BUILDER = ImmutableMultimap.of(
        ForgeMod.BLOCK_REACH.get(), new AttributeModifier(BUILDER_UUID, "Builder", 3, AttributeModifier.Operation.ADDITION)
    );

    public BuilderEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8B6914);
    }

    public static void onAdd(Attribute attribute, AttributeMap attributeMap) {
        if (attribute == ForgeMod.BLOCK_REACH.get()) {
            attributeMap.addTransientAttributeModifiers(BUILDER);
        }
    }

    public static void onRemove(Attribute attribute, AttributeMap attributeMap) {
        if (attribute == ForgeMod.BLOCK_REACH.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(attribute);
            if (attributeInstance != null) {
                attributeInstance.removeModifier(BUILDER_UUID);
            }
        }
    }
}
