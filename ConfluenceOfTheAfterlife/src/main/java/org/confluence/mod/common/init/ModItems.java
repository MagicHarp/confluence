package org.confluence.mod.common.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.item.Arrows;
import org.confluence.mod.common.init.item.Bows;
import org.confluence.mod.common.init.item.Swords;
import org.confluence.mod.common.init.item.common.IconItem;
import org.confluence.mod.common.item.mana.ManaStar;
import org.confluence.mod.common.init.item.common.*;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Confluence.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(Confluence.MODID);

    public static final DeferredItem<ManaStar> MANA_STAR = ITEMS.register("mana_star", ManaStar::new);

    public static void register(IEventBus eventBus) {

        ITEMS.register(eventBus);
        Swords.SWORDS.register(eventBus);
        Bows.BOWS.register(eventBus);

        Arrows.ARROWS.register(eventBus);
        Materials.MATERIALS.register(eventBus);

        BLOCK_ITEMS.register(eventBus);


//        new ModEffects();
        IconItem.init();
    }
}
