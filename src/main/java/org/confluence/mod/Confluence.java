package org.confluence.mod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.item.ConfluenceItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
@Mod(Confluence.MODID)
public class Confluence {
    public static final String MODID = "confluence";
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");

    public Confluence() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ConfluenceBlocks.BLOCKS.register(bus);
        ConfluenceItems.ITEMS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfluenceConfig.SPEC);
    }
}
