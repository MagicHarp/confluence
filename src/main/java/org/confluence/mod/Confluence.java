package org.confluence.mod;

import com.mojang.datafixers.util.Function3;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.confluence.mod.advancement.ModTriggers;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.ClientConfigs;
import org.confluence.mod.client.particle.ModParticles;
import org.confluence.mod.command.ModArgumentTypeInfos;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.npc.ModVillagers;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.ModTabs;
import org.confluence.mod.loot.ModLootModifiers;
import org.confluence.mod.menu.ModMenus;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.misc.ModPaintings;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.recipe.ModRecipes;
import org.confluence.mod.worldgen.ModWorldGens;
import org.confluence.mod.worldgen.feature.ModFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

import java.nio.file.Path;
import java.util.Hashtable;

@SuppressWarnings("unused")
@Mod(Confluence.MODID)
public final class Confluence {
    public static final String MODID = "confluence";
    public static final Logger LOGGER = LoggerFactory.getLogger("Confluence");

    public static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("confluence");
    public static final Hashtable<Class<? extends AbstractMinecart>, Item> MINECART_CURIO = new Hashtable<>();
    public static final Hashtable<Item, Function3<Level, BlockPos, Double, AbstractMinecart>> CURIO_MINECART = new Hashtable<>();
    public static GameRules.Key<GameRules.IntegerValue> SPREADABLE_CHANCE;

    public Confluence() {
        ModConfigs.registerCommon();
        ClientConfigs.registerClient();
        GeckoLib.initialize();
        ModFluids.initialize();
        ModTriggers.initialize();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.register(bus);
        ModItems.register(bus);
        ModPaintings.register(bus);
        ModRecipes.register(bus);
        ModVillagers.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModAttributes.ATTRIBUTES.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModTabs.TABS.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModSoundEvents.SOUNDS.register(bus);
        ModArgumentTypeInfos.INFOS.register(bus);
        ModLootModifiers.MODIFIERS.register(bus);
        ModFeatures.FEATURES.register(bus);
        ModMenus.TYPES.register(bus);
        bus.addListener(ModWorldGens::registerGenerators);
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
