package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class FishHookEntity extends AbstractHookEntity {
    public FishHookEntity(EntityType<FishHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public FishHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.FISH_HOOK.get(), item, player, level);
    }
}
