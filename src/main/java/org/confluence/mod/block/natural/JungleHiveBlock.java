package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.entity.projectile.BeeProjectile;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class JungleHiveBlock extends Block implements CustomModel {

    public JungleHiveBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.BEEHIVE));
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        if(level.isClientSide || player.getAbilities().instabuild) return;
        int randomNumber = level.random.nextInt(3);
        if (randomNumber == 0) {
            level.setBlockAndUpdate(pos, ModBlocks.HONEY.get().defaultBlockState());
        } else if (randomNumber == 1) {
            Entity Beeentity = new BeeProjectile(level, null, false);
            Beeentity.setPos(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
            level.addFreshEntity(Beeentity);
        } else if (randomNumber == 2) {
            super.playerDestroy(level, player, pos, state, blockEntity, tool);
        } else {
            level.setBlockAndUpdate(pos, ModBlocks.HONEY.get().defaultBlockState());
        }
    }
}
