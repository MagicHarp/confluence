package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.fluid.ModFluids;
import org.confluence.mod.worldgen.biome.ModBiomes;

import java.util.Random;

public class JungleHoneycomBlock extends Block {

    public JungleHoneycomBlock() {super(BlockBehaviour.Properties.of());}

    @Override
    public void destroy(LevelAccessor level,BlockPos pos,BlockState blockState) {
        if (level instanceof ServerLevel){
            ((ServerLevel) level).setBlockAndUpdate(pos,ModBlocks.HONEY.get().defaultBlockState());
        }
    }
    }