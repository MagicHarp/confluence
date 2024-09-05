package org.confluence.mod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.confluence.mod.common.init.ModTabs;
import org.confluence.mod.common.init.block.ModBlocks;
import org.confluence.mod.common.init.block.ModOreBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(Confluence.MODID)
public class Confluence {
    public static final String MODID = "confluence";
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");

    public Confluence(IEventBus eventBus, ModContainer container) {
        ModTabs.TABS.register(eventBus);
        ModBlocks.register(eventBus);
    }
}
