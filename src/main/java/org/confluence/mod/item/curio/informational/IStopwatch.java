package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public interface IStopwatch {
    static Component getInfo(Player localPlayer) {
        return Component.translatable(
                "info.confluence.stopwatch",
                "%.2f".formatted(Mth.length(
                        localPlayer.getX() - localPlayer.xOld,
                        localPlayer.getY() - localPlayer.yOld,
                        localPlayer.getZ() - localPlayer.zOld
                ) * 20)
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.stopwatch");
    byte INDEX = 9;
}
