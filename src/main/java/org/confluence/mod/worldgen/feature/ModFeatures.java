package org.confluence.mod.worldgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.block.functional.AbstractMechanicalBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public final class ModFeatures {
    static final Direction[] HORIZONTAL = new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};
    static final Predicate<BlockState> IS_REPLACEABLE = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Confluence.MODID);

    public static final RegistryObject<BoulderTrapFeature> BOULDER_TRAP = FEATURES.register("boulder_trap", () -> new BoulderTrapFeature(BoulderTrapFeature.Config.CODEC));
    public static final RegistryObject<DartTrapFeature> DART_TRAP = FEATURES.register("dart_trap", () -> new DartTrapFeature(DartTrapFeature.Config.CODEC));
    public static final RegistryObject<JewelryTreeFeature> JEWELRY_TREE = FEATURES.register("jewelry_tree", () -> new JewelryTreeFeature(JewelryTreeFeature.Config.CODEC));

    public static final ResourceKey<ConfiguredFeature<?, ?>> SHADOW = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "trees_set/trees_tr_crimson"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> EBONY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "trees_set/trees_corruption"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "palm_tree_checked"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> PEARL = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "trees_set/trees_hallow"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUBY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "ruby_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBER = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "amber_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> TOPAZ = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "topaz_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "emerald_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "diamond_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAPPHIRE = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "sapphire_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> TR_AMETHYST = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "tr_amethyst_tree"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASH = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Confluence.MODID, "ash_tree"));

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
            if (level.isStateAtPosition(blockPos.relative(direction), BlockBehaviour.BlockStateBase::isAir)) {
                return true;
            }
        }
        return false;
    }

    static boolean isPosAir(WorldGenLevel level, BlockPos blockPos) {
        return level.isStateAtPosition(blockPos, BlockBehaviour.BlockStateBase::isAir);
    }

    static boolean isPosSturdy(WorldGenLevel level, BlockPos blockPos, Direction face) {
        return level.isStateAtPosition(blockPos, blockState -> blockState.isFaceSturdy(level, blockPos, face));
    }
}
