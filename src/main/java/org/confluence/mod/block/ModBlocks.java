package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.AltarBlock;
import org.confluence.mod.block.common.BaseBlock;
import org.confluence.mod.block.entity.ActuatorsBlockEntity;
import org.confluence.mod.block.functional.ActuatorsBlock;
import org.confluence.mod.block.functional.EchoBlock;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.misc.ModFluids;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.confluence.mod.block.WoodSetType.*;

@SuppressWarnings("unused")
public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Confluence.MODID);

    // ebony
    public static final LogBlocks EBONY_LOG_BLOCKS = new LogBlocks("ebony", EBONY);
    public static final RegistryObject<Block> EBONY_STONE = registerWithItem("ebony_stone", () -> new SpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_SAND = registerWithItem("ebony_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CORRUPT, 0x372B4B, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> CORRUPT_GRASS_BLOCK = registerWithItem("corrupt_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    // hallow
    public static final LogBlocks PEARL_LOG_BLOCKS = new LogBlocks("pearl", PEARL);
    public static final RegistryObject<Block> PEARL_STONE = registerWithItem("pearl_stone", () -> new SpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_SAND = registerWithItem("pearl_sand", () -> new SpreadingSandBlock(ISpreadable.Type.HALLOW, 0xEDD5F6, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> HALLOW_GRASS_BLOCK = registerWithItem("hallow_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    // crimson
    public static final LogBlocks SHADOW_LOG_BLOCKS = new LogBlocks("shadow", SHADOW);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE = registerWithItem("another_crimson_stone", () -> new SpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_SAND = registerWithItem("another_crimson_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CRIMSON, 0x5313E0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_GRASS_BLOCK = registerWithItem("another_crimson_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    // desert
    public static final LogBlocks PALM_LOG_BLOCKS = new LogBlocks("palm", PALM);
    // ash
    public static final LogBlocks ASH_LOG_BLOCKS = new LogBlocks("ash", ASH.SET, ASH.TYPE, true, false);
    public static final RegistryObject<Block> ASH_BLOCK = registerWithItem("ash_block", BaseBlock::new);
    // glowing
    public static final RegistryObject<Block> GLOWING_GRASS_BLOCK = registerWithItem("glowing_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.GLOWING, BlockBehaviour.Properties.of()));
    // jewelry
    public static final RegistryObject<Block> BIG_RUBY_BLOCK = registerWithItem("big_ruby_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_AMBER_BLOCK = registerWithItem("big_amber_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_TOPAZ_BLOCK = registerWithItem("big_topaz_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_SAPPHIRE_BLOCK = registerWithItem("big_sapphire_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_ANOTHER_AMETHYST_BLOCK = registerWithItem("big_another_amethyst_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    // other environmental stones
    public static final RegistryObject<Block> ANOTHER_POLISHED_GRANITE = registerWithItem("another_polished_granite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> POLISHED_MARBLE = registerWithItem("polished_marble", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    // decorative blocks
    public static final LogBlocks SPOOKY_LOG_BLOCKS = new LogBlocks("spooky", SPOOKY.SET, SPOOKY.TYPE, false, true);
    // functional block
    public static final RegistryObject<EchoBlock> ECHO_BLOCK = registerWithItem("echo_block", EchoBlock::new);
    public static final RegistryObject<ActuatorsBlock> ACTUATORS = registerWithItem("actuators", ActuatorsBlock::new);
    public static final RegistryObject<BlockEntityType<ActuatorsBlockEntity>> ACTUATORS_ENTITY = BLOCK_ENTITIES.register("actuators_entity", () -> BlockEntityType.Builder.of(ActuatorsBlockEntity::new, ACTUATORS.get()).build(null));
    // frost
    public static final RegistryObject<ThinIceBlock> THIN_ICE_BLOCK = registerWithItem("thin_ice_block", ThinIceBlock::new);
    // crafting
    public static final RegistryObject<AltarBlock> ALTAR_BLOCK = registerWithItem("altar_block", AltarBlock::new);
    // fluid
    public static final RegistryObject<LiquidBlock> HONEY = registerWithoutItem("honey", () -> new LiquidBlock(ModFluids.HONEY, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
    public static final RegistryObject<CrispyHoneyBlock> CRISPY_HONEY_BLOCK = registerWithItem("crispy_honey_block", CrispyHoneyBlock::new);
    //罐子
    public static final RegistryObject<ForestJarBlock> FOREST_JARS = registerWithItem("forest_jars",() -> new ForestJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static final RegistryObject<ForestJarBlock> SNOW_JARS = registerWithItem("snow_jars",() -> new ForestJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static final RegistryObject<ForestJarBlock> SPIDER_CAVE_JARS = registerWithItem("spider_cave_jars",() -> new ForestJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static final RegistryObject<DesertJarBlock> DESERT_JARS = registerWithItem("desert_jars",() -> new DesertJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static final RegistryObject<AnotherCrimsonJarBlock> ANOTHER_CRIMSON_JARS = registerWithItem("another_crimson_jars",() -> new AnotherCrimsonJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static final RegistryObject<CorruptJarBlock> CORRUPT_JARS = registerWithItem("corrupt_jars",() -> new CorruptJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));

    public static final RegistryObject<ForestJarBlock> ASH_JARS = registerWithItem("ash_jars",() -> new ForestJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static final RegistryObject<CorruptJarBlock> TEMPLE_JARS = registerWithItem("temple_jars",() -> new CorruptJarBlock(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));
    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block) {
        return registerWithItem(id, block, new Item.Properties());
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block, Function<Supplier<B>, Supplier<BlockItem>> item) {
        RegistryObject<B> object = BLOCKS.register(id, block);
        ModItems.ITEMS.register(id, item.apply(object));
        return object;
    }

    public static <B extends Block> RegistryObject<B> registerWithItem(String id, Supplier<B> block, Item.Properties properties) {
        RegistryObject<B> object = BLOCKS.register(id, block);
        ModItems.ITEMS.register(id, () -> new BlockItem(object.get(), properties));
        return object;
    }

    public static <B extends Block> RegistryObject<B> registerWithoutItem(String id, Supplier<B> block) {
        return BLOCKS.register(id, block);
    }

    public static void register(IEventBus bus) {
        Ores.init();
        DecorativeBlocks.init();
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}
