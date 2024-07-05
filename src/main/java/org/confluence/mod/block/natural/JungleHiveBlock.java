package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class JungleHiveBlock extends Block implements CustomModel {

    public JungleHiveBlock() {
        super(BlockBehaviour.Properties.of());
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        Random random = new Random();
        int randomNumber = random.nextInt(2);
        if (randomNumber == 0 && level instanceof ServerLevel && !player.isCreative()) {
            level.setBlockAndUpdate(pos, ModBlocks.HONEY.get().defaultBlockState());
        } else if (randomNumber == 1 && level instanceof ServerLevel && !player.isCreative()){
            Entity Beeentity = EntityType.BEE.create(level);
            if (Beeentity != null){
                Beeentity.setPos(pos.getX() + 1, pos.getY() + 3, pos.getZ() + 1);
                level.addFreshEntity(Beeentity);
            }
        } else if (level instanceof ServerLevel && player.isCreative()){
            level.setBlockAndUpdate(pos, ModBlocks.HONEY.get().defaultBlockState());
        }
    }
}