package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HourWatch extends AbstractInfoCurio implements IWatch {
    public static final byte OWNER = 1;
    public static final byte OTHER = -126;

    public HourWatch() {
        super(ModRarity.WHITE);
    }

    public static Component wrapTime(long dayTime) {
        long hour = (dayTime % 24000) / 1000 + 6;
        if (hour > 23) hour -= 24;
        return Component.translatable(
            "info.confluence.time",
            IWatch.format(hour),
            "00"
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }
}
