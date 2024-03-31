package org.confluence.mod.item.curio.combat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.confluence.mod.util.CuriosUtils;

public interface IFireAttack {
    static void apply(Player player, Entity entity) {
        if (CuriosUtils.hasCurio(player, IFireAttack.class)) {
            float f = player.getRandom().nextFloat();
            int time;
            if (f < 0.25F) {
                time = 6;
            } else if (f < 0.375F) {
                time = 4;
            } else {
                time = 2;
            }
            entity.setSecondsOnFire(time);
        }
    }
}
