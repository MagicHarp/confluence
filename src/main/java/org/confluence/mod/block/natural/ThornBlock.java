package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.misc.ModDamageTypes;
import org.jetbrains.annotations.NotNull;

public class ThornBlock extends PipeBlock implements CustomModel {
    private final float amount;
    private static final IntegerProperty PROP_AGE = IntegerProperty.create("age", 0, 7);

    public ThornBlock(float amount){
        super(0.3125f, BlockBehaviour.Properties.of().replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).randomTicks());
        this.amount = amount;
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Entity pEntity){
        if(pLevel.isClientSide())return;
        if(pEntity instanceof ServerPlayer player){
            player.hurt(ModDamageTypes.of(pLevel,ModDamageTypes.THORN),amount);
            pLevel.destroyBlock(pPos, false);
        }
        if(pEntity instanceof Projectile){
            pLevel.destroyBlock(pPos, false);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, PROP_AGE);
        super.createBlockStateDefinition(pBuilder);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState pState){
        return pState.getValue(PROP_AGE) < 7;
    }

    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom){
        // TODO: 降低概率
        Direction dir = Direction.getRandom(pRandom);
        BlockPos targetPos = pPos.relative(dir);
        if(!pLevel.getBlockState(targetPos).isAir() || checkBorder(pLevel,targetPos,dir))return;


        int age = pState.getValue(PROP_AGE);
        pLevel.setBlockAndUpdate(targetPos, defaultBlockState().setValue(PROP_AGE, Math.min(7, age + pRandom.nextInt(1, 3))));
        pLevel.setBlockAndUpdate(pPos, defaultBlockState().setValue(PROP_AGE, pRandom.nextBoolean() ? 7 : age + 1));
    }

    private boolean checkBorder(Level level, BlockPos pos, Direction direction){
        for(Direction value : Direction.values()){
            if(value==direction || value==direction.getOpposite())continue;
            if(level.getBlockState(pos.relative(value)).is(this)){
                return true;
            }
        }
        return false;
    }

    @Override
    @NotNull
    public BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos){
        return pState.setValue(PROPERTY_BY_DIRECTION.get(pDirection), pNeighborState.is(this));
    }
}
