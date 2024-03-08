package org.confluence.mod;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.client.particle.ConfluenceParticles;
import org.confluence.mod.entity.ConfluenceEntities;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.ConfluenceTabs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

@SuppressWarnings("unused")
@Mod(Confluence.MODID)
public class Confluence {
    public static final String MODID = "confluence";
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");
    public static GameRules.Key<GameRules.IntegerValue> GAME_PHASE;

    public Confluence() {
        GeckoLib.initialize();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ConfluenceBlocks.register(bus);
        ConfluenceItems.register(bus);
        ConfluenceParticles.PARTICLES.register(bus);
        ConfluenceEntities.ENTITIES.register(bus);
        ConfluenceTabs.TABS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfluenceConfig.SPEC);
    }
}
