package org.confluence.mod.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.*;
import org.confluence.mod.block.functional.*;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.confluence.mod.block.natural.MushroomBlock;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.block.natural.herbs.*;
import org.confluence.mod.block.natural.spreadable.*;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.ModItems;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.confluence.mod.block.natural.LogBlocks.WoodSetType.*;

@SuppressWarnings("unused")
public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<LifeCrystalBlock> LIFE_CRYSTAL_BLOCK = registerWithItem("life_crystal_block", LifeCrystalBlock::new, supplier -> () -> new LifeCrystalBlock.Item(supplier.get()));
    public static final RegistryObject<BlockEntityType<LifeCrystalBlock.Entity>> LIFE_CRYSTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("life_crystal_block_entity", () -> BlockEntityType.Builder.of(LifeCrystalBlock.Entity::new, LIFE_CRYSTAL_BLOCK.get()).build(null));
    public static final RegistryObject<Block>HARDENED_SAND_BLOCK = registerWithItem("hardened_sand_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    // ebony
    public static final LogBlocks EBONY_LOG_BLOCKS = new LogBlocks("ebony", EBONY);
    public static final RegistryObject<Block> EBONY_STONE = registerWithItem("ebony_stone", () -> new SpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_COBBLESTONE = registerWithItem("ebony_cobblestone", () -> new SpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_SANDSTONE = registerWithItem("ebony_sandstone", () -> new SpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_HARDENED_SAND_BLOCK = registerWithItem("ebony_hardened_sand_block", () -> new SpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_SAND = registerWithItem("ebony_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CORRUPT, 0x372B4B, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> CORRUPT_GRASS_BLOCK = registerWithItem("corrupt_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> PURPLE_ICE = registerWithItem("purple_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
    public static final RegistryObject<Block> PURPLE_PACKED_ICE = registerWithItem("purple_packed_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    // hallow
    public static final LogBlocks PEARL_LOG_BLOCKS = new LogBlocks("pearl", PEARL);
    public static final RegistryObject<Block> PEARL_STONE = registerWithItem("pearl_stone", () -> new SpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_COBBLESTONE = registerWithItem("pearl_cobblestone", () -> new SpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_HARDENED_SAND_BLOCK = registerWithItem("pearl_hardened_sand_block", () -> new SpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_SANDSTONE = registerWithItem("pearl_sandstone", () -> new SpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_SAND = registerWithItem("pearl_sand", () -> new SpreadingSandBlock(ISpreadable.Type.HALLOW, 0xEDD5F6, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final RegistryObject<Block> HALLOW_GRASS_BLOCK = registerWithItem("hallow_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> RED_ICE = registerWithItem("red_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
    public static final RegistryObject<Block> RED_PACKED_ICE = registerWithItem("red_packed_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    // crimson
    public static final LogBlocks SHADOW_LOG_BLOCKS = new LogBlocks("shadow", SHADOW);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE = registerWithItem("another_crimson_stone", () -> new SpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_COBBLESTONE = registerWithItem("another_crimson_cobblestone", () -> new SpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_HARDENED_SAND_BLOCK = registerWithItem("another_crimson_hardened_sand_block", () -> new SpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_SANDSTONE = registerWithItem("another_crimson_sandstone", () -> new SpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_SAND = registerWithItem("another_crimson_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CRIMSON, 0x5313E0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> ANOTHER_CRIMSON_GRASS_BLOCK = registerWithItem("another_crimson_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> PINK_ICE = registerWithItem("pink_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
    public static final RegistryObject<Block> PINK_PACKED_ICE = registerWithItem("pink_packed_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    // desert
    public static final LogBlocks PALM_LOG_BLOCKS = new LogBlocks("palm", PALM);
    // ash
    public static final LogBlocks ASH_LOG_BLOCKS = new LogBlocks("ash", ASH.SET, ASH.TYPE, true, false);
    public static final RegistryObject<Block> ASH_BLOCK = registerWithItem("ash_block", CustomModelBlock::new);
    public static final RegistryObject<Block> ASH_GRASS_BLOCK = registerWithItem("ash_grass_block", AshGrassBlock::new);
    // mushroom
    public static final RegistryObject<MushroomGrassBlock> MUSHROOM_GRASS_BLOCK = registerWithItem("mushroom_grass_block", MushroomGrassBlock::new);
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
    public static final RegistryObject<InstantExplosionBlock> INSTANTANEOUS_EXPLOSION_TNT = registerWithItem("instantaneous_explosion_tnt", InstantExplosionBlock::new);
    public static final RegistryObject<SwitchBlock> SWITCH = registerWithItem("switch", SwitchBlock::new);
    public static final RegistryObject<SignalAdapterBlock> SIGNAL_ADAPTER = registerWithItem("signal_adapter", SignalAdapterBlock::new);
    public static final RegistryObject<DartTrapBlock> DART_TRAP = registerWithItem("dart_trap", DartTrapBlock::new);
    public static final RegistryObject<TimersBlock> TIMERS_BLOCK_1_1 = registerWithItem("timers_1_1", () -> new TimersBlock(20)); // 1s
    public static final RegistryObject<TimersBlock> TIMERS_BLOCK_3_1 = registerWithItem("timers_3_1", () -> new TimersBlock(60)); // 3s
    public static final RegistryObject<TimersBlock> TIMERS_BLOCK_5_1 = registerWithItem("timers_5_1", () -> new TimersBlock(100)); // 5s
    public static final RegistryObject<TimersBlock> TIMERS_BLOCK_1_2 = registerWithItem("timers_1_2", () -> new TimersBlock(10)); // 1/2s
    public static final RegistryObject<TimersBlock> TIMERS_BLOCK_1_4 = registerWithItem("timers_1_4", () -> new TimersBlock(5)); // 1/4s
    public static final RegistryObject<BlockEntityType<AbstractMechanicalBlock.Entity>> MECHANICAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("mechanical_block_entity", () -> BlockEntityType.Builder.of(AbstractMechanicalBlock.Entity::new,
        BoulderBlock.Variant.NORMAL.get(), INSTANTANEOUS_EXPLOSION_TNT.get(), SWITCH.get(), SIGNAL_ADAPTER.get(), DART_TRAP.get(), TIMERS_BLOCK_1_1.get(), TIMERS_BLOCK_3_1.get(), TIMERS_BLOCK_5_1.get(), TIMERS_BLOCK_1_2.get(), TIMERS_BLOCK_1_4.get()).build(null));
    // frost
    public static final RegistryObject<ThinIceBlock> THIN_ICE_BLOCK = registerWithItem("thin_ice_block", ThinIceBlock::new);
    // crafting
    public static final RegistryObject<AltarBlock> DEMON_ALTAR = registerWithItem("demon_altar", () -> new AltarBlock(AltarBlock.Variant.DEMON), supplier -> () -> new AltarBlock.Item(supplier.get()));
    public static final RegistryObject<AltarBlock> CRIMSON_ALTAR = registerWithItem("crimson_altar", () -> new AltarBlock(AltarBlock.Variant.CRIMSON), supplier -> () -> new AltarBlock.Item(supplier.get()));
    public static final RegistryObject<BlockEntityType<AltarBlock.Entity>> ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("altar_block_entity", () -> BlockEntityType.Builder.of(AltarBlock.Entity::new, DEMON_ALTAR.get(), CRIMSON_ALTAR.get()).build(null));
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
    public static final RegistryObject<Block> ANOTHER_CRIMSON_MUSHROOM = registerWithoutItem("another_crimson_mushroom", () -> new MushroomBlock(Set.of(ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK.get())));//毒蘑菇
    public static final RegistryObject<Block> EBONY_MUSHROOM = registerWithoutItem("ebony_mushroom", () -> new MushroomBlock(Set.of(ModBlocks.CORRUPT_GRASS_BLOCK.get())));//魔菇
    // TODO: 发光蘑菇可以长在天花板上，可以长很长；天花板上的属于藤蔓，还没做
    public static final RegistryObject<Block> GLOWING_MUSHROOM = registerWithoutItem("glowing_mushroom", () -> new MushroomBlock(Set.of(ModBlocks.MUSHROOM_GRASS_BLOCK.get())));//发光蘑菇
    public static final RegistryObject<Block> LIFE_MUSHROOM = registerWithoutItem("life_mushroom", () -> new MushroomBlock(Set.of(Blocks.GRASS_BLOCK, HALLOW_GRASS_BLOCK.get())));//生命蘑菇
    public static final RegistryObject<JungleSporeBlock> JUNGLE_SPORE = registerWithoutItem("jungle_spore", JungleSporeBlock::new);
    // 草药
    public static final RegistryObject<BaseHerbBlock> WATERLEAF = registerWithoutItem("waterleaf", Waterleaf::new);//幌菊
    public static final RegistryObject<FlameFlower> FLAMEFLOWERS = registerWithoutItem("flameflowers", FlameFlower::new);//火焰花
    public static final RegistryObject<MoonshineGrass> MOONSHINE_GRASS = registerWithoutItem("moonshine_grass", MoonshineGrass::new);//月光草
    public static final RegistryObject<BaseHerbBlock> SHINE_ROOT = registerWithoutItem("shine_root", ShineRoot::new);//闪耀根
    public static final RegistryObject<BaseHerbBlock> SHIVERINGTHORNS = registerWithoutItem("shiveringthorns", ShiveringThorn::new);//寒颤棘
    public static final RegistryObject<BaseHerbBlock> SUNFLOWERS = registerWithoutItem("sunflowers", SunFlower::new);//太阳花
    public static final RegistryObject<DeathWeed> DEATHWEED = registerWithoutItem("deathweed", DeathWeed::new);//死亡草
    public static final RegistryObject<BlockEntityType<BaseHerbBlock.Entity>> HERBS_ENTITY = BLOCK_ENTITIES.register("herbs_entity", () -> BlockEntityType.Builder.of(BaseHerbBlock.Entity::new,
        WATERLEAF.get(), FLAMEFLOWERS.get(), MOONSHINE_GRASS.get(), SHINE_ROOT.get(), SHIVERINGTHORNS.get(), SUNFLOWERS.get(), DEATHWEED.get()).build(null));
    // grass
    public static final RegistryObject<Block> CORRUPT_GRASS = registerWithItem("corrupt_grass", () -> new BasePlantBlock(Set.of(ModBlocks.CORRUPT_GRASS_BLOCK.get())));//腐化草
    public static final RegistryObject<Block> ANOTHER_CRIMSON_GRASS = registerWithItem("another_crimson_grass", () -> new BasePlantBlock(Set.of(ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK.get())));//猩红草
    public static final RegistryObject<Block> HALLOW_GRASS = registerWithItem("hallow_grass", () -> new BasePlantBlock(Set.of(HALLOW_GRASS_BLOCK.get())));//神圣草
    public static final RegistryObject<Block> ASH_GRASS = registerWithItem("ash_grass", () -> new BasePlantBlock(Set.of(ASH_GRASS_BLOCK.get())));
    public static final RegistryObject<Block> NATURES_GIFT = registerWithoutItem("natures_gift", NaturesGiftBlock::new);
    // TODO: 头盔栏时装，shape，不可放置
    public static final RegistryObject<Block> JUNGLE_ROSE = registerWithItem("jungle_rose", () -> new BasePlantBlock(Set.of(Blocks.GRASS_BLOCK, Blocks.MOSS_BLOCK, Blocks.CLAY)));

    public static final RegistryObject<ThornBlock> CRIMSON_THORN = registerWithItem("crimson_thorn", () -> new SpreadingThornBlock(2,ANOTHER_CRIMSON_GRASS_BLOCK.get(), ISpreadable.Type.CRIMSON));
    public static final RegistryObject<ThornBlock> CORRUPTION_THORN = registerWithItem("corruption_thorn", () -> new SpreadingThornBlock(2,CORRUPT_GRASS_BLOCK.get(), ISpreadable.Type.CORRUPT));
    public static final RegistryObject<ThornBlock> JUNGLE_THORN = registerWithItem("jungle_thorn", () -> new ThornBlock(3.4f,Blocks.MOSS_BLOCK));
    public static final RegistryObject<ThornBlock> PLANTERA_THORN = registerWithItem("plantera_thorn", () -> new ThornBlock(20,null));
    // 云块
    public static final RegistryObject<Block> CLOUD_BLOCK = registerWithItem("cloud_block", CloudBlock::new);
    public static final RegistryObject<Block> RAIN_CLOUD_BLOCK = registerWithItem("rain_cloud_block", CloudBlock::new);
    public static final RegistryObject<Block> SNOW_CLOUD_BLOCK = registerWithItem("snow_cloud_block", CloudBlock::new);
    // 丛林蜂巢
    public static final RegistryObject<Block> JUNGLE_HIVE_BLOCK = registerWithItem("jungle_hive_block", JungleHiveBlock::new);
    // 红石变种
    public static final RegistryObject<Block> PEARL_STONE_REDSTONE_ORE = registerWithItem("pearl_stone_redstone_ore", CustomModelBlock::new);
    public static final RegistryObject<Block> EBONY_STONE_REDSTONE_ORE = registerWithItem("ebony_stone_redstone_ore", CustomModelBlock::new);
    public static final RegistryObject<Block> ANOTHER_CRIMSON_STONE_REDSTONE_ORE = registerWithItem("another_crimson_stone_redstone_ore", CustomModelBlock::new);

    // 石中剑
    public static final RegistryObject<Block> SWORD_IN_STONE = registerWithItem("sword_in_stone", CustomModelBlock::new);
    // 血肉眼球块
    public static final RegistryObject<Block> OCULAR_BLOCKS = registerWithItem("ocular_blocks", CustomModelBlock::new);
    public static final RegistryObject<PressurePlateBlock> DEEPSLATE_PRESSURE_PLATE = registerWithItem("deepslate_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).mapColor(MapColor.DEEPSLATE).strength(0.1F), BlockSetType.STONE));

    public static final RegistryObject<BlockEntityType<SignBlockEntity>> SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign_block_entity", () -> BlockEntityType.Builder.of(SignBlockEntity::new, LogBlocks.getSignBlocks()).build(null));

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
        BoulderBlock.Variant.init();
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
    }
}