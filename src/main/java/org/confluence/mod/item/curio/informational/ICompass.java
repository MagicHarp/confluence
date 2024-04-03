package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public interface ICompass {
    static Component getInfo(LocalPlayer localPlayer) {
        double x = localPlayer.getX();
        double z = localPlayer.getZ();
        return Component.translatable("info.confluence.compass." + (x > 0 ? "east" : "west"), x)
            .append(Component.translatable("info.confluence.compass." + (z > 0 ? "south" : "north"), z));
    }
}
