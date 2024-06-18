package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.*;
import org.confluence.mod.block.functional.ActuatorsBlock;
import org.confluence.mod.block.functional.BoulderBlock;
import org.confluence.mod.block.functional.EchoBlock;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.block.natural.MushroomBlock;
import org.confluence.mod.block.natural.herbs.*;
import org.confluence.mod.block.natural.spreadable.ISpreadable;
import org.confluence.mod.block.natural.spreadable.SpreadingBlock;
import org.confluence.mod.block.natural.spreadable.SpreadingGrassBlock;
import org.confluence.mod.block.natural.spreadable.SpreadingSandBlock;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.ModItems;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.confluence.mod.block.natural.LogBlocks.WoodSetType.*;

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
    // mushroom
    public static final RegistryObject<Block> MUSHROOM_GRASS_BLOCK = registerWithItem("mushroom_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.GLOWING, BlockBehaviour.Properties.of()));
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
    public static final RegistryObject<BlockEntityType<ActuatorsBlock.Entity>> ACTUATORS_ENTITY = BLOCK_ENTITIES.register("actuators_entity", () -> BlockEntityType.Builder.of(ActuatorsBlock.Entity::new, ACTUATORS.get()).build(null));
    public static final RegistryObject<BoulderBlock> BOULDER = registerWithItem("boulder", BoulderBlock::new);
    // frost
    public static final RegistryObject<ThinIceBlock> THIN_ICE_BLOCK = registerWithItem("thin_ice_block", ThinIceBlock::new);
    // crafting
    public static final RegistryObject<AltarBlock> ALTAR_BLOCK = registerWithItem("altar_block", AltarBlock::new);
    // fluid
    public static final RegistryObject<LiquidBlock> HONEY = registerWithoutItem("honey", () -> new LiquidBlock(ModFluids.HONEY.fluid(), BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<CrispyHoneyBlock> CRISPY_HONEY_BLOCK = registerWithItem("crispy_honey_block", CrispyHoneyBlock::new);
    public static final RegistryObject<LiquidBlock> SHIMMER = registerWithoutItem("shimmer", () -> new LiquidBlock(ModFluids.SHIMMER.fluid(), BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_PINK)));
    // misc
    public static final RegistryObject<BlockEntityType<Torches.Entity>> COLORFUL_TORCH_ENTITY = BLOCK_ENTITIES.register("colorful_block_entity", () -> BlockEntityType.Builder.of(
        Torches.Entity::new, Torches.DEMON_TORCH.stand.get(), Torches.DEMON_TORCH.wall.get(), Torches.RAINBOW_TORCH.stand.get(), Torches.RAINBOW_TORCH.wall.get()).build(null));
    // chain
    public static final RegistryObject<BaseChainBlock> RUBY_CHAIN = registerWithItem("ruby_chain", () -> new BaseChainBlock(MapColor.COLOR_RED));
    public static final RegistryObject<BaseChainBlock> AMBER_CHAIN = registerWithItem("amber_chain", () -> new BaseChainBlock(MapColor.COLOR_ORANGE));
    public static final RegistryObject<BaseChainBlock> TOPAZ_CHAIN = registerWithItem("topaz_chain", () -> new BaseChainBlock(MapColor.COLOR_YELLOW));
    public static final RegistryObject<BaseChainBlock> EMERALD_CHAIN = registerWithItem("emerald_chain", () -> new BaseChainBlock(MapColor.EMERALD));
    public static final RegistryObject<BaseChainBlock> SAPPHIRE_CHAIN = registerWithItem("sapphire_chain", () -> new BaseChainBlock(MapColor.COLOR_BLUE));
    public static final RegistryObject<BaseChainBlock> DIAMOND_CHAIN = registerWithItem("diamond_chain", () -> new BaseChainBlock(MapColor.DIAMOND));
    public static final RegistryObject<BaseChainBlock> AMETHYST_CHAIN = registerWithItem("amethyst_chain", () -> new BaseChainBlock(MapColor.COLOR_PURPLE));
    public static final RegistryObject<BaseChainBlock> SILK_CHAIN = registerWithItem("silk_chain", () -> new BaseChainBlock(MapColor.TERRACOTTA_WHITE));
    public static final RegistryObject<BaseChainBlock> BONE_CHAIN = registerWithItem("bone_chain", () -> new BaseChainBlock(MapColor.TERRACOTTA_WHITE));
    // plant
    public static final RegistryObject<Block> ANOTHER_CRIMSON_MUSHROOM = registerWithoutItem("another_crimson_mushroom", ()->new MushroomBlock(ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK.get()));//毒蘑菇
    public static final RegistryObject<Block> EBONY_MUSHROOM = registerWithoutItem("ebony_mushroom", ()->new MushroomBlock(ModBlocks.CORRUPT_GRASS_BLOCK.get()));//魔菇
    public static final RegistryObject<Block> GLOWING_MUSHROOM = registerWithoutItem("glowing_mushroom", ()->new MushroomBlock(ModBlocks.MUSHROOM_GRASS_BLOCK.get()));//发光蘑菇
    public static final RegistryObject<Block> LIFE_MUSHROOM = registerWithoutItem("life_mushroom", ()->new MushroomBlock(Blocks.GRASS_BLOCK));//生命蘑菇
    public static final RegistryObject<Block> JUNGLE_SPORE = registerWithoutItem("jungle_spore", ()->new MushroomBlock(Blocks.GRASS_BLOCK)); // TODO: 丛林草
    // 草药
    public static final RegistryObject<BaseHerbBlock> WATERLEAF = registerWithoutItem("waterleaf", Waterleaf::new);//幌菊
    public static final RegistryObject<BaseHerbBlock> FLAMEFLOWERS = registerWithoutItem("flameflowers", FlameFlower::new);//火焰花
    public static final RegistryObject<BaseHerbBlock> MOONSHINE_GRASS = registerWithoutItem("moonshine_grass", MoonshineGrass::new);//月光草
    public static final RegistryObject<BaseHerbBlock> SHINE_ROOT = registerWithoutItem("shine_root", ShineRoot::new);//闪耀根
    public static final RegistryObject<BaseHerbBlock> SHIVERINGTHORNS = registerWithoutItem("shiveringthorns", ShiveringThorn::new);//寒颤棘
    public static final RegistryObject<BaseHerbBlock> SUNFLOWERS = registerWithoutItem("sunflowers", SunFlower::new);//太阳花
    public static final RegistryObject<BaseHerbBlock> DEATHWEED = registerWithoutItem("deathweed", DeathWeed::new);//死亡草
    public static final RegistryObject<BlockEntityType<BaseHerbBlock.Entity>> HERBS_ENTITY = BLOCK_ENTITIES.register("herbs_entity", () -> BlockEntityType.Builder.of(BaseHerbBlock.Entity::new,
        WATERLEAF.get(),FLAMEFLOWERS.get(),MOONSHINE_GRASS.get(),SHINE_ROOT.get(),SHIVERINGTHORNS.get(),SUNFLOWERS.get(),DEATHWEED.get()).build(null));

    // grass
    public static final RegistryObject<FlowerBlock> CORRUPT_GRASS = registerWithItem("corrupt_grass", BasePlantBlock::new);//腐化草
    public static final RegistryObject<FlowerBlock> ANOTHER_CRIMSON_HUNGRY_GHOST_GRASS = registerWithItem("another_crimson_hungry_ghost_grass", BasePlantBlock::new);//猩红饿鬼草
    public static final RegistryObject<FlowerBlock> ANOTHER_CRIMSON_EYEBALL_GRASS = registerWithItem("another_crimson_eyeball_grass", BasePlantBlock::new);//猩红眼球草
    public static final RegistryObject<FlowerBlock> ANOTHER_CRIMSON_GRASS = registerWithItem("another_crimson_grass", BasePlantBlock::new);//猩红草
    public static final RegistryObject<FlowerBlock> HALLOW_GRASS = registerWithItem("hallow_grass", BasePlantBlock::new);//神圣草
    public static final RegistryObject<FlowerBlock> HALLOW_FLOWERS = registerWithItem("hallow_flowers", BasePlantBlock::new);//神圣花丛
    // <--空格呢?!

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
        Pots.init();
        Boxes.init();
        Torches.init();
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}
