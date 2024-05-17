package org.confluence.mod.item.curio.fishing;

import net.minecraftforge.event.entity.player.ItemFishedEvent;
import org.confluence.mod.util.CuriosUtils;

public interface IHighTestFishingLine {
    static void apply(ItemFishedEvent event) {
        if (event.getEntity().level().random.nextFloat() < 0.1429F && CuriosUtils.noSameCurio(event.getEntity(), IHighTestFishingLine.class)) {
            event.setCanceled(true);
        }
    }
}
