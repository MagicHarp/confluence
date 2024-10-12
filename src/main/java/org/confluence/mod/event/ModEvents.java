package org.confluence.mod.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.RegisterEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.crafting.AlchemyTableBlock;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.block.natural.spreadable.ISpreadable;
import org.confluence.mod.block.reveal.StepRevealingBlock;
import org.confluence.mod.client.EntityAtlas;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.entity.monster.BloodCrawler;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.boss.geoEntity.CthulhuEye;
import org.confluence.mod.entity.boss.KingSlime;
import org.confluence.mod.entity.monster.CrimsonCreeper;
import org.confluence.mod.entity.monster.demoneye.DemonEye;
import org.confluence.mod.entity.minion.FinchMinionEntity;
import org.confluence.mod.entity.npc.ModVillagers;
import org.confluence.mod.entity.monster.slime.BaseSlime;
import org.confluence.mod.entity.monster.slime.HoneySlime;
import org.confluence.mod.entity.monster.worm.TestWormEntity;
import org.confluence.mod.entity.monster.worm.TestWormPart;
import org.confluence.mod.fluid.FluidBuilder;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.misc.ModConfigs;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.recipe.ModRecipes;
import org.confluence.mod.util.ModResources;
import org.confluence.mod.worldgen.ModWorldGens;
import org.confluence.mod.worldgen.biome.ModBiomes;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.Confluence.entityAtlas;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    @SubscribeEvent
    public static void attributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BLUE_SLIME.get(), BaseSlime.createSlimeAttributes(4.0F, 0, 16.0F).build());
        event.put(ModEntities.GREEN_SLIME.get(), BaseSlime.createSlimeAttributes(3.0F, 0, 9.0F).build());
        event.put(ModEntities.PINK_SLIME.get(), BaseSlime.createSlimeAttributes(2.0F, 2, 97.0F).build());
        event.put(ModEntities.CORRUPTED_SLIME.get(), BaseSlime.createSlimeAttributes(35.0F, 6, 110.0F).build());
        event.put(ModEntities.DESERT_SLIME.get(), BaseSlime.createSlimeAttributes(6.0F, 0, 21.0F).build());
        event.put(ModEntities.JUNGLE_SLIME.get(), BaseSlime.createSlimeAttributes(12.0F, 0, 46.0F).build());
        event.put(ModEntities.EVIL_SLIME.get(), BaseSlime.createSlimeAttributes(29.0F, 2, 58.0F).build());
        event.put(ModEntities.ICE_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 0, 13.0F).build());
        event.put(ModEntities.LAVA_SLIME.get(), BaseSlime.createSlimeAttributes(10.0F, 2, 30.0F).build());
        event.put(ModEntities.LUMINOUS_SLIME.get(), BaseSlime.createSlimeAttributes(45.0F, 6, 117.0F).build());
        event.put(ModEntities.CRIMSON_SLIME.get(), BaseSlime.createSlimeAttributes(39.0F, 5, 130.0F).build());
        event.put(ModEntities.PURPLE_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 1, 25.0F).build());
        event.put(ModEntities.RED_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 1, 25.0F).build());
        event.put(ModEntities.TROPIC_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 0, 13.0F).build());
        event.put(ModEntities.YELLOW_SLIME.get(), BaseSlime.createSlimeAttributes(6.0F, 2, 25.0F).build());
        event.put(ModEntities.HONEY_SLIME.get(), HoneySlime.createSlimeAttributes(0F, 0, 16.0F).build());
        event.put(ModEntities.BLACK_SLIME.get(), Monster.createMonsterAttributes().build()); // 由finalizeSpawn设置
        event.put(ModEntities.DEMON_EYE.get(), DemonEye.createAttributes().build());
        event.put(ModEntities.BLOOD_CRAWLER.get(), BloodCrawler.createAttributes().build());
        event.put(ModEntities.CRIMSON_CREEPER.get(), CrimsonCreeper.createAttributes().build());

        event.put(ModEntities.KING_SLIME.get(), KingSlime.createSlimeAttributes().build());
        event.put(ModEntities.CTHULHU_EYE.get(), CthulhuEye.createAttributes().build());

        event.put(ModEntities.TEST_WORM.get(), TestWormEntity.createAttributes().build());
        event.put(ModEntities.TEST_WORM_PART.get(), TestWormPart.createAttributes().build());

        event.put(ModEntities.FINCH_MINION.get(), FinchMinionEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModConfigs.onCommonLoad();
            NetworkHandler.register();
            ModFluids.registerInteraction();
            ModFluids.registerShimmerTransform();
            ModAttributes.modifyAttributesUpperLimit();
            ModBiomes.registerRegionAndSurface();
            Confluence.registerGameRules();
        });

        //炼药桌
        AlchemyTableBlock.addStatement(1, Integer::sum, false);  //a+b
        AlchemyTableBlock.addStatement(2, (a, b) -> a - b, false);
        AlchemyTableBlock.addStatement(3, (a, b) -> a * b, false);
        AlchemyTableBlock.addStatement(4, (a, b) -> a / b, false);
        AlchemyTableBlock.addStatement(5, (a, b) -> a % b, false);
        AlchemyTableBlock.addStatement(6, (a, b) -> (int) Math.sqrt(a), true);
        AlchemyTableBlock.addStatement(7, (a, b) -> (int) Math.pow(a, 2), true);
        AlchemyTableBlock.addStatement(8, (a, b) -> (int) Math.pow(a, 3), true);
        AlchemyTableBlock.addStatement(9, (a, b) -> -a, true);

        AlchemyTableBlock.addOperator(ModItems.WATERLEAF.get(), 1);
        AlchemyTableBlock.addOperator(ModItems.MOONSHINE_GRASS.get(), 2);
        AlchemyTableBlock.addOperator(ModItems.SHINE_ROOT.get(), 3);
        AlchemyTableBlock.addOperator(ModItems.SHIVERINGTHORNS.get(), 4);
        AlchemyTableBlock.addOperator(ModItems.SUNFLOWERS.get(), 5);
        AlchemyTableBlock.addOperator(ModItems.DEATHWEED.get(), 6);
        AlchemyTableBlock.addOperator(ModItems.FLAMEFLOWERS.get(), 7);
        AlchemyTableBlock.addOperator(ModItems.EBONY_MUSHROOM.get(), 8);
        AlchemyTableBlock.addOperator(ModItems.GLOWING_MUSHROOM.get(), 8);
        AlchemyTableBlock.addOperator(ModItems.LIFE_MUSHROOM.get(), 8);
        AlchemyTableBlock.addOperator(ModItems.TR_CRIMSON_MUSHROOM.get(), 8);
        AlchemyTableBlock.addOperator(Materials.BLACK_PEARL.get(), 9);
    }

    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            StepRevealingBlock.registerOurOwn();
            LogBlocks.wrapStrip();
            ISpreadable.Type.buildMap();
            Confluence.registerMinecartAbility();
            ModEffects.addAttributeModifiers();
        });
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo modFileInfo = ModList.get().getModFileById(MODID);
            IModFile modFile = modFileInfo.getFile();
            event.addRepositorySource(consumer -> {
                Pack pack = Pack.readMetaAndCreate(
                        "confluence:terraria_art", Component.translatable("resourcepack.terraria_art"), false,
                        id -> new ModResources(id, modFile, "resourcepacks/terraria_art"),
                        PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN
                );
                if (pack != null) consumer.accept(pack);
            });

            event.addRepositorySource(consumer -> {
                Pack pack = Pack.readMetaAndCreate(
                        "confluence:mainstream_connected_ores", Component.translatable("resourcepack.mainstream_connected_ores"), false,
                        id -> new ModResources(id, modFile, "resourcepacks/mainstream_connected_ores"),
                        PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN
                );
                if (pack != null) consumer.accept(pack);
            });

            event.addRepositorySource(consumer -> {
                Pack pack = Pack.readMetaAndCreate(
                        "confluence:ter_armor", Component.translatable("resourcepack.ter_armor"), false,
                        id -> new ModResources(id, modFile, "resourcepacks/ter_armor"),
                        PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN
                );
                if (pack != null) consumer.accept(pack);
            });
        }
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        ModRecipes.registerSerializers(event);
        FluidBuilder.register(event);
        ModVillagers.registerTypes(event);
        ModWorldGens.registerGenerators(event);
    }

    @SubscribeEvent
    public static void spawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.BLUE_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.GREEN_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.PURPLE_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.PINK_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.CORRUPTED_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.DESERT_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.JUNGLE_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.ICE_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.TROPIC_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.CRIMSON_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.YELLOW_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.RED_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.BLACK_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BaseSlime::checkSlimeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.DEMON_EYE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEye::checkDemonEyeSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.BLOOD_CRAWLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BloodCrawler::checkBloodCrawlerSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void modify(EntityAttributeModificationEvent event) {
        ModAttributes.readJsonConfig();
        ModAttributes.registerAttribute(ModAttributes.CRIT_CHANCE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_VELOCITY.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_DAMAGE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.DODGE_CHANCE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.MINING_SPEED.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.AGGRO.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.MAGIC_DAMAGE.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.ARMOR_PASS.get(), event::add);
        ModAttributes.registerAttribute(ModAttributes.PICKUP_RANGE.get(), event::add);
    }

    @SubscribeEvent
    public static void onRegisterClientReload(RegisterClientReloadListenersEvent event){
        Confluence.entityAtlas = new EntityAtlas();
        event.registerReloadListener(entityAtlas);
    }
}
