package org.confluence.mod.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class BuilderEffect extends MobEffect {  //建筑工 增加触及距离
    private static final UUID BUILDER_UUID = UUID.fromString("02B12F05-C426-AC6F-953C-AC0DBE1CEB57");
    private static final ImmutableMultimap<Attribute, AttributeModifier> BUILDER = ImmutableMultimap.of(
        ForgeMod.BLOCK_REACH.get(), new AttributeModifier(BUILDER_UUID, "Builder", 3, AttributeModifier.Operation.ADDITION)
    );

    public BuilderEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8B6914);
    }

    public static void onAdd(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.BUILDER.get()) {
            attributeMap.addTransientAttributeModifiers(BUILDER);
        }
    }

    public static void onRemove(MobEffect mobEffect, AttributeMap attributeMap) {
        if (mobEffect == ModEffects.BUILDER.get()) {
            AttributeInstance attributeInstance = attributeMap.getInstance(ForgeMod.BLOCK_REACH.get());
            if (attributeInstance != null) {
                attributeInstance.removeModifier(BUILDER_UUID);
            }
        }
    }
}
