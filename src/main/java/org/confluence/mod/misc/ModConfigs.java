package org.confluence.mod.misc;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.Confluence;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> RARE_BLOCKS = BUILDER.comment(
        "In order for the block to be found by the Metal Detector",
        "You need to fill the list with string like 'modid:block[state1=true]' or 'modid:block'",
        "The higher the block in the list, the higher the value"
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
        "The higher the creature in the list, the higher the value"
    ).defineListAllowEmpty("rareCreatures", List.of(
        "minecraft:panda",
        "minecraft:skeleton_horse",
        "minecraft:allay",
        "minecraft:axolotl",
        "minecraft:evoker",
        "minecraft:piglin_prute",
        "minecraft:vindicator",
        "minecraft:enderman"
    ), o -> true);

    public static final ForgeConfigSpec.BooleanValue RANDOM_ATTACK_DAMAGE = BUILDER.push("Random Attack Damage").define("enable", true);
    public static final ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MIN = BUILDER.defineInRange("min", 0.8, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MAX = BUILDER.defineInRange("max", 1.2, 1.0, 2.0);

    public static final ForgeConfigSpec.IntValue BERSERKERS_GLOVE_AGGRO = BUILDER.pop().push("Aggro Attach").defineInRange("BerserkersGlove", 400, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue FLESH_KNUCKLES_AGGRO = BUILDER.defineInRange("FleshKnuckles", 400, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue HERO_SHIELD_AGGRO = BUILDER.defineInRange("HeroShield", 400, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue PUTRID_SCENT_AGGRO = BUILDER.defineInRange("PutridScent", -400, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue STALKERS_QUIVER_AGGRO = BUILDER.defineInRange("StalkersQuiver", -400, Integer.MIN_VALUE, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.IntValue SHARK_TOOTH_NECKLACE_ARMOR_PASS = BUILDER.pop().push("Armor Pass").defineInRange("SharkToothNecklace", 5, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue STINGER_NECKLACE_ARMOR_PASS = BUILDER.defineInRange("StingerNecklace", 5, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.DoubleValue CELESTIAL_STONE_CRITICAL_CHANCE = BUILDER.pop().push("Critical Chance").defineInRange("CelestialStone", 0.02, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue DESTROYER_EMBLEM_CRITICAL_CHANCE = BUILDER.defineInRange("DestroyerEmblem", 0.08, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue EYE_OF_GOLEM_CRITICAL_CHANCE = BUILDER.defineInRange("EyeOfTheGolem", 0.1, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue PUTRID_SCENT_CRITICAL_CHANCE = BUILDER.defineInRange("PutridScent", 0.05, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue SHIELD_OF_CTHULHU_CRITICAL_CHANCE = BUILDER.defineInRange("ShieldOfCthulhu", 0.04, 0.0, 1.0);

    public static final ForgeConfigSpec.DoubleValue HURT_EVASION_CHANCE = BUILDER.pop().defineInRange("Hurt Evasion Chance", 0.1, 0.0, 1.0);

    public static final ForgeConfigSpec.IntValue CROSS_NECKLACE_INVULNERABLE_TIME = BUILDER.push("Invulnerable Time").defineInRange("CrossNecklace", 40, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue STAR_VEIL_INVULNERABLE_TIME = BUILDER.defineInRange("StarVeil", 40, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.DoubleValue SORCERER_EMBLEM_MAGIC_BONUS = BUILDER.pop().push("Magic Attack Bonus").defineInRange("SorcererEmblem", 0.15, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue AVENGER_EMBLEM_MAGIC_BONUS = BUILDER.defineInRange("AvengerEmblem", 0.12, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue DESTROYER_EMBLEM_MAGIC_BONUS = BUILDER.defineInRange("DestroyerEmblem", 0.1, 0.0, Double.MAX_VALUE);

    public static final ForgeConfigSpec.DoubleValue AVENGER_EMBLEM_PROJECTILE_BONUS = BUILDER.pop().push("Projectile Attack Bonus").defineInRange("AvengerEmblem", 0.12, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue DESTROYER_EMBLEM_PROJECTILE_BONUS = BUILDER.defineInRange("DestroyerEmblem", 0.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue RANGER_EMBLEM_PROJECTILE_BONUS = BUILDER.defineInRange("RangerEmblem", 0.15, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue MAGIC_QUIVER_PROJECTILE_BONUS = BUILDER.defineInRange("MagicQuiver", 0.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue MOLTEN_QUIVER_PROJECTILE_BONUS = BUILDER.defineInRange("MoltenQuiver", 0.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue STALKER_QUIVER_PROJECTILE_BONUS = BUILDER.defineInRange("StalkersQuiver", 0.1, 0.0, Double.MAX_VALUE);

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
    public static final ForgeConfigSpec.DoubleValue FART_IN_A_BOTTLE_JUMP_SPEED = BUILDER.defineInRange("FartInABottle", 2.8, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SANDSTORM_IN_A_BALLOON_JUMP_SPEED = BUILDER.defineInRange("SandstormInABalloon", 0.45, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SANDSTORM_IN_A_BOTTLE_JUMP_SPEED = BUILDER.defineInRange("SandstormInABottle", 0.45, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue SHARKRON_BALLOON_JUMP_SPEED = BUILDER.defineInRange("SharkronBalloon", 1.1, 0.0, Double.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue TSUNAMI_IN_A_BOTTLE_JUMP_SPEED = BUILDER.defineInRange("TsunamiInABottle", 1.0, 0.0, Double.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static final ArrayList<BlockState> rareBlocks = new ArrayList<>();
    public static final ArrayList<EntityType<?>> rareCreatures = new ArrayList<>();

    @SubscribeEvent
    @SuppressWarnings("deprecation")
    static void onLoad(final ModConfigEvent event) {
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
