package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.cloaked.StepRevealingBlock;
import org.confluence.mod.block.common.BaseBlock;
import org.confluence.mod.block.common.EchoBlock;
import org.confluence.mod.block.common.MeteoriteOre;
import org.confluence.mod.block.common.PlateBlock;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

import static org.confluence.mod.block.WoodSetType.*;

@SuppressWarnings("unused")
public class ConfluenceBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);
    //region registries
    //  ebony
    public static final DecorationLogBlocks EBONY_LOG_BLOCKS = new DecorationLogBlocks("ebony", EBONY.SET, EBONY.TYPE);
    public static final RegistryObject<Block> EBONY_STONE = registerWithItem("ebony_stone", BaseBlock::new);
    public static final RegistryObject<Block> EBONY_SAND = registerWithItem("ebony_sand", () -> new SandBlock(0x372b4b, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_COBBLE_STONE = registerWithItem("ebony_cobble_stone", BaseBlock::new);
    public static final RegistryObject<Block> CORRUPTION_GRASS_BLOCKS = registerWithItem("corruption_grass_blocks", BaseBlock::new);
    //  holy
    public static final DecorationLogBlocks PEARL_LOG_BLOCKS = new DecorationLogBlocks("pearl", PEARL.SET, PEARL.TYPE);
    public static final RegistryObject<Block> PEARL_STONE = registerWithItem("pearl_stone", BaseBlock::new);
    public static final RegistryObject<Block> PEARL_SAND = registerWithItem("pearl_sand", () -> new SandBlock(0xedd5f6, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_COBBLE_STONE = registerWithItem("pearl_cobble_stone", BaseBlock::new);
    public static final RegistryObject<Block> HALLOW_GRASS_BLOCKS = registerWithItem("hallow_grass_blocks", BaseBlock::new);
    //  crimson
    public static final DecorationLogBlocks SHADOW_LOG_BLOCKS = new DecorationLogBlocks("shadow", SHADOW.SET, SHADOW.TYPE);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE = registerWithItem("another_crimson_stone", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_SAND = registerWithItem("another_crimson_sand", () -> new SandBlock(0x5313e, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_COBBLE_STONE = registerWithItem("another_crimson_cobble_stone", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_GRASS_BLOCKS = registerWithItem("another_crimson_grass_blocks", BaseBlock::new);
    //  desert
    public static final DecorationLogBlocks PALM_LOG_BLOCKS = new DecorationLogBlocks("palm", PALM.SET, PALM.TYPE);
    //  ash
    public static final DecorationLogBlocks ASH_LOG_BLOCKS = new DecorationLogBlocks("ash", ASH.SET, ASH.TYPE, true, false);
    public static final RegistryObject<Block> ASH_BLOCK = registerWithItem("ash_block", BaseBlock::new);
    //  jewelry
    public static final RegistryObject<Block> BIG_RUBY_BLOCK = registerWithItem("big_ruby_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_AMBER_BLOCK = registerWithItem("big_amber_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_TOPAZ_BLOCK = registerWithItem("big_topaz_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_ANOTHER_EMERALD_BLOCK = registerWithItem("big_another_emerald_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_SAPPHIRE_BLOCK = registerWithItem("big_sapphire_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_ANOTHER_AMETHYST_BLOCK = registerWithItem("big_another_amethyst_block", BaseBlock::new);
    //  other environmental stones
    public static final RegistryObject<Block> ANOTHER_POLISHED_GRANITE = registerWithItem("another_polished_granite", BaseBlock::new);
    public static final RegistryObject<Block> POLISHED_MARBLE = registerWithItem("polished_marble", BaseBlock::new);
    //  decorative blocks
    public static final RegistryObject<Block> ANOTHER_COPPER_BRICKS = registerWithItem("another_copper_bricks", PlateBlock::new);
    public static final RegistryObject<Block> ANOTHER_COPPER_PLATE = registerWithItem("another_copper_plate", PlateBlock::new);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_ORE_BRICKS = registerWithItem("another_crimson_ore_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_ROCK_BRICKS = registerWithItem("another_crimson_rock_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE_BRICKS = registerWithItem("another_crimson_stone_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_GOLD_BRICKS = registerWithItem("another_gold_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_IRON_BRICKS = registerWithItem("another_iron_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_STONE_BRICKS = registerWithItem("another_stone_bricks", BaseBlock::new);
    public static final RegistryObject<Block> EVIL_ORE_BRICKS = registerWithItem("evil_ore_bricks", BaseBlock::new);
    public static final RegistryObject<Block> EVIL_ROCK_BRICKS = registerWithItem("evil_rock_bricks", BaseBlock::new);
    public static final RegistryObject<Block> BLUE_GEL_BLOCK = registerWithItem("blue_gel_block", BaseBlock::new);
    public static final RegistryObject<Block> GREEN_CANDY_BLOCK = registerWithItem("green_candy_block", BaseBlock::new);
    public static final RegistryObject<Block> ICE_BRICKS = registerWithItem("ice_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ICED_GEL_BLOCK = registerWithItem("iced_gel_block", BaseBlock::new);
    public static final RegistryObject<Block> LEAD_BRICKS = registerWithItem("lead_bricks", BaseBlock::new);
    public static final RegistryObject<Block> METEORITE_BRICKS = registerWithItem("meteorite_bricks", BaseBlock::new);
    public static final RegistryObject<Block> PEARL_ROCK_BRICKS = registerWithItem("pearl_rock_bricks", BaseBlock::new);
    public static final RegistryObject<Block> PINK_GEL_BLOCK = registerWithItem("pink_gel_block", BaseBlock::new);
    public static final RegistryObject<Block> PLATINUM_BRICKS = registerWithItem("platinum_bricks", BaseBlock::new);
    public static final RegistryObject<Block> RED_CANDY_BLOCK = registerWithItem("red_candy_block", BaseBlock::new);
    public static final RegistryObject<Block> SILVER_BRICKS = registerWithItem("silver_bricks", BaseBlock::new);
    public static final RegistryObject<Block> SNOW_BRICKS = registerWithItem("snow_bricks", BaseBlock::new);
    public static final RegistryObject<Block> SUN_PLATE = registerWithItem("sun_plate", BaseBlock::new);
    public static final RegistryObject<Block> TIN_BRICKS = registerWithItem("tin_bricks", BaseBlock::new);
    public static final RegistryObject<Block> TIN_PLATE = registerWithItem("tin_plate", PlateBlock::new);
    public static final RegistryObject<Block> WOLFRAM_BRICKS = registerWithItem("wolfram_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_LAVA_BEAM = registerWithItem("another_lava_beam", PlateBlock::new);
    public static final RegistryObject<Block> ANOTHER_LAVA_BRICKS = registerWithItem("another_lava_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_OBSIDIAN_BEAM = registerWithItem("another_obsidian_beam", PlateBlock::new);
    public static final RegistryObject<Block> ANOTHER_OBSIDIAN_BRICKS = registerWithItem("another_obsidian_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_OBSIDIAN_PLATE = registerWithItem("another_obsidian_plate", PlateBlock::new);
    public static final RegistryObject<Block> ANOTHER_OBSIDIAN_SMALL_BRICKS = registerWithItem("another_obsidian_small_bricks", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_SMOOTH_OBSIDIAN_SLAB = registerWithItem("another_smooth_obsidian_slab", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_SMOOTH_OBSIDIAN = registerWithItem("another_smooth_obsidian", BaseBlock::new);
    public static final RegistryObject<Block> CHISELED_ANOTHER_OBSIDIAN_BRICKS = registerWithItem("chiseled_another_obsidian_bricks", BaseBlock::new);
    public static final DecorationLogBlocks SPOOKY_LOG_BLOCKS = new DecorationLogBlocks("spooky", SPOOKY.SET, SPOOKY.TYPE, false, true);

    public static final DecorationLogBlocks ANOTHER_OAK_LOG_BLOCKS = new DecorationLogBlocks("another_oak", ANOTHER_OAK.SET, ANOTHER_OAK.TYPE, false, true);
    public static final DecorationLogBlocks ANOTHER_NORTHLAND_LOG_BLOCKS = new DecorationLogBlocks("another_northland", ANOTHER_NORTHLAND.SET, ANOTHER_NORTHLAND.TYPE, false, true);
    public static final RegistryObject<Block> ANOTHER_OAK_BEAM = registerWithItem("another_northland_beam", PlateBlock::new);
    public static final RegistryObject<EchoBlock> ECHO_BLOCK = registerWithItem("echo_block", EchoBlock::new);


    //endregion registries

    public enum Ores implements EnumRegister<Block> {
        TIN_ORE("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
        DEEPSLATE_TIN_ORE("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE))),
        RAW_TIN_BLOCK("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK))),
        TIN_BLOCK("tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),

        LEAD_ORE("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
        DEEPSLATE_LEAD_ORE("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
        RAW_LEAD_BLOCK("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
        LEAD_BLOCK("lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

        SILVER_ORE("silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
        DEEPSLATE_SILVER_ORE("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
        RAW_SILVER_BLOCK("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
        SILVER_BLOCK("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

        WOLFRAM_ORE("wolfram_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
        DEEPSLATE_WOLFRAM_ORE("deepslate_wolfram_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
        RAW_WOLFRAM_BLOCK("raw_wolfram_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
        WOLFRAM_BLOCK("wolfram_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

        PLATINUM_ORE("platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
        DEEPSLATE_PLATINUM_ORE("deepslate_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE))),
        RAW_PLATINUM_BLOCK("raw_platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK))),
        PLATINUM_BLOCK("platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK))),
        //  陨铁
        METEORITE_ORE("meteorite_ore", MeteoriteOre::new),

        //  钴
        DEEPSLATE_COBALT_ORE("deepslate_cobalt_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
        RAW_COBALT_BLOCK("raw_cobalt_block", () -> new BaseBlock()),
        COBALT_BLOCK("cobalt_block", () -> new BaseBlock()),
        //  钯金
        DEEPSLATE_PALLADIUM_ORE("deepslate_palladium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
        RAW_PALLADIUM_BLOCK("raw_palladium_block", () -> new BaseBlock()),
        PALLADIUM_BLOCK("palladium_block", () -> new BaseBlock()),
        //  秘银
        DEEPSLATE_MITHRIL_ORE("deepslate_mithril_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
        RAW_MITHRIL_BLOCK("raw_mithril_block", () -> new BaseBlock()),
        MITHRIL_BLOCK("mithril_block", () -> new BaseBlock()),
        //  山铜
        DEEPSLATE_ORICHALCUM_ORE("deepslate_orichalcum_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
        RAW_ORICHALCUM_BLOCK("raw_orichalcum_block", () -> new BaseBlock()),
        ORICHALCUM_BLOCK("orichalcum_block", () -> new BaseBlock()),
        //  精金
        DEEPSLATE_ADAMANTITE_ORE("deepslate_adamantite_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
        RAW_ADAMANTITE_BLOCK("raw_adamantite_block", () -> new BaseBlock()),
        ADAMANTITE_BLOCK("adamantite_block", () -> new BaseBlock()),
        //  钛金
        DEEPSLATE_TITANIUM_ORE("deepslate_titanium_ore", () -> new StepRevealingBlock(BlockBehaviour.Properties.of())),
        RAW_TITANIUM_BLOCK("raw_titanium_block", () -> new BaseBlock()),
        TITANIUM_BLOCK("titanium_block", () -> new BaseBlock());

        private final RegistryObject<Block> value;

        Ores(String id, Supplier<Block> ore) {
            this.value = registerWithItem(id, ore);
        }

        @Override
        public RegistryObject<Block> getValue() {
            return value;
        }

        static void init() {
            Confluence.LOGGER.info("Registering ores");
        }
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block) {
        return registerWithItem(id, block, new Item.Properties());
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block, Item.Properties properties) {
        RegistryObject<B> object = BLOCKS.register(id, block);
        ConfluenceItems.ITEMS.register(id, () -> new BlockItem(object.get(), properties));
        return object;
    }

    public static <B extends Block> RegistryObject<B> registerWithoutItem(String id, Supplier<B> block) {
        return BLOCKS.register(id, block);
    }

    public static void register(IEventBus bus) {
        Ores.init();
        BLOCKS.register(bus);
    }
}
