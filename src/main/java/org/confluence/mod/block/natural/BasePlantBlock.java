package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.natural.spreadable.ISpreadable;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BasePlantBlock extends BushBlock implements CustomModel, CustomItemModel {
    private final Block[] survive;

    public BasePlantBlock(Block... survive) {
        super(BlockBehaviour.Properties.copy(Blocks.DANDELION).replaceable());
        this.survive = survive;
    }

    public BasePlantBlock(Properties prop, Block... survive) {
        super(prop);
        this.survive = survive;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(world, pos);
        return box(2, 0, 2, 14, 13, 14).move(offset.x, offset.y, offset.z);
    }

    @Override
    public boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
        return Arrays.stream(survive).allMatch(state -> state == groundState.getBlock());
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockstate, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState groundState = worldIn.getBlockState(blockpos);
        return mayPlaceOn(groundState, worldIn, blockpos);
    }

    /**
     * 邪恶草和蘑菇下方的方块被转化时转化自身
     */
    @Override
    @NotNull
    public BlockState updateShape(@NotNull BlockState originState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        BlockState after = super.updateShape(originState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        if (pFacing != Direction.DOWN) return after;
        ISpreadable.Type type = pFacingState.getBlock() instanceof ISpreadable sp ? sp.getType() : ISpreadable.Type.PURE;
        Block b = type.getBlockMap().get(originState.getBlock());
        BlockState transformResult = b == null ? originState : b.defaultBlockState();  // 默认不转化，如果结果是摧毁则是写到map里面
        return transformResult.canSurvive(pLevel, pCurrentPos) ? transformResult : Blocks.AIR.defaultBlockState();
    }
}
