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
import static org.confluence.mod.block.ConfluenceBlocks.registerWithoutItem;

public class DecorationLogBlocks {
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
    public final RegistryObject<StandingSignBlock> SIGN;
    public final RegistryObject<WallSignBlock> WALL_SIGN;
    public final RegistryObject<TrapDoorBlock> TRAPDOOR;
    public final RegistryObject<DoorBlock> DOOR;

    public DecorationLogBlocks(String id, BlockSetType blockSetType, WoodType woodType) {
        this.LOG = registerWithItem(id + "_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.PLANKS = registerWithItem(id + "_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.BUTTON = registerWithItem(id + "_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), blockSetType, 30, true));
        this.FENCE = registerWithItem(id + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.FENCE_GATE = registerWithItem(id + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava(), woodType));
        this.LEAVES = registerWithItem(id + "_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(DecorationLogBlocks::valid).isSuffocating(DecorationLogBlocks::never).isViewBlocking(DecorationLogBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(DecorationLogBlocks::never)));
        this.PRESSURE_PLATE = registerWithItem(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), blockSetType));
        this.SLAB = registerWithItem(id + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.STAIRS = registerWithItem(id + "_stairs", () -> new StairBlock(() -> PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(PLANKS.get())));
        this.WOOD = registerWithItem(id + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.SIGN = registerWithItem(id + "_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava(), woodType));
        this.WALL_SIGN = registerWithoutItem(id + "_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).lootFrom(SIGN).ignitedByLava(), woodType));
        this.TRAPDOOR = registerWithItem(id + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(DecorationLogBlocks::never).ignitedByLava(), blockSetType));
        this.DOOR = registerWithItem(id + "_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), blockSetType));
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
}
