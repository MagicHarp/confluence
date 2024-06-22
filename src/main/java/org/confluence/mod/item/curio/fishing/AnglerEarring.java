package org.confluence.mod.item.curio.fishing;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class AnglerEarring extends BaseCurioItem {
    public static final UUID LUCK_UUID = UUID.fromString("FF9B64BC-4199-3B58-D520-A854E79600AE");
    private static final ImmutableMultimap<Attribute, AttributeModifier> LUCK = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCK_UUID, "Angler Earring", 10, AttributeModifier.Operation.ADDITION)
    );

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return LUCK;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.angler_earring.info"),
            Component.translatable("item.confluence.angler_earring.info2")
        };
    }
}
