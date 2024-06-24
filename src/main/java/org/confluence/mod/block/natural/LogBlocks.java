package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
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
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.Supplier;

import static org.confluence.mod.block.ModBlocks.registerWithItem;
import static org.confluence.mod.block.ModBlocks.registerWithoutItem;

public final class LogBlocks {
    public static final ArrayList<LogBlocks> LOG_BLOCKS = new ArrayList<>();
    private static final Hashtable<Supplier<RotatedPillarBlock>, Supplier<RotatedPillarBlock>> STRIP_TABLE = new Hashtable<>();
    public static final Hashtable<Block, Block> WRAPPED_STRIP = new Hashtable<>();

    public final String id;
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

    public static void acceptAxeTag(IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tag) {
        for (LogBlocks logBlocks : LOG_BLOCKS) {
            tag.add(logBlocks.PLANKS.get());
            if (logBlocks.LOG != null) {
                tag.add(logBlocks.LOG.get());
            }
            if (logBlocks.STRIPPED_LOG != null) {
                tag.add(logBlocks.STRIPPED_LOG.get());
            }
            if (logBlocks.WOOD != null) {
                tag.add(logBlocks.WOOD.get());
            }
            if (logBlocks.STRIPPED_WOOD != null) {
                tag.add(logBlocks.STRIPPED_WOOD.get());
            }
            tag.add(logBlocks.BUTTON.get());
            tag.add(logBlocks.FENCE.get());
            tag.add(logBlocks.FENCE_GATE.get());
            tag.add(logBlocks.PRESSURE_PLATE.get());
            tag.add(logBlocks.SLAB.get());
            tag.add(logBlocks.STAIRS.get());
            tag.add(logBlocks.SIGN.get());
            tag.add(logBlocks.TRAPDOOR.get());
            tag.add(logBlocks.DOOR.get());
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
