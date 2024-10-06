package org.confluence.mod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalmTreeFeature extends Feature<PalmTreeFeature.Config> {
    public PalmTreeFeature(Codec<Config> pCodec) {
        super(pCodec);
    }
    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        int height = 7 + pContext.random().nextInt(3);
        boolean facingT = pContext.random().nextBoolean();
        boolean facingF = pContext.random().nextBoolean();
        List<Integer> leavesListX = new ArrayList<>(Arrays.asList(1, 2, 2, 3, 4, 1, 2, -1, -2, -2, -3, -4, -1, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 1, 2, 3, -1, -2, -3, -1, -2, -3));
        List<Integer> leavesListY = new ArrayList<>(Arrays.asList(2, 2, 1, 1, 0, -1, -2, 2, 2, 1, 1, 0, -1, -2, 2, 2, 1, 1, 0, -1, -2, 2, 2, 1, 1, 0, -1, -2, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1));
        List<Integer> leavesListZ = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3, 4, 1, 2, -1, -2, -2, -3, -4, -1, -2, -1, -2, -3, 1, 2, 3, -1, -2, -3, 1, 2, 3));
        int treeX;
        int treeZ;
        int bl = facingF ? 1 : -1;
        Config config = pContext.config();
        WorldGenLevel level = pContext.level();
        BlockPos trunkBlockPos = pContext.origin();
        BlockPos leavesBlockPos = pContext.origin();
        BlockState trunkBlockState = config.trunk().getState(pContext.random(), trunkBlockPos);
        BlockState leavesBlockState = config.leaves().getState(pContext.random(), leavesBlockPos);

        boolean placed = true;

        for (int checkY = 1; checkY <= height; checkY++) {
            treeX = 0;
            treeZ = 0;
            if (facingT) {
                treeZ = (int) ((Math.sqrt(-checkY + height) - Math.sqrt(height)) * bl);
            } else {
                treeX = (int) ((Math.sqrt(-checkY + height) - Math.sqrt(height)) * bl);
            }
            BlockPos trunkBlockPosPlace = new BlockPos(trunkBlockPos.getX() + treeX, trunkBlockPos.getY() + checkY - 1, trunkBlockPos.getZ() + treeZ);
            BlockState trunkPos = level.getBlockState(trunkBlockPosPlace);
            if (!(trunkPos.isAir() || trunkPos.is(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "leaves"))))) {
                placed = false;
            }
        }

        if (placed) {

            for (int treeY = 1; treeY <= height; treeY++) {
                treeX = 0;
                treeZ = 0;
                if (facingT) {
                    treeZ = (int) ((Math.sqrt(-treeY + height) - Math.sqrt(height)) * bl);
                } else {
                    treeX = (int) ((Math.sqrt(-treeY + height) - Math.sqrt(height)) * bl);
                }
                BlockPos trunkBlockPosPlace = new BlockPos(trunkBlockPos.getX() + treeX, trunkBlockPos.getY() + treeY - 1, trunkBlockPos.getZ() + treeZ);
                level.setBlock(trunkBlockPosPlace, trunkBlockState, 2);
            }
            treeX = 0;
            treeZ = 0;

            if (facingT) {
                treeZ = (int) (Math.sqrt(height)) * bl;
            } else {
                treeX = (int) (Math.sqrt(height)) * bl;
            }
            leavesBlockPos = new BlockPos(leavesBlockPos.getX() - treeX, leavesBlockPos.getY() + height - 1, leavesBlockPos.getZ() - treeZ);
            level.setBlock(leavesBlockPos.atY(leavesBlockPos.getY() + 1), leavesBlockState, 2);
            for (int i = 0; i < 40; i++) {
                BlockPos leavesBlockPosPlace = new BlockPos(leavesBlockPos.getX() + leavesListX.get(i), leavesBlockPos.getY() + leavesListY.get(i), leavesBlockPos.getZ() + leavesListZ.get(i));
                BlockState leavesPos = level.getBlockState(leavesBlockPosPlace);
                if (leavesPos.isAir()) {
                    level.setBlock(leavesBlockPosPlace, leavesBlockState, 2);
                }
            }

            return true;
        }
        return false;
    }

    public record Config(BlockStateProvider trunk, BlockStateProvider leaves) implements FeatureConfiguration {
        public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockStateProvider.CODEC.fieldOf("trunk_block").forGetter(Config::trunk),
                BlockStateProvider.CODEC.fieldOf("leaves_block").forGetter(Config::leaves)
        ).apply(instance, Config::new));
    }
}
