package org.confluence.mod.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.util.EnumRegister;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.Block.box;

public enum Jars implements EnumRegister<Jars.BaseJarBlock> {
    FOREST_JARS("forest_jars"),
    SNOW_JARS("snow_jars"),
    SPIDER_CAVE_JARS("spider_cave_jars"),
    DESERT_JARS("desert_jars",Shapes.or(box(4, 0, 4, 12, 1, 12), box(4, 10, 4, 12, 11, 12), box(4, 17, 4, 12, 19, 12), box(5, 11, 5, 11, 17, 11), box(3, 1, 3, 13, 3, 13), box(3, 8, 3, 13, 10, 13), box(2, 3, 2, 14, 8, 14))),
    JUNGLE_JARS("jungle_jars",Shapes.or(box(3, 1, 3, 13, 13, 13), box(4, 14, 4, 12, 15, 12), box(5, 13, 5, 11, 14, 11), box(4, 0, 4, 12, 1, 12))),
    MARBLE_JARS("marble_jars",Shapes.or((box(4, 2, 4, 12, 3, 12)), box(5, 1, 5, 11, 2, 11), box(5, 12, 5, 11, 13, 11), box(4, 11, 4, 12, 12, 12), box(4, 13, 4, 12, 14, 12), box(4, 0, 4, 12, 1, 12), box(3, 3, 3, 13, 4, 13), box(2, 4, 2, 14, 10, 14), box(3, 10, 3, 13, 11, 13))),
    ANOTHER_CRIMSON_JARS("another_crimson_jars",Shapes.or(box(4, 0, 4, 12, 1, 12), box(5, 11, 5, 11, 12, 11), box(4, 3, 4, 12, 11, 12), box(5, 1, 5, 11, 3, 11))),
    PYRAMID_JARS("pyramid_jars",Shapes.or(box(2, 2, 2, 14, 13, 14), box(3, 1, 3, 13, 2, 13), box(2, 0, 2, 14, 1, 14), box(3, 13, 3, 13, 15, 13), box(2, 15, 2, 14, 17, 14))),
    CORRUPT_JARS("corrupt_jars",Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14))),
    DUNGEON_JARS("dungeon_jars",Shapes.or(box(3, 0, 3, 13, 6, 13), box(3, 15, 3, 13, 16, 13), box(2, 6, 2, 14, 15, 14))),
    ASH_JARS("ash_jars"),
    TEMPLE_JARS("temple_jars",Shapes.or(box(3, 0, 3, 13, 1, 13), box(4, 1, 4, 12, 3, 12), box(3, 3, 3, 13, 12, 13), box(2, 12, 2, 14, 14, 14)));

    private final RegistryObject<BaseJarBlock> value;

    Jars(String id, VoxelShape voxelShape) {
        this.value = ModBlocks.registerWithItem(id, () -> new BaseJarBlock(voxelShape));
    }

    Jars(String id) {
        this.value = ModBlocks.registerWithItem(id, BaseJarBlock::new);
    }

    @Override
    public RegistryObject<BaseJarBlock> getValue() {
        return value;
    }

    public static void init() {}

    public static class BaseJarBlock extends HorizontalDirectionalBlock implements CustomModel {
        private final VoxelShape voxelShape;

        public BaseJarBlock(VoxelShape voxelShape) {
            super(Properties.of().sound(SoundType.DECORATED_POT).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
            this.voxelShape = voxelShape;
            registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        public BaseJarBlock() {
            this(Shapes.or(box(3, 1, 3, 13, 10, 13), box(4, 11, 4, 12, 12, 12), box(5, 10, 5, 11, 11, 11), box(4, 0, 4, 12, 1, 12)));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }

        @Override
        @Nullable
        public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
            return defaultBlockState().setValue(FACING, placeContext.getHorizontalDirection().getOpposite());
        }

        @Override
        public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
            return voxelShape;
        }
    }
}
