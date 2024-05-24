package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.item.hook.AbstractHookItem;

public class WebSlingerEntity extends AbstractHookEntity {
    public WebSlingerEntity(EntityType<WebSlingerEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public WebSlingerEntity(EntityType<WebSlingerEntity> entityType, AbstractHookItem item, Player player, Level level) {
        super(entityType, item, player, level);
    }
}
