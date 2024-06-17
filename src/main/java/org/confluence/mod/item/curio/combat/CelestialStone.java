package org.confluence.mod.item.curio.combat;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class CelestialStone extends BaseCurioItem implements ICriticalHit {
    public static final UUID ATTACK_SPEED_UUID = UUID.fromString("A1F8AB0C-8285-3BE9-575A-E05787707241");
    public static final UUID DAMAGE_UUID = UUID.fromString("2B80C158-EBB2-39C0-E246-E401C544D9D8");
    public static final UUID ARMOR_UUID = UUID.fromString("814ABB7D-ADB4-F0C6-B7BD-A2E3FB23EE8D");

    private static final ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE = ImmutableMultimap.of(
        Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_UUID, "Celestial Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_UUID, "Celestial Stone", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL),
        Attributes.ARMOR, new AttributeModifier(ARMOR_UUID, "Celestial Stone", 4, AttributeModifier.Operation.ADDITION)
    );

    public CelestialStone() {
        super(ModRarity.LIME);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return ATTRIBUTE;
    }

    @Override
    public double getChance() {
        return 0.02;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity living = slotContext.entity();
        ModEffects.heal(living, 2);
        living.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1, 0, false, false, false));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.celestial_stone.info"),
            Component.translatable("item.confluence.celestial_stone.info2"),
            Component.translatable("item.confluence.celestial_stone.info3"),
            Component.translatable("item.confluence.celestial_stone.info4"),
            Component.translatable("item.confluence.celestial_stone.info5"),
            Component.translatable("item.confluence.celestial_stone.info6"),
            Component.translatable("item.confluence.celestial_stone.info7"),
            Component.translatable("item.confluence.celestial_stone.info8"),
            Component.translatable("item.confluence.celestial_stone.info9"),
            Component.translatable("item.confluence.celestial_stone.info10"),
            Component.translatable("item.confluence.celestial_stone.info11")
        };
    }
}
