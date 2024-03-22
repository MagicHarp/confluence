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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.entity.ActuatorsBlockEntity;
import org.confluence.mod.datagen.CustomModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ActuatorsBlock extends Block implements EntityBlock, CustomModel {
    public ActuatorsBlock() {
        super(BlockBehaviour.Properties.of().noOcclusion());
        registerDefaultState(stateDefinition.any()
            .setValue(StateProperties.DRIVE, true)
            .setValue(StateProperties.VISIBLE, false)
        );
    }

    public boolean propagatesSkylightDown(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(StateProperties.DRIVE, StateProperties.VISIBLE);
    }

    public float getShadeBrightness(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
        return 1.0F;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new ActuatorsBlockEntity(blockPos, blockState);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult blockHitResult) {
        if (!level.isClientSide) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (itemStack.getItem() instanceof BlockItem blockItem) {
                Block block = blockItem.getBlock();
                Vec3 view = player.getViewVector(1.0F);
                BlockState resultState = block.getStateForPlacement(new BlockPlaceContext(level, player, hand, itemStack, new BlockHitResult(view, Direction.getNearest(view.x, view.y, view.z), blockPos, true)));
                if (resultState != null && block.getRenderShape(resultState) != RenderShape.ENTITYBLOCK_ANIMATED && level.getBlockEntity(blockPos) instanceof ActuatorsBlockEntity actuatorsBlockEntity) {
                    actuatorsBlockEntity.setContain(resultState);
                    actuatorsBlockEntity.markUpdated();
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
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
            level.scheduleTick(blockPos, this, 4);
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
            return actuatorsBlockEntity.getContain().getShape(blockGetter, blockPos, context);
        } else {
            return Shapes.block();
        }
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        return getShape(blockState, blockGetter, blockPos, context);
    }
}
