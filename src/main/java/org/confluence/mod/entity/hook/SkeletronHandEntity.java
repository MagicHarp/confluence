package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class SkeletronHandEntity extends AbstractHookEntity {
    public SkeletronHandEntity(EntityType<SkeletronHandEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public SkeletronHandEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.SKELETRON_HAND.get(), item, player, level);
    }
}
