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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class AnkletOfTheWind extends BaseCurioItem {
    public static final UUID SPEED_UUID = UUID.fromString("355067F2-9D3A-92F8-B557-31ED341BFAC3");
    private static final ImmutableMultimap<Attribute, AttributeModifier> SPEED = ImmutableMultimap.of(
        Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_UUID, "Anklet of the Wind", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
    );

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return SPEED;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
    }
}
