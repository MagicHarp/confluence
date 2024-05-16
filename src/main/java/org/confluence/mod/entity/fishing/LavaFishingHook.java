package org.confluence.mod.entity.fishing;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.util.IFishingHook;

public class LavaFishingHook extends AbstractFishingHook {
    public LavaFishingHook(Player pPlayer, Level pLevel, int pLuck, int pLureSpeed) {
        super(ModEntities.LAVA_FISHING_HOOK.get(), pLevel, pLuck, pLureSpeed);
        ((IFishingHook) this).c$setIsLavaHook();
        setup(pPlayer);
    }

    public LavaFishingHook(EntityType<LavaFishingHook> entityType, Level level) {
        super(entityType, level);
    }
}
