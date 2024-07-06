package org.confluence.mod.block.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum DecorativeBlocks implements EnumRegister<Block> {
    ANOTHER_OAK_BEAM("another_oak_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))),
    ANOTHER_OAK_PLANKS("another_oak_planks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))),
    ANOTHER_NORTHLAND_BEAM("another_northland_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))),
    ANOTHER_NORTHLAND_PLANKS("another_northland_planks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))),
    WOOD_STONE_SLATTED_BLOCKS("wood_stone_slatted_blocks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))),
    ICE_BRICKS("ice_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLUE_ICE))),
    SNOW_BRICKS("snow_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_STONE_BRICKS("another_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_COPPER_BRICKS("another_copper_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),
    ANOTHER_COPPER_PLATE("another_copper_plate", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),
    TIN_BRICKS("tin_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),
    TIN_PLATE("tin_plate", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK))),
    ANOTHER_IRON_BRICKS("another_iron_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    LEAD_BRICKS("lead_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    SILVER_BRICKS("silver_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    TUNGSTEN_BRICKS("tungsten_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    ANOTHER_GOLD_BRICKS("another_gold_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    PLATINUM_BRICKS("platinum_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    EBONY_ORE_BRICKS("ebony_ore_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    EBONY_ROCK_BRICKS("ebony_rock_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    METEORITE_BRICKS("meteorite_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    ANOTHER_CRIMSON_ORE_BRICKS("another_crimson_ore_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    ANOTHER_CRIMSON_ROCK_BRICKS("another_crimson_rock_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))),
    PEARL_ROCK_BRICKS("pearl_rock_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    GREEN_CANDY_BLOCK("green_candy_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    RED_CANDY_BLOCK("red_candy_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    FROZEN_GEL_BLOCK("frozen_gel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK))),
    BLUE_GEL_BLOCK("blue_gel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK))),
    PINK_GEL_BLOCK("pink_gel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK))),
    SUN_PLATE("sun_plate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_LAVA_BEAM("another_lava_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_LAVA_BRICKS("another_lava_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_OBSIDIAN_BEAM("another_obsidian_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_OBSIDIAN_BRICKS("another_obsidian_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_OBSIDIAN_PLATE("another_obsidian_plate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_OBSIDIAN_SMALL_BRICKS("another_obsidian_small_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_SMOOTH_OBSIDIAN("another_smooth_obsidian", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    ANOTHER_GRANITE_COLUMN("another_granite_column", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    MARBLE_COLUMN("marble_column", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),

    CHISELED_ANOTHER_OBSIDIAN_BRICKS("chiseled_another_obsidian_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),
    CRYSTAL_BLOCK("crystal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK))),
    RAINBOW_BRICK("rainbow_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS))),

;
    private final RegistryObject<Block> value;

    DecorativeBlocks(String id, Supplier<Block> block) {
        this.value = ModBlocks.registerWithItem(id, block);
    }

    @Override
    public RegistryObject<Block> getValue() {
        return value;
    }

    public static void init() {}
}
