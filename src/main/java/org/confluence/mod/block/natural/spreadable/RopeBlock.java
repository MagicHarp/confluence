package org.confluence.mod.block.natural.spreadable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.SculkSensorBlock.WATERLOGGED;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.BOTTOM;

public class RopeBlock extends Block implements CustomModel, CustomItemModel {
    private static final VoxelShape TOP_COLLISION = Block.box(5,15,5,11,16,11);
    public RopeBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SCAFFOLDING).pushReaction(PushReaction.IGNORE));
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return box(7, 0, 7, 9, 16, 9);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return pContext.isAbove(Shapes.block(), pPos, true) && !pContext.isDescending() ? TOP_COLLISION : Shapes.empty();
    }

    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED, BOTTOM);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

}
