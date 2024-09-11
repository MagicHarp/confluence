package org.confluence.mod.item.curio.construction;

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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class AncientChisel extends BaseCurioItem {
    public static final UUID MINING_UUID = UUID.fromString("D41B4FAF-A972-3A93-7292-991D77AAD0BD");
    private static ImmutableMultimap<Attribute, AttributeModifier> ATTRIBUTE;

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.ancient_chisel.tooltip2"));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (ATTRIBUTE == null) {
            ATTRIBUTE = ImmutableMultimap.of(
                ModAttributes.getMiningSpeed(), new AttributeModifier(MINING_UUID, "Ancient Chisel", 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return ATTRIBUTE;
    }
}
