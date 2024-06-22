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
import org.confluence.mod.block.natural.spreadable.ISpreadable;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BasePlantBlock extends BushBlock implements CustomModel, CustomItemModel {
    private final Set<Block> survive;

    public BasePlantBlock(Set<Block> survive){
        super(BlockBehaviour.Properties.copy(Blocks.DANDELION));
        this.survive = survive;
    }
    public BasePlantBlock(Set<Block> survive,Properties prop){
        super(prop);
        this.survive = survive;
    }

    @Override
    public boolean mayPlaceOn(BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos){
        return survive.contains(groundState.getBlock());
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockstate, LevelReader worldIn, BlockPos pos){
        BlockPos blockpos = pos.below();
        BlockState groundState = worldIn.getBlockState(blockpos);
        return this.mayPlaceOn(groundState, worldIn, blockpos);
    }

    /** 邪恶草和蘑菇下方的方块被转化时转化自身 */
    @Override
    @NotNull
    public BlockState updateShape(@NotNull BlockState originState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos){
        BlockState after = super.updateShape(originState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        if(pFacing != Direction.DOWN) return after;
        ISpreadable.Type type = pFacingState.getBlock() instanceof ISpreadable sp ? sp.getType() : ISpreadable.Type.PURE;
        Block b = type.getBlockMap().get(originState.getBlock());
        BlockState transformResult = b == null ? originState : b.defaultBlockState();  // 默认不转化，如果结果是摧毁则是写到map里面
        return transformResult.canSurvive(pLevel, pCurrentPos) ? transformResult : Blocks.AIR.defaultBlockState();
    }
}
