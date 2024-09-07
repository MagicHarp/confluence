package org.confluence.mod.misc;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;

import java.util.ArrayList;
import java.util.List;

public final class ModConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_BLOCKS = BUILDER.comment(
            "In order for the block to be found by the Metal Detector",
            "You need to fill the list with string like 'modid:block[state1=true]' or 'modid:block'",
            "The order of the blocks in the list is the order of their rarity"
    ).defineListAllowEmpty("rareBlocks", List.of(
            "minecraft:ancient_debris",
            "minecraft:suspicious_gravel",
            "minecraft:suspicious_sand",
            "minecraft:deepslate_emerald_ore",
            "minecraft:emerald_ore",
            "minecraft:deepslate_diamond_ore",
            "minecraft:diamond_ore",
            "minecraft:deepslate_lapis_ore",
            "minecraft:lapis_ore",
            "minecraft:deepslate_redstone_ore",
            "minecraft:redstone_ore",
            "minecraft:nether_gold_ore",
            "minecraft:deepslate_gold_ore",
            "minecraft:gold_ore",
            "minecraft:nether_quartz_ore",
            "minecraft:deepslate_iron_ore",
            "minecraft:iron_ore",
            "minecraft:deepslate_copper_ore",
            "minecraft:copper_ore",
            "minecraft:deepslate_coal_ore",
            "minecraft:coal_ore"
    ), o -> true);
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_CREATURES = BUILDER.comment(
            "In order for the creature to be found by the Life Form Analyzer",
            "You need to fill the list with string like 'modid:entity'",
            "The order of the entities in the list is the order of their rarity"
    ).defineListAllowEmpty("rareCreatures", List.of(
            "minecraft:panda",
            "minecraft:skeleton_horse",
            "minecraft:allay",
            "minecraft:axolotl",
            "minecraft:evoker",
            "minecraft:piglin_brute",
            "minecraft:vindicator",
            "minecraft:enderman"
    ), o -> true);

    public static final ForgeConfigSpec.BooleanValue RANDOM_ATTACK_DAMAGE = BUILDER.push("Random Attack Damage").define("enable", false);
    public static final ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MIN = BUILDER.defineInRange("min", 0.8, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MAX = BUILDER.defineInRange("max", 1.2, 1.0, 2.0);

    public static final ForgeConfigSpec.IntValue MAX_ACCESSORIES = BUILDER.pop().defineInRange("Max Accessory Amount", 7, 6, 100);

    public static final ForgeConfigSpec.IntValue CROSS_NECKLACE_INVULNERABLE_TIME = BUILDER.push("Invulnerable Time").defineInRange("CrossNecklace", 40, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue STAR_VEIL_INVULNERABLE_TIME = BUILDER.defineInRange("StarVeil", 40, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.IntValue AMBER_HORSESHOE_BALLOON_FALL_RESISTANCE = BUILDER.pop().push("Fall Resistance").defineInRange("AmberHorseshoeBalloon", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue AMBHIPIAN_BOOTS_FALL_RESISTANCE = BUILDER.defineInRange("AmbhipianBoots", 7, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue BLUE_HORSESHOE_BALLOON_FALL_RESISTANCE = BUILDER.defineInRange("BlueHorseshoeBalloon", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue BUNDLE_OF_HORSESHOE_BALLOONS_FALL_RESISTANCE = BUILDER.defineInRange("BundleOfHorseshoeBalloons", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue FROG_FLIPPER_FALL_RESISTANCE = BUILDER.defineInRange("FrogFlipper", 7, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue FROG_LEG_FALL_RESISTANCE = BUILDER.defineInRange("FrogLeg", 7, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue GREEN_HORSESHOE_BALLOON_FALL_RESISTANCE = BUILDER.defineInRange("GreenHorseshoeBalloon", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue LUCKY_HORSESHOE_FALL_RESISTANCE = BUILDER.defineInRange("LuckyHorseshoe", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue OBSIDIAN_HORSESHOE_FALL_RESISTANCE = BUILDER.defineInRange("ObsidianHorseshoe", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue PINK_HORSESHOE_BALLOON_FALL_RESISTANCE = BUILDER.defineInRange("PinkHorseshoeBalloon", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue WHITE_HORSESHOE_BALLOON_FALL_RESISTANCE = BUILDER.defineInRange("WhiteHorseshoeBalloon", -1, -1, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue YELLOW_HORSESHOE_BALLOON_FALL_RESISTANCE = BUILDER.defineInRange("YellowHorseshoeBalloon", -1, -1, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.DoubleValue AMBER_HORSESHOE_BALLOON_JUMP_BOOST = BUILDER.pop().push("Jump Boost").defineInRange("AmberHorseshoeBalloon", 1.75, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue AMBHIPIAN_BOOTS_JUMP_BOOST = BUILDER.defineInRange("AmbhipianBoots", 1.6, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue BALLOON_JUMP_BOOST = BUILDER.defineInRange("Balloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue BLIZZARD_IN_A_BALLOON_JUMP_BOOST = BUILDER.defineInRange("BlizzardInABalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue BLUE_HORSESHOE_BALLOON_JUMP_BOOST = BUILDER.defineInRange("BlueHorseshoeBalloon", 1.75, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue BUNDLE_OF_BALLOONS_JUMP_BOOST = BUILDER.defineInRange("BundleOfBalloons", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue BUNDLE_OF_HORSESHOE_BALLOONS_JUMP_BOOST = BUILDER.defineInRange("BundleOfHorseshoeBalloons", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue CLOUD_IN_A_BALLOON_JUMP_BOOST = BUILDER.defineInRange("CloudInABalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue FART_IN_A_BALLOON_JUMP_BOOST = BUILDER.defineInRange("FartInABalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue FROG_FLIPPER_JUMP_BOOST = BUILDER.defineInRange("FrogFlipper", 1.6, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue FROG_LEG_JUMP_BOOST = BUILDER.defineInRange("FrogLeg", 1.6, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue GREEN_HORSESHOE_BALLOON_JUMP_BOOST = BUILDER.defineInRange("GreenHorseshoeBalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue HONEY_BALLOON_JUMP_BOOST = BUILDER.defineInRange("HoneyBalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue PINK_HORSESHOE_BALLOON_JUMP_BOOST = BUILDER.defineInRange("PinkHorseshoeBalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SANDSTORM_IN_A_BALLOON_JUMP_BOOST = BUILDER.defineInRange("SandstormInABalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SHARKRON_BALLOON_JUMP_BOOST = BUILDER.defineInRange("SharkronBalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue WHITE_HORSESHOE_BALLOON_JUMP_BOOST = BUILDER.defineInRange("WhiteHorseshoeBalloon", 1.33, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue YELLOW_HORSESHOE_BALLOON_JUMP_BOOST = BUILDER.defineInRange("YellowHorseshoeBalloon", 1.75, 0.0, Double.MAX_VALUE);

    public static final ForgeConfigSpec.IntValue FAIRY_BOOTS_FLY_TICKS = BUILDER.pop().push("May Fly").push("Fly Ticks").defineInRange("FairyBoots", 32, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue FROSTSPARK_BOOTS_FLY_TICKS = BUILDER.defineInRange("FrostsparkBoots", 32, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue LIGHTNING_BOOTS_FLY_TICKS = BUILDER.defineInRange("LightningBoots", 32, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue ROCKET_BOOTS_FLY_TICKS = BUILDER.defineInRange("RocketBoots", 32, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue SPECTRE_BOOTS_FLY_TICKS = BUILDER.defineInRange("SpectreBoots", 32, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue TERRASPARK_BOOTS_FLY_TICKS = BUILDER.defineInRange("TerrasparkBoots", 32, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue FAIRY_BOOTS_FLY_SPEED = BUILDER.pop().push("Fly Speed").defineInRange("FairyBoots", 0.3, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue FROSTSPARK_BOOTS_FLY_SPEED = BUILDER.defineInRange("FrostsparkBoots", 0.3, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue LIGHTNING_BOOTS_FLY_SPEED = BUILDER.defineInRange("LightningBoots", 0.3, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue ROCKET_BOOTS_FLY_SPEED = BUILDER.defineInRange("RocketBoots", 0.3, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SPECTRE_BOOTS_FLY_SPEED = BUILDER.defineInRange("SpectreBoots", 0.3, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue TERRASPARK_BOOTS_FLY_SPEED = BUILDER.defineInRange("TerrasparkBoots", 0.3, 0.0, Double.MAX_VALUE);

    public static final ForgeConfigSpec.IntValue BLIZZARD_IN_A_BALLOON_JUMP_TICKS = BUILDER.pop().pop().push("Multi Jump").push("Jump Ticks").defineInRange("BlizzardInABalloon", 16, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue BLIZZARD_IN_A_BOTTLE_JUMP_TICKS = BUILDER.defineInRange("BlizzardInABottle", 16, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue SANDSTORM_IN_A_BALLOON_JUMP_TICKS = BUILDER.defineInRange("SandstormInABalloon", 20, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue SANDSTORM_IN_A_BOTTLE_JUMP_TICKS = BUILDER.defineInRange("SandstormInABottle", 20, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue BLIZZARD_IN_A_BALLOON_JUMP_SPEED = BUILDER.pop().push("Jump Speed").defineInRange("BlizzardInABalloon", 0.4, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue BLIZZARD_IN_A_BOTTLE_JUMP_SPEED = BUILDER.defineInRange("BlizzardInABottle", 0.4, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue CLOUD_IN_A_BALLOON_JUMP_SPEED = BUILDER.defineInRange("CloudInABalloon", 1.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue CLOUD_IN_A_BOTTLE_JUMP_SPEED = BUILDER.defineInRange("CloudInABottle", 1.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue FART_IN_A_BALLOON_JUMP_SPEED = BUILDER.defineInRange("FartInABalloon", 2.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue FART_IN_A_JAR_JUMP_SPEED = BUILDER.defineInRange("FartInAJar", 2.8, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SANDSTORM_IN_A_BALLOON_JUMP_SPEED = BUILDER.defineInRange("SandstormInABalloon", 0.45, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SANDSTORM_IN_A_BOTTLE_JUMP_SPEED = BUILDER.defineInRange("SandstormInABottle", 0.45, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SHARKRON_BALLOON_JUMP_SPEED = BUILDER.defineInRange("SharkronBalloon", 1.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue TSUNAMI_IN_A_BOTTLE_JUMP_SPEED = BUILDER.defineInRange("TsunamiInABottle", 1.0, 0.0, Double.MAX_VALUE);

    public static final ForgeConfigSpec.DoubleValue MAGIC_QUIVER_NO_CONSUME_CHANCE = BUILDER.pop().pop().push("Magic Quiver").defineInRange("arrowNoConsumeChance", 0.2, 0.0, 1.0);


    // Attributes
    public static final ForgeConfigSpec.DoubleValue ANCIENT_CHISEL_MINING = BUILDER.pop().push("Attributes").push("Ancient Chisel").defineInRange("miningSpeed", 0.25, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue ANKH_SHIELD_RESISTANCE = BUILDER.pop().push("Ankh Shield").defineInRange("knockbackResistance", 1.0, 0.0, 1.0);
    public static final ForgeConfigSpec.IntValue ANKH_SHIELD_ARMOR = BUILDER.defineInRange("armor", 4, 0, 1024);

    public static final ForgeConfigSpec.DoubleValue AVENGER_EMBLEM_DAMAGE = BUILDER.pop().push("Avenger Emblem").defineInRange("attackDamage", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue AVENGER_EMBLEM_RANGED = BUILDER.defineInRange("rangedDamage", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue AVENGER_EMBLEM_MAGIC = BUILDER.defineInRange("magicDamage", 0.12, 0.0, 10.0);

    public static final ForgeConfigSpec.IntValue BERSERKERS_GLOVE_ARMOR = BUILDER.pop().push("Berserker's Glove").defineInRange("armor", 8, 0, 1024);
    public static final ForgeConfigSpec.DoubleValue BERSERKERS_GLOVE_SPEED = BUILDER.defineInRange("attackSpeed", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue BERSERKERS_GLOVE_KNOCKBACK = BUILDER.defineInRange("attackKnockback", 1.0, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue BERSERKERS_GLOVE_REACH = BUILDER.defineInRange("entityReach", 0.1, 0.0, 1024.0);
    public static final ForgeConfigSpec.IntValue BERSERKERS_GLOVE_AGGRO = BUILDER.defineInRange("aggro", 400, -10000, 10000);

    public static final ForgeConfigSpec.DoubleValue CELESTIAL_STONE_SPEED = BUILDER.pop().push("Celestial Stone").defineInRange("attackSpeed", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue CELESTIAL_STONE_DAMAGE = BUILDER.defineInRange("attackDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.IntValue CELESTIAL_STONE_ARMOR = BUILDER.defineInRange("armor", 4, 0, 1024);
    public static final ForgeConfigSpec.DoubleValue CELESTIAL_STONE_CRITICAL_CHANCE = BUILDER.defineInRange("criticalChance", 0.02, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue CELESTIAL_STONE_MINING = BUILDER.defineInRange("miningSpeed", 0.15, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue CELESTIAL_STONE_RANGED = BUILDER.defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue CELESTIAL_STONE_MAGIC = BUILDER.defineInRange("magicDamage", 0.1, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue COBALT_SHIELD_RESISTANCE = BUILDER.pop().push("Cobalt Shield").defineInRange("knockbackResistance", 1.0, 0.0, 1.0);
    public static final ForgeConfigSpec.IntValue COBALT_SHIELD_ARMOR = BUILDER.defineInRange("armor", 1, 0, 1024);

    public static final ForgeConfigSpec.DoubleValue DESTROYER_EMBLEM_DAMAGE = BUILDER.pop().push("Destroyer Emblem").defineInRange("attackDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue DESTROYER_EMBLEM_CRITICAL_CHANCE = BUILDER.defineInRange("criticalChance", 0.08, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue DESTROYER_EMBLEM_RANGED = BUILDER.defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue DESTROYER_EMBLEM_MAGIC = BUILDER.defineInRange("magicDamage", 0.1, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue EYE_OF_GOLEM_CRITICAL_CHANCE = BUILDER.pop().push("EyeOfTheGolem").defineInRange("criticalChance", 0.1, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue FERAL_CLAWS_SPEED = BUILDER.pop().push("Feral Claws").defineInRange("attackSpeed", 0.12, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue FIRE_GAUNTLET_DAMAGE = BUILDER.pop().push("Fire Gauntlet").defineInRange("attackDamage", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue FIRE_GAUNTLET_SPEED = BUILDER.defineInRange("attackSpeed", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue FIRE_GAUNTLET_KNOCKBACK = BUILDER.defineInRange("attackKnockback", 1.0, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue FIRE_GAUNTLET_REACH = BUILDER.defineInRange("entityReach", 0.1, 0.0, 1024.0);

    public static final ForgeConfigSpec.IntValue FLESH_KNUCKLES_ARMOR = BUILDER.pop().push("Flesh Knuckles").defineInRange("armor", 8, 0, 1024);
    public static final ForgeConfigSpec.IntValue FLESH_KNUCKLES_AGGRO = BUILDER.defineInRange("aggro", 400, -10000, 10000);

    public static final ForgeConfigSpec.IntValue FROZEN_SHIELD_ARMOR = BUILDER.pop().push("Frozen Shield").defineInRange("armor", 6, 0, 1024);
    public static final ForgeConfigSpec.DoubleValue FROZEN_SHIELD_RESISTANCE = BUILDER.defineInRange("knockbackResistance", 1.0, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue HAND_OF_CREATION_REACH = BUILDER.pop().push("Hand Of Creation").defineInRange("blockReach", 3.0, 0.0, 1024.0);
    public static final ForgeConfigSpec.DoubleValue HAND_OF_CREATION_MINING = BUILDER.defineInRange("miningSpeed", 0.25, 0.0, 10.0);

    public static final ForgeConfigSpec.IntValue HERO_SHIELD_ARMOR = BUILDER.pop().push("Hero Shield").defineInRange("armor", 10, 0, 1024);
    public static final ForgeConfigSpec.DoubleValue HERO_SHIELD_RESISTANCE = BUILDER.defineInRange("knockbackResistance", 1.0, 0.0, 1.0);
    public static final ForgeConfigSpec.IntValue HERO_SHIELD_AGGRO = BUILDER.defineInRange("aggro", 400, -10000, 10000);

    public static final ForgeConfigSpec.DoubleValue MAGIC_QUIVER_RANGED = BUILDER.pop().push("Magic Quiver").defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue MAGIC_QUIVER_VELOCITY = BUILDER.defineInRange("rangedVelocity", 0.2, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue MECHANICAL_GLOVE_DAMAGE = BUILDER.pop().push("Mechanical Glove").defineInRange("attackDamage", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue MECHANICAL_GLOVE_SPEED = BUILDER.defineInRange("attackDamage", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue MECHANICAL_GLOVE_KNOCKBACK = BUILDER.defineInRange("attackKnockback", 1.0, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue MECHANICAL_GLOVE_REACH = BUILDER.defineInRange("entityReach", 0.1, 0.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue MOLTEN_QUIVER_RANGED = BUILDER.pop().push("Molten Quiver").defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue MOLTEN_QUIVER_VELOCITY = BUILDER.defineInRange("rangedVelocity", 0.2, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue MOON_STONE_SPEED = BUILDER.pop().push("Moon Stone").defineInRange("attackSpeed", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue MOON_STONE_DAMAGE = BUILDER.defineInRange("attackDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.IntValue MOON_STONE_ARMOR = BUILDER.defineInRange("armor", 4, 0, 1024);
    public static final ForgeConfigSpec.DoubleValue MOON_STONE_CRITICAL_CHANCE = BUILDER.defineInRange("criticalChance", 0.02, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue MOON_STONE_MINING = BUILDER.defineInRange("miningSpeed", 0.15, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue MOON_STONE_RANGED = BUILDER.defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue MOON_STONE_MAGIC = BUILDER.defineInRange("magicDamage", 0.1, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue OBSIDIAN_SHIELD_RESISTANCE = BUILDER.pop().push("Obsidian Shield").defineInRange("knockbackResistance", 1.0, 0.0, 1.0);
    public static final ForgeConfigSpec.IntValue OBSIDIAN_SHIELD_ARMOR = BUILDER.defineInRange("armor", 2, 0, 1024);

    public static final ForgeConfigSpec.IntValue PALADINS_SHIELD_ARMOR = BUILDER.pop().push("Paladins Shield").defineInRange("armor", 6, 0, 1024);
    public static final ForgeConfigSpec.DoubleValue PALADINS_SHIELD_RESISTANCE = BUILDER.defineInRange("knockbackResistance", 1.0, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue PANIC_NECKLACE_MOVEMENT = BUILDER.pop().push("Panic Necklace").defineInRange("movementSpeed", 1.0, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue POWER_GLOVE_SPEED = BUILDER.pop().push("Power Glove").defineInRange("attackSpeed", 0.12, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue POWER_GLOVE_KNOCKBACK = BUILDER.defineInRange("attackKnockback", 1.0, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue POWER_GLOVE_REACH = BUILDER.defineInRange("entityReach", 0.1, 0.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue PUTRID_SCENT_DAMAGE = BUILDER.pop().push("Putrid Scent").defineInRange("attackDamage", 0.05, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue PUTRID_SCENT_CRITICAL_CHANCE = BUILDER.defineInRange("criticalChance", 0.05, 0.0, 1.0);
    public static final ForgeConfigSpec.IntValue PUTRID_SCENT_AGGRO = BUILDER.defineInRange("aggro", -400, -10000, 10000);

    public static final ForgeConfigSpec.DoubleValue RANGER_EMBLEM_RANGED = BUILDER.pop().push("Ranger Emblem").defineInRange("rangedDamage", 0.15, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue RECON_SCOPE_CRITICAL_CHANCE = BUILDER.pop().push("Recon Scope").defineInRange("criticalChance", 0.1, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue RECON_SCOPE_RANGED = BUILDER.defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.IntValue RECON_SCOPE_AGGRO = BUILDER.defineInRange("aggro", -400, -10000, 10000);

    public static final ForgeConfigSpec.IntValue SHACKLE_ARMOR = BUILDER.pop().push("Shackle").defineInRange("armor", 1, 0, 1024);

    public static final ForgeConfigSpec.IntValue SHARK_TOOTH_NECKLACE_ARMOR_PASS = BUILDER.pop().push("Shark Tooth Necklace").defineInRange("armorPass", 5, 0, 10000);

    public static final ForgeConfigSpec.DoubleValue SHIELD_OF_CTHULHU_CRITICAL_CHANCE = BUILDER.pop().push("Shield Of Cthulhu").defineInRange("criticalChance", 0.04, 0.0, 1.0);
    public static final ForgeConfigSpec.IntValue SHIELD_OF_CTHULHU_ARMOR = BUILDER.defineInRange("armor", 2, 0, 1024);

    public static final ForgeConfigSpec.DoubleValue SNIPER_SCOPE_CRITICAL_CHANCE = BUILDER.pop().push("Sniper Scope").defineInRange("criticalChance", 0.1, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue SNIPER_SCOPE_RANGED = BUILDER.defineInRange("rangedDamage", 0.1, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue SORCERER_EMBLEM_MAGIC = BUILDER.pop().push("Sorcerer Emblem").defineInRange("magicDamage", 0.15, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue STALKER_QUIVER_RANGED = BUILDER.pop().push("Stalker Quiver").defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue STALKER_QUIVER_VELOCITY = BUILDER.defineInRange("rangedVelocity", 0.2, 0.0, 10.0);
    public static final ForgeConfigSpec.IntValue STALKERS_QUIVER_AGGRO = BUILDER.defineInRange("aggro", -400, -10000, 10000);

    public static final ForgeConfigSpec.IntValue STINGER_NECKLACE_ARMOR_PASS = BUILDER.pop().push("Stinger Necklace").defineInRange("armorPass", 5, 0, 10000);

    public static final ForgeConfigSpec.DoubleValue SUN_STONE_SPEED = BUILDER.pop().push("Sun Stone").defineInRange("attackSpeed", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue SUN_STONE_DAMAGE = BUILDER.defineInRange("attackDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.IntValue SUN_STONE_ARMOR = BUILDER.defineInRange("armor", 4, 0, 1024);
    public static final ForgeConfigSpec.DoubleValue SUN_STONE_CRITICAL_CHANCE = BUILDER.defineInRange("criticalChance", 0.02, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue SUN_STONE_MINING = BUILDER.defineInRange("miningSpeed", 0.15, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue SUN_STONE_RANGED = BUILDER.defineInRange("rangedDamage", 0.1, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue SUN_STONE_MAGIC = BUILDER.defineInRange("magicDamage", 0.1, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue TITAN_GLOVE_KNOCKBACK = BUILDER.pop().push("Titan Glove").defineInRange("attackKnockback", 1.0, 0.0, 10.0);
    public static final ForgeConfigSpec.DoubleValue TITAN_GLOVE_REACH = BUILDER.defineInRange("entityReach", 0.1, 0.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue TREASURE_MAGNET_PICKUP = BUILDER.pop().push("Treasure Magnet").defineInRange("pickupRange", 6.25, 0.0, 64.0);

    public static final ForgeConfigSpec.DoubleValue WARRIOR_EMBLEM_DAMAGE = BUILDER.pop().push("Warrior Emblem").defineInRange("attackDamage", 0.15, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue EXTENDO_GRIP_REACH = BUILDER.pop().push("Extendo Grip").defineInRange("blockReach", 3.0, 0.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue TOOLBELT_REACH = BUILDER.pop().push("Toolbelt").defineInRange("blockReach", 1.0, 0.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue TOOLBOX_REACH = BUILDER.pop().push("Toolbox").defineInRange("blockReach", 1.0, 0.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue ANGLER_EARRING_LUCK = BUILDER.pop().push("Angler Earring").defineInRange("luck", 10.0, -1024.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue AGLET_MOVEMENT = BUILDER.pop().push("Aglet").defineInRange("movementSpeed", 0.05, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue ANKLET_OF_THE_WIND = BUILDER.pop().push("Anklet of the Wind").defineInRange("movementSpeed", 0.1, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue FLIPPER_SWIM = BUILDER.pop().push("Flipper").defineInRange("swimSpeed", 0.5, 0.0, 10.0);

    public static final ForgeConfigSpec.DoubleValue LUCKY_HORSESHOE = BUILDER.pop().push("Lucky Horseshoe").defineInRange("luck", 0.05, -1024.0, 1024.0);

    public static final ForgeConfigSpec.DoubleValue MAGILUMINESCENCE_MOVEMENT = BUILDER.pop().push("Magiluminescence").defineInRange("movementSpeed", 0.15, 0.0, 10.0);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static final ArrayList<BlockState> rareBlocks = new ArrayList<>();
    public static final ArrayList<EntityType<?>> rareCreatures = new ArrayList<>();

    @SuppressWarnings("deprecation")
    public static void onLoadCommon() {
        rareBlocks.clear();
        rareCreatures.clear();
        RARE_BLOCKS.get().forEach(s -> {
            try {
                rareBlocks.add(BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), s, false).blockState());
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
            }
        });
        RARE_CREATURES.get().forEach(s -> rareCreatures.add(ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(s))));
    }
}
