package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.TRIGGERED;

@SuppressWarnings("deprecation")
public class DartTrapBlock extends AbstractMechanicalBlock implements CustomModel {
    private final ItemStack poisonArrow;

    public DartTrapBlock() {
        super(Properties.copy(Blocks.DISPENSER));
        this.poisonArrow = PotionUtils.setCustomEffects(new ItemStack(Items.TIPPED_ARROW), Set.of(new MobEffectInstance(MobEffects.POISON, 600, 1)));
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, false));
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TRIGGERED);
    }

    @Override
    public void neighborChanged(@NotNull BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Block pNeighborBlock, @NotNull BlockPos pNeighborPos, boolean pMovedByPiston) {
        if (!pLevel.isClientSide) {
            execute(pState, (ServerLevel) pLevel, pPos, pLevel.hasNeighborSignal(pPos));
        }
    }

    @Override
    public void onExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        if (!pState.getValue(TRIGGERED)) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(TRIGGERED, true));
            Direction direction = pState.getValue(FACING);
            double x = pPos.getX() + 0.5 + 0.7 * direction.getStepX();
            double y = pPos.getY() + 0.5 + 0.7 * direction.getStepY();
            double z = pPos.getZ() + 0.5 + 0.7 * direction.getStepZ();
            Arrow arrow = new Arrow(pLevel, x, y, z);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            arrow.setEffectsFromItem(poisonArrow);
            arrow.setBaseDamage(5.0);
            arrow.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 3.0F, 0.0F);
            pLevel.addFreshEntity(arrow);
        }
    }

    @Override
    public void onUnExecute(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        if (pState.getValue(TRIGGERED)) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(TRIGGERED, false));
        }
    }
}
