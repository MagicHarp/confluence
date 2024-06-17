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

public class CelestialCuffs extends MagicCuffs implements IRangePickup.Star {
    public CelestialCuffs() {
        super(ModRarity.PINK);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.celestial_cuffs.tooltip"));
        list.add(Component.translatable("item.confluence.celestial_cuffs.tooltip2"));
        list.add(Component.translatable("item.confluence.celestial_cuffs.tooltip3"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.celestial_cuffs.info"),
            Component.translatable("item.confluence.celestial_cuffs.info2"),
            Component.translatable("item.confluence.celestial_cuffs.info3"),
            Component.translatable("item.confluence.celestial_cuffs.info4"),
            Component.translatable("item.confluence.celestial_cuffs.info5"),
            Component.translatable("item.confluence.celestial_cuffs.info6"),
            Component.translatable("item.confluence.celestial_cuffs.info7")
        };
    }
}
