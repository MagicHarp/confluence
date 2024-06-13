package org.confluence.mod.block.functional;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
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

public class BoulderBlock extends Block implements CustomModel, CustomItemModel {
    public BoulderBlock() {
        super(Properties.of());
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void playerDestroy(@NotNull Level pLevel, Player pPlayer, BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
        Vec3 position = new Vec3(pPos.getX() + 0.5, pPos.getY(), pPos.getZ() + 0.5);
        BoulderEntity entity = new BoulderEntity(pLevel, position);
        Vec3 vec3 = pPlayer.position().subtract(position).normalize();
        entity.setYRot(((float) Mth.atan2(vec3.z, vec3.x)) * Mth.PI);
        entity.setDeltaMovement(vec3.scale(BoulderEntity.SPEED));
        pLevel.addFreshEntity(entity);
    }
}
