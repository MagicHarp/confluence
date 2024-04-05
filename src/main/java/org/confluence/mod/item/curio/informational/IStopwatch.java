package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public interface IStopwatch {
    static Component getInfo(LocalPlayer localPlayer) {
        return Component.translatable(
            "info.confluence.stopwatch",
            "%.2f".formatted(Mth.length(
                localPlayer.getX() - localPlayer.xOld,
                localPlayer.getY() - localPlayer.yOld,
                localPlayer.getZ() - localPlayer.zOld
            ) * 20)
        );
    }
}
