package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.IRangePickup;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagnetFlower extends ManaFlower implements IRangePickup.Star {
    public MagnetFlower() {
        super(ModRarity.PINK);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.magnet_flower.tooltip"));
        list.add(Component.translatable("item.confluence.magnet_flower.tooltip2"));
        list.add(Component.translatable("item.confluence.magnet_flower.tooltip3"));
    }
}
