package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightningBoots extends BaseSpeedBoots implements IMayFly {
    private static final List<Component> ATTR_TOOLTIP = List.of(Component.translatable("item.confluence.lightning_boots.attribute"));

    @Override
    public int getFlyTicks() {
        return 32;
    }

    @Override
    public double getFlySpeed() {
        return 0.3;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return ATTR_TOOLTIP;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.lightning_boots.tooltip"));
    }
}
