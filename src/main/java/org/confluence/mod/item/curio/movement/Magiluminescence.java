package org.confluence.mod.item.curio.movement;

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
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class Magiluminescence extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("F0E9149C-E146-5D87-A319-A45CE63A2C65");
    private static ImmutableMultimap<Attribute, AttributeModifier> SPEED;

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        if (SPEED == null) {
            SPEED = ImmutableMultimap.of(
                Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Magiluminescence", ModConfigs.MAGILUMINESCENCE_MOVEMENT.get(), AttributeModifier.Operation.MULTIPLY_TOTAL)
            );
        }
        return SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magiluminescence.tooltip"));
        //list.add(Component.translatable("item.confluence.magiluminescence.tooltip2"));
        list.add(Component.translatable("item.confluence.magiluminescence.tooltip3"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.magiluminescence.info"),
            Component.translatable("item.confluence.magiluminescence.info2"),
            Component.translatable("item.confluence.magiluminescence.info3"),
            Component.translatable("item.confluence.magiluminescence.info4")
        };
    }
}
