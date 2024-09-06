package org.confluence.mod.common.init.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModItems;
import org.confluence.mod.common.item.BoxBlockItem;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Confluence.MODID);
    public static final DeferredRegister.Blocks BOX = DeferredRegister.createBlocks(Confluence.MODID);


    //TODO 暂未添加宝匣Tag标记
    public static final DeferredHolder<Block, Block> WOODEN_BOX = registerBoxBlock("wooden_box");
    public static final DeferredHolder<Block, Block> IRON_BOX = registerBoxBlock("iron_box");
    public static final DeferredHolder<Block, Block> GOLDEN_BOX = registerBoxBlock("golden_box");
    public static final DeferredHolder<Block, Block> JUNGLE_BOX = registerBoxBlock("jungle_box");
    public static final DeferredHolder<Block, Block> SKY_BOX = registerBoxBlock("sky_box");
    public static final DeferredHolder<Block, Block> CORRUPT_BOX = registerBoxBlock("corrupt_box");
    public static final DeferredHolder<Block, Block> TR_CRIMSON_BOX = registerBoxBlock("tr_crimson_box");
    public static final DeferredHolder<Block, Block> SACRED_BOX = registerBoxBlock("sacred_box");
    public static final DeferredHolder<Block, Block> DUNGEON_BOX = registerBoxBlock("dungeon_box");
    public static final DeferredHolder<Block, Block> FREEZE_BOX = registerBoxBlock("freeze_box");
    public static final DeferredHolder<Block, Block> OASIS_BOX = registerBoxBlock("oasis_box");
    public static final DeferredHolder<Block, Block> OBSIDIAN_BOX = registerBoxBlock("obsidian_box");
    public static final DeferredHolder<Block, Block> OCEAN_BOX = registerBoxBlock("ocean_box");

    public static final DeferredHolder<Block, Block> PEARLWOOD_BOX = registerBoxBlock("pearlwood_box");
    public static final DeferredHolder<Block, Block> MITHRIL_BOX = registerBoxBlock("mithril_box");
    public static final DeferredHolder<Block, Block> TITANIUM_BOX = registerBoxBlock("titanium_box");
    public static final DeferredHolder<Block, Block> THORNS_BOX = registerBoxBlock("thorns_box");
    public static final DeferredHolder<Block, Block> SPACE_BOX = registerBoxBlock("space_box");
    public static final DeferredHolder<Block, Block> DEFACED_BOX = registerBoxBlock("defaced_box");
    public static final DeferredHolder<Block, Block> BLOOD_BOX = registerBoxBlock("blood_box");
    public static final DeferredHolder<Block, Block> PROVIDENTIAL_BOX = registerBoxBlock("providential_box");
    public static final DeferredHolder<Block, Block> FENCING_BOX = registerBoxBlock("fencing_box");
    public static final DeferredHolder<Block, Block> CONIFEROUS_WOOD_BOX = registerBoxBlock("coniferous_wood_box");
    public static final DeferredHolder<Block, Block> ILLUSION_BOX = registerBoxBlock("illusion_box");
    public static final DeferredHolder<Block, Block> HELL_STONE_BOX = registerBoxBlock("hell_stone_box");
    public static final DeferredHolder<Block, Block> BEACH_BOX = registerBoxBlock("beach_box");
    
    
    public static void register(IEventBus eventBus) {
        BOX.register(eventBus);
        ModOreBlocks.BLOCKS.register(eventBus);
        ModDecorativeBlocks.BLOCKS.register(eventBus);
    }

    public static DeferredHolder<Block, Block> registerBoxBlock(String name) {
        DeferredBlock<Block> block = BOX.registerSimpleBlock(name, BlockBehaviour.Properties.of());
        ModItems.BLOCK_ITEMS.register(name, (key) -> new BoxBlockItem(block.get(),key));
        return block;
    }




}
