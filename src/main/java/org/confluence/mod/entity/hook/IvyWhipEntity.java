package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class IvyWhipEntity extends AbstractHookEntity {
    public IvyWhipEntity(EntityType<IvyWhipEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public IvyWhipEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.IVY_WHIP.get(), item, player, level);
    }
}
