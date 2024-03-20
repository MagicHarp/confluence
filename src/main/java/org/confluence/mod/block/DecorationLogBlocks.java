package org.confluence.mod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

import static org.confluence.mod.block.ConfluenceBlocks.registerWithItem;
import static org.confluence.mod.block.ConfluenceBlocks.registerWithoutItem;

public class DecorationLogBlocks {
    public static final ArrayList<DecorationLogBlocks> DECORATION_LOG_BLOCKS = new ArrayList<>();

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
    public final RegistryObject<TrapDoorBlock> TRAPDOOR;
    public final RegistryObject<DoorBlock> DOOR;


    public DecorationLogBlocks(String id, BlockSetType blockSetType, WoodType woodType, boolean requiresTree, boolean ignitedByLava) {
        this.id = id;
        BlockBehaviour.Properties planks = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD);
        this.PLANKS = registerWithItem(id + "_planks", () -> new Block(ignitedByLava ? planks.ignitedByLava() : planks));
        if (requiresTree) {
            BlockBehaviour.Properties log = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD);
            BlockBehaviour.Properties finalLog = ignitedByLava ? log.ignitedByLava() : log;
            this.LOG = registerWithItem(id + "_log", () -> new RotatedPillarBlock(finalLog));
            this.STRIPPED_LOG = registerWithItem(id + "_stripped_log", () -> new RotatedPillarBlock(finalLog));
            BlockBehaviour.Properties leaves = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(DecorationLogBlocks::valid).isSuffocating(DecorationLogBlocks::never).isViewBlocking(DecorationLogBlocks::never).pushReaction(PushReaction.DESTROY).isRedstoneConductor(DecorationLogBlocks::never);
            this.LEAVES = registerWithItem(id + "_leaves", () -> new LeavesBlock(ignitedByLava ? leaves.ignitedByLava() : leaves));
            BlockBehaviour.Properties wood = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD);
            BlockBehaviour.Properties finalWood = ignitedByLava ? wood.ignitedByLava() : wood;
            this.WOOD = registerWithItem(id + "_wood", () -> new RotatedPillarBlock(finalWood));
            this.STRIPPED_WOOD = registerWithItem(id + "_stripped_wood", () -> new RotatedPillarBlock(finalWood));
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
        this.SIGN = registerWithItem(id + "_sign", () -> new StandingSignBlock(ignitedByLava ? sign.ignitedByLava() : sign, woodType));
        BlockBehaviour.Properties wall_sign = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).lootFrom(SIGN);
        this.WALL_SIGN = registerWithoutItem(id + "_wall_sign", () -> new WallSignBlock(ignitedByLava ? wall_sign.ignitedByLava() : wall_sign, woodType));
        BlockBehaviour.Properties trapdoor = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(DecorationLogBlocks::never);
        this.TRAPDOOR = registerWithItem(id + "_trapdoor", () -> new TrapDoorBlock(ignitedByLava ? trapdoor.ignitedByLava() : trapdoor, blockSetType));
        BlockBehaviour.Properties door = BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY);
        this.DOOR = registerWithItem(id + "_door", () -> new DoorBlock(ignitedByLava ? door.mapColor(PLANKS.get().defaultMapColor()).ignitedByLava() : door.mapColor(PLANKS.get().defaultMapColor()), blockSetType));

        DECORATION_LOG_BLOCKS.add(this);
    }

    public DecorationLogBlocks(String id, WoodSetType woodSetType) {
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
        for (DecorationLogBlocks decorationLogBlocks : DECORATION_LOG_BLOCKS) {
            output.accept(decorationLogBlocks.PLANKS.get());
            if (decorationLogBlocks.STRIPPED_LOG != null) {
                output.accept(decorationLogBlocks.STRIPPED_LOG.get());
            }
            if (decorationLogBlocks.WOOD != null) {
                output.accept(decorationLogBlocks.WOOD.get());
            }
            if (decorationLogBlocks.STRIPPED_WOOD != null) {
                output.accept(decorationLogBlocks.STRIPPED_WOOD.get());
            }
            output.accept(decorationLogBlocks.BUTTON.get());
            output.accept(decorationLogBlocks.FENCE.get());
            output.accept(decorationLogBlocks.FENCE_GATE.get());
            output.accept(decorationLogBlocks.PRESSURE_PLATE.get());
            output.accept(decorationLogBlocks.SLAB.get());
            output.accept(decorationLogBlocks.STAIRS.get());
            output.accept(decorationLogBlocks.SIGN.get());
            output.accept(decorationLogBlocks.TRAPDOOR.get());
            output.accept(decorationLogBlocks.DOOR.get());
        }
    }
}
