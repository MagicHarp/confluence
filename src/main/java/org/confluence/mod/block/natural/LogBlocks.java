package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.ModBlockTagsProvider;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import static org.confluence.mod.block.ModBlocks.registerWithItem;
import static org.confluence.mod.block.ModBlocks.registerWithoutItem;

public final class LogBlocks {
    public static final ArrayList<LogBlocks> LOG_BLOCKS = new ArrayList<>();
    private static final Hashtable<Supplier<RotatedPillarBlock>, Supplier<RotatedPillarBlock>> STRIP_TABLE = new Hashtable<>();
    public static final Hashtable<Block, Block> WRAPPED_STRIP = new Hashtable<>();

    public final String id;
    public final boolean ignitedByLava;
    public final RegistryObject<Block> PLANKS;
    public RegistryObject<RotatedPillarBlock> LOG;
    public RegistryObject<RotatedPillarBlock> STRIPPED_LOG;
    public RegistryObject<LeavesBlock> LEAVES;
    public RegistryObject<RotatedPillarBlock> WOOD;
    public RegistryObject<RotatedPillarBlock> STRIPPED_WOOD;
    public final RegistryObject<ButtonBlock> BUTTON;
    public final RegistryObject<FenceBlock> FENCE;
    public final RegistryObject<FenceGateBlock> FENCE_GATE;
    public final RegistryObject<PressurePlateBlock> PRESSURE_PLATE;
    public final RegistryObject<SlabBlock> SLAB;
    public final RegistryObject<StairBlock> STAIRS;
    public final RegistryObject<StandingSignBlock> SIGN;
    public final RegistryObject<WallSignBlock> WALL_SIGN;
    public final RegistryObject<SignItem> SIGN_ITEM;
    public final RegistryObject<TrapDoorBlock> TRAPDOOR;
    public final RegistryObject<DoorBlock> DOOR;


