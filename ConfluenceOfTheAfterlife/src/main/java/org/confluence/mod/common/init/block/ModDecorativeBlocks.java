package org.confluence.mod.common.init.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.item.ModItems;

public class ModDecorativeBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Confluence.MODID);


    //TODO 暂未注册BeamLikeBlock
    public static final DeferredHolder<Block,Block> TR_OAK_BEAM = copyBlockRegister("tr_oak_beam", Blocks.OAK_PLANKS); 
//    public static final DeferredHolder<Block,Block> TR_OAK_PLANKS = copyBlockRegister("tr_oak_planks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS);
    public static final DeferredHolder<Block,Block> TR_NORTHLAND_BEAM = copyBlockRegister("tr_northland_beam", Blocks.OAK_PLANKS); 
//    public static final DeferredHolder<Block,Block> TR_NORTHLAND_PLANKS = copyBlockRegister("tr_northland_planks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS);
//    public static final DeferredHolder<Block,Block> WOOD_STONE_SLATTED_BLOCKS = copyBlockRegister("wood_stone_slatted_blocks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS);
    public static final DeferredHolder<Block,Block> ICE_BRICKS = copyBlockRegister("ice_bricks", Blocks.BLUE_ICE); 
    public static final DeferredHolder<Block,Block> SNOW_BRICKS = copyBlockRegister("snow_bricks", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_STONE_BRICKS = copyBlockRegister("tr_stone_bricks", Blocks.STONE_BRICKS); 

    public static final DeferredHolder<Block,Block> PURE_GLASS = copyBlockRegister("pure_glass", Blocks.GLASS); 

    public static final DeferredHolder<Block,Block> TR_COPPER_BRICKS = copyBlockRegister("tr_copper_bricks", Blocks.COPPER_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_COPPER_BRICKS = copyBlockRegister("ancient_copper_bricks", Blocks.COPPER_BLOCK); 
//    public static final DeferredHolder<Block,Block> TR_COPPER_PLATE = copyBlockRegister("tr_copper_plate", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK);
    public static final DeferredHolder<Block,Block> TIN_BRICKS = copyBlockRegister("tin_bricks", Blocks.COPPER_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_TIN_BRICKS = copyBlockRegister("ancient_tin_bricks", Blocks.COPPER_BLOCK); 
//    public static final DeferredHolder<Block,Block> TIN_PLATE = copyBlockRegister("tin_plate", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK);
    public static final DeferredHolder<Block,Block> TR_IRON_BRICKS = copyBlockRegister("tr_iron_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_IRON_BRICKS = copyBlockRegister("ancient_iron_bricks", Blocks.IRON_BLOCK); 
//    public static final DeferredHolder<Block,Block> TR_IRON_PLATE = copyBlockRegister("tr_iron_plate", () -> new  BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK);

    public static final DeferredHolder<Block,Block> LEAD_BRICKS = copyBlockRegister("lead_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_LEAD_BRICKS = copyBlockRegister("ancient_lead_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> SILVER_BRICKS = copyBlockRegister("silver_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_SILVER_BRICKS = copyBlockRegister("ancient_silver_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> TUNGSTEN_BRICKS = copyBlockRegister("tungsten_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_TUNGSTEN_BRICKS = copyBlockRegister("ancient_tungsten_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> TR_GOLD_BRICKS = copyBlockRegister("tr_gold_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_GOLD_BRICKS = copyBlockRegister("ancient_gold_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> PLATINUM_BRICKS = copyBlockRegister("platinum_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> ANCIENT_PLATINUM_BRICKS = copyBlockRegister("ancient_platinum_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> EBONY_ORE_BRICKS = copyBlockRegister("ebony_ore_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> EBONY_ROCK_BRICKS = copyBlockRegister("ebony_rock_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> METEORITE_BRICKS = copyBlockRegister("meteorite_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> TR_CRIMSON_ORE_BRICKS = copyBlockRegister("tr_crimson_ore_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> TR_CRIMSON_ROCK_BRICKS = copyBlockRegister("tr_crimson_rock_bricks", Blocks.IRON_BLOCK); 
    public static final DeferredHolder<Block,Block> PEARL_ROCK_BRICKS = copyBlockRegister("pearl_rock_bricks", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> GREEN_CANDY_BLOCK = copyBlockRegister("green_candy_block", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> RED_CANDY_BLOCK = copyBlockRegister("red_candy_block", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> FROZEN_GEL_BLOCK = copyBlockRegister("frozen_gel_block", Blocks.SLIME_BLOCK); 
    public static final DeferredHolder<Block,Block> BLUE_GEL_BLOCK = copyBlockRegister("blue_gel_block", Blocks.SLIME_BLOCK); 
    public static final DeferredHolder<Block,Block> PINK_GEL_BLOCK = copyBlockRegister("pink_gel_block", Blocks.SLIME_BLOCK); 
    public static final DeferredHolder<Block,Block> SUN_PLATE = copyBlockRegister("sun_plate", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_LAVA_BEAM = copyBlockRegister("tr_lava_beam", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_LAVA_BRICKS = copyBlockRegister("tr_lava_bricks", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_OBSIDIAN_BEAM = copyBlockRegister("tr_obsidian_beam", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_OBSIDIAN_BRICKS = copyBlockRegister("tr_obsidian_bricks", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_OBSIDIAN_PLATE = copyBlockRegister("tr_obsidian_plate", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_OBSIDIAN_SMALL_BRICKS = copyBlockRegister("tr_obsidian_small_bricks", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_SMOOTH_OBSIDIAN = copyBlockRegister("tr_smooth_obsidian", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> TR_GRANITE_COLUMN = copyBlockRegister("tr_granite_column", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> MARBLE_COLUMN = copyBlockRegister("marble_column", Blocks.STONE_BRICKS); 

    public static final DeferredHolder<Block,Block> CHISELED_TR_OBSIDIAN_BRICKS = copyBlockRegister("chiseled_tr_obsidian_bricks", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> BLUE_BRICK = copyBlockRegister("blue_brick", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> GREEN_BRICK = copyBlockRegister("green_brick", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> PINK_BRICK = copyBlockRegister("pink_brick", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> AETHERIUM_BRICK = copyBlockRegister("aetherium_brick", Blocks.STONE_BRICKS); 
    public static final DeferredHolder<Block,Block> CRYSTAL_BLOCK = copyBlockRegister("crystal_block", Blocks.AMETHYST_BLOCK); 
    public static final DeferredHolder<Block,Block> RAINBOW_BRICK = copyBlockRegister("rainbow_brick", Blocks.STONE_BRICKS); 


    private static DeferredHolder<Block,Block> copyBlockRegister(String newName, Block originalBlock) {
        DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(newName, BlockBehaviour.Properties.ofFullCopy(originalBlock));
        ModItems.BLOCK_ITEMS.registerSimpleBlockItem(newName, block);
        return block;
    }
}
