package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.confluence.mod.datagen.limit.CustomItemModel;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.entity.projectile.BoulderEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class BoulderBlock extends Block implements CustomModel, CustomItemModel {
    public BoulderBlock() {
        super(Properties.of());
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
        summon(pLevel, pPos, entity -> pPlayer);
    }

    @Override
    public void wasExploded(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Explosion pExplosion) {
        summon(pLevel, pPos, entity -> pLevel.getNearestPlayer(entity, BoulderEntity.SEARCH_RANGE));
    }

    public static void summon(Level level, BlockPos pos, Function<BoulderEntity, Player> function) {
        Vec3 position = new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        BoulderEntity entity = new BoulderEntity(level, position);
        if (!level.getBlockState(pos.below()).isAir()) {
            entity.targetTo(function.apply(entity));
        }
        level.addFreshEntity(entity);
    }
}
