package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class WebSlingerEntity extends AbstractHookEntity {
    public WebSlingerEntity(EntityType<WebSlingerEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public WebSlingerEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.WEB_SLINGER.get(), item, player, level);
    }
}
