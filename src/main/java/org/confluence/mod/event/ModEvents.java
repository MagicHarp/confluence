package org.confluence.mod.event;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.block.natural.spreadable.ISpreadable;
import org.confluence.mod.block.reveal.StepRevealingBlock;
import org.confluence.mod.entity.ModEntities;
import org.confluence.mod.entity.demoneye.DemonEye;
import org.confluence.mod.entity.slime.BaseSlime;
import org.confluence.mod.fluid.FluidBuilder;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.mixin.accessor.RangedAttributeAccessor;
import org.confluence.mod.network.NetworkHandler;
import org.confluence.mod.recipe.AmountIngredient;
import org.confluence.mod.util.ModResources;
import org.confluence.mod.worldgen.biome.*;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import static org.confluence.mod.Confluence.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    @SubscribeEvent
    public static void attributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BLUE_SLIME.get(), BaseSlime.createSlimeAttributes(4.0F, 0, 16.0F).build());
        event.put(ModEntities.GREEN_SLIME.get(), BaseSlime.createSlimeAttributes(3.0F, 0, 9.0F).build());
        event.put(ModEntities.PINK_SLIME.get(), BaseSlime.createSlimeAttributes(2.0F, 2, 97.0F).build());
        event.put(ModEntities.CORRUPTED_SLIME.get(), BaseSlime.createSlimeAttributes(35.0F, 6, 110.0F).build());
        event.put(ModEntities.DESERT_SLIME.get(), BaseSlime.createSlimeAttributes(6.0F, 0 ,21.0F).build());
        event.put(ModEntities.EVIL_SLIME.get(), BaseSlime.createSlimeAttributes(29.0F, 2, 58.0F).build());
        event.put(ModEntities.ICE_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 0, 13.0F).build());
        event.put(ModEntities.LAVA_SLIME.get(), BaseSlime.createSlimeAttributes(10.0F, 2, 30.0F).build());
        event.put(ModEntities.LUMINOUS_SLIME.get(), BaseSlime.createSlimeAttributes(45.0F, 6, 117.0F).build());
        event.put(ModEntities.CRIMSON_SLIME.get(), BaseSlime.createSlimeAttributes(39.0F, 5, 130.0F).build());
        event.put(ModEntities.PURPLE_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 1, 25.0F).build());
        event.put(ModEntities.RED_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 1, 25.0F).build());
        event.put(ModEntities.TROPIC_SLIME.get(), BaseSlime.createSlimeAttributes(5.0F, 0, 13.0F).build());
        event.put(ModEntities.YELLOW_SLIME.get(), BaseSlime.createSlimeAttributes(6.0F, 2, 25.0F).build());
        event.put(ModEntities.BLACK_SLIME.get(), Monster.createMonsterAttributes().build()); // 由finalizeSpawn设置
        event.put(ModEntities.DEMON_EYE.get(), DemonEye.createAttributes().build());
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkHandler.register();
            ModFluids.registerInteraction();
            ModFluids.registerShimmerTransform();

            if (!ModList.get().isLoaded("attributefix")) {
                Attribute armor = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor"));
                if (armor instanceof RangedAttribute rangedAttribute) {
                    ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0);
                }
                Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation("generic.armor_toughness"));
                if (attribute instanceof RangedAttribute rangedAttribute) {
                    ((RangedAttributeAccessor) rangedAttribute).setMaxValue(1024.0);
                }
            }

            Regions.register(new AnotherCrimsonRegion(new ResourceLocation(MODID, "another_crimson"), 1));
            Regions.register(new TheHallowRegion(new ResourceLocation(MODID, "the_hallow"), 1));
            Regions.register(new TheCorruptionRegion(new ResourceLocation(MODID, "the_corruption"), 1));
            Regions.register(new AshForestRegion(new ResourceLocation(MODID, "ash_forest"), 0));
            Regions.register(new AshWastelandRegion(new ResourceLocation(MODID, "ash_wasteland"), 0));
            Regions.register(new GlowingMushroomRegion(new ResourceLocation(MODID, "glowing_mushroom"), 1));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, SurfaceRuleData.makeRules());
        });
    }

    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            StepRevealingBlock.registerOurOwn();
            LogBlocks.wrapStrip();
            ISpreadable.Type.buildMap();
        });
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo modFileInfo = ModList.get().getModFileById(MODID);
            IModFile modFile = modFileInfo.getFile();
            event.addRepositorySource(consumer -> {
                Pack pack = Pack.readMetaAndCreate(
                    "confluence:terraria_art", Component.literal("Terraria Art"), false,
                    id -> new ModResources(id, modFile, "resourcepacks/terraria_art"),
                    PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN
                );
                if (pack != null) {
                    consumer.accept(pack);
                }
            });
        }
    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, helper -> {
            CraftingHelper.register(new ResourceLocation(MODID, "amount"), AmountIngredient.Serializer.INSTANCE);
        });
        FluidBuilder.register(event);
    }

    @SubscribeEvent
    public static void spawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.BLUE_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.GREEN_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.PURPLE_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.PINK_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        // pink
        event.register(ModEntities.CORRUPTED_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.DESERT_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.ICE_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.TROPIC_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ModEntities.CRIMSON_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
