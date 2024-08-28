package org.confluence.mod.item.curio.movement;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModConfigs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class BundleOfHorseshoeBalloons extends BundleOfBalloons implements IFallResistance {
    @Override
    public int getFallResistance() {
        return ModConfigs.BUNDLE_OF_HORSESHOE_BALLOONS_FALL_RESISTANCE.get();
    }

    @Override
    public double getBoost() {
        return ModConfigs.BUNDLE_OF_HORSESHOE_BALLOONS_JUMP_BOOST.get();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return LuckyHorseshoe.getOrCreateAttributes();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.bundle_of_horseshoe_balloon.tooltip"));
        list.add(Component.translatable("item.confluence.bundle_of_horseshoe_balloon.tooltip2"));
    }
}
