package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HalfHourWatch extends AbstractInfoCurio implements IWatch {
    public HalfHourWatch() {
        super(ModRarity.WHITE);
    }

    public static Component wrapTime(long dayTime) {
        dayTime = dayTime % 24000;
        long hour = dayTime / 1000 + 6;
        if (hour > 23) hour -= 24;
        String half = dayTime % 1000 > 499 ? "30" : "00";
        return Component.translatable(
            "info.confluence.time",
            IWatch.format(hour),
            half
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }
}
