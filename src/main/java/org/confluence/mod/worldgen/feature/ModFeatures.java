package org.confluence.mod.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.functional.mechanical.AbstractMechanicalBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ModFeatures {
    static final Direction[] HORIZONTAL = new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Confluence.MODID);

    public static final RegistryObject<BoulderTrapFeature> BOULDER_TRAP = FEATURES.register("boulder_trap", () -> new BoulderTrapFeature(BoulderTrapFeature.Config.CODEC));
    public static final RegistryObject<DartTrapFeature> DART_TRAP = FEATURES.register("dart_trap", () -> new DartTrapFeature(DartTrapFeature.Config.CODEC));

    static @NotNull BlockState getPressurePlate(WorldGenLevel level, BlockPos supportPos) {
        return level.isStateAtPosition(supportPos, blockState -> blockState.is(Blocks.DEEPSLATE)) ?
            ModBlocks.DEEPSLATE_PRESSURE_PLATE.get().defaultBlockState() : Blocks.STONE_PRESSURE_PLATE.defaultBlockState();
    }

    static @Nullable AbstractMechanicalBlock.Entity getEntity(WorldGenLevel level, BlockPos blockPos) {
        if (level.getBlockEntity(blockPos) instanceof AbstractMechanicalBlock.Entity entity) {
            return entity;
        }
        Confluence.LOGGER.error("Failed to fetch mechanical block entity at ({}, {}, {})", blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return null;
    }

    static boolean isPosExposed(WorldGenLevel level, BlockPos blockPos) {
        for (Direction direction : HORIZONTAL) {
            if (level.isStateAtPosition(blockPos.offset(direction.getNormal()), BlockBehaviour.BlockStateBase::isAir)) {
                return true;
            }
        }
        return false;
    }

    static boolean isPosAir(WorldGenLevel level, BlockPos blockPos) {
        return level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir);
    }
}
