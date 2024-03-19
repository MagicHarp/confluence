package org.confluence.mod.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.BaseBlock;
import org.confluence.mod.block.common.BeamLikeBlock;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum DecorativeBlocks implements EnumRegister<Block> {
    ANOTHER_OAK_BEAM("another_oak_beam", BaseBlock::new),
    ANOTHER_OAK_PLANKS("another_oak_planks", BeamLikeBlock::new),
    ANOTHER_NORTHLAND_BEAM("another_northland_beam", BaseBlock::new),
    ANOTHER_NORTHLAND_PLANKS("another_northland_planks", BeamLikeBlock::new),
    ICE_BRICKS("ice_bricks", BaseBlock::new),
    SNOW_BRICKS("snow_bricks", BaseBlock::new),
    ANOTHER_STONE_BRICKS("another_stone_bricks", BaseBlock::new),
    ANOTHER_COPPER_BRICKS("another_copper_bricks", BaseBlock::new),
    ANOTHER_COPPER_PLATE("another_copper_plate", BeamLikeBlock::new),
    TIN_BRICKS("tin_bricks", BaseBlock::new),
    TIN_PLATE("tin_plate", BeamLikeBlock::new),
    ANOTHER_IRON_BRICKS("another_iron_bricks", BaseBlock::new),
    LEAD_BRICKS("lead_bricks", BaseBlock::new),
    SILVER_BRICKS("silver_bricks", BaseBlock::new),
    WOLFRAM_BRICKS("wolfram_bricks", BaseBlock::new),
    ANOTHER_GOLD_BRICKS("another_gold_bricks", BaseBlock::new),
    PLATINUM_BRICKS("platinum_bricks", BaseBlock::new),
    EVIL_ORE_BRICKS("evil_ore_bricks", BaseBlock::new),
    EVIL_ROCK_BRICKS("evil_rock_bricks", BaseBlock::new),
    METEORITE_BRICKS("meteorite_bricks", BaseBlock::new),
    ANOTHER_CRIMSON_ORE_BRICKS("another_crimson_ore_bricks", BaseBlock::new),
    ANOTHER_CRIMSON_ROCK_BRICKS("another_crimson_rock_bricks", BaseBlock::new),
    ANOTHER_CRIMSON_STONE_BRICKS("another_crimson_stone_bricks", BaseBlock::new),
    PEARL_ROCK_BRICKS("pearl_rock_bricks", BaseBlock::new),
    GREEN_CANDY_BLOCK("green_candy_block", BaseBlock::new),
    RED_CANDY_BLOCK("red_candy_block", BaseBlock::new),
    ICED_GEL_BLOCK("iced_gel_block", BaseBlock::new),
    BLUE_GEL_BLOCK("blue_gel_block", BaseBlock::new),
    PINK_GEL_BLOCK("pink_gel_block", BaseBlock::new),
    SUN_PLATE("sun_plate", BaseBlock::new),
    ANOTHER_LAVA_BEAM("another_lava_beam", BaseBlock::new),
    ANOTHER_LAVA_BRICKS("another_lava_bricks", BaseBlock::new),
    ANOTHER_OBSIDIAN_BEAM("another_obsidian_beam", BaseBlock::new),
    ANOTHER_OBSIDIAN_BRICKS("another_obsidian_bricks", BaseBlock::new),
    ANOTHER_OBSIDIAN_PLATE("another_obsidian_plate", BaseBlock::new),
    ANOTHER_OBSIDIAN_SMALL_BRICKS("another_obsidian_small_bricks", BaseBlock::new),
    ANOTHER_SMOOTH_OBSIDIAN("another_smooth_obsidian", BeamLikeBlock::new),
    CHISELED_ANOTHER_OBSIDIAN_BRICKS("chiseled_another_obsidian_bricks", BaseBlock::new),

    CRYSTAL_BLOCK("crystal_block", BaseBlock::new);

    private final RegistryObject<Block> value;

    DecorativeBlocks(String id, Supplier<Block> block) {
        this.value = ConfluenceBlocks.registerWithItem(id, block);
    }

    @Override
    public RegistryObject<Block> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering decorative blocks");
    }
}
