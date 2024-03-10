package org.confluence.mod.item.common;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TooltipItem extends Item {
    private final Component[] tooltips;

    public TooltipItem(Properties properties, Component... tooltips) {
        super(properties);
        this.tooltips = tooltips;
    }

    public TooltipItem(Component... tooltips) {
        this(new Properties(), tooltips);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        components.addAll(Arrays.asList(tooltips));
    }
}
