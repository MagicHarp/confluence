package org.confluence.mod.item.curio.HealthAndMana;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class ArcaneFlower extends BaseCurioItem implements IManaReduce {
    public static final UUID AGGRO_UUID = UUID.fromString("50B86990-3BE2-CD3A-BC88-BEBFC4B21BC2");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTES;

    public ArcaneFlower() {
        super(ModRarity.PINK);
    }

    @Override
    public double getManaReduce() {
        return 0.08;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTES == null) {
            ATTRIBUTES = ImmutableMultimap.of(
                    ModAttributes.getAggro(), new AttributeModifier(AGGRO_UUID, "Arcane FLower", -400, AttributeModifier.Operation.ADDITION)
            );
        }
        return ATTRIBUTES;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.arcane_flower.tooltip"));
        list.add(Component.translatable("item.confluence.arcane_flower.tooltip2"));
        list.add(Component.translatable("item.confluence.arcane_flower.tooltip3"));
    }
}
