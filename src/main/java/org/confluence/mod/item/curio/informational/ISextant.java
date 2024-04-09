package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public interface ISextant {
    static Component getInfo(LocalPlayer localPlayer) {
        return Component.translatable("info.confluence.sextant." + localPlayer.level().getMoonPhase());
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.sextant");
}
