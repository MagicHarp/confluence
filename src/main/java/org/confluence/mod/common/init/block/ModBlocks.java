package org.confluence.mod.common.init.block;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Confluence.MODID);

    public static void register(IEventBus eventBus) {
        ModOreBlocks.BLOCKS.register(eventBus);
    }
}
