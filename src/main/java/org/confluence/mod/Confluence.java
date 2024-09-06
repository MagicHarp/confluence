package org.confluence.mod;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.confluence.mod.common.init.ModDataComponentTypes;
import org.confluence.mod.common.init.ModItems;
import org.confluence.mod.common.init.ModSoundEvents;
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
        ModItems.register(eventBus);
        ModDataComponentTypes.DATA_COMPONENT_TYPE.register(eventBus);
        ModSoundEvents.SOUND_EVENT.register(eventBus);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
