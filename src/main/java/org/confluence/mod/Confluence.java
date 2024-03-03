package org.confluence.mod;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.item.ConfluenceItems;
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
        ConfluenceBlocks.BLOCKS.register(bus);
        ConfluenceItems.ITEMS.register(bus);
        bus.addListener(Confluence::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfluenceConfig.SPEC);
    }

    private static void commonSetup(FMLCommonSetupEvent event){
        GAME_PHASE = GameRules.register("terraGamePhase", GameRules.Category.UPDATES, GameRules.IntegerValue.create(8));
    }
}
