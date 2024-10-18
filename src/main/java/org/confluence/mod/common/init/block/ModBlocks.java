package org.confluence.mod.common.init.block;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.item.BoxBlockItem;

public class ModBlocks {
    public static NonNullSupplier<Registrate> BOX = NonNullSupplier.lazy(() -> Registrate.create(Confluence.MODID)); // todo 销毁


    //TODO 暂未添加宝匣Tag标记
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


    public static void register(IEventBus eventBus) {
        ModOreBlocks.BLOCKS.register(eventBus);
        ModDecorativeBlocks.BLOCKS.register(eventBus);
    }

    public static BlockEntry<Block> registerBoxBlock(String name) {
        BlockBuilder<Block, Registrate> blockBuilder = BOX.get().block(name, Block::new).initialProperties(() -> Blocks.OAK_PLANKS);
        blockBuilder.item((block, properties) -> new BoxBlockItem(block, Confluence.asResource(name))).register();
        return blockBuilder.register();
    }
}
