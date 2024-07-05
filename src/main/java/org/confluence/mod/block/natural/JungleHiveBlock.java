package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class JungleHiveBlock extends Block  implements CustomModel {

    public JungleHiveBlock() {
        super(BlockBehaviour.Properties.of());
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        if (level instanceof ServerLevel && !player.isCreative()) {
            level.setBlockAndUpdate(pos, ModBlocks.HONEY.get().defaultBlockState());
        }else {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }
}