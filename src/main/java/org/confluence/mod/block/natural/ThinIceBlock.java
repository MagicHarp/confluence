package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThinIceBlock extends IceBlock {
    public ThinIceBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE));
    }

    @Override
    public void updateEntityAfterFallOn(@NotNull BlockGetter blockGetter, @NotNull Entity entity) {
    }

    @Override
    public void playerDestroy(Level p_54157_, Player player, BlockPos p_54159_, BlockState p_54160_, @Nullable BlockEntity p_54161_, ItemStack p_54162_) {
        player.awardStat(Stats.BLOCK_MINED.get(this));
        player.causeFoodExhaustion(0.005F);
    }

    public interface IceSafe {
    }
}
