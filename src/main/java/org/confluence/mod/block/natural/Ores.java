package org.confluence.mod.block.natural;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.common.BaseBlock;
import org.confluence.mod.block.common.MeteoriteOre;
import org.confluence.mod.block.reveal.StepRevealingBlock;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum Ores implements EnumRegister<Block> {
    TIN_ORE("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    PEARL_STONE_TIN_ORE("pearl_stone_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    EBONY_STONE_TIN_ORE("ebony_stone_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    ANOTHER_CRIMSON_STONE_TIN_ORE("another_crimson_stone_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
    DEEPSLATE_TIN_ORE("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE))),
    RAW_TIN_BLOCK("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK))),
    TIN_BLOCK("tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),

    LEAD_ORE("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_LEAD_ORE("pearl_stone_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_LEAD_ORE("ebony_stone_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_LEAD_ORE("another_crimson_stone_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_LEAD_ORE("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    RAW_LEAD_BLOCK("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    LEAD_BLOCK("lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    SILVER_ORE("silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_SILVER_ORE("pearl_stone_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_SILVER_ORE("ebony_stone_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_SILVER_ORE("another_crimson_stone_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_SILVER_ORE("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    RAW_SILVER_BLOCK("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    SILVER_BLOCK("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    TUNGSTEN_ORE("tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_TUNGSTEN_ORE("pearl_stone_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_TUNGSTEN_ORE("ebony_stone_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_TUNGSTEN_ORE("another_crimson_stone_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_TUNGSTEN_ORE("deepslate_tungsten_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    RAW_TUNGSTEN_BLOCK("raw_tungsten_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    TUNGSTEN_BLOCK("tungsten_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

    PLATINUM_ORE("platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    PEARL_STONE_PLATINUM_ORE("pearl_stone_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    EBONY_STONE_PLATINUM_ORE("ebony_stone_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    ANOTHER_CRIMSON_STONE_PLATINUM_ORE("another_crimson_stone_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
    DEEPSLATE_PLATINUM_ORE("deepslate_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE))),
    RAW_PLATINUM_BLOCK("raw_platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK))),
    PLATINUM_BLOCK("platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK))),
    // 陨铁
    METEORITE_ORE("meteorite_ore", MeteoriteOre::new),
    //红玉矿
    RUBY_ORE("ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_RUBY_ORE("pearl_stone_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_RUBY_ORE("ebony_stone_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_RUBY_ORE("another_crimson_stone_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_RUBY_ORE("deepslate_ruby_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    //黄玉矿
    TOPAZ_ORE("topaz_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_TOPAZ_ORE("pearl_stone_topaz_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_TOPAZ_ORE("ebony_stone_topaz_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_TOPAZ_ORE("another_crimson_stone_topaz_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_TOPAZ_ORE("deepslate_topaz_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    //琥珀矿
    AMBER_ORE("amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_AMBER_ORE("pearl_stone_amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_AMBER_ORE("ebony_stone_amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_AMBER_ORE("another_crimson_stone_amber_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_AMBER_ORE("deepslate_amber_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    //异域紫晶矿
    ANOTHER_AMETHYST_ORE("another_amethyst_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_ANOTHER_AMETHYST_ORE("pearl_stone_another_amethyst_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_ANOTHER_AMETHYST_ORE("ebony_stone_another_amethyst_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_ANOTHER_AMETHYST_ORE("another_crimson_stone_another_amethyst_ore",  () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_ANOTHER_AMETHYST_ORE("deepslate_another_amethyst_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    //蓝玉矿
    SAPPHIRE_ORE("sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_SAPPHIRE_ORE("pearl_stone_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_SAPPHIRE_ORE("ebony_stone_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_SAPPHIRE_ORE("another_crimson_stone_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_SAPPHIRE_ORE("deepslate_sapphire_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    // 魔矿
    EBONY_ORE("ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_EBONY_ORE("pearl_stone_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_EBONY_ORE("ebony_stone_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_EBONY_ORE("another_crimson_stone_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_EBONY_ORE("deepslate_ebony_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    EBONY_BLOCK("ebony_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    RAW_EBONY_BLOCK("raw_ebony_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    // 猩红矿
    ANOTHER_CRIMSON_ORE("another_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    PEARL_STONE_ANOTHER_CRIMSON_ORE("pearl_stone_another_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    EBONY_STONE_ANOTHER_CRIMSON_ORE("ebony_stone_another_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    ANOTHER_CRIMSON_STONE_ANOTHER_CRIMSON_ORE("another_crimson_stone_another_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    DEEPSLATE_ANOTHER_CRIMSON_ORE("deepslate_another_crimson_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
    ANOTHER_CRIMSON_BLOCK("another_crimson_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    RAW_ANOTHER_CRIMSON_BLOCK("raw_another_crimson_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    // 叶绿矿
    CHLOROPHYTE_ORE("chlorophyte_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
    CHLOROPHYTE_BLOCK("chlorophyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    RAW_CHLOROPHYTE_BLOCK("raw_chlorophyte_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    // 夜明矿
    LUMINITE_BLOCK("luminite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
    RAW_LUMINITE_BLOCK("raw_luminite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    // 狱石矿
    HELLSTONE("hellstone", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    ASH_HELLSTONE("ash_hellstone", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    HELLSTONE_BLOCK("hellstone_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    RAW_HELLSTONE_BLOCK("raw_hellstone_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),

    // 钴
    DEEPSLATE_COBALT_ORE("deepslate_cobalt_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    RAW_COBALT_BLOCK("raw_cobalt_block", BaseBlock::new),
    COBALT_BLOCK("cobalt_block", BaseBlock::new),
    // 钯金
    DEEPSLATE_PALLADIUM_ORE("deepslate_palladium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    RAW_PALLADIUM_BLOCK("raw_palladium_block", BaseBlock::new),
    PALLADIUM_BLOCK("palladium_block", BaseBlock::new),
    // 秘银
    DEEPSLATE_MITHRIL_ORE("deepslate_mithril_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    RAW_MITHRIL_BLOCK("raw_mithril_block", BaseBlock::new),
    MITHRIL_BLOCK("mithril_block", BaseBlock::new),
    // 山铜
    DEEPSLATE_ORICHALCUM_ORE("deepslate_orichalcum_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    RAW_ORICHALCUM_BLOCK("raw_orichalcum_block", BaseBlock::new),
    ORICHALCUM_BLOCK("orichalcum_block", BaseBlock::new),
    // 精金
    DEEPSLATE_ADAMANTITE_ORE("deepslate_adamantite_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    RAW_ADAMANTITE_BLOCK("raw_adamantite_block", BaseBlock::new),
    ADAMANTITE_BLOCK("adamantite_block", BaseBlock::new),
    // 钛金
    DEEPSLATE_TITANIUM_ORE("deepslate_titanium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops())),
    RAW_TITANIUM_BLOCK("raw_titanium_block", BaseBlock::new),
    TITANIUM_BLOCK("titanium_block", BaseBlock::new);

    private final RegistryObject<Block> value;

    Ores(String id, Supplier<Block> ore) {
        this.value = ModBlocks.registerWithItem(id, ore);
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

    public static void init() {}
}
