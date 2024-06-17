package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** @author voila1106 */
public abstract class BaseHerbBlock extends CropBlock implements CustomModel, CustomItemModel, EntityBlock {
    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    // TODO: 按实际情况修改
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)};

    public BaseHerbBlock(){
        super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks());
    }

    @Override
    public abstract boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos);

    public boolean canBloom(ServerLevel world, BlockState state){
        return false;
    }

    // 重写，不检查光照，不检查合理密植
    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        int age = getAge(pState);
        if(age < getMaxAge() - 1){
            // 抄的父方法
            if(!pLevel.isAreaLoaded(pPos, 1)) return;
            float f = 25F;   /* getGrowthSpeed(this, pLevel, pPos); */
            if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int) (25.0F / f) + 1) == 0)){
                pLevel.setBlock(pPos, this.getStateForAge(age + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, boolean pIsClient){
        return false;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState blockstate, LevelReader worldIn, BlockPos pos){
        BlockPos blockpos = pos.below();
        BlockState groundState = worldIn.getBlockState(blockpos);
        return this.mayPlaceOn(groundState, worldIn, blockpos);
    }

    @Override
    @NotNull
    protected ItemLike getBaseSeedId(){
        return Items.AIR;
    }

    @NotNull
    protected IntegerProperty getAgeProperty(){
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(AGE);
    }

    public int getMaxAge(){
        return MAX_AGE;
    }

    @NotNull
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext){
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState){
        return new Entity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType){
        if(pLevel.isClientSide()){
            return null;
        }
        return ModUtils.getTicker(pBlockEntityType, ModBlocks.HERBS_ENTITY.get(), (level, blockPos, blockState, e) -> {
            int age = getAge(pState);
            if(age < MAX_AGE - 1) return;
            if(canBloom((ServerLevel) pLevel, blockState)){
                pLevel.setBlock(blockPos, pState.setValue(AGE, MAX_AGE), 2);
            }else if(age == MAX_AGE){ // 如果不能开花且已经开花
                pLevel.setBlock(blockPos, pState.setValue(AGE, MAX_AGE - 1), 2);
            }
        });
    }

    public static class Entity extends BlockEntity {

        public Entity(BlockPos pPos, BlockState pBlockState){
            super(ModBlocks.HERBS_ENTITY.get(), pPos, pBlockState);
        }
    }
}
