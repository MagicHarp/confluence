package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public interface IStopwatch {
    static Component getInfo(LocalPlayer localPlayer) {
        return Component.translatable(
            "info.confluence.stopwatch",
            "%.2f".formatted(localPlayer.getDeltaMovement().length() * 20 - 1.568)
        );
    }
}
