package org.confluence.mod.misc;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModConfigs {
    public static ForgeConfigSpec.BooleanValue DROP_MONEY;
    public static ForgeConfigSpec.BooleanValue REPLACE_VANILLA_GOLDEN_ITEMS;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_BLOCKS;
    private static ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_CREATURES;
    public static ArrayList<BlockState> rareBlocks = new ArrayList<>();
    public static ArrayList<EntityType<?>> rareCreatures = new ArrayList<>();

    private static ForgeConfigSpec.BooleanValue TERRA_STYLE_HEALTH;
    public static boolean terraStyleHealth = true;

    @SubscribeEvent
    public static void onLoad(ModConfigEvent event) {
        RARE_BLOCKS.get().forEach(s -> {
            try {
                rareBlocks.add(BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), s, false).blockState());
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
            }
        });
        RARE_CREATURES.get().forEach(s -> rareCreatures.add(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(s))));

        if (TERRA_STYLE_HEALTH != null) {
            terraStyleHealth = TERRA_STYLE_HEALTH.get();
        }
    }

    public static void registerCommon() {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        DROP_MONEY = BUILDER
            .comment("Determines if entity drops money after death")
            .define("dropsMoney", true);
        REPLACE_VANILLA_GOLDEN_ITEMS = BUILDER
            .comment("Determines if replace vanilla golden items such as 'minecraft:golden_sword'")
            .define("replaceVanillaGoldenItems", true);
        RARE_BLOCKS = BUILDER.comment(
            "In order for the block to be found by the Metal Detector",
            "You need to fill the list with string like 'modid:block[state1=true]' or 'modid:block'",
            "The higher the block in the list, the higher the value"
        ).defineListAllowEmpty("rareBlocks", List.of(
            "confluence:life_crystal_block",
            "confluence:another_crimson_ore",
            "confluence:deepslate_another_crimson_ore",
            "confluence:ebony_ore",
            "confluence:deepslate_ebony_ore",
            "minecraft:ancient_debris",
            "minecraft:diamond_ore",
            "minecraft:deepslate_diamond_ore",
            "confluence:platinum_ore",
            "confluence:deepslate_platinum_ore",
            "minecraft:gold_ore",
            "minecraft:deepslate_gold_ore",
            "minecraft:chest",
            "confluence:forest_pots",
            "confluence:tundra_pots",
            "confluence:spider_nest_pots",
            "confluence:underground_desert_pots",
            "confluence:jungle_pots",
            "confluence:marble_cave_pots",
            "confluence:pyramid_pots",
            "confluence:corruption_pots",
            "confluence:another_crimson_pots",
            "confluence:dungeon_pots",
            "confluence:underworld_pots",
            "confluence:lihzahrd_pots",
            "confluence:tungsten_ore",
            "confluence:deepslate_tungsten_ore",
            "confluence:silver_ore",
            "confluence:deepslate_silver_ore",
            "confluence:lead_ore",
            "confluence:deepslate_lead_ore",
            "minecraft:iron_ore",
            "minecraft:deepslate_iron_ore",
            "confluence:tin_ore",
            "confluence:deepslate_tin_ore",
            "minecraft:copper_ore",
            "minecraft:deepslate_copper_ore"
        ), o -> true);
        RARE_CREATURES = BUILDER.comment(
            "In order for the creature to be found by the Life Form Analyzer",
            "You need to fill the list with string like 'modid:entity'",
            "The higher the creature in the list, the higher the value"
        ).defineListAllowEmpty("rareCreatures", List.of(
            "confluence:pink_slime",
            "minecraft:skeleton_horse",
            "minecraft:sniffer",
            "minecraft:allay",
            "minecraft:warden",
            "minecraft:mooshroom",
            "minecraft:panda"), o -> true);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BUILDER.build());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerClient() {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        TERRA_STYLE_HEALTH = BUILDER.push("HUD").define("drawVanillaHealth", true);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BUILDER.build());
    }
}
