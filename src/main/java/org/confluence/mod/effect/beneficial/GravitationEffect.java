package org.confluence.mod.effect.beneficial;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class GravitationEffect extends MobEffect {
    public static final UUID GRAVITY_UUID = UUID.fromString("30AE55C6-016B-09A2-74B8-96C68C22AFE1");
    public static final ImmutableMultimap<Attribute, AttributeModifier> GRAVITY = ImmutableMultimap.of(
        ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(GRAVITY_UUID, "Gravitation", -2.0, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    public GravitationEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA00AA);
    }
}
