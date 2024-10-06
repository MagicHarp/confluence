package org.confluence.mod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.ArrayList;
import java.util.List;

public class LivingTreeFeature extends Feature<LivingTreeFeature.Config> {
    public LivingTreeFeature(Codec<Config> pCodec) {
        super(pCodec);
    }
    @Override
    public boolean place(FeaturePlaceContext<Config> pContext) {
        int bl = pContext.random().nextBoolean() ? 1 : -1;
        Config config = pContext.config();
        WorldGenLevel level = pContext.level();
        BlockPos trunkBlockPos = pContext.origin();
        BlockPos leavesBlockPos = pContext.origin();
        BlockState trunkBlockState = config.trunk().getState(pContext.random(), trunkBlockPos);
        BlockState leavesBlockState = config.leaves().getState(pContext.random(), leavesBlockPos);

        if (true) {

            List<List<Double>> locationList1 = new ArrayList<>();
            List<Double> locationStart = new ArrayList<>();
            List<Double> locationEnd = new ArrayList<>();

            locationStart.add((double) (trunkBlockPos.getX()));
            locationStart.add((double) (trunkBlockPos.getY()));
            locationStart.add((double) (trunkBlockPos.getZ()));
            locationEnd.add((double) (trunkBlockPos.getX() + bl * pContext.random().nextInt(5)));
            locationEnd.add((double) (trunkBlockPos.getY() + 55 + pContext.random().nextInt(10)));
            locationEnd.add((double) (trunkBlockPos.getZ() + bl * pContext.random().nextInt(5)));

            locationList1.add(locationStart);
            locationList1.add(locationEnd);
            List<List<Double>> leavesListFirst = new ArrayList<>();
            leavesListFirst.add(locationEnd);
            placeBlock(placeList(leavesListFirst, 30.0, 30.0, 0.5, 0.005, pContext), 4.0, 4.0, leavesBlockState, level, 0.5, 0.5, false, pContext);

            refineLocationList(locationList1, 1, pContext);
            placeBlock(locationList1, 6.0, 1.0, trunkBlockState, level, 1.0, 1.0, true, pContext);

            int stickCount = 5 + pContext.random().nextInt(5);
            for (int stickPlace = 0; stickPlace < stickCount; stickPlace++) {
                double anCs = 360.0 / stickCount;
                double everyA = anCs * stickPlace / 180 * Math.PI;
                double everyB = ((((double) pContext.random().nextInt(110) - 20) * Math.pow((double) pContext.random().nextInt(101) / 100, 3))/ 180 * Math.PI);
                int len = 20 + pContext.random().nextInt(15);
                double endX = len * Math.cos(everyA) * Math.cos(everyB);
                double endY = len * Math.sin(everyB);
                double endZ = len * Math.sin(everyA) * Math.cos(everyB);
                List<Double> stickStart = locationList1.get(locationList1.size() - (int) (locationList1.size() / 11 * 7) - pContext.random().nextInt(10));
                List<Double> stickEnd = new ArrayList<>();
                stickEnd.add(locationEnd.get(0) + endX);
                stickEnd.add(locationEnd.get(1) + endY - 20);
                stickEnd.add(locationEnd.get(2) + endZ);
                List<List<Double>> stickList = new ArrayList<>();
                List<List<Double>> leavesList = new ArrayList<>();
                stickList.add(stickStart);
                stickList.add(stickEnd);
                refineLocationList(stickList, 1, pContext);
                placeBlock(stickList, 2.0 + pContext.random().nextInt(2), 1.0, trunkBlockState, level, 1.0, 1.0, true, pContext);
                leavesList.add(stickEnd);
                placeBlock(placeList(leavesList, 20.0, 20.0, 0.5, 0.005, pContext), 4.0, 4.0, leavesBlockState, level, 0.5, 0.5, false, pContext);
            }

            for (int stickPlace = 0; stickPlace < stickCount; stickPlace++) {
                double anCs = 360.0 / stickCount;
                double everyA = anCs * stickPlace / 180 * Math.PI;
                double everyB = ((double) pContext.random().nextInt(50) / 180 * Math.PI);
                int len = 20 + pContext.random().nextInt(15);
                double endX = len * Math.cos(everyA) * Math.cos(everyB);
                double endY = len * Math.sin(everyB);
                double endZ = len * Math.sin(everyA) * Math.cos(everyB);
                List<Double> stickStart = locationList1.get(pContext.random().nextInt(10));
                List<Double> stickEnd = new ArrayList<>();
                BlockPos stickEndPos = new BlockPos((int) (locationStart.get(0) + endX), (int) (locationStart.get(1) - endY), (int) (locationStart.get(2) + endZ));
                if (!level.getBlockState(stickEndPos).isAir()) {
                    stickEnd.add(locationStart.get(0) + endX);
                    stickEnd.add(locationStart.get(1) - endY);
                    stickEnd.add(locationStart.get(2) + endZ);
                    List<List<Double>> stickList = new ArrayList<>();
                    stickList.add(stickStart);
                    stickList.add(stickEnd);
                    refineLocationList(stickList, 1, pContext);
                    placeBlock(stickList, 2.0 + pContext.random().nextInt(2), 1.0, trunkBlockState, level, 1.0, 1.0, true, pContext);
                }
            }

            for (int setY = -10; setY <= 10; setY++) {
                double rPlaceSq = 25 - setY * setY;
                if (rPlaceSq >= 0) {
                    double rPlace = Math.sqrt(rPlaceSq);
                    for (int setZ = -10; setZ <= 10; setZ++) {
                        double xSq = rPlace * rPlace - setZ * setZ;
                        if (xSq >= 0) {
                            int trunkBlockPosX;
                            int trunkBlockPosX1 = trunkBlockPos.getX() + (int) Math.sqrt(xSq);
                            int trunkBlockPosX2 = trunkBlockPos.getX() - (int) Math.sqrt(xSq);
                            int trunkBlockPosY = trunkBlockPos.getY() + setY;
                            int trunkBlockPosZ = trunkBlockPos.getZ() + setZ;
                            if (trunkBlockPosX1 == trunkBlockPosX2) {
                                trunkBlockPosX = trunkBlockPosX1;
                                BlockPos trunkBlockPosPlace = new BlockPos(trunkBlockPosX, trunkBlockPosY, trunkBlockPosZ);
                                level.setBlock(trunkBlockPosPlace, trunkBlockState, 2);
                            } else {
                                for (int trunkBlockPosXli = trunkBlockPosX2; trunkBlockPosXli <= trunkBlockPosX1; trunkBlockPosXli++) {
                                    BlockPos trunkBlockPosPlace = new BlockPos(trunkBlockPosXli, trunkBlockPosY, trunkBlockPosZ);
                                    level.setBlock(trunkBlockPosPlace, Blocks.AIR.defaultBlockState(), 2);
                                }
                            }
                        }
                    }
                }
            }

            placeRoot(trunkBlockPos, pContext, 6.0, 2.0, 1.5, 1.5, trunkBlockState, Blocks.AIR.defaultBlockState(), level);
            //BlockPos trunkBlockPosPlace1 = new BlockPos(trunkBlockPos.getX() + 4, trunkBlockPos.getY() + 3, trunkBlockPos.getZ());
            //BlockPos trunkBlockPosPlace2 = new BlockPos(trunkBlockPos.getX() - 4, trunkBlockPos.getY() + 3, trunkBlockPos.getZ());
            //BlockPos trunkBlockPosPlace3 = new BlockPos(trunkBlockPos.getX(), trunkBlockPos.getY() + 3, trunkBlockPos.getZ() + 3);
            //BlockPos trunkBlockPosPlace4 = new BlockPos(trunkBlockPos.getX(), trunkBlockPos.getY() + 3, trunkBlockPos.getZ() - 3);

            //level.setBlock(trunkBlockPosPlace1, Blocks.WALL_TORCH.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.EAST), 2);
            //level.setBlock(trunkBlockPosPlace2, Blocks.WALL_TORCH.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.WEST), 2);
            //level.setBlock(trunkBlockPosPlace3, Blocks.WALL_TORCH.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.SOUTH), 2);
            //level.setBlock(trunkBlockPosPlace4, Blocks.WALL_TORCH.defaultBlockState().setValue(DirectionalBlock.FACING, Direction.NORTH), 2);

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

    private static void refineLocationList(List<List<Double>> locationList, int dis, FeaturePlaceContext<Config> context) {
        boolean refined;
        do {
            refined = false;
            for (int i = 0; i < locationList.size() - 1; i++) {
                List<Double> point1 = locationList.get(i);
                List<Double> point2 = locationList.get(i + 1);
                double distance = calculateDistance(point1, point2);
                if (distance > dis) {
                    List<Double> midpoint = calculateMidpoint(point1, point2);
                    addRandomOffset(midpoint, distance, context);
                    locationList.add(i + 1, midpoint);
                    refined = true;
                }
            }
        } while (refined);
    }

    private static double calculateDistance(List<Double> point1, List<Double> point2) {
        return Math.sqrt(Math.pow(point2.get(0) - point1.get(0), 2) +
                Math.pow(point2.get(1) - point1.get(1), 2) +
                Math.pow(point2.get(2) - point1.get(2), 2));
    }

    private static List<Double> calculateMidpoint(List<Double> point1, List<Double> point2) {
        List<Double> midpoint = new ArrayList<>();
        midpoint.add((point1.get(0) + point2.get(0)) / 2);
        midpoint.add((point1.get(1) + point2.get(1)) / 2);
        midpoint.add((point1.get(2) + point2.get(2)) / 2);
        return midpoint;
    }

    private static void addRandomOffset(List<Double> midpoint, double distance, FeaturePlaceContext<Config> context) {
        double offset = distance / 8;
        midpoint.set(0, midpoint.get(0) + (context.random().nextDouble() - 0.5) * offset * 2);
        midpoint.set(1, midpoint.get(1) + (context.random().nextDouble() - 0.5) * offset * 2);
        midpoint.set(2, midpoint.get(2) + (context.random().nextDouble() - 0.5) * offset * 2);
    }

    private static void placeBlock(List<List<Double>> posListToPlace, double rMax, double rMin, BlockState blockStateToPlace, WorldGenLevel level, double rPer, double placePer, boolean repAir, FeaturePlaceContext<Config> context) {

        int rCheck = (int) (rMax + 2.0);

        for (int posList = 0; posList < posListToPlace.size(); posList++) {
            double r = rMax - (rMax - rMin) / posListToPlace.size() * posList;
            for (int setY = -rCheck; setY <= rCheck; setY++) {
                double rPlaceSq = r * r - setY * setY;
                if (rPlaceSq >= 0) {
                    double rPlace = Math.sqrt(rPlaceSq);
                    for (int setZ = -rCheck; setZ <= rCheck; setZ++) {
                        double xSq = rPlace * rPlace - setZ * setZ;
                        if (xSq >= 0) {
                            int trunkBlockPosX1 = posListToPlace.get(posList).get(0).intValue() + (int) Math.sqrt(xSq);
                            int trunkBlockPosX2 = posListToPlace.get(posList).get(0).intValue() - (int) Math.sqrt(xSq);
                            int trunkBlockPosY = (int) (posListToPlace.get(posList).get(1).intValue() + setY * rPer);
                            int trunkBlockPosZ = posListToPlace.get(posList).get(2).intValue() + setZ;
                            if (trunkBlockPosX1 != trunkBlockPosX2) {
                                for (int trunkBlockPosXli = trunkBlockPosX2; trunkBlockPosXli <= trunkBlockPosX1; trunkBlockPosXli++) {
                                    BlockPos trunkBlockPosPlace = new BlockPos(trunkBlockPosXli, trunkBlockPosY, trunkBlockPosZ);
                                    if (((double) context.random().nextInt(10001) / 10000) <= placePer && (level.getBlockState(trunkBlockPosPlace).isAir() || repAir) && !(level.getBlockState(trunkBlockPosPlace) == blockStateToPlace)) {
                                        level.setBlock(trunkBlockPosPlace, blockStateToPlace, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static List<List<Double>> placeList(List<List<Double>> posListToPlace, double rMax, double rMin, double rPer, double placePer, FeaturePlaceContext<Config> context) {

        int rCheck = (int) (rMax + 2.0);
        List<List<Double>> listToReturn = new ArrayList<>();

        for (int posList = 0; posList < posListToPlace.size(); posList++) {
            double r = rMax - (rMax - rMin) / posListToPlace.size() * posList;
            for (int setY = -rCheck; setY <= rCheck; setY++) {
                double rPlaceSq = r * r - setY * setY;
                if (rPlaceSq >= 0) {
                    double rPlace = Math.sqrt(rPlaceSq);
                    for (int setZ = -rCheck; setZ <= rCheck; setZ++) {
                        double xSq = rPlace * rPlace - setZ * setZ;
                        if (xSq >= 0) {
                            int trunkBlockPosX1 = posListToPlace.get(posList).get(0).intValue() + (int) Math.sqrt(xSq);
                            int trunkBlockPosX2 = posListToPlace.get(posList).get(0).intValue() - (int) Math.sqrt(xSq);
                            int trunkBlockPosY = (int) (posListToPlace.get(posList).get(1).intValue() + setY * rPer);
                            int trunkBlockPosZ = posListToPlace.get(posList).get(2).intValue() + setZ;
                            if (trunkBlockPosX1 != trunkBlockPosX2) {
                                for (int trunkBlockPosXli = trunkBlockPosX2; trunkBlockPosXli <= trunkBlockPosX1; trunkBlockPosXli++) {
                                    BlockPos trunkBlockPosPlace = new BlockPos(trunkBlockPosXli, trunkBlockPosY, trunkBlockPosZ);
                                    if (((double) context.random().nextInt(10001) / 10000) <= placePer) {
                                        List<Double> posS = new ArrayList<>();
                                        posS.add((double) trunkBlockPosPlace.getX());
                                        posS.add((double) trunkBlockPosPlace.getY());
                                        posS.add((double) trunkBlockPosPlace.getZ());
                                        listToReturn.add(posS);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return listToReturn;
    }

    private static void placeRoot(BlockPos blockPos, FeaturePlaceContext<Config> context, double rWoodMax, double rWoodMin, double rAirMax, double rAirMin, BlockState blockStateForWood, BlockState blockStateForAir, WorldGenLevel level) {
        int rCheck = (int) rWoodMax + 2;
        int lengthWood = 40 + context.random().nextInt(20);
        int lengthAir = 5 + lengthWood;
        for (int yToPlace = 0; yToPlace < lengthWood; yToPlace++) {
            double rToPlace = rWoodMax - (rWoodMax - rWoodMin) / lengthWood * yToPlace;
            for (int zToPlace = -rCheck; zToPlace <= rCheck; zToPlace++) {
                double xSq = rToPlace * rToPlace - zToPlace * zToPlace;
                if (xSq >= 0) {
                    int xToPlace = (int) Math.sqrt(xSq);
                    int xToPlace2 = -xToPlace;
                    if (xToPlace != xToPlace2) {
                        for (int xPlaceLi = xToPlace2; xPlaceLi <= xToPlace; xPlaceLi++) {
                            BlockPos posToPlaceWood = new BlockPos(blockPos.getX() + xPlaceLi, blockPos.getY() - 1 - yToPlace, blockPos.getZ() + zToPlace);
                            level.setBlock(posToPlaceWood, blockStateForWood,2);
                        }
                    }
                }
            }
        }
        for (int yToPlace = 0; yToPlace < lengthAir; yToPlace++) {
            double rToPlace = rAirMax - (rAirMax - rAirMin) / lengthAir * yToPlace;
            for (int zToPlace = -rCheck; zToPlace <= rCheck; zToPlace++) {
                double xSq = rToPlace * rToPlace - zToPlace * zToPlace;
                if (xSq >= 0) {
                    int xToPlace = (int) Math.sqrt(xSq);
                    int xToPlace2 = -xToPlace;
                    if (xToPlace != xToPlace2) {
                        for (int xPlaceLi = xToPlace2; xPlaceLi <= xToPlace; xPlaceLi++) {
                            BlockPos posToPlaceAir = new BlockPos(blockPos.getX() + xPlaceLi, blockPos.getY() - 1 - yToPlace, blockPos.getZ() + zToPlace);
                            level.setBlock(posToPlaceAir, blockStateForAir,2);
                        }
                    }
                }
            }
        }
    }
}
