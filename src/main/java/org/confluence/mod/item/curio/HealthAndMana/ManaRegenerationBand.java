package org.confluence.mod.item.curio.HealthAndMana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaRegenerationBand extends BaseCurioItem {
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(Component.translatable("item.confluence.mana_regeneration_band.tooltip"));
        list.add(Component.translatable("item.confluence.mana_regeneration_band.tooltip2"));
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.mana_regeneration_band.info"),
                Component.translatable("item.confluence.mana_regeneration_band.info2")
        };
    }
}
