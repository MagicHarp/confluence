package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

public interface IRadar {
    static Component getInfo(LocalPlayer localPlayer) {
        return Component.translatable(
            "info.confluence.radar",
            localPlayer.level()
                .getEntities(localPlayer, new AABB(localPlayer.getOnPos()).inflate(63.5), entity ->
                    entity instanceof Enemy
                ).size()
        );
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.radar");
}
