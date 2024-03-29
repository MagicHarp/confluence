package org.confluence.mod.block.natural;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.jetbrains.annotations.NotNull;

public interface ISpreadable {
    // That was a joke haha!
    BooleanProperty STILL_ALIVE = BooleanProperty.create("still_alive");

    Type getType();

    default void spread(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!blockState.getValue(STILL_ALIVE)) return;
        for (int i = 0; i < 4; ++i) {
            BlockPos pos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
            BlockState source = serverLevel.getBlockState(pos);
            RegistryObject<Block> target = getType().blockMap.get(source.getBlock());
            if (target == null) continue;
            if (source.is(Blocks.DIRT)) {
                if (!isFullBlock(serverLevel, pos.above())) {
                    spreadOrDie(blockState, serverLevel, blockPos, randomSource, target.get().defaultBlockState(), pos);
                }
            } else {
                spreadOrDie(blockState, serverLevel, blockPos, randomSource, target.get().defaultBlockState(), pos);
            }
        }
    }

    static boolean isFullBlock(ServerLevel serverLevel, BlockPos pos) {
        return Block.isShapeFullBlock(serverLevel.getBlockState(pos).getCollisionShape(serverLevel, pos));
    }

    static void spreadOrDie(BlockState selfState, ServerLevel serverLevel, BlockPos selfPos, RandomSource randomSource, BlockState targetState, BlockPos targetPos) {
        int phase = serverLevel.getServer().getGameRules().getInt(Confluence.GAME_PHASE);
        if (phase == 0) return;
        if (randomSource.nextFloat() < 0.01 * phase) {
            serverLevel.setBlockAndUpdate(targetPos, targetState);
        } else if (phase > 2) {
            serverLevel.setBlockAndUpdate(selfPos, selfState.setValue(STILL_ALIVE, false));
        }
    }

    enum Type {
        HALLOW(ImmutableMap.of(
            Blocks.DIRT, ModBlocks.HALLOW_GRASS_BLOCK,
            Blocks.DIRT_PATH, ModBlocks.HALLOW_GRASS_BLOCK,
            Blocks.GRASS_BLOCK, ModBlocks.HALLOW_GRASS_BLOCK
            /* todo 其它东西 */
        )),
        CRIMSON(ImmutableMap.of(
            Blocks.DIRT, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            Blocks.DIRT_PATH, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            Blocks.GRASS_BLOCK, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK
        )),
        CORRUPT(ImmutableMap.of(
            Blocks.DIRT, ModBlocks.CORRUPT_GRASS_BLOCK,
            Blocks.DIRT_PATH, ModBlocks.CORRUPT_GRASS_BLOCK,
            Blocks.GRASS_BLOCK, ModBlocks.CORRUPT_GRASS_BLOCK
        )),
        GLOWING(ImmutableMap.of(
            Blocks.MUD, ModBlocks.GLOWING_GRASS_BLOCK
        ));

        public final ImmutableMap<Block, RegistryObject<Block>> blockMap;

        Type(ImmutableMap<Block, RegistryObject<Block>> blockMap) {
            this.blockMap = blockMap;
        }
    }

//    static ImmutableMap<Block, RegistryObject<Block>> createGlowing() {
//        ImmutableMap.Builder<Block, RegistryObject<Block>> builder = new ImmutableMap.Builder<>();
//        builder.put(Blocks.MUD, ModBlocks.GLOWING_GRASS_BLOCK);
//        return builder.build();
//    }
}
