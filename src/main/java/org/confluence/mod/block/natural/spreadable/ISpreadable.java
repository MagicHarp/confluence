package org.confluence.mod.block.natural.spreadable;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.command.ConfluenceData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface ISpreadable {
    // That was a joke haha!
    BooleanProperty STILL_ALIVE = BooleanProperty.create("still_alive");

    Type getType();

    default void spread(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
        if (!blockState.getValue(STILL_ALIVE)) return;
        int phase = ConfluenceData.get(serverLevel).getGamePhase().ordinal();
        for (int i = 0; i < 4; ++i) {
            BlockPos pos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
            BlockState source = serverLevel.getBlockState(pos);
            RegistryObject<? extends Block> target = getType().getMap().get(source.getBlock());
            if (target == null || source.is(Blocks.GRASS) || source.is(Blocks.FERN) || source.is(Blocks.TALL_GRASS)) continue; // 不要直接传播草
            if (source.is(Blocks.DIRT)) {
                if (!isFullBlock(serverLevel, pos.above())) {
                    spreadOrDie(phase, blockState, serverLevel, blockPos, randomSource, target.get().defaultBlockState(), pos);
                }
            } else {
                spreadOrDie(phase, blockState, serverLevel, blockPos, randomSource, target.get().defaultBlockState(), pos);
            }
            BlockState above = serverLevel.getBlockState(pos.above());
            if(above.is(Blocks.GRASS) || above.is(Blocks.FERN) || above.is(Blocks.TALL_GRASS)){  // 被动传播草
                target = getType().getMap().get(above.getBlock());
                serverLevel.setBlockAndUpdate(pos.above(), target == null ? above : target.get().defaultBlockState());
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

    enum Type {
        HALLOW(ImmutableMap.of(
            Blocks.DIRT, ModBlocks.HALLOW_GRASS_BLOCK,
            Blocks.DIRT_PATH, ModBlocks.HALLOW_GRASS_BLOCK,
            Blocks.GRASS_BLOCK, ModBlocks.HALLOW_GRASS_BLOCK,
            Blocks.STONE, ModBlocks.PEARL_STONE,
            Blocks.SAND, ModBlocks.PEARL_SAND,
            Blocks.GRASS,ModBlocks.HALLOW_GRASS,
            Blocks.TALL_GRASS,ModBlocks.HALLOW_GRASS,
            Blocks.FERN,ModBlocks.HALLOW_FLOWERS
        ),ImmutableMap.of(
            ModBlocks.ANOTHER_CRIMSON_MUSHROOM,ModBlocks.LIFE_MUSHROOM,
            ModBlocks.EBONY_MUSHROOM,ModBlocks.LIFE_MUSHROOM,
            ModBlocks.CORRUPT_GRASS,ModBlocks.HALLOW_GRASS,
            ModBlocks.ANOTHER_CRIMSON_HUNGRY_GHOST_GRASS,ModBlocks.HALLOW_GRASS,
            ModBlocks.ANOTHER_CRIMSON_EYEBALL_GRASS,ModBlocks.HALLOW_GRASS,
            ModBlocks.ANOTHER_CRIMSON_GRASS,ModBlocks.HALLOW_GRASS)),

        CRIMSON(ImmutableMap.of(
            Blocks.DIRT, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            Blocks.DIRT_PATH, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            Blocks.GRASS_BLOCK, ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK,
            Blocks.STONE, ModBlocks.ANOTHER_CRIMSON_STONE,
            Blocks.SAND, ModBlocks.ANOTHER_CRIMSON_SAND,
            Blocks.GRASS,ModBlocks.ANOTHER_CRIMSON_GRASS,
            Blocks.TALL_GRASS,ModBlocks.ANOTHER_CRIMSON_HUNGRY_GHOST_GRASS,
            Blocks.FERN,ModBlocks.ANOTHER_CRIMSON_EYEBALL_GRASS
        ),ImmutableMap.of(
            ModBlocks.EBONY_MUSHROOM,ModBlocks.ANOTHER_CRIMSON_MUSHROOM,
            ModBlocks.GLOWING_MUSHROOM,ModBlocks.ANOTHER_CRIMSON_MUSHROOM,
            ModBlocks.LIFE_MUSHROOM,ModBlocks.ANOTHER_CRIMSON_MUSHROOM,
            ModBlocks.JUNGLE_SPORE,ModBlocks.ANOTHER_CRIMSON_GRASS,
            ModBlocks.CORRUPT_GRASS,ModBlocks.ANOTHER_CRIMSON_GRASS,
            ModBlocks.HALLOW_GRASS,ModBlocks.ANOTHER_CRIMSON_GRASS,
            ModBlocks.HALLOW_FLOWERS,ModBlocks.ANOTHER_CRIMSON_GRASS)),

        CORRUPT(ImmutableMap.of(
            Blocks.DIRT, ModBlocks.CORRUPT_GRASS_BLOCK,
            Blocks.DIRT_PATH, ModBlocks.CORRUPT_GRASS_BLOCK,
            Blocks.GRASS_BLOCK, ModBlocks.CORRUPT_GRASS_BLOCK,
            Blocks.STONE, ModBlocks.EBONY_STONE,
            Blocks.SAND, ModBlocks.EBONY_SAND,
            Blocks.GRASS,ModBlocks.CORRUPT_GRASS,
            Blocks.TALL_GRASS,ModBlocks.CORRUPT_GRASS,
            Blocks.FERN,ModBlocks.CORRUPT_GRASS
        ),ImmutableMap.of(
            ModBlocks.ANOTHER_CRIMSON_MUSHROOM,ModBlocks.EBONY_MUSHROOM,
            ModBlocks.LIFE_MUSHROOM,ModBlocks.EBONY_MUSHROOM,
            ModBlocks.JUNGLE_SPORE,ModBlocks.CORRUPT_GRASS,
            ModBlocks.ANOTHER_CRIMSON_HUNGRY_GHOST_GRASS,ModBlocks.CORRUPT_GRASS,
            ModBlocks.ANOTHER_CRIMSON_EYEBALL_GRASS,ModBlocks.CORRUPT_GRASS,
            ModBlocks.ANOTHER_CRIMSON_GRASS,ModBlocks.CORRUPT_GRASS,
            ModBlocks.HALLOW_GRASS,ModBlocks.CORRUPT_GRASS,
            ModBlocks.HALLOW_FLOWERS,ModBlocks.CORRUPT_GRASS
        )),

        GLOWING(ImmutableMap.of(
            Blocks.MUD, ModBlocks.MUSHROOM_GRASS_BLOCK
        ),ImmutableMap.of(
            ModBlocks.JUNGLE_SPORE,ModBlocks.GLOWING_MUSHROOM
        )),

        PURE(Map.of(), map(
            ModBlocks.ANOTHER_CRIMSON_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.EBONY_MUSHROOM, ModBlocks.LIFE_MUSHROOM,
            ModBlocks.CORRUPT_GRASS, getBlockRegistry(Blocks.GRASS),
            ModBlocks.ANOTHER_CRIMSON_HUNGRY_GHOST_GRASS, getBlockRegistry(Blocks.GRASS),
            ModBlocks.ANOTHER_CRIMSON_EYEBALL_GRASS, getBlockRegistry(Blocks.GRASS),
            ModBlocks.ANOTHER_CRIMSON_GRASS, getBlockRegistry(Blocks.GRASS),
            ModBlocks.HALLOW_GRASS, getBlockRegistry(Blocks.GRASS),
            ModBlocks.HALLOW_FLOWERS, getBlockRegistry(Blocks.GRASS),
            ModBlocks.ANOTHER_CRIMSON_STONE, getBlockRegistry(Blocks.STONE),
            ModBlocks.ANOTHER_CRIMSON_SAND, getBlockRegistry(Blocks.SAND),
            ModBlocks.ANOTHER_CRIMSON_GRASS_BLOCK, getBlockRegistry(Blocks.GRASS_BLOCK),
            ModBlocks.CORRUPT_GRASS_BLOCK, getBlockRegistry(Blocks.GRASS_BLOCK),
            ModBlocks.HALLOW_GRASS_BLOCK, getBlockRegistry(Blocks.GRASS_BLOCK)
        ));

        // 本来可以写得很优雅的，但是这个类在初始化的时候调用ModBlocks.XXX.get()方法会报错，只好要用的时候再get
        // 搞得枚举的Immutable特性都没了，希望不要出问题
        // 如果原版方块也用RegistryObject的话好像也能解决而且优雅多了，但是写都写了以后需要再试吧
        public final Map<Block, RegistryObject<? extends Block>> getMap(){
            if(modBlockMap != null){
                HashMap<Block, RegistryObject<? extends Block>> map = new HashMap<>(blockMap);
                modBlockMap.forEach((k, v) -> map.put(k.get(), v));
                blockMap = map;
                modBlockMap = null;
            }
            return blockMap;
        }

        private static RegistryObject<Block> getBlockRegistry(Block block){
            return RegistryObject.create(ForgeRegistries.BLOCKS.getKey(block), ForgeRegistries.BLOCKS);
        }

        // 缺点是不能在编译期检查类型。各家的Entry实现都藏着掖着不让用，又不想自己写，不然调库保险一点
        @SuppressWarnings("unchecked")
        private static <K,V> Map<K,V> map(K k,V v,Object... kv){
            HashMap<K, V> ret = new HashMap<>();
            ret.put(k, v);
            for(int i = 0, kvLength = kv.length; i < kvLength; i+=2){
                ret.put((K) kv[i], (V) kv[i + 1]);
            }
            return ret;
        }

        private Map<Block, RegistryObject<? extends Block>> blockMap;
        private Map<RegistryObject<? extends Block>, RegistryObject<? extends Block>> modBlockMap;

        Type(Map<Block, RegistryObject<? extends Block>> blockMap,Map<RegistryObject<? extends Block>, RegistryObject<? extends Block>> modBlockMap) {
            this.blockMap = blockMap;
            this.modBlockMap = modBlockMap;
        }
    }
}
