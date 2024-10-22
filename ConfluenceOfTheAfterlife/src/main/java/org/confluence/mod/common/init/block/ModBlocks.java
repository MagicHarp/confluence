package org.confluence.mod.common.init.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.util.CreativeModeTabModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.block.CoinPileBlock;
import org.confluence.mod.common.init.ModItems;
import org.confluence.mod.common.init.ModTabs;
import org.confluence.mod.common.item.common.BoxBlockItem;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Confluence.MODID);
    public static final NonNullBiConsumer<DataGenContext<Item, BoxBlockItem>, CreativeModeTabModifier> PARENT_ONLY = (context, modifier) -> modifier.accept(context, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);

    public static final BlockEntry<Block> WOODEN_BOX = registerBoxBlock("wooden_box");
    public static final BlockEntry<Block> IRON_BOX = registerBoxBlock("iron_box");
    public static final BlockEntry<Block> GOLDEN_BOX = registerBoxBlock("golden_box");
    public static final BlockEntry<Block> JUNGLE_BOX = registerBoxBlock("jungle_box");
    public static final BlockEntry<Block> SKY_BOX = registerBoxBlock("sky_box");
    public static final BlockEntry<Block> CORRUPT_BOX = registerBoxBlock("corrupt_box");
    public static final BlockEntry<Block> TR_CRIMSON_BOX = registerBoxBlock("tr_crimson_box");
    public static final BlockEntry<Block> SACRED_BOX = registerBoxBlock("sacred_box");
    public static final BlockEntry<Block> DUNGEON_BOX = registerBoxBlock("dungeon_box");
    public static final BlockEntry<Block> FREEZE_BOX = registerBoxBlock("freeze_box");
    public static final BlockEntry<Block> OASIS_BOX = registerBoxBlock("oasis_box");
    public static final BlockEntry<Block> OBSIDIAN_BOX = registerBoxBlock("obsidian_box");
    public static final BlockEntry<Block> OCEAN_BOX = registerBoxBlock("ocean_box");

    public static final BlockEntry<Block> PEARLWOOD_BOX = registerBoxBlock("pearlwood_box");
    public static final BlockEntry<Block> MITHRIL_BOX = registerBoxBlock("mithril_box");
    public static final BlockEntry<Block> TITANIUM_BOX = registerBoxBlock("titanium_box");
    public static final BlockEntry<Block> THORNS_BOX = registerBoxBlock("thorns_box");
    public static final BlockEntry<Block> SPACE_BOX = registerBoxBlock("space_box");
    public static final BlockEntry<Block> DEFACED_BOX = registerBoxBlock("defaced_box");
    public static final BlockEntry<Block> BLOOD_BOX = registerBoxBlock("blood_box");
    public static final BlockEntry<Block> PROVIDENTIAL_BOX = registerBoxBlock("providential_box");
    public static final BlockEntry<Block> FENCING_BOX = registerBoxBlock("fencing_box");
    public static final BlockEntry<Block> CONIFEROUS_WOOD_BOX = registerBoxBlock("coniferous_wood_box");
    public static final BlockEntry<Block> ILLUSION_BOX = registerBoxBlock("illusion_box");
    public static final BlockEntry<Block> HELL_STONE_BOX = registerBoxBlock("hell_stone_box");
    public static final BlockEntry<Block> BEACH_BOX = registerBoxBlock("beach_box");

    public static final DeferredBlock<CoinPileBlock> COPPER_COIN_PILE = registerWithoutItem("copper_coin_pile", CoinPileBlock::new);
    public static final DeferredBlock<CoinPileBlock> SILVER_COIN_PILE = registerWithoutItem("silver_coin_pile", CoinPileBlock::new);
    public static final DeferredBlock<CoinPileBlock> GOLDEN_COIN_PILE = registerWithoutItem("golden_coin_pile", CoinPileBlock::new);
    public static final DeferredBlock<CoinPileBlock> PLATINUM_COIN_PILE = registerWithoutItem("platinum_coin_pile", CoinPileBlock::new);


    public static <B extends Block> DeferredBlock<B> registerWithItem(String id, Supplier<B> block) {
        return registerWithItem(id, block, new Item.Properties());
    }

    public static <B extends Block> DeferredBlock<B> registerWithItem(String id, Supplier<B> block, Function<Supplier<B>, Supplier<BlockItem>> item) {
        DeferredBlock<B> object = BLOCKS.register(id, block);
        ModItems.ITEMS.register(id, item.apply(object));
        return object;
    }

    public static <B extends Block> DeferredBlock<B> registerWithItem(String id, Supplier<B> block, Item.Properties properties) {
        DeferredBlock<B> object = BLOCKS.register(id, block);
        ModItems.ITEMS.register(id, () -> new BlockItem(object.get(), properties));
        return object;
    }

    public static <B extends Block> DeferredBlock<B> registerWithoutItem(String id, Supplier<B> block) {
        return BLOCKS.register(id, block);
    }

    public static BlockEntry<Block> registerBoxBlock(String name) { // TODO 暂未添加宝匣Tag标记
        BlockBuilder<Block, Registrate> blockBuilder = Confluence.REGISTRATE.get().block(name, Block::new).initialProperties(() -> Blocks.OAK_PLANKS);
        blockBuilder.item((block, properties) -> new BoxBlockItem(block, Confluence.asResource(name))).tab(ModTabs.CREATIVES.getKey(), PARENT_ONLY).register();
        return blockBuilder.register();
    }

    public static float getObsidianBasedExplosionResistance(float delta) {
        return Blocks.OBSIDIAN.getExplosionResistance() + delta;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ModOreBlocks.BLOCKS.register(eventBus);
        ModDecorativeBlocks.BLOCKS.register(eventBus);
    }
}
