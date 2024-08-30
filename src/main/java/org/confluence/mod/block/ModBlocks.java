package org.confluence.mod.block;

import net.minecraft.core.particles.ParticleTypes;
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
import org.confluence.mod.block.crafting.AltarBlock;
import org.confluence.mod.block.crafting.ExtractinatorBlock;
import org.confluence.mod.block.crafting.SkyMillBlock;
import org.confluence.mod.block.crafting.WorkshopBlock;
import org.confluence.mod.block.functional.*;
import org.confluence.mod.block.furniture.chair.WhitePlasticChairBlock;
import org.confluence.mod.block.natural.MushroomBlock;
import org.confluence.mod.block.natural.*;
import org.confluence.mod.block.natural.herbs.*;
import org.confluence.mod.block.natural.spreadable.*;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.worldgen.feature.*;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.confluence.mod.block.natural.LogBlocks.WoodSetType.*;

@SuppressWarnings("unused")
public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Confluence.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<LifeCrystalBlock> LIFE_CRYSTAL_BLOCK = registerWithItem("life_crystal_block", LifeCrystalBlock::new, supplier -> () -> new LifeCrystalBlock.Item(supplier.get()));
    public static final RegistryObject<BlockEntityType<LifeCrystalBlock.Entity>> LIFE_CRYSTAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("life_crystal_block_entity", () -> BlockEntityType.Builder.of(LifeCrystalBlock.Entity::new, LIFE_CRYSTAL_BLOCK.get()).build(null));
    // 环境辅助
    public static final RegistryObject<Block> HARDENED_SAND_BLOCK = registerWithItem("hardened_sand_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> RED_HARDENED_SAND_BLOCK = registerWithItem("red_hardened_sand_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> DIATOMACEOUS = registerWithItem("diatomaceous", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    // 提取块
    public static final RegistryObject<Block> DESERT_FOSSIL = registerWithItem("desert_fossil", () -> new CustomModelBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.5F, 1.0F)));
    public static final RegistryObject<Block> SLUSH = registerWithItem("slush", () -> new SandBlock(0xcfddde, BlockBehaviour.Properties.copy(Blocks.GRAVEL).strength(0.7F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> MARINE_GRAVEL = registerWithItem("marine_gravel", () -> new SandBlock(0xdedede, BlockBehaviour.Properties.copy(Blocks.GRAVEL).strength(0.8F).sound(SoundType.GRAVEL)));
    // ebony
    public static final LogBlocks EBONY_LOG_BLOCKS = new LogBlocks("ebony", EBONY);
    public static final RegistryObject<Block> EBONY_STONE = registerWithItem("ebony_stone", () -> new CustomModelSpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_COBBLESTONE = registerWithItem("ebony_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> EBONY_SANDSTONE = registerWithItem("ebony_sandstone", () -> new CustomModelSpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_HARDENED_SAND_BLOCK = registerWithItem("ebony_hardened_sand_block", () -> new SpreadingBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> EBONY_SAND = registerWithItem("ebony_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CORRUPT, 0x372B4B, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> CORRUPT_GRASS_BLOCK = registerWithItem("corrupt_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CORRUPT, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> PURPLE_ICE = registerWithItem("purple_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
    public static final RegistryObject<Block> PURPLE_PACKED_ICE = registerWithItem("purple_packed_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    // hallow
    public static final LogBlocks PEARL_LOG_BLOCKS = new LogBlocks("pearl", PEARL);
    public static final RegistryObject<Block> PEARL_STONE = registerWithItem("pearl_stone", () -> new CustomModelSpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_COBBLESTONE = registerWithItem("pearl_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> PEARL_HARDENED_SAND_BLOCK = registerWithItem("pearl_hardened_sand_block", () -> new SpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_SANDSTONE = registerWithItem("pearl_sandstone", () -> new CustomModelSpreadingBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> PEARL_SAND = registerWithItem("pearl_sand", () -> new SpreadingSandBlock(ISpreadable.Type.HALLOW, 0xEDD5F6, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)));
    public static final RegistryObject<Block> HALLOW_GRASS_BLOCK = registerWithItem("hallow_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.HALLOW, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> RED_ICE = registerWithItem("red_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
    public static final RegistryObject<Block> RED_PACKED_ICE = registerWithItem("red_packed_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    // crimson
    public static final LogBlocks SHADOW_LOG_BLOCKS = new LogBlocks("shadow", SHADOW);
    public static final RegistryObject<Block> TR_CRIMSON_STONE = registerWithItem("tr_crimson_stone", () -> new CustomModelSpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TR_CRIMSON_COBBLESTONE = registerWithItem("tr_crimson_cobblestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistryObject<Block> TR_CRIMSON_HARDENED_SAND_BLOCK = registerWithItem("tr_crimson_hardened_sand_block", () -> new SpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TR_CRIMSON_SANDSTONE = registerWithItem("tr_crimson_sandstone", () -> new CustomModelSpreadingBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.of()));
    public static final RegistryObject<Block> TR_CRIMSON_SAND = registerWithItem("tr_crimson_sand", () -> new SpreadingSandBlock(ISpreadable.Type.CRIMSON, 0x5313E0, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> TR_CRIMSON_GRASS_BLOCK = registerWithItem("tr_crimson_grass_block", () -> new SpreadingGrassBlock(ISpreadable.Type.CRIMSON, BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> PINK_ICE = registerWithItem("pink_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.ICE)));
    public static final RegistryObject<Block> PINK_PACKED_ICE = registerWithItem("pink_packed_ice", () -> new IceBlock(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE)));
    // 树苗
    public static final RegistryObject<Block> SHADOW_SAPLING = registerWithItem("shadow_sapling", () -> new ShadowSaplingBlock(new ShadowTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> EBONY_SAPLING = registerWithItem("ebony_sapling", () -> new EbonySaplingBlock(new EbonyTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> PALM_SAPLING = registerWithItem("palm_sapling", () -> new PalmSaplingBlock(new PalmTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> PEARL_SAPLING = registerWithItem("pearl_sapling", () -> new PearlSaplingBlock(new PearlTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> RUBY_SAPLING = registerWithItem("ruby_sapling", () -> new StoneSaplingBlock(new RubyTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    // desert
    public static final LogBlocks PALM_LOG_BLOCKS = new LogBlocks("palm", PALM);
    // ash
    public static final LogBlocks ASH_LOG_BLOCKS = new LogBlocks("ash", ASH.SET, ASH.TYPE, true, false);
    public static final RegistryObject<Block> ASH_BLOCK = registerWithItem("ash_block", AshBlock::new);
    public static final RegistryObject<Block> ASH_GRASS_BLOCK = registerWithItem("ash_grass_block", AshGrassBlock::new);
    // mushroom
    public static final RegistryObject<MushroomGrassBlock> MUSHROOM_GRASS_BLOCK = registerWithItem("mushroom_grass_block", MushroomGrassBlock::new);
    // jewelry
    public static final RegistryObject<Block> BIG_RUBY_BLOCK = registerWithItem("big_ruby_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_AMBER_BLOCK = registerWithItem("big_amber_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_TOPAZ_BLOCK = registerWithItem("big_topaz_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_SAPPHIRE_BLOCK = registerWithItem("big_sapphire_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistryObject<Block> BIG_TR_AMETHYST_BLOCK = registerWithItem("big_tr_amethyst_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    // other environmental stones
    public static final RegistryObject<Block> TR_POLISHED_GRANITE = registerWithItem("tr_polished_granite", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
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
    public static final RegistryObject<GeyserBlock> GEYSER_BLOCK = registerWithItem("geyser_block", GeyserBlock::new);
    public static final RegistryObject<BlockEntityType<AbstractMechanicalBlock.Entity>> MECHANICAL_BLOCK_ENTITY = BLOCK_ENTITIES.register("mechanical_block_entity", () -> BlockEntityType.Builder.of(AbstractMechanicalBlock.Entity::new,
        BoulderBlock.Variant.NORMAL.get(), INSTANTANEOUS_EXPLOSION_TNT.get(), SWITCH.get(), SIGNAL_ADAPTER.get(), DART_TRAP.get(), TIMERS_BLOCK_1_1.get(), TIMERS_BLOCK_3_1.get(), TIMERS_BLOCK_5_1.get(), TIMERS_BLOCK_1_2.get(), TIMERS_BLOCK_1_4.get()).build(null));
    // frost
    public static final RegistryObject<ThinIceBlock> THIN_ICE_BLOCK = registerWithItem("thin_ice_block", ThinIceBlock::new);
    // crafting
    public static final RegistryObject<ExtractinatorBlock> EXTRACTINATOR = registerWithItem("extractinator", () -> new ExtractinatorBlock(BlockBehaviour.Properties.of().strength(2.2F, 5.0F)), supplier -> () -> new ExtractinatorBlock.Item(supplier.get()));
    public static final RegistryObject<BlockEntityType<ExtractinatorBlock.Entity>> EXTRACTINATOR_ENTITY = BLOCK_ENTITIES.register("extractinator_entity", () -> BlockEntityType.Builder.of(ExtractinatorBlock.Entity::new, EXTRACTINATOR.get()).build(null));
    public static final RegistryObject<AltarBlock> DEMON_ALTAR = registerWithItem("demon_altar", () -> new AltarBlock(AltarBlock.Variant.DEMON), supplier -> () -> new AltarBlock.Item(supplier.get()));
    public static final RegistryObject<AltarBlock> CRIMSON_ALTAR = registerWithItem("crimson_altar", () -> new AltarBlock(AltarBlock.Variant.CRIMSON), supplier -> () -> new AltarBlock.Item(supplier.get()));
    public static final RegistryObject<BlockEntityType<AltarBlock.Entity>> ALTAR_BLOCK_ENTITY = BLOCK_ENTITIES.register("altar_block_entity", () -> BlockEntityType.Builder.of(AltarBlock.Entity::new, DEMON_ALTAR.get(), CRIMSON_ALTAR.get()).build(null));
    public static final RegistryObject<SkyMillBlock> SKY_MILL = registerWithItem("sky_mill", SkyMillBlock::new);
    public static final RegistryObject<BlockEntityType<SkyMillBlock.Entity>> SKY_MILL_ENTITY = BLOCK_ENTITIES.register("sky_mill_entity", () -> BlockEntityType.Builder.of(SkyMillBlock.Entity::new, SKY_MILL.get()).build(null));
    public static final RegistryObject<WorkshopBlock> WORKSHOP = registerWithItem("workshop", WorkshopBlock::new);
    public static final RegistryObject<BlockEntityType<WorkshopBlock.Entity>> WORKSHOP_ENTITY = BLOCK_ENTITIES.register("workshop_entity", () -> BlockEntityType.Builder.of(WorkshopBlock.Entity::new, WORKSHOP.get()).build(null));
    // fluid
    public static final RegistryObject<LiquidBlock> HONEY = registerWithoutItem("honey", () -> new LiquidBlock(ModFluids.HONEY.fluid(), BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<CrispyHoneyBlock> CRISPY_HONEY_BLOCK = registerWithItem("crispy_honey_block", CrispyHoneyBlock::new);
    public static final RegistryObject<LiquidBlock> SHIMMER = registerWithoutItem("shimmer", () -> new LiquidBlock(ModFluids.SHIMMER.fluid(), BlockBehaviour.Properties.copy(Blocks.WATER).mapColor(MapColor.COLOR_PINK).lightLevel(blockState -> 10)));
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
    public static final RegistryObject<Block> TR_CRIMSON_MUSHROOM = registerWithoutItem("tr_crimson_mushroom", () -> new MushroomBlock(ModBlocks.TR_CRIMSON_GRASS_BLOCK.get()));//毒蘑菇
    public static final RegistryObject<Block> EBONY_MUSHROOM = registerWithoutItem("ebony_mushroom", () -> new MushroomBlock(ModBlocks.CORRUPT_GRASS_BLOCK.get()));//魔菇
    // TODO: 发光蘑菇可以长在天花板上，可以长很长；天花板上的属于藤蔓，还没做
    public static final RegistryObject<Block> GLOWING_MUSHROOM = registerWithoutItem("glowing_mushroom", () -> new MushroomBlock(ModBlocks.MUSHROOM_GRASS_BLOCK.get()));//发光蘑菇
    public static final RegistryObject<Block> LIFE_MUSHROOM = registerWithoutItem("life_mushroom", () -> new MushroomBlock(Blocks.GRASS_BLOCK, HALLOW_GRASS_BLOCK.get()));//生命蘑菇
    public static final RegistryObject<JungleSporeBlock> JUNGLE_SPORE = registerWithoutItem("jungle_spore", JungleSporeBlock::new);
    // 云块
    public static final RegistryObject<Block> CLOUD_BLOCK = registerWithItem("cloud_block", CloudBlock::new);
    public static final RegistryObject<Block> RAIN_CLOUD_BLOCK = registerWithItem("rain_cloud_block", () -> new ParticleCloudBlock(ParticleTypes.FALLING_WATER));
    public static final RegistryObject<Block> SNOW_CLOUD_BLOCK = registerWithItem("snow_cloud_block", () -> new ParticleCloudBlock(ParticleTypes.SNOWFLAKE));
    public static final RegistryObject<Block> EVAPORATIVE_CLOUD_BLOCK = registerWithItem("evaporative_cloud_block", EvaporativeCloudBlock::new);
    // 草药
    public static final RegistryObject<BaseHerbBlock> WATERLEAF = registerWithoutItem("waterleaf", Waterleaf::new);//幌菊
    public static final RegistryObject<FlameFlower> FLAMEFLOWERS = registerWithoutItem("flameflowers", FlameFlower::new);//火焰花
    public static final RegistryObject<MoonshineGrass> MOONSHINE_GRASS = registerWithoutItem("moonshine_grass", MoonshineGrass::new);//月光草
    public static final RegistryObject<StellarBlossom> STELLAR_BLOSSOM = registerWithoutItem("stellar_blossom_stage0", StellarBlossom::new);//星辰花
    public static final RegistryObject<BaseHerbBlock> SHINE_ROOT = registerWithoutItem("shine_root", ShineRoot::new);//闪耀根
    public static final RegistryObject<BaseHerbBlock> SHIVERINGTHORNS = registerWithoutItem("shiveringthorns", ShiveringThorn::new);//寒颤棘
    public static final RegistryObject<BaseHerbBlock> SUNFLOWERS = registerWithoutItem("sunflowers", SunFlower::new);//太阳花
    public static final RegistryObject<DeathWeed> DEATHWEED = registerWithoutItem("deathweed", DeathWeed::new);//死亡草
    public static final RegistryObject<BlockEntityType<BaseHerbBlock.Entity>> HERBS_ENTITY = BLOCK_ENTITIES.register("herbs_entity", () -> BlockEntityType.Builder.of(BaseHerbBlock.Entity::new,
        WATERLEAF.get(), FLAMEFLOWERS.get(), MOONSHINE_GRASS.get(), STELLAR_BLOSSOM.get(), SHINE_ROOT.get(), SHIVERINGTHORNS.get(), SUNFLOWERS.get(), DEATHWEED.get()).build(null));
    // grass
    public static final RegistryObject<Block> CORRUPT_GRASS = registerWithItem("corrupt_grass", () -> new BasePlantBlock(ModBlocks.CORRUPT_GRASS_BLOCK.get()));//腐化草
    public static final RegistryObject<Block> TR_CRIMSON_GRASS = registerWithItem("tr_crimson_grass", () -> new BasePlantBlock(ModBlocks.TR_CRIMSON_GRASS_BLOCK.get()));//猩红草
    public static final RegistryObject<Block> HALLOW_GRASS = registerWithItem("hallow_grass", () -> new BasePlantBlock(HALLOW_GRASS_BLOCK.get()));//神圣草
    public static final RegistryObject<Block> ASH_GRASS = registerWithItem("ash_grass", () -> new BasePlantBlock(ASH_GRASS_BLOCK.get()));
    public static final RegistryObject<Block> NATURES_GIFT = registerWithoutItem("natures_gift", NaturesGiftBlock::new);
    // TODO: shape，不可放置
    public static final RegistryObject<Block> JUNGLE_ROSE = registerWithItem("jungle_rose", () -> new BasePlantBlock(Blocks.GRASS_BLOCK, Blocks.MOSS_BLOCK, Blocks.CLAY));

    public static final RegistryObject<ThornBlock> CRIMSON_THORN = registerWithItem("crimson_thorn", () -> new SpreadingThornBlock(2, TR_CRIMSON_GRASS_BLOCK.get(), ISpreadable.Type.CRIMSON));
    public static final RegistryObject<ThornBlock> CORRUPTION_THORN = registerWithItem("corruption_thorn", () -> new SpreadingThornBlock(2, CORRUPT_GRASS_BLOCK.get(), ISpreadable.Type.CORRUPT));
    public static final RegistryObject<ThornBlock> JUNGLE_THORN = registerWithItem("jungle_thorn", () -> new ThornBlock(3.4f, Blocks.MOSS_BLOCK));
    public static final RegistryObject<ThornBlock> PLANTERA_THORN = registerWithItem("plantera_thorn", () -> new ThornBlock(20, null));
    // 丛林蜂巢
    public static final RegistryObject<Block> JUNGLE_HIVE_BLOCK = registerWithItem("jungle_hive_block", JungleHiveBlock::new);
    //变种蜂蜜块
    public static final RegistryObject<Block> THIN_HONEY_BLOCK = registerWithItem("thin_honey_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HONEY_BLOCK)));
    public static final RegistryObject<Block> LOOSE_HONEY_BLOCK = registerWithItem("loose_honey_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HONEY_BLOCK)));
    // 红石变种
    public static final RegistryObject<Block> SANCTIFICATION_REDSTONE_ORE = registerWithItem("sanctification_redstone_ore", CustomModelBlock::new);
    public static final RegistryObject<Block> CORRUPTION_REDSTONE_ORE = registerWithItem("corruption_redstone_ore", CustomModelBlock::new);
    public static final RegistryObject<Block> FLESHIFICATION_REDSTONE_ORE = registerWithItem("fleshification_redstone_ore", CustomModelBlock::new);

    // 石中剑
    public static final RegistryObject<Block> SWORD_IN_STONE = registerWithItem("sword_in_stone", SwordInStoneBlock::new);
    // 宝石树
    public static final RegistryObject<Block> STONY_LOGS = registerWithItem("stony_logs", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BLACK).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RUBY_BRANCHES = registerWithItem("ruby_branches", () -> new BranchesBlock(ModBlocks.STONY_LOGS.get()));
    public static final RegistryObject<Block> AMBER_BRANCHES = registerWithItem("amber_branches", () -> new BranchesBlock(ModBlocks.STONY_LOGS.get()));
    public static final RegistryObject<Block> TOPAZ_BRANCHES = registerWithItem("topaz_branches", () -> new BranchesBlock(ModBlocks.STONY_LOGS.get()));
    public static final RegistryObject<Block> EMERALD_BRANCHES = registerWithItem("emerald_branches", () -> new BranchesBlock(ModBlocks.STONY_LOGS.get()));
    public static final RegistryObject<Block> DIAMOND_BRANCHES = registerWithItem("diamond_branches", () -> new BranchesBlock(ModBlocks.STONY_LOGS.get()));
    public static final RegistryObject<Block> SAPPHIRE_BRANCHES = registerWithItem("sapphire_branches", () -> new BranchesBlock(ModBlocks.STONY_LOGS.get()));
    public static final RegistryObject<Block> TR_AMETHYST_BRANCHES = registerWithItem("tr_amethyst_branches", () -> new BranchesBlock(ModBlocks.STONY_LOGS.get()));
    public static final RegistryObject<Block> ASH_BRANCHES = registerWithItem("ash_branches", () -> new BranchesBlock(ModBlocks.ASH_LOG_BLOCKS.LOG.get()));
    // 血肉眼球块
    public static final RegistryObject<Block> OCULAR_BLOCKS = registerWithItem("ocular_blocks", CustomModelBlock::new);
    // 深板岩压力板
    public static final RegistryObject<PressurePlateBlock> DEEPSLATE_PRESSURE_PLATE = registerWithItem("deepslate_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.STONE_PRESSURE_PLATE).mapColor(MapColor.DEEPSLATE).strength(0.1F), BlockSetType.STONE));
    // 地牢碎砖
    public static final RegistryObject<CrackedBrickBlock> CRACKED_BLUE_BRICK = registerWithItem("cracked_blue_brick", CrackedBrickBlock::new);
    public static final RegistryObject<CrackedBrickBlock> CRACKED_GREEN_BRICK = registerWithItem("cracked_green_brick", CrackedBrickBlock::new);
    public static final RegistryObject<CrackedBrickBlock> CRACKED_PINK_BRICK = registerWithItem("cracked_pink_brick", CrackedBrickBlock::new);
    // 镒块
    public static final RegistryObject<Block> AETHERIUM_BLOCK = registerWithItem("aetherium_block", CustomModelBlock::new);
    public static final RegistryObject<BlockEntityType<SignBlockEntity>> SIGN_BLOCK_ENTITY = BLOCK_ENTITIES.register("sign_block_entity", () -> BlockEntityType.Builder.of(SignBlockEntity::new, LogBlocks.getSignBlocks()).build(null));
    public static final RegistryObject<Block> DARK_AETHERIUM_BLOCK = registerWithItem("dark_aetherium_block", CustomModelBlock::new);
    // 箱子
    public static final RegistryObject<BaseChestBlock> BASE_CHEST_BLOCK = registerWithItem("base_chest_block", BaseChestBlock::new);
    public static final RegistryObject<BlockEntityType<BaseChestBlock.Entity>> BASE_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("base_chest_block_entity", () -> BlockEntityType.Builder.of(BaseChestBlock.Entity::new, BASE_CHEST_BLOCK.get()).build(null));
    public static final RegistryObject<DeathChestBlock> DEATH_CHEST_BLOCK = registerWithItem("death_chest_block", DeathChestBlock::new);
    public static final RegistryObject<BlockEntityType<DeathChestBlock.Entity>> DEATH_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("death_chest_block_entity", () -> BlockEntityType.Builder.of(DeathChestBlock.Entity::new, DEATH_CHEST_BLOCK.get()).build(null));
    // 片
    public static final RegistryObject<CoinPileBlock> COPPER_COIN_PILE = registerWithoutItem("copper_coin_pile", CoinPileBlock::new);
    public static final RegistryObject<CoinPileBlock> SILVER_COIN_PILE = registerWithoutItem("silver_coin_pile", CoinPileBlock::new);
    public static final RegistryObject<CoinPileBlock> GOLDEN_COIN_PILE = registerWithoutItem("golden_coin_pile", CoinPileBlock::new);
    public static final RegistryObject<CoinPileBlock> PLATINUM_COIN_PILE = registerWithoutItem("platinum_coin_pile", CoinPileBlock::new);
    public static final RegistryObject<Block> EBONY_SAND_LAYER_BLOCK = registerWithItem("ebony_sand_layer_block", SandLayerBlock::new);
    public static final RegistryObject<Block> PEARL_SAND_LAYER_BLOCK = registerWithItem("pearl_sand_layer_block", SandLayerBlock::new);
    public static final RegistryObject<Block> TR_CRIMSON_SAND_LAYER_BLOCK = registerWithItem("tr_crimson_sand_layer_block", SandLayerBlock::new);
    public static final RegistryObject<Block> SAND_LAYER_BLOCK = registerWithItem("sand_layer_block", SandLayerBlock::new);
    public static final RegistryObject<Block> RED_SAND_LAYER_BLOCK = registerWithItem("red_sand_layer_block", SandLayerBlock::new);
    public static final RegistryObject<WhitePlasticChairBlock> WHITE_PLASTIC_CHAIR_BLOCK = registerWithItem("white_plastic_chair", WhitePlasticChairBlock::new, supplier -> () -> new WhitePlasticChairBlock.Item(supplier.get()));
    public static final RegistryObject<BlockEntityType<WhitePlasticChairBlock.Entity>> WHITE_PLASTIC_CHAIR_ENTITY = BLOCK_ENTITIES.register("white_plastic_chair_entity", () -> BlockEntityType.Builder.of(WhitePlasticChairBlock.Entity::new, WHITE_PLASTIC_CHAIR_BLOCK.get()).build(null));

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