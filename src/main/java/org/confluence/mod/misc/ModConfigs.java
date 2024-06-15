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

    private static final ForgeConfigSpec.BooleanValue RANDOM_ATTACK_DAMAGE = BUILDER.push("Random Attack Damage").define("enable", true);
    private static final ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MIN = BUILDER.defineInRange("min", 0.8, 0.0, 1.0);
    private static final ForgeConfigSpec.DoubleValue RANDOM_ATTACK_DAMAGE_MAX = BUILDER.defineInRange("max", 1.2, 1.0, 2.0);

    private static final ForgeConfigSpec.BooleanValue SHOWING_MODEL = BUILDER.pop().comment("If showing curios model").define("showingModel", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static final ArrayList<BlockState> rareBlocks = new ArrayList<>();
    public static final ArrayList<EntityType<?>> rareCreatures = new ArrayList<>();
    public static boolean randomAttackDamage = true;
    public static double randomAttackDamageMin = 0.8;
    public static double randomAttackDamageMax = 1.2;
    public static boolean showingModel = false;

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
        randomAttackDamage = RANDOM_ATTACK_DAMAGE.get();
        randomAttackDamageMin = RANDOM_ATTACK_DAMAGE_MIN.get();
        randomAttackDamageMax = RANDOM_ATTACK_DAMAGE_MAX.get();
        showingModel = SHOWING_MODEL.get();
    }
}
