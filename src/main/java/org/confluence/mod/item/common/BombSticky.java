package org.confluence.mod.item.common;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.projectile.bombs.BaseBombEntity;
import org.confluence.mod.entity.projectile.bombs.BouncyBombEntity;
import org.confluence.mod.entity.projectile.bombs.StickyBombEntity;
import org.jetbrains.annotations.NotNull;

public class BombSticky extends Bomb {
    public BombSticky() {
        super();
    }

    protected BaseBombEntity EntityConstructor(Level level, Player player) {
        return new StickyBombEntity(level, player);
    }
}
