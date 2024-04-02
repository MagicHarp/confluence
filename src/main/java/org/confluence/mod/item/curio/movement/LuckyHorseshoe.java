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

public class LuckyHorseshoe extends BaseCurioItem implements IFallResistance {
    public static final UUID LUCKY_UUID = UUID.fromString("EEFDE523-84A5-60DE-4176-71EBE048D5F3");
    static final ImmutableMultimap<Attribute, AttributeModifier> LUCKY = ImmutableMultimap.of(
        Attributes.LUCK, new AttributeModifier(LUCKY_UUID, "Lucky Horseshoe", 0.05, AttributeModifier.Operation.ADDITION)
    );

    @Override
    public int getFallResistance() {
        return -1;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return LUCKY;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("curios.tooltip.negates_fall_damage"));
        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }
}
