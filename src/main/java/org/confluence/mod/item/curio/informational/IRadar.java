package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.AABB;

public interface IRadar {
    static Component getInfo(LocalPlayer localPlayer) {
        AABB region = new AABB(localPlayer.getOnPos()).inflate(63.5);
        return Component.translatable(
            "info.confluence.radar",
            localPlayer.level().getEntities(localPlayer, region, entity -> entity instanceof Enemy).size()
        );
    }
}
