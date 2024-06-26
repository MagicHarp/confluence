package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.confluence.mod.misc.ModRarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MinuteWatch extends AbstractInfoCurio implements IWatch {
    public static final byte OWNER = 3;
    public static final byte OTHER = -128;

    public MinuteWatch() {
        super(ModRarity.BLUE);
    }

    public static Component wrapTime(long dayTime) {
        dayTime = dayTime % 24000;
        long hour = dayTime / 1000 + 6;
        if (hour > 23) hour -= 24;
        long minute = (long) ((dayTime % 1000) * 0.06F);
        return Component.translatable(
            "info.confluence.time",
            IWatch.format(hour),
            IWatch.format(minute)
        );
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        list.add(TOOLTIP);
    }
}
