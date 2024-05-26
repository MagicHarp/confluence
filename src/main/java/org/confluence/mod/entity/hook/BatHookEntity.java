package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class BatHookEntity extends AbstractHookEntity {
    public BatHookEntity(EntityType<BatHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public BatHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.BAT_HOOK.get(), item, player, level);
    }

    @Override
    public double getPullVelocity() {
        return 0.1929;
    }
}
