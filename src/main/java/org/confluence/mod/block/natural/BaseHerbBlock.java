package org.confluence.mod.block.natural;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/** @author voila1106 */
public abstract class BaseHerbBlock extends CropBlock implements CustomModel, CustomItemModel, EntityBlock {
    public static final int MAX_AGE = 2;
    public static final int BRIGHTNESS = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)};
    private static final Map<RegistryObject<? extends Block>, RegistryObject<? extends Block>> groundHerbMap = new ImmutableMap.Builder<RegistryObject<? extends Block>, RegistryObject<? extends Block>>()
        .put(getRegistry(Blocks.GRASS_BLOCK), ModBlocks.SUNFLOWERS)
        .put(ModBlocks.HALLOW_GRASS_BLOCK, ModBlocks.SUNFLOWERS)
        // TODO: 丛林草
        .put(getRegistry(Blocks.DIRT), ModBlocks.SHINE_ROOT)
        .put(getRegistry(Blocks.MUD), ModBlocks.SHINE_ROOT)
        .put(ModBlocks.CORRUPT_GRASS_BLOCK, ModBlocks.DEATHWEED)
        .put(ModBlocks.EBONY_STONE, ModBlocks.DEATHWEED)
        .put(ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK, ModBlocks.DEATHWEED)
        .put(ModBlocks.ANOTHER_CRIMSON_STONE, ModBlocks.DEATHWEED)
        // TODO: 邪恶丛林草
        .put(getRegistry(Blocks.SAND), ModBlocks.WATERLEAF)
        .put(ModBlocks.PEARL_SAND, ModBlocks.WATERLEAF)
        .put(ModBlocks.ASH_BLOCK, ModBlocks.FLAMEFLOWERS)
        // TODO: 灰烬草
        .put(getRegistry(Blocks.SNOW_BLOCK), ModBlocks.SHIVERINGTHORNS)
        .put(getRegistry(Blocks.ICE), ModBlocks.SHIVERINGTHORNS)
        .build();

    public BaseHerbBlock(){
        super(BlockBehaviour.Properties.copy(Blocks.DANDELION).randomTicks());
    }

    public BaseHerbBlock(Properties prop){
        super(prop);
    }

    @Override
    public abstract boolean mayPlaceOn(@NotNull BlockState groundState, @NotNull BlockGetter worldIn, @NotNull BlockPos pos);

    public boolean canBloom(ServerLevel world, BlockState state){
        return false;
    }

    // 重写，不检查光照，不检查合理密植
    // TODO: 部分草药有粒子
    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        int age = getAge(pState);
        if(age < getMaxAge() - 1){
            // 抄的父方法
            if(!pLevel.isAreaLoaded(pPos, 1)) return;
            float f = 0.7F;   /* getGrowthSpeed(this, pLevel, pPos); */
            if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int) (25.0F / f) + 1) == 0)){
                pLevel.setBlockAndUpdate(pPos, this.getStateForAge(age + 1));
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
        // 每刻判断能不能开花
        return ModUtils.getTicker(pBlockEntityType, ModBlocks.HERBS_ENTITY.get(), (level, blockPos, blockState, e) -> {
            int age = getAge(blockState);
            if(age < MAX_AGE - 1) return;
            if(canBloom((ServerLevel) level, blockState)){
                if(age != MAX_AGE){
                    level.setBlockAndUpdate(blockPos, blockState.setValue(AGE, MAX_AGE));
                }
            }else if(age == MAX_AGE){ // 如果不能开花且已经开花
                level.setBlockAndUpdate(blockPos, blockState.setValue(AGE, MAX_AGE - 1));
            }
        });
    }

    private static RegistryObject<? extends Block> getRegistry(Block block){
        return RegistryObject.create(ForgeRegistries.BLOCKS.getKey(block), ForgeRegistries.BLOCKS);
    }

    public static void growNewHerb(BlockState groundState, BlockPos groundPos, Level level){
        RegistryObject<? extends Block> target = groundHerbMap.get(getRegistry(groundState.getBlock()));
        if(target != null && level.getBlockState(groundPos.above()).isAir() && !BaseHerbBlock.hasHerbInRange(level, groundPos)){
            level.setBlockAndUpdate(groundPos.above(), target.get().defaultBlockState());
        }
    }

    private static boolean hasHerbInRange(Level level, BlockPos center){
        int radius = 30;
        int startX = center.getX() - radius;
        int startY = center.getY() - radius;
        int startZ = center.getZ() - radius;
        int endX = center.getX() + radius;
        int endY = center.getY() + radius;
        int endZ = center.getZ() + radius;

        for(int x = startX; x <= endX; x++){
            for(int y = startY; y <= endY; y++){
                for(int z = startZ; z <= endZ; z++){
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if(state.getBlock() instanceof BaseHerbBlock){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static class Entity extends BlockEntity {

        public Entity(BlockPos pPos, BlockState pBlockState){
            super(ModBlocks.HERBS_ENTITY.get(), pPos, pBlockState);
        }
    }
}
