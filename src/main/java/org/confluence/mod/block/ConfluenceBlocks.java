package org.confluence.mod.block;

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
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.util.DecorationLogBlocks;
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
    //  holy
    public static final DecorationLogBlocks PEARL_LOG_BLOCKS = new DecorationLogBlocks("pearl", PEARL.SET, PEARL.TYPE);
    public static final RegistryObject<Block> PEARL_STONE = registerWithItem("pearl_stone", BaseBlock::new);
    public static final RegistryObject<Block> PEARL_SAND = registerWithItem("pearl_sand", () -> new SandBlock(0xedd5f6, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_COBBLE_STONE = registerWithItem("pearl_cobble_stone", BaseBlock::new);
    //  crimson
    public static final DecorationLogBlocks SHADOW_LOG_BLOCKS = new DecorationLogBlocks("shadow", SHADOW.SET, SHADOW.TYPE);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE = registerWithItem("another_crimson_stone", BaseBlock::new);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_SAND = registerWithItem("another_crimson_sand", () -> new SandBlock(0x5313e, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_COBBLE_STONE = registerWithItem("another_crimson_cobble_stone", BaseBlock::new);
    //  desert
    public static final DecorationLogBlocks PALM_LOG_BLOCKS = new DecorationLogBlocks("palm", PALM.SET, PALM.TYPE);
    //  jewelry
    public static final RegistryObject<Block> BIG_RUBY_BLOCK = registerWithItem("big_ruby_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_AMBER_BLOCK = registerWithItem("big_amber_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_TOPAZ_BLOCK = registerWithItem("big_topaz_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_ANOTHER_EMERALD_BLOCK = registerWithItem("big_another_emerald_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_SAPPHIRE_BLOCK = registerWithItem("big_sapphire_block", BaseBlock::new);
    public static final RegistryObject<Block> BIG_ANOTHER_AMETHYST_BLOCK = registerWithItem("big_another_amethyst_block", BaseBlock::new);
    //endregion registries

    public enum Ores implements EnumRegister<Block> {
        TIN_ORE("tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE))),
        DEEPSLATE_TIN_ORE("deepslate_tin_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE))),
        RAW_TIN_BLOCK("raw_tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK))),
        TIN_BLOCK("tin_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),

        WOLFRAM_ORE("wolfram_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
        DEEPSLATE_WOLFRAM_ORE("deepslate_wolfram_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
        RAW_WOLFRAM_BLOCK("raw_wolfram_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
        WOLFRAM_BLOCK("wolfram_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

        SILVER_ORE("silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
        DEEPSLATE_SILVER_ORE("deepslate_silver_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
        RAW_SILVER_BLOCK("raw_silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
        SILVER_BLOCK("silver_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

        LEAD_ORE("lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE))),
        DEEPSLATE_LEAD_ORE("deepslate_lead_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE))),
        RAW_LEAD_BLOCK("raw_lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK))),
        LEAD_BLOCK("lead_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),

        PLATINUM_ORE("platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE))),
        DEEPSLATE_PLATINUM_ORE("deepslate_platinum_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE))),
        RAW_PLATINUM_BLOCK("raw_platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK))),
        PLATINUM_BLOCK("platinum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK))),

        METEORITE("meteorite", () -> new BaseBlock());


        private final RegistryObject<Block> value;

        Ores(String id, Supplier<Block> ore) {
            this.value = registerWithItem(id, ore);
        }

        @Override
        public RegistryObject<Block> getValue() {
            return value;
        }

        @Override
        public Block get() {
            return value.get();
        }

        public static void init() {
            Confluence.LOGGER.info("Registering ores");
        }
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block) {
        return registerWithItem(id, block, new Item.Properties());
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block, Item.Properties properties) {
        RegistryObject<B> object = BLOCKS.register(id, block);
        ConfluenceItems.ITEMS.register(id, () -> new BaseBlock.Item(object.get(), properties));
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
