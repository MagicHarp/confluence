package org.confluence.mod.common;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.confluence.mod.Confluence;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class CommonConfigs {
    public static final Supplier<String> STRING_SUPPLIER = () -> null;
    public static final Predicate<Object> ALWAYS_TRUE = o -> true;
    public static ModConfigSpec.BooleanValue DROP_MONEY;
    private static ModConfigSpec.ConfigValue<List<? extends String>> RARE_BLOCKS;
    private static ModConfigSpec.ConfigValue<List<? extends String>> RARE_CREATURES;
    public static ArrayList<BlockState> rareBlocks = new ArrayList<>();
    public static ArrayList<EntityType<?>> rareCreatures = new ArrayList<>();

    public static void onLoad() {
        RARE_BLOCKS.get().forEach(s -> {
            try {
                rareBlocks.add(BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), s, false).blockState());
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
            }
        });
        RARE_CREATURES.get().forEach(s -> rareCreatures.add(BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(s))));
    }

    public static void register(ModContainer container) {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        DROP_MONEY = BUILDER
                .comment("Determines if entity drops money after death")
                .define("dropsMoney", true);
        RARE_BLOCKS = BUILDER.comment(
                "In order for the block to be found by the Metal Detector",
                "You need to fill the list with string like 'modid:block[state1=true]' or 'modid:block'",
                "The higher the block in the list, the higher the value"
        ).defineListAllowEmpty("rareBlocks", List.of(
                "confluence:life_crystal_block",
                "confluence:tr_crimson_ore",
                "confluence:deepslate_tr_crimson_ore",
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
                "confluence:tr_crimson_pots",
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
        ), STRING_SUPPLIER, ALWAYS_TRUE);
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
                "minecraft:panda"), STRING_SUPPLIER, ALWAYS_TRUE);
        container.registerConfig(ModConfig.Type.COMMON, BUILDER.build());
    }
}
