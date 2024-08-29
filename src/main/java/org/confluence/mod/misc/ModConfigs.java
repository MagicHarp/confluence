package org.confluence.mod.misc;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.effect.beneficial.RageEffect;
import org.confluence.mod.effect.neutral.CerebralMindtrickEffect;

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
    public static ForgeConfigSpec.ConfigValue<String> CRI_CHANCE;
    public static ForgeConfigSpec.ConfigValue<String> RANGED_VELOCITY;
    public static ForgeConfigSpec.ConfigValue<String> RANGED_DAMAGE;
    public static ForgeConfigSpec.ConfigValue<String> DODGE_CHANCE;
    public static ForgeConfigSpec.ConfigValue<String> MINING_SPEED;

    public static void onCommonLoad() {
        RARE_BLOCKS.get().forEach(s -> {
            try {
                rareBlocks.add(BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), s, false).blockState());
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
            }
        });
        RARE_CREATURES.get().forEach(s -> rareCreatures.add(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(s))));
        ModEffects.RAGE.get().addAttributeModifier(ModAttributes.getCriticalChance(), RageEffect.CRIT_UUID, 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        ModEffects.CEREBRAL_MINDTRICK.get().addAttributeModifier(ModAttributes.getCriticalChance(), CerebralMindtrickEffect.CRIT_UUID, 0.04, AttributeModifier.Operation.ADDITION);
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
        BUILDER.push("Custom Attributes");
        CRI_CHANCE = BUILDER.define("critcalChance", "attributeslib:crit_chance");
        RANGED_VELOCITY = BUILDER.define("rangedVelocity", "attributeslib:arrow_velocity");
        RANGED_DAMAGE = BUILDER.define("rangedDamage", "attributeslib:arrow_damage");
        DODGE_CHANCE = BUILDER.define("dodgeChance", "attributeslib:dodge_chance");
        MINING_SPEED = BUILDER.define("miningSpeed", "attributeslib:mining_speed");
        BUILDER.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BUILDER.build());
    }
}
