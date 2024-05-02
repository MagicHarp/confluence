package org.confluence.mod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTabs;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
@Mod(Confluence.MODID)
public final class Confluence {
    public static final String MODID = "confluence";
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");

    public Confluence() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.SPEC);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(bus);
        ModTabs.TABS.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModSounds.SOUNDS.register(bus);
    }
}
