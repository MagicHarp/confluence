package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.confluence.mod.misc.ModDamageTypes;
import org.jetbrains.annotations.NotNull;

public class BaseThornBlock extends BushBlock {
    private final float amount;

    public BaseThornBlock(float amount){
        super(BlockBehaviour.Properties.of().replaceable().noCollission().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
        this.amount = amount;
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
    protected boolean mayPlaceOn(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos){
        return true;
    }
}
