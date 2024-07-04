package org.confluence.mod.block.natural.spreadable;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.command.ConfluenceData;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;
import java.util.Map;
import java.util.function.Supplier;

public interface ISpreadable {
    // That was a joke haha!
    BooleanProperty STILL_ALIVE = BooleanProperty.create("still_alive");

    Type getType();

    default void spread(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource){
        if(!blockState.getValue(STILL_ALIVE)) return;
        int phase = ConfluenceData.get(serverLevel).getGamePhase().ordinal();
        for(int i = 0; i < 4; ++i){
            BlockPos targetPos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
            BlockState beforeTransformState = serverLevel.getBlockState(targetPos);
            Block targetBlock = getType().blockMap.get(beforeTransformState.getBlock());
            if(targetBlock == null || beforeTransformState.is(Blocks.GRASS) || beforeTransformState.is(Blocks.FERN) || beforeTransformState.is(Blocks.TALL_GRASS)){
                continue; // 不要直接传播草
            }
            if(beforeTransformState.is(Blocks.DIRT) || beforeTransformState.is(ModBlocks.ASH_BLOCK.get())){
                if(!isFullBlock(serverLevel, targetPos.above())){
                    spreadOrDie(phase, blockState, serverLevel, blockPos, randomSource, targetBlock.defaultBlockState(), targetPos);
                }
            }else{
                spreadOrDie(phase, blockState, serverLevel, blockPos, randomSource, targetBlock.defaultBlockState(), targetPos);
            }
            BlockState above = serverLevel.getBlockState(targetPos.above());
            if(above.is(Blocks.GRASS) || above.is(Blocks.FERN) || above.is(Blocks.TALL_GRASS)){  // 被动传播草
                targetBlock = getType().blockMap.get(above.getBlock());
                serverLevel.setBlockAndUpdate(targetPos.above(), targetBlock == null ? above : targetBlock.defaultBlockState());
            }

        }
    }

    static boolean isFullBlock(ServerLevel serverLevel, BlockPos pos) {
        return Block.isShapeFullBlock(serverLevel.getBlockState(pos).getCollisionShape(serverLevel, pos));
    }

    static void spreadOrDie(int phase, BlockState selfState, ServerLevel serverLevel, BlockPos selfPos, RandomSource randomSource, BlockState targetState, BlockPos targetPos) {
        serverLevel.setBlockAndUpdate(targetPos, targetState);
        if (randomSource.nextInt(7) > phase) {
            serverLevel.setBlockAndUpdate(selfPos, selfState.setValue(STILL_ALIVE, false));
        }
    }

    // 到时候溶液也用这个
    enum Type {
        HALLOW(
            () -> Blocks.DIRT, ModBlocks.HALLOW_GRASS_BLOCK,
            () -> Blocks.DIRT_PATH, ModBlocks.HALLOW_GRASS_BLOCK,
            () -> Blocks.GRASS_BLOCK, ModBlocks.HALLOW_GRASS_BLOCK,
            () -> Blocks.STONE, ModBlocks.PEARL_STONE,
            () -> Blocks.COBBLESTONE, ModBlocks.PEARL_COBBLESTONE,
            () -> Blocks.SANDSTONE, ModBlocks.PEARL_SANDSTONE,
            () -> Blocks.SAND, ModBlocks.PEARL_SAND,
            () -> Blocks.GRASS, ModBlocks.HALLOW_GRASS,
            () -> Blocks.TALL_GRASS, ModBlocks.HALLOW_GRASS,
            ModBlocks.ANOTHER_CRIMSON_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.EBONY_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.CORRUPT_GRASS, ModBlocks.HALLOW_GRASS,
            ModBlocks.ANOTHER_CRIMSON_GRASS, ModBlocks.HALLOW_GRASS,
            ModBlocks.CRIMSON_THORN, () -> Blocks.AIR,
            ModBlocks.CORRUPTION_THORN, () -> Blocks.AIR,
            ModBlocks.JUNGLE_THORN, () -> Blocks.AIR,
            ModBlocks.PLANTERA_THORN, () -> Blocks.AIR
        ),

        CRIMSON(
            () -> Blocks.DIRT, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            () -> Blocks.DIRT_PATH, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            () -> Blocks.GRASS_BLOCK, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            () -> Blocks.STONE, ModBlocks.ANOTHER_CRIMSON_STONE,
            () -> Blocks.COBBLESTONE, ModBlocks.ANOTHER_CRIMSON_COBBLESTONE,
            () -> Blocks.SANDSTONE, ModBlocks.ANOTHER_CRIMSON_SANDSTONE,
            () -> Blocks.SAND, ModBlocks.ANOTHER_CRIMSON_SAND,
            () -> Blocks.GRASS, ModBlocks.ANOTHER_CRIMSON_GRASS,
            ModBlocks.EBONY_MUSHROOM, ModBlocks.ANOTHER_CRIMSON_MUSHROOM,
            ModBlocks.GLOWING_MUSHROOM, ModBlocks.ANOTHER_CRIMSON_MUSHROOM,
            ModBlocks.LIFE_MUSHROOM, ModBlocks.ANOTHER_CRIMSON_MUSHROOM,
            ModBlocks.JUNGLE_SPORE, ModBlocks.ANOTHER_CRIMSON_GRASS,
            ModBlocks.CORRUPT_GRASS, ModBlocks.ANOTHER_CRIMSON_GRASS,
            ModBlocks.HALLOW_GRASS, ModBlocks.ANOTHER_CRIMSON_GRASS,
            ModBlocks.CORRUPTION_THORN, ModBlocks.CRIMSON_THORN,
            ModBlocks.JUNGLE_THORN, ModBlocks.CRIMSON_THORN,
            ModBlocks.PLANTERA_THORN, ModBlocks.CRIMSON_THORN
        ),

