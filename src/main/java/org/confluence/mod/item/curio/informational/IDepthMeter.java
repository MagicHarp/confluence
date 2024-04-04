package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public interface IDepthMeter {
    static Component getInfo(LocalPlayer localPlayer) {
        double y = localPlayer.getY();
        return Component.translatable("info.confluence.depth_meter." + (y > 63 ? "surface" : "underground"), "%.2f".formatted(y));
    }
}
