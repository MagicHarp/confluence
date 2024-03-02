package org.confluence.mod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
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

import static org.confluence.mod.block.ConfluenceBlocks.registerWithItem;

public class DecorationLogBlocks {
    public static final BlockSetType EBONY_SET = BlockSetType.register(new BlockSetType("ebony"));
    public static final WoodType EBONY_TYPE = WoodType.register(new WoodType("ebony", EBONY_SET));

    public final RegistryObject<RotatedPillarBlock> LOG;
    public final RegistryObject<Block> PLANKS;
    public final RegistryObject<ButtonBlock> BUTTON;
    public final RegistryObject<FenceBlock> FENCE;
    public final RegistryObject<FenceGateBlock> FENCE_GATE;
    public final RegistryObject<LeavesBlock> LEAVES;
    public final RegistryObject<PressurePlateBlock> PRESSURE_PLATE;
    public final RegistryObject<SlabBlock> SLAB;
    public final RegistryObject<StairBlock> STAIRS;
    public final RegistryObject<RotatedPillarBlock> WOOD;
    //public final RegistryObject<StandingSignBlock> SIGN;
    //public final RegistryObject<WallSignBlock> WALL_SIGN;
    //public final RegistryObject<TrapDoorBlock> TRAP_DOOR;
    //public final RegistryObject<DoorBlock> DOOR;

    public DecorationLogBlocks(String id) {
        this.LOG = registerWithItem(id + "_log", new RotatedPillarBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.PLANKS = registerWithItem(id + "_planks", new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.BUTTON = registerWithItem(id + "_button", new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), EBONY_SET, 30, true));
        this.FENCE = registerWithItem(id + "_fence", new FenceBlock(BlockBehaviour.Properties.of().mapColor(PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.FENCE_GATE = registerWithItem(id + "_fence_gate", new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), EBONY_TYPE));
        this.LEAVES = registerWithItem(id + "_leaves", new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(DecorationLogBlocks::valid).isSuffocating(DecorationLogBlocks::never).isViewBlocking(DecorationLogBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(DecorationLogBlocks::never)));
        this.PRESSURE_PLATE = registerWithItem(id + "_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), EBONY_SET));
        this.SLAB = registerWithItem(id + "_slab", new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.STAIRS = registerWithItem(id + "_stairs", new StairBlock(() -> PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PLANKS.get())));
        this.WOOD = registerWithItem(id + "_wood", new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static boolean valid(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }
}
