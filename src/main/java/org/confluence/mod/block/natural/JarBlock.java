package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JarBlock extends Block implements CustomModel {

    public JarBlock(Properties pProperties) {
        super(pProperties);
    }
}