        CORRUPT(
            () -> Blocks.DIRT, ModBlocks.CORRUPT_GRASS_BLOCK,
            () -> Blocks.DIRT_PATH, ModBlocks.CORRUPT_GRASS_BLOCK,
            () -> Blocks.GRASS_BLOCK, ModBlocks.CORRUPT_GRASS_BLOCK,
            () -> Blocks.STONE, ModBlocks.EBONY_STONE,
            () -> Blocks.COBBLESTONE, ModBlocks.EBONY_COBBLESTONE,
            () -> Blocks.SANDSTONE, ModBlocks.EBONY_SANDSTONE,
            () -> Blocks.SAND, ModBlocks.EBONY_SAND,
            () -> Blocks.GRASS, ModBlocks.CORRUPT_GRASS,
            () -> Blocks.TALL_GRASS, ModBlocks.CORRUPT_GRASS,
            () -> Blocks.FERN, ModBlocks.CORRUPT_GRASS,
            ModBlocks.ANOTHER_CRIMSON_MUSHROOM, ModBlocks.EBONY_MUSHROOM,
            ModBlocks.LIFE_MUSHROOM, ModBlocks.EBONY_MUSHROOM,
            ModBlocks.JUNGLE_SPORE, ModBlocks.CORRUPT_GRASS,
            ModBlocks.ANOTHER_CRIMSON_GRASS, ModBlocks.CORRUPT_GRASS,
            ModBlocks.HALLOW_GRASS, ModBlocks.CORRUPT_GRASS,
            ModBlocks.CRIMSON_THORN, ModBlocks.CORRUPTION_THORN,
            ModBlocks.JUNGLE_THORN, ModBlocks.CORRUPTION_THORN,
            ModBlocks.PLANTERA_THORN, ModBlocks.CORRUPTION_THORN
        ),

        GLOWING(
            () -> Blocks.MUD, ModBlocks.MUSHROOM_GRASS_BLOCK,
            ModBlocks.JUNGLE_SPORE, ModBlocks.GLOWING_MUSHROOM
        ),

        PURE(
            ModBlocks.ASH_BLOCK, ModBlocks.ASH_GRASS_BLOCK,
            () -> Blocks.MUD, ModBlocks.MUSHROOM_GRASS_BLOCK,
            ModBlocks.ANOTHER_CRIMSON_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.EBONY_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.CORRUPT_GRASS, () -> Blocks.GRASS,
            ModBlocks.ANOTHER_CRIMSON_GRASS, () -> Blocks.GRASS,
            ModBlocks.HALLOW_GRASS, () -> Blocks.GRASS,
            ModBlocks.ANOTHER_CRIMSON_STONE, () -> Blocks.STONE,
            ModBlocks.ANOTHER_CRIMSON_SAND, () -> Blocks.SAND,
            ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK, () -> Blocks.GRASS_BLOCK,
            ModBlocks.CORRUPT_GRASS_BLOCK, () -> Blocks.GRASS_BLOCK,
            ModBlocks.HALLOW_GRASS_BLOCK, () -> Blocks.GRASS_BLOCK,
            ModBlocks.CRIMSON_THORN, () -> Blocks.AIR,
            ModBlocks.CORRUPTION_THORN, () -> Blocks.AIR
        );

        private Map<Supplier<? extends Block>, Supplier<? extends Block>> supplierMap;
        private Map<Block, Block> blockMap;

        @SafeVarargs
        Type(Supplier<? extends Block>... suppliers) {
            if (suppliers.length % 2 != 0) throw new RuntimeException("Not enough suppliers!");
            Hashtable<Supplier<? extends Block>, Supplier<? extends Block>> map = new Hashtable<>();
            for (int i = 0; i < suppliers.length / 2; i++) {
                int j = i * 2;
                map.put(suppliers[j], suppliers[j + 1]);
            }
            this.supplierMap = map;
        }

        public Map<Block, Block> getBlockMap() {
            return blockMap;
        }

        public static void buildMap() {
            for (Type type : values()) {
                ImmutableMap.Builder<Block, Block> map = ImmutableMap.builder();
                type.supplierMap.forEach((s1, s2) -> map.put(s1.get(), s2.get()));
                type.blockMap = map.build();
                type.supplierMap = null;
            }
        }
    }
}
