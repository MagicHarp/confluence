package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.block.entity.ActuatorsBlockEntity;
import org.confluence.mod.datagen.CustomItemModel;
import org.confluence.mod.datagen.CustomModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class ActuatorsBlock extends HalfTransparentBlock implements EntityBlock, IMechanical, CustomModel, CustomItemModel {
    public ActuatorsBlock() {
        super(BlockBehaviour.Properties.of()
            .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false)
            .isRedstoneConductor((blockState, blockGetter, blockPos) -> true)
        );
        registerDefaultState(stateDefinition.any()
            .setValue(StateProperties.DRIVE, false)
            .setValue(StateProperties.VISIBLE, false)
        );
    }

    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return blockState.getValue(StateProperties.DRIVE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(StateProperties.DRIVE, StateProperties.VISIBLE);
    }

    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        if (blockGetter.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
            return isCollisionShapeFullBlock(actuatorsBlockEntity.getContain(), blockGetter, blockPos) ? 0.2F : 1.0F;
        }
        return 0.2F;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new ActuatorsBlockEntity(blockPos, blockState);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult blockHitResult) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof ActuatorsBlock) return InteractionResult.PASS;
            Vec3 view = player.getViewVector(1.0F);
            BlockState resultState = block.getStateForPlacement(new BlockPlaceContext(level, player, hand, itemStack, new BlockHitResult(view, Direction.getNearest(view.x, view.y, view.z).getOpposite(), blockPos, false)));
            if (resultState != null && block.getRenderShape(resultState) == RenderShape.MODEL && level.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
                actuatorsBlockEntity.setContain(resultState);
                actuatorsBlockEntity.markUpdated();
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    public void neighborChanged(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Block block, @NotNull BlockPos blockPos1, boolean b) {
        if (level.isClientSide) return;
        boolean drive = blockState.getValue(StateProperties.DRIVE);
        if (drive == level.hasNeighborSignal(blockPos)) return;
        if (drive) {
            level.scheduleTick(blockPos, this, 1);
        } else {
            level.setBlock(blockPos, blockState.cycle(StateProperties.DRIVE), Block.UPDATE_CLIENTS);
        }
    }

    public void tick(BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (blockState.getValue(StateProperties.DRIVE) && !serverLevel.hasNeighborSignal(blockPos)) {
            serverLevel.setBlock(blockPos, blockState.cycle(StateProperties.DRIVE), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState blockState, BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (blockGetter.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
            BlockState target = actuatorsBlockEntity.getContain();
            if (target.is(ConfluenceBlocks.ACTUATORS.get())) return Shapes.block();
            return target.getShape(blockGetter, blockPos, context);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getVisualShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (blockGetter.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
            BlockState target = actuatorsBlockEntity.getContain();
            if (target.is(ConfluenceBlocks.ACTUATORS.get())) return Shapes.block();
            return target.getVisualShape(blockGetter, blockPos, context);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        if (blockGetter.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
            BlockState target = actuatorsBlockEntity.getContain();
            if (target.is(ConfluenceBlocks.ACTUATORS.get())) return Shapes.block();
            return target.getBlockSupportShape(blockGetter, blockPos);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        if (blockGetter.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
            BlockState target = actuatorsBlockEntity.getContain();
            if (target.is(ConfluenceBlocks.ACTUATORS.get())) return Shapes.block();
            return target.getOcclusionShape(blockGetter, blockPos);
        }
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (!blockState.getValue(StateProperties.DRIVE)) {
            if (blockGetter.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
                BlockState target = actuatorsBlockEntity.getContain();
                if (target.is(ConfluenceBlocks.ACTUATORS.get())) return Shapes.block();
                return target.getCollisionShape(blockGetter, blockPos);
            }
        }
        return Shapes.empty();
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull PathComputationType type) {
        return !blockState.getValue(StateProperties.DRIVE);
    }
}
