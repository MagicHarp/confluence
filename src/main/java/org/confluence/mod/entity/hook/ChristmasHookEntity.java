package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class ChristmasHookEntity extends AbstractHookEntity {
    public ChristmasHookEntity(EntityType<ChristmasHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public ChristmasHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.CHRISTMAS_HOOK.get(), item, player, level);
    }
}
