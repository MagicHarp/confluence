package org.confluence.mod.item.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class GrassSeedItem extends Item {
    Set<Block> blockSet;
    Block targetBlock;

    public GrassSeedItem(Set<Block> blocks, Block targetBlock) {
        super(new Properties());
        this.blockSet = blocks;
        this.targetBlock = targetBlock;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockSet.contains(level.getBlockState(pos).getBlock())) {
            level.setBlock(pos, targetBlock.defaultBlockState(), 11);
            pContext.getItemInHand().shrink(1);
        }
        return super.useOn(pContext);
    }
}
