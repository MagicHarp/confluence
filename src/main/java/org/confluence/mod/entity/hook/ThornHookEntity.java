package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class ThornHookEntity extends AbstractHookEntity {
    public ThornHookEntity(EntityType<ThornHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public ThornHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.THORN_HOOK.get(), item, player, level);
    }

    @Override
    public double getPullVelocity() {
        return 0.1634;
    }
}
