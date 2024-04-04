package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.BaseCurioItem;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class SunStone extends BaseCurioItem implements ICriticalHit {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("");
    public static final UUID DAMAGE_UUID = UUID.fromString("");
    public static final UUID ARMOR_UUID = UUID.fromString("");

    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Sun Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Sun Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Sun Stone", 4, AttributeModifier.Operation.ADDITION)
    );

    public SunStone() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public float getChance() {
        return 0.02F;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        int amplifier = living.hasEffect(ModEffects.HONEY.get()) ? 2 : 1;
        living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1, amplifier, false, false, false));
        living.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1, 0, false, false, false));
    }
}
