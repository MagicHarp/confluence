package org.confluence.mod.block.natural;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.EnumBlockRegister;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.BaseBlock;
import org.confluence.mod.block.reveal.StepRevealingBlock;

import java.util.function.Supplier;

public enum Ores implements EnumBlockRegister<Block> {
    SANCTIFICATION_COAL_ORE("sanctification_coal_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE))),
    CORRUPTION_COAL_ORE("corruption_coal_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE))),
    FLESHIFICATION_COAL_ORE("fleshification_coal_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE))),

    SANCTIFICATION_COPPER_ORE("sanctification_copper_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    CORRUPTION_COPPER_ORE("corruption_copper_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    FLESHIFICATION_COPPER_ORE("fleshification_copper_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),

    TIN_ORE("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    DEEPSLATE_TIN_ORE("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE))),
    SANCTIFICATION_TIN_ORE("sanctification_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    CORRUPTION_TIN_ORE("corruption_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    FLESHIFICATION_TIN_ORE("fleshification_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),


    RAW_TIN_BLOCK("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK))),
    TIN_BLOCK("tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),
    SANCTIFICATION_IRON_ORE("sanctification_iron_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    CORRUPTION_IRON_ORE("corruption_iron_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    FLESHIFICATION_IRON_ORE("fleshification_iron_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),

    LEAD_ORE("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_LEAD_ORE("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    SANCTIFICATION_LEAD_ORE("sanctification_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    CORRUPTION_LEAD_ORE("corruption_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    FLESHIFICATION_LEAD_ORE("fleshification_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),

    RAW_LEAD_BLOCK("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    LEAD_BLOCK("lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    SILVER_ORE("silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_SILVER_ORE("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    SANCTIFICATION_SILVER_ORE("sanctification_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    CORRUPTION_SILVER_ORE("corruption_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    FLESHIFICATION_SILVER_ORE("fleshification_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),

    RAW_SILVER_BLOCK("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    SILVER_BLOCK("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    TUNGSTEN_ORE("tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_TUNGSTEN_ORE("deepslate_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    SANCTIFICATION_TUNGSTEN_ORE("sanctification_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    CORRUPTION_TUNGSTEN_ORE("corruption_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    FLESHIFICATION_TUNGSTEN_ORE("fleshification_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),

    RAW_TUNGSTEN_BLOCK("raw_tungsten_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    TUNGSTEN_BLOCK("tungsten_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    SANCTIFICATION_GOLD_ORE("sanctification_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    CORRUPTION_GOLD_ORE("corruption_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    FLESHIFICATION_GOLD_ORE("fleshification_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    PLATINUM_ORE("platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    DEEPSLATE_PLATINUM_ORE("deepslate_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE))),
    SANCTIFICATION_PLATINUM_ORE("sanctification_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    CORRUPTION_PLATINUM_ORE("corruption_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    FLESHIFICATION_PLATINUM_ORE("fleshification_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    RAW_PLATINUM_BLOCK("raw_platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK))),
    PLATINUM_BLOCK("platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK))),
    // 陨铁
    METEORITE_ORE("meteorite_ore", MeteoriteOre::new),
    RAW_METEORITE_BLOCK("raw_meteorite_block", MeteoriteOre::new),
    METEORITE_BLOCK("meteorite_block", MeteoriteOre::new),
    // 坚固化石
    STURDY_FOSSIL_BLOCK("sturdy_fossil_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK))),
    // 绿宝石
    SANCTIFICATION_EMERALD_ORE("sanctification_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE))),
    CORRUPTION_EMERALD_ORE("corruption_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE))),
    FLESHIFICATION_EMERALD_ORE("fleshification_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE))),
    // 钻石
    SANCTIFICATION_DIAMOND_ORE("sanctification_diamond_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    CORRUPTION_DIAMOND_ORE("corruption_diamond_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    FLESHIFICATION_DIAMOND_ORE("fleshification_diamond_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    // 红玉矿
    RUBY_ORE("ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    DEEPSLATE_RUBY_ORE("deepslate_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    SANCTIFICATION_RUBY_ORE("sanctification_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    CORRUPTION_RUBY_ORE("corruption_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    FLESHIFICATION_RUBY_ORE("fleshification_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),

    // 黄玉矿
    TOPAZ_ORE("topaz_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    DEEPSLATE_TOPAZ_ORE("deepslate_topaz_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    SANCTIFICATION_TOPAZ_ORE("sanctification_topaz_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    CORRUPTION_TOPAZ_ORE("corruption_topaz_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    FLESHIFICATION_TOPAZ_ORE("fleshification_topaz_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),

    // 琥珀矿
    AMBER_ORE("amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    DEEPSLATE_AMBER_ORE("deepslate_amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    SANCTIFICATION_AMBER_ORE("sanctification_amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    CORRUPTION_AMBER_ORE("corruption_amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    FLESHIFICATION_AMBER_ORE("fleshification_amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),

    // 翡翠矿
    TR_EMERALD_ORE("tr_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    DEEPSLATE_TR_EMERALD_ORE("deepslate_tr_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    SANCTIFICATION_TR_EMERALD_ORE("sanctification_tr_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    CORRUPTION_TR_EMERALD_ORE("corruption_tr_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    FLESHIFICATION_TR_EMERALD_ORE("fleshification_tr_emerald_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),

    // 异域紫晶矿
    TR_AMETHYST_ORE("tr_amethyst_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    DEEPSLATE_TR_AMETHYST_ORE("deepslate_tr_amethyst_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    SANCTIFICATION_TR_AMETHYST_ORE("sanctification_tr_amethyst_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    CORRUPTION_TR_AMETHYST_ORE("corruption_tr_amethyst_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    FLESHIFICATION_TR_AMETHYST_ORE("fleshification_tr_amethyst_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),

    // 蓝玉矿
    SAPPHIRE_ORE("sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    DEEPSLATE_SAPPHIRE_ORE("deepslate_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    SANCTIFICATION_SAPPHIRE_ORE("sanctification_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    CORRUPTION_SAPPHIRE_ORE("corruption_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),
    FLESHIFICATION_SAPPHIRE_ORE("fleshification_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE))),

    // 青金石矿
    SANCTIFICATION_LAPIS_ORE("sanctification_lapis_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE))),
    CORRUPTION_LAPIS_ORE("corruption_lapis_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE))),
    FLESHIFICATION_LAPIS_ORE("fleshification_lapis_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE))),
    // 红石矿

    // 魔矿
    EBONY_ORE("ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    DEEPSLATE_EBONY_ORE("deepslate_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    SANCTIFICATION_EBONY_ORE("sanctification_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    CORRUPTION_EBONY_ORE("corruption_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    FLESHIFICATION_EBONY_ORE("fleshification_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),

    EBONY_BLOCK("ebony_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)), TAGS_ORE_DIAMOND_TOOL),
    RAW_EBONY_BLOCK("raw_ebony_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), TAGS_ORE_DIAMOND_TOOL),
    // 猩红矿
    TR_CRIMSON_ORE("tr_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    DEEPSLATE_TR_CRIMSON_ORE("deepslate_tr_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    SANCTIFICATION_TR_CRIMSON_ORE("sanctification_tr_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    CORRUPTION_TR_CRIMSON_ORE("corruption_tr_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),
    FLESHIFICATION_TR_CRIMSON_ORE("fleshification_tr_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_DIAMOND_TOOL),

    RAW_TR_CRIMSON_BLOCK("raw_tr_crimson_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), TAGS_ORE_DIAMOND_TOOL),
    TR_CRIMSON_BLOCK("tr_crimson_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)), TAGS_ORE_DIAMOND_TOOL),


    // 狱石矿
    HELLSTONE("hellstone", HellStoneBlock::new, TAGS_ORE_TIER_4_TOOL),
    ASH_HELLSTONE("ash_hellstone", HellStoneBlock::new, TAGS_ORE_TIER_4_TOOL),
    RAW_HELLSTONE_BLOCK("raw_hellstone_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_4_TOOL),
    HELLSTONE_BLOCK("hellstone_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_4_TOOL),


    // 钴
    DEEPSLATE_COBALT_ORE("deepslate_cobalt_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_5_TOOL),
    RAW_COBALT_BLOCK("raw_cobalt_block", BaseBlock::new, TAGS_ORE_TIER_5_TOOL),
    COBALT_BLOCK("cobalt_block", BaseBlock::new, TAGS_ORE_TIER_5_TOOL),
    // 钯金
    DEEPSLATE_PALLADIUM_ORE("deepslate_palladium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_5_TOOL),
    RAW_PALLADIUM_BLOCK("raw_palladium_block", BaseBlock::new, TAGS_ORE_TIER_5_TOOL),
    PALLADIUM_BLOCK("palladium_block", BaseBlock::new, TAGS_ORE_TIER_5_TOOL),
    // 秘银
    DEEPSLATE_MITHRIL_ORE("deepslate_mithril_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_6_TOOL),
    RAW_MITHRIL_BLOCK("raw_mithril_block", BaseBlock::new, TAGS_ORE_TIER_6_TOOL),
    MITHRIL_BLOCK("mithril_block", BaseBlock::new, TAGS_ORE_TIER_6_TOOL),
    // 山铜
    DEEPSLATE_ORICHALCUM_ORE("deepslate_orichalcum_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_6_TOOL),
    RAW_ORICHALCUM_BLOCK("raw_orichalcum_block", BaseBlock::new, TAGS_ORE_TIER_6_TOOL),
    ORICHALCUM_BLOCK("orichalcum_block", BaseBlock::new, TAGS_ORE_TIER_6_TOOL),
    // 精金
    DEEPSLATE_ADAMANTITE_ORE("deepslate_adamantite_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_7_TOOL),
    RAW_ADAMANTITE_BLOCK("raw_adamantite_block", BaseBlock::new, TAGS_ORE_TIER_7_TOOL),
    ADAMANTITE_BLOCK("adamantite_block", BaseBlock::new, TAGS_ORE_TIER_7_TOOL),
    // 钛金
    DEEPSLATE_TITANIUM_ORE("deepslate_titanium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()), TAGS_ORE_TIER_7_TOOL),
    RAW_TITANIUM_BLOCK("raw_titanium_block", BaseBlock::new, TAGS_ORE_TIER_7_TOOL),
    TITANIUM_BLOCK("titanium_block", BaseBlock::new, TAGS_ORE_TIER_7_TOOL),
    // 神圣矿
    HALLOWED_BLOCK("hallowed_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), TAGS_ORE_TIER_8_TOOL),

    // 叶绿矿
    CHLOROPHYTE_ORE("chlorophyte_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)), TAGS_ORE_TIER_9_TOOL),
    RAW_CHLOROPHYTE_BLOCK("raw_chlorophyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), TAGS_ORE_TIER_9_TOOL),
    CHLOROPHYTE_BLOCK("chlorophyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)), TAGS_ORE_TIER_9_TOOL),

    // 蘑菇矿
    SHROOMITE_BLOCK("shroomite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), TAGS_ORE_TIER_9_TOOL),
    // 幽灵矿
    SPECTRE_BLOCK("spectre_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), TAGS_ORE_TIER_9_TOOL),
    // 夜明矿
    RAW_LUMINITE_BLOCK("raw_luminite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)), TAGS_ORE_TIER_9_TOOL),
    LUMINITE_BLOCK("luminite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK)), TAGS_ORE_TIER_9_TOOL);

    private final RegistryObject<Block> value;
    private final BlockTagsHolder tagsHolder;

    Ores(String id, Supplier<Block> ore) {
        this(id, ore, TAGS_ORE_BASIC);
    }

    Ores(String id, Supplier<Block> ore, BlockTagsHolder tagsHolder) {
        this.value = ModBlocks.registerWithItem(id, ore);
        this.tagsHolder = tagsHolder;
    }

    @Override
    public BlockTagsHolder getBlockTags() {
        return tagsHolder;
    }

    @Override
    public RegistryObject<Block> getValue() {
        return value;
    }

    public static void acceptTag(IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag) {
        for (Ores ores : values()) {
            tag.add(ores.get());
        }
    }

    public static void init() {
    }
}
