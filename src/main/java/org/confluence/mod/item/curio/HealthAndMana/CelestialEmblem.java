package org.confluence.mod.item.curio.HealthAndMana;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class CelestialEmblem extends BaseCurioItem implements IRangePickup.Star {
    public static final UUID MAGIC_UUID = UUID.fromString("1100891B-DE9E-0F26-7919-A9FD3DD846D7");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public CelestialEmblem() {
        super(ModRarity.PINK);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                    ModAttributes.getMagicDamage(), new AttributeModifier(MAGIC_UUID, "Celestial Emblem", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.celestial_emblem.tooltip"));
        list.add(Component.translatable("item.confluence.celestial_emblem.tooltip2"));
    }
}
