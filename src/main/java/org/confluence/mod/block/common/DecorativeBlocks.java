package org.confluence.mod.block.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;

import java.util.LinkedList;
import java.util.function.Supplier;

public final class DecorativeBlocks {
    private static LinkedList<RegistryObject<Block>> list = new LinkedList<>();
    private static RegistryObject<Block>[] values;

    public static final RegistryObject<Block> TR_OAK_BEAM = registerWithItem("tr_oak_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> TR_OAK_PLANKS = registerWithItem("tr_oak_planks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> TR_NORTHLAND_BEAM = registerWithItem("tr_northland_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> TR_NORTHLAND_PLANKS = registerWithItem("tr_northland_planks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WOOD_STONE_SLATTED_BLOCKS = registerWithItem("wood_stone_slatted_blocks", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ICE_BRICKS = registerWithItem("ice_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLUE_ICE)));
    public static final RegistryObject<Block> SNOW_BRICKS = registerWithItem("snow_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_STONE_BRICKS = registerWithItem("tr_stone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> PURE_GLASS = registerWithItem("pure_glass", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    public static final RegistryObject<Block> TR_COPPER_BRICKS = registerWithItem("tr_copper_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_COPPER_BRICKS = registerWithItem("ancient_copper_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<Block> TR_COPPER_PLATE = registerWithItem("tr_copper_plate", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<Block> TIN_BRICKS = registerWithItem("tin_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_TIN_BRICKS = registerWithItem("ancient_tin_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<Block> TIN_PLATE = registerWithItem("tin_plate", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistryObject<Block> TR_IRON_BRICKS = registerWithItem("tr_iron_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_IRON_BRICKS = registerWithItem("ancient_iron_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TR_IRON_PLATE = registerWithItem("tr_iron_plate", () -> new BeamLikeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> LEAD_BRICKS = registerWithItem("lead_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_LEAD_BRICKS = registerWithItem("ancient_lead_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SILVER_BRICKS = registerWithItem("silver_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_SILVER_BRICKS = registerWithItem("ancient_silver_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TUNGSTEN_BRICKS = registerWithItem("tungsten_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_TUNGSTEN_BRICKS = registerWithItem("ancient_tungsten_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TR_GOLD_BRICKS = registerWithItem("tr_gold_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_GOLD_BRICKS = registerWithItem("ancient_gold_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PLATINUM_BRICKS = registerWithItem("platinum_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ANCIENT_PLATINUM_BRICKS = registerWithItem("ancient_platinum_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> EBONY_ORE_BRICKS = registerWithItem("ebony_ore_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> EBONY_ROCK_BRICKS = registerWithItem("ebony_rock_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METEORITE_BRICKS = registerWithItem("meteorite_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TR_CRIMSON_ORE_BRICKS = registerWithItem("tr_crimson_ore_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TR_CRIMSON_ROCK_BRICKS = registerWithItem("tr_crimson_rock_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PEARL_ROCK_BRICKS = registerWithItem("pearl_rock_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> GREEN_CANDY_BLOCK = registerWithItem("green_candy_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> RED_CANDY_BLOCK = registerWithItem("red_candy_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> FROZEN_GEL_BLOCK = registerWithItem("frozen_gel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));
    public static final RegistryObject<Block> BLUE_GEL_BLOCK = registerWithItem("blue_gel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));
    public static final RegistryObject<Block> PINK_GEL_BLOCK = registerWithItem("pink_gel_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));
    public static final RegistryObject<Block> SUN_PLATE = registerWithItem("sun_plate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_LAVA_BEAM = registerWithItem("tr_lava_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_LAVA_BRICKS = registerWithItem("tr_lava_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_OBSIDIAN_BEAM = registerWithItem("tr_obsidian_beam", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_OBSIDIAN_BRICKS = registerWithItem("tr_obsidian_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_OBSIDIAN_PLATE = registerWithItem("tr_obsidian_plate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_OBSIDIAN_SMALL_BRICKS = registerWithItem("tr_obsidian_small_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_SMOOTH_OBSIDIAN = registerWithItem("tr_smooth_obsidian", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> TR_GRANITE_COLUMN = registerWithItem("tr_granite_column", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> MARBLE_COLUMN = registerWithItem("marble_column", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static final RegistryObject<Block> CHISELED_TR_OBSIDIAN_BRICKS = registerWithItem("chiseled_tr_obsidian_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BLUE_BRICK = registerWithItem("blue_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).explosionResistance(ModBlocks.getObsidianBasedExplosionResistance(200))));
    public static final RegistryObject<Block> GREEN_BRICK = registerWithItem("green_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).explosionResistance(ModBlocks.getObsidianBasedExplosionResistance(200))));
    public static final RegistryObject<Block> PINK_BRICK = registerWithItem("pink_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS).explosionResistance(ModBlocks.getObsidianBasedExplosionResistance(200))));
    public static final RegistryObject<Block> AETHERIUM_BRICK = registerWithItem("aetherium_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> CRYSTAL_BLOCK = registerWithItem("crystal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK)));
    public static final RegistryObject<Block> RAINBOW_BRICK = registerWithItem("rainbow_brick", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    public static RegistryObject<Block> registerWithItem(String id, Supplier<Block> block) {
        RegistryObject<Block> object = ModBlocks.registerWithItem(id, block);
        list.add(object);
        return object;
    }

    @SuppressWarnings("unchecked")
    public static RegistryObject<Block>[] values() {
        if (values == null) {
            values = (RegistryObject<Block>[]) list.toArray(RegistryObject<?>[]::new);
            list = null;
        }
        return values;
    }

    public static void init() {}
}
