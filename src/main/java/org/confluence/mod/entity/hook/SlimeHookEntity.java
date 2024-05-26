package org.confluence.mod.entity.hook;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.hook.AbstractHookItem;

public class SlimeHookEntity extends AbstractHookEntity {
    public SlimeHookEntity(EntityType<SlimeHookEntity> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public SlimeHookEntity(AbstractHookItem item, Player player, Level level) {
        super(ModEntities.SLIME_HOOK.get(), item, player, level);
    }
}
