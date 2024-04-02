package org.confluence.mod.effect;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class IronSkinEffect extends MobEffect {
    public static final UUID IRON_UUID = UUID.fromString("1E4E10F1-C3D2-E16D-1C91-CB49935164E8");
    public IronSkinEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xF5F5F5);
    }

    public Multimap<Attribute, AttributeModifier> changeArmor(int amplifier){
        return ImmutableMultimap.of(Attributes.ARMOR, new AttributeModifier(IRON_UUID, "Iron_Skin" , 10*amplifier, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier){
        if (entity instanceof Player && !entity.isSpectator()){
            entity.getAttributes().addTransientAttributeModifiers(changeArmor(amplifier));
        }
    }
}

