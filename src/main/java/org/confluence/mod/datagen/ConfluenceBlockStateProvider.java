package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.block.WoodSetType;

import java.util.Arrays;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceBlockStateProvider extends BlockStateProvider {
    private static final String[] WOODS = Arrays.stream(WoodSetType.values()).map(woodSetType -> woodSetType.name().toLowerCase()).toArray(String[]::new);

    public ConfluenceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ConfluenceBlocks.BLOCKS.getEntries().forEach(block -> {
            Block value = block.get();
            String path = block.getId().getPath();
            if (shouldSkip(value)) return;

            try {
                if (value instanceof ButtonBlock buttonBlock) {
                    buttonBlock(buttonBlock, texture(path, "_button"));
                } else if (value instanceof RotatedPillarBlock rotatedPillarBlock) {
                    ResourceLocation side = new ResourceLocation(MODID, "block/" + path);
                    if (path.contains("wood")) {
                        axisBlock(rotatedPillarBlock, side, side);
                    } else if (path.contains("log")) {
                        logBlock(rotatedPillarBlock);
                    } else {
                        axisBlock(rotatedPillarBlock, side);
                    }
                } else if (value instanceof FenceBlock fenceBlock) {
                    fenceBlock(fenceBlock, texture(path, "_fence"));
                } else if (value instanceof FenceGateBlock fenceGateBlock) {
                    fenceGateBlock(fenceGateBlock, texture(path, "_fence_gate"));
                } else if (value instanceof PressurePlateBlock pressurePlateBlock) {
                    pressurePlateBlock(pressurePlateBlock, texture(path, "_pressure_plate"));
                } else if (value instanceof SlabBlock slabBlock) {
                    ResourceLocation texture = texture(path, "_slab");
                    slabBlock(slabBlock, texture, texture);
                } else if (value instanceof StairBlock stairBlock) {
                    stairsBlock(stairBlock, texture(path, "_stairs"));
                } else if (value instanceof TrapDoorBlock trapDoorBlock) {
                    trapdoorBlock(trapDoorBlock, new ResourceLocation(MODID, "block/" + path), true);
                } else if (value instanceof DoorBlock doorBlock) {
                    doorBlock(doorBlock, new ResourceLocation(MODID, "block/" + path + "_bottom"), top(path));
                } else if (value instanceof ICubeTop) {
                    ConfiguredModel configuredModel = new ConfiguredModel(models()
                        .cubeTop(path, new ResourceLocation(MODID, "block/" + path + "_side"), top(path)));
                    getVariantBuilder(value).partialState().setModels(configuredModel);
                } else if (value instanceof LeavesBlock) {
                    ConfiguredModel configuredModel = new ConfiguredModel(models()
                        .withExistingParent(path, "block/leaves").texture("all", new ResourceLocation(MODID, "block/" + path)));
                    getVariantBuilder(value).partialState().setModels(configuredModel);
                } else {
                    simpleBlock(value);
                }
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
            }
        });
    }

    private static boolean shouldSkip(Block block) {
        return block instanceof CustomModel || block instanceof SlimeBlock || block instanceof SignBlock;
    }

    private static ResourceLocation texture(String path, String regex) {
        if (Arrays.stream(WOODS).anyMatch(path::contains)) {
            return new ResourceLocation(MODID, "block/" + path.replace(regex, "_planks"));
        }
        return new ResourceLocation(MODID, "block/" + path.replace(regex, ""));
    }

    private static ResourceLocation top(String path) {
        return new ResourceLocation(MODID, "block/" + path + "_top");
    }
}
