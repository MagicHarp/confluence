package org.confluence.mod.effect.BeneficialEffect;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.*;
import org.confluence.mod.effect.ModEffects;

import java.util.UUID;

public class SpeedEffect extends MobEffect {    //速度 加0.05基础移速
    public final static UUID SPEED_UUID = UUID.fromString("49116569-8404-6F3E-DB46-5CCFCAA309E6");
    private static final ImmutableMultimap<Attribute, AttributeModifier> SPEED = ImmutableMultimap.of(
            Attributes.MOVEMENT_SPEED,new AttributeModifier(SPEED_UUID,"Speed",0.05, AttributeModifier.Operation.ADDITION)
    );
    public SpeedEffect() {
        super(MobEffectCategory.BENEFICIAL,0xF8F8FF);
    }
    public void onAdd(MobEffect mobEffect, AttributeMap attributeMap){
        if(mobEffect == ModEffects.SPEED.get()){
            attributeMap.addTransientAttributeModifiers(SPEED);
        }
    }
    public void onRemove(MobEffect mobEffect, AttributeMap attributeMap){
        if (mobEffect == ModEffects.SPEED.get()){
            AttributeInstance attributeInstance = attributeMap.getInstance(Attributes.MOVEMENT_SPEED);
            if (attributeInstance != null){
                attributeInstance.removeModifier(SPEED_UUID);
            }
        }
    }
}
