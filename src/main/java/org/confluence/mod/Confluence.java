package org.confluence.mod;

import com.google.gson.Gson;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTabs;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModPaintings;
import org.confluence.mod.misc.ModSounds;
import org.confluence.mod.util.ModResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

import java.util.HashSet;

@SuppressWarnings("unused")
@Mod(Confluence.MODID)
public final class Confluence {
    public static final String MODID = "confluence";
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");
    public static final Gson GSON = new Gson();

    public static final HashSet<ResourceLocation> REQUIRE_PARENT_DONE = new HashSet<>();

    public Confluence() throws ClassNotFoundException {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.SPEC);
        GeckoLib.initialize();
        ModResources.initialize();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.register(bus);
        ModItems.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModPaintings.register(bus);
    }
}
