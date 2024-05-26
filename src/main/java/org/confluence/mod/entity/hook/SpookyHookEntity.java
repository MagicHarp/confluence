package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class SpookyHookEntity extends AbstractHookEntity {
    public SpookyHookEntity(EntityType<SpookyHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public SpookyHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.SPOOKY_HOOK.get(), item, player, level);
    }
}