    public LogBlocks(String id, BlockSetType blockSetType, WoodType woodType, boolean requiresTree, boolean ignitedByLava) {
        this.id = id;
        this.ignitedByLava = ignitedByLava;
        BlockBehaviour.Properties planks = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD);
        this.PLANKS = registerWithItem(id + "_planks", () -> new Block(ignitedByLava ? planks.ignitedByLava() : planks));
        if (requiresTree) {
            BlockBehaviour.Properties log = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD);
            BlockBehaviour.Properties finalLog = ignitedByLava ? log.ignitedByLava() : log;
            this.LOG = registerWithItem(id + "_log", () -> new RotatedPillarBlock(finalLog));
            this.STRIPPED_LOG = registerWithItem(id + "_stripped_log", () -> new RotatedPillarBlock(finalLog));
            BlockBehaviour.Properties leaves = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(LogBlocks::valid).isSuffocating(LogBlocks::never).isViewBlocking(LogBlocks::never).pushReaction(PushReaction.DESTROY).isRedstoneConductor(LogBlocks::never);
            this.LEAVES = registerWithItem(id + "_leaves", () -> new LeavesBlock(ignitedByLava ? leaves.ignitedByLava() : leaves));
            BlockBehaviour.Properties wood = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD);
            BlockBehaviour.Properties finalWood = ignitedByLava ? wood.ignitedByLava() : wood;
            this.WOOD = registerWithItem(id + "_wood", () -> new RotatedPillarBlock(finalWood));
            this.STRIPPED_WOOD = registerWithItem(id + "_stripped_wood", () -> new RotatedPillarBlock(finalWood));

            STRIP_TABLE.put(LOG, STRIPPED_LOG);
            STRIP_TABLE.put(WOOD, STRIPPED_WOOD);

        }
        this.BUTTON = registerWithItem(id + "_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), blockSetType, 30, true));
        BlockBehaviour.Properties fence = BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD);
        this.FENCE = registerWithItem(id + "_fence", () -> new FenceBlock(ignitedByLava ? fence.mapColor(PLANKS.get().defaultMapColor()).ignitedByLava() : fence.mapColor(PLANKS.get().defaultMapColor())));
        BlockBehaviour.Properties fence_gate = BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F);
        this.FENCE_GATE = registerWithItem(id + "_fence_gate", () -> new FenceGateBlock(ignitedByLava ? fence_gate.mapColor(PLANKS.get().defaultMapColor()).ignitedByLava() : fence_gate.mapColor(PLANKS.get().defaultMapColor()), woodType));
        BlockBehaviour.Properties pressure_plate = BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        this.PRESSURE_PLATE = registerWithItem(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, ignitedByLava ? pressure_plate.mapColor(PLANKS.get().defaultMapColor()).ignitedByLava() : pressure_plate.mapColor(PLANKS.get().defaultMapColor()), blockSetType));
        BlockBehaviour.Properties slab = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD);
        this.SLAB = registerWithItem(id + "_slab", () -> new SlabBlock(ignitedByLava ? slab.ignitedByLava() : slab));
        this.STAIRS = registerWithItem(id + "_stairs", () -> new StairBlock(() -> PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PLANKS.get())));

        BlockBehaviour.Properties sign = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F);
        this.SIGN = registerWithoutItem(id + "_sign", () -> new ModStandingSignBlock(ignitedByLava ? sign.ignitedByLava() : sign, woodType));
        BlockBehaviour.Properties wall_sign = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).lootFrom(SIGN);
        this.WALL_SIGN = registerWithoutItem(id + "_wall_sign", () -> new ModWallSignBlock(ignitedByLava ? wall_sign.ignitedByLava() : wall_sign, woodType));
        this.SIGN_ITEM = ModItems.ITEMS.register(id + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), SIGN.get(), WALL_SIGN.get()));

        BlockBehaviour.Properties trapdoor = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(LogBlocks::never);
        this.TRAPDOOR = registerWithItem(id + "_trapdoor", () -> new TrapDoorBlock(ignitedByLava ? trapdoor.ignitedByLava() : trapdoor, blockSetType));
        BlockBehaviour.Properties door = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY);
        this.DOOR = registerWithItem(id + "_door", () -> new DoorBlock(ignitedByLava ? door.mapColor(PLANKS.get().defaultMapColor()).ignitedByLava() : door.mapColor(PLANKS.get().defaultMapColor()), blockSetType));

        LOG_BLOCKS.add(this);
    }

    public LogBlocks(String id, WoodSetType woodSetType) {
        this(id, woodSetType.SET, woodSetType.TYPE, true, true);
    }

    public LogBlocks(String id, WoodSetType woodSetType, Function<BlockBehaviour.Properties, Supplier<LeavesBlock>> customLeaves) {
        this.id = id;
        this.ignitedByLava = true;
        this.PLANKS = registerWithItem(id + "_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        BlockBehaviour.Properties log = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
        this.LOG = registerWithItem(id + "_log", () -> new RotatedPillarBlock(log));
        this.STRIPPED_LOG = registerWithItem(id + "_stripped_log", () -> new RotatedPillarBlock(log));
        this.LEAVES = registerWithItem(id + "_leaves", customLeaves.apply(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(LogBlocks::valid).isSuffocating(LogBlocks::never).isViewBlocking(LogBlocks::never).pushReaction(PushReaction.DESTROY).isRedstoneConductor(LogBlocks::never).ignitedByLava()));
        BlockBehaviour.Properties wood = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava();
        this.WOOD = registerWithItem(id + "_wood", () -> new RotatedPillarBlock(wood));
        this.STRIPPED_WOOD = registerWithItem(id + "_stripped_wood", () -> new RotatedPillarBlock(wood));

        STRIP_TABLE.put(LOG, STRIPPED_LOG);
        STRIP_TABLE.put(WOOD, STRIPPED_WOOD);

        this.BUTTON = registerWithItem(id + "_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), woodSetType.SET, 30, true));
        this.FENCE = registerWithItem(id + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).mapColor(PLANKS.get().defaultMapColor()).ignitedByLava()));
        this.FENCE_GATE = registerWithItem(id + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).mapColor(PLANKS.get().defaultMapColor()).ignitedByLava(), woodSetType.TYPE));
        this.PRESSURE_PLATE = registerWithItem(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY).mapColor(PLANKS.get().defaultMapColor()).ignitedByLava(), woodSetType.SET));
        this.SLAB = registerWithItem(id + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.STAIRS = registerWithItem(id + "_stairs", () -> new StairBlock(() -> PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PLANKS.get())));

        this.SIGN = registerWithoutItem(id + "_sign", () -> new ModStandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), woodSetType.TYPE));
        this.WALL_SIGN = registerWithoutItem(id + "_wall_sign", () -> new ModWallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).lootFrom(SIGN).ignitedByLava(), woodSetType.TYPE));
        this.SIGN_ITEM = ModItems.ITEMS.register(id + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), SIGN.get(), WALL_SIGN.get()));

        this.TRAPDOOR = registerWithItem(id + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(LogBlocks::never).ignitedByLava(), woodSetType.SET));
        this.DOOR = registerWithItem(id + "_door", () -> new DoorBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY).mapColor(PLANKS.get().defaultMapColor()).ignitedByLava(), woodSetType.SET));

        LOG_BLOCKS.add(this);
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean valid(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static void acceptBuilding(CreativeModeTab.Output output) {
        for (LogBlocks logBlocks : LOG_BLOCKS) {
            output.accept(logBlocks.PLANKS.get());
            if (logBlocks.STRIPPED_LOG != null) {
                output.accept(logBlocks.STRIPPED_LOG.get());
            }
            if (logBlocks.WOOD != null) {
                output.accept(logBlocks.WOOD.get());
            }
            if (logBlocks.STRIPPED_WOOD != null) {
                output.accept(logBlocks.STRIPPED_WOOD.get());
            }
            output.accept(logBlocks.BUTTON.get());
            output.accept(logBlocks.FENCE.get());
            output.accept(logBlocks.FENCE_GATE.get());
            output.accept(logBlocks.PRESSURE_PLATE.get());
            output.accept(logBlocks.SLAB.get());
            output.accept(logBlocks.STAIRS.get());
            output.accept(logBlocks.SIGN_ITEM.get());
            output.accept(logBlocks.TRAPDOOR.get());
            output.accept(logBlocks.DOOR.get());
        }
    }

    public static void acceptTags(ModBlockTagsProvider provider) {
        IntrinsicTagAppender<Block> completes = provider.tag(BlockTags.COMPLETES_FIND_TREE_TUTORIAL);
        IntrinsicTagAppender<Block> burn = provider.tag(BlockTags.LOGS_THAT_BURN);
        IntrinsicTagAppender<Block> logs = provider.tag(BlockTags.LOGS);
        IntrinsicTagAppender<Block> planks = provider.tag(BlockTags.PLANKS);
        IntrinsicTagAppender<Block> buttons = provider.tag(BlockTags.BUTTONS);
        IntrinsicTagAppender<Block> woodenButtons = provider.tag(BlockTags.WOODEN_BUTTONS);
        IntrinsicTagAppender<Block> stairs = provider.tag(BlockTags.STAIRS);
        IntrinsicTagAppender<Block> woodenStairs = provider.tag(BlockTags.WOODEN_STAIRS);
        IntrinsicTagAppender<Block> slabs = provider.tag(BlockTags.SLABS);
        IntrinsicTagAppender<Block> woodenSlabs = provider.tag(BlockTags.WOODEN_SLABS);
        IntrinsicTagAppender<Block> fences = provider.tag(BlockTags.FENCES);
        IntrinsicTagAppender<Block> woodenFences = provider.tag(BlockTags.WOODEN_FENCES);
        IntrinsicTagAppender<Block> forgeFences = provider.tag(Tags.Blocks.FENCES);
        IntrinsicTagAppender<Block> forgeFencesWooden = provider.tag(Tags.Blocks.FENCES_WOODEN);
        IntrinsicTagAppender<Block> fenceGates = provider.tag(BlockTags.FENCE_GATES);
        IntrinsicTagAppender<Block> forgeFenceGates = provider.tag(Tags.Blocks.FENCE_GATES);
        IntrinsicTagAppender<Block> forgeFenceGatesWooden = provider.tag(Tags.Blocks.FENCE_GATES_WOODEN);
        IntrinsicTagAppender<Block> trapdoors = provider.tag(BlockTags.TRAPDOORS);
        IntrinsicTagAppender<Block> woodenTrapdoors = provider.tag(BlockTags.WOODEN_TRAPDOORS);
        IntrinsicTagAppender<Block> woodenPressurePlates = provider.tag(BlockTags.WOODEN_PRESSURE_PLATES);
        IntrinsicTagAppender<Block> doors = provider.tag(BlockTags.DOORS);
        IntrinsicTagAppender<Block> woodenDoors = provider.tag(BlockTags.WOODEN_DOORS);
        IntrinsicTagAppender<Block> leaves = provider.tag(BlockTags.LEAVES);
        IntrinsicTagAppender<Block> signs = provider.tag(BlockTags.SIGNS);
        for (LogBlocks logBlocks : LOG_BLOCKS) {
            planks.add(logBlocks.PLANKS.get());

            if (logBlocks.LOG != null) {
                RotatedPillarBlock value = logBlocks.LOG.get();
                completes.add(value);
                if (logBlocks.ignitedByLava) burn.add(value);
                logs.add(value);
            }
            if (logBlocks.STRIPPED_LOG != null) {
                RotatedPillarBlock value = logBlocks.STRIPPED_LOG.get();
                completes.add(value);
                if (logBlocks.ignitedByLava) burn.add(value);
                logs.add(value);
            }
            if (logBlocks.WOOD != null) {
                RotatedPillarBlock value = logBlocks.WOOD.get();
                completes.add(value);
                if (logBlocks.ignitedByLava) burn.add(value);
                logs.add(value);
            }
            if (logBlocks.STRIPPED_WOOD != null) {
                RotatedPillarBlock value = logBlocks.STRIPPED_WOOD.get();
                completes.add(value);
                if (logBlocks.ignitedByLava) burn.add(value);
                logs.add(value);
            }
            if (logBlocks.LEAVES != null) {
                LeavesBlock leavesBlock = logBlocks.LEAVES.get();
                completes.add(leavesBlock);
                leaves.add(leavesBlock);
            }

            ButtonBlock buttonBlock = logBlocks.BUTTON.get();
            buttons.add(buttonBlock);
            woodenButtons.add(buttonBlock);

            FenceBlock fenceBlock = logBlocks.FENCE.get();
            fences.add(fenceBlock);
            woodenFences.add(fenceBlock);
            forgeFences.add(fenceBlock);
            forgeFencesWooden.add(fenceBlock);

            FenceGateBlock fenceGateBlock = logBlocks.FENCE_GATE.get();
            fenceGates.add(fenceGateBlock);
            forgeFenceGates.add(fenceGateBlock);
            forgeFenceGatesWooden.add(fenceGateBlock);

            PressurePlateBlock pressurePlateBlock = logBlocks.PRESSURE_PLATE.get();
            woodenPressurePlates.add(pressurePlateBlock);

            SlabBlock slabBlock = logBlocks.SLAB.get();
            slabs.add(slabBlock);
            woodenSlabs.add(slabBlock);

            StairBlock stairBlock = logBlocks.STAIRS.get();
            stairs.add(stairBlock);
            woodenStairs.add(stairBlock);

            signs.add(logBlocks.SIGN.get());

            TrapDoorBlock trapDoorBlock = logBlocks.TRAPDOOR.get();
            trapdoors.add(trapDoorBlock);
            woodenTrapdoors.add(trapDoorBlock);

            DoorBlock doorBlock = logBlocks.DOOR.get();
            doors.add(doorBlock);
            woodenDoors.add(doorBlock);
        }
    }

    public static void wrapStrip() {
        STRIP_TABLE.forEach((s1, s2) -> WRAPPED_STRIP.put(s1.get(), s2.get()));
        STRIP_TABLE.clear();
    }

    private static SignBlock[] SIGN_BLOCKS;

    public static SignBlock[] getSignBlocks() {
        if (SIGN_BLOCKS == null) {
            SignBlock[] signBlocks = new SignBlock[LOG_BLOCKS.size() * 2];
            for (int i = 0; i < LOG_BLOCKS.size(); i++) {
                LogBlocks logBlocks = LOG_BLOCKS.get(i);
                signBlocks[i * 2] = logBlocks.SIGN.get();
                signBlocks[i * 2 + 1] = logBlocks.WALL_SIGN.get();
            }
            SIGN_BLOCKS = signBlocks;
        }
        return SIGN_BLOCKS;
    }

    public static class ModSignBlockEntity extends SignBlockEntity {
        public ModSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
            super(ModBlocks.SIGN_BLOCK_ENTITY.get(), pPos, pBlockState);
        }
    }

    public static class ModStandingSignBlock extends StandingSignBlock {
        public ModStandingSignBlock(Properties pProperties, WoodType pType) {
            super(pProperties, pType);
        }

        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
            return new ModSignBlockEntity(pPos, pState);
        }
    }

    public static class ModWallSignBlock extends WallSignBlock {
        public ModWallSignBlock(Properties pProperties, WoodType pType) {
            super(pProperties, pType);
        }

        @Override
        public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
            return new ModSignBlockEntity(pPos, pState);
        }
    }

    public enum WoodSetType {
        EBONY("ebony"),
        PEARL("pearl"),
        SHADOW("shadow"),
        PALM("palm"),
        ASH("ash"),
        SPOOKY("spooky");

        public final BlockSetType SET;
        public final WoodType TYPE;

        WoodSetType(String id) {
            id = Confluence.MODID + ":" + id;
            this.SET = BlockSetType.register(new BlockSetType(id));
            this.TYPE = WoodType.register(new WoodType(id, SET));
        }
    }
}
