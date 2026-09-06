package org.confluence.mod.common.entity.fishing;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.confluence.mod.common.init.entity.ModEntities;

/// 血腥鱼竿的专用浮标实体。
///
/// 该类型用于让统一鱼获事件识别鱼饵投掷者，并提高血月钓起敌怪的概率。
public class BloodyFishingHook extends AbstractFishingHook {
    public BloodyFishingHook(EntityType<BloodyFishingHook> entityType, Level level) {
        super(entityType, level);
    }

    public BloodyFishingHook(Player player, Level level, int luck, int lureSpeed) {
        super(ModEntities.BLOODY_FISHING_HOOK.get(), level, luck, lureSpeed);
        setup(player);
    }
}
