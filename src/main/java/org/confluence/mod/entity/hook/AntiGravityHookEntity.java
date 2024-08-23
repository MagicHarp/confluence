package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class AntiGravityHookEntity extends AbstractHookEntity {
    public AntiGravityHookEntity(EntityType<AntiGravityHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public AntiGravityHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.ANTI_GRAVITY_HOOK.get(), item, player, level);
    }
}
