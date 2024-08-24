package org.confluence.mod.item.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.projectile.bombs.BaseBombEntity;
import org.confluence.mod.entity.projectile.bombs.ScarabBombEntity;

public class BombScarab extends Bomb {
    public BombScarab() {
        super();
    }

    protected BaseBombEntity EntityConstructor(Level level, Player player) {
        return new ScarabBombEntity(level, player);
    }
}
