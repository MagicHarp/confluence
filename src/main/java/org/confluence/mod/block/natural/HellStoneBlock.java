package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;


public class HellStoneBlock extends Block {

    public HellStoneBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.ANCIENT_DEBRIS).mapColor(MapColor.COLOR_RED).lightLevel(value -> 5).requiresCorrectToolForDrops());
    }

    public void stepOn(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.hurt(level.damageSources().hotFloor(), 2.5F);
            entity.setSecondsOnFire(3);
        }
        super.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState blockState) {
        if (level instanceof ServerLevel) {
            ((ServerLevel) level).setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
        }
    }
}
