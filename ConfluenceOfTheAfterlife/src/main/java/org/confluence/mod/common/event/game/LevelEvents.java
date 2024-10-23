package org.confluence.mod.common.event.game;

import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.entity.projectile.bombs.ScarabBombEntity;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class LevelEvents {
    @SubscribeEvent
    public static void explosionDetonate(ExplosionEvent.Detonate event) {
        if (event.getExplosion().getDirectSourceEntity() instanceof ScarabBombEntity) {
            event.getAffectedEntities().removeIf(entity -> entity instanceof ItemEntity);
        }
    }

    @SubscribeEvent
    public static void blockToolModification(BlockEvent.BlockToolModificationEvent event) {

    }
}
