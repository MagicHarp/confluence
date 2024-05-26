package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class CandyCaneHookEntity extends AbstractHookEntity {
    public CandyCaneHookEntity(EntityType<CandyCaneHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public CandyCaneHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.CANDY_CANE_HOOK.get(), item, player, level);
    }
}
