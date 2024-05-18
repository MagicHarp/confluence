package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.WoodSetType;
import org.confluence.mod.block.natural.LogBlocks;
import org.confluence.mod.block.natural.spreadable.SpreadingGrassBlock;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.datagen.limit.ICubeBottomTop;
import org.confluence.mod.datagen.limit.ICubeTop;

import java.util.Arrays;

import static org.confluence.mod.Confluence.MODID;
import static org.confluence.mod.block.ModBlocks.*;

public class ModBlockStateProvider extends BlockStateProvider {
    private static final String[] WOODS = Arrays.stream(WoodSetType.values()).map(woodSetType -> woodSetType.name().toLowerCase()).toArray(String[]::new);

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BLOCKS.getEntries().forEach(block -> {
            Block value = block.get();
            String path = block.getId().getPath();
            if (shouldSkip(value)) return;

            try {
                if (value instanceof ButtonBlock buttonBlock) {
                    ResourceLocation texture = texture(path, "_button");
                    buttonBlock(buttonBlock, texture);
                    models().withExistingParent(path + "_inventory", "block/button_inventory").texture("texture", texture);
                } else if (value instanceof RotatedPillarBlock rotatedPillarBlock) {
                    if (path.contains("wood")) {
                        // _side, _side
                        ResourceLocation side = new ResourceLocation(MODID, "block/" + path.replace("wood", "log") + "_side");
                        axisBlock(rotatedPillarBlock, side, side);
                    } else if (path.contains("log")) {
                        // _side, _end
                        axisBlock(rotatedPillarBlock, new ResourceLocation(MODID, "block/" + path));
                    } else {
                        // _side, _end
                        axisBlock(rotatedPillarBlock, new ResourceLocation(MODID, "block/" + path));
                    }
                } else if (value instanceof FenceBlock fenceBlock) {
                    ResourceLocation texture = texture(path, "_fence");
                    fenceBlock(fenceBlock, texture);
                    models().withExistingParent(path + "_inventory", "block/fence_inventory").texture("texture", texture);
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
                    doorBlock(doorBlock, bottom(path), top(path));
                } else if (value instanceof ICubeTop) {
                    ConfiguredModel configuredModel = new ConfiguredModel(models()
                        .cubeTop(path, side(path), top(path)));
                    getVariantBuilder(value).partialState().setModels(configuredModel);
                } else if (value instanceof ICubeBottomTop) {
                    ConfiguredModel configuredModel = new ConfiguredModel(models()
                        .cubeBottomTop(path, side(path), value instanceof SpreadingGrassBlock ? new ResourceLocation("block/dirt") : bottom(path), top(path)));
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

        registerSignBlock(EBONY_LOG_BLOCKS);
        registerSignBlock(PEARL_LOG_BLOCKS);
        registerSignBlock(SHADOW_LOG_BLOCKS);
        registerSignBlock(PALM_LOG_BLOCKS);
        registerSignBlock(ASH_LOG_BLOCKS);
        registerSignBlock(SPOOKY_LOG_BLOCKS);
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

    private static ResourceLocation side(String path) {
        return new ResourceLocation(MODID, "block/" + path + "_side");
    }

    private static ResourceLocation bottom(String path) {
        return new ResourceLocation(MODID, "block/" + path + "_bottom");
    }

    private static ResourceLocation top(String path) {
        return new ResourceLocation(MODID, "block/" + path + "_top");
    }

    private void registerSignBlock(LogBlocks logBlocks) {
        try {
            signBlock(logBlocks.SIGN.get(), logBlocks.WALL_SIGN.get(), new ResourceLocation(MODID, "block/" + logBlocks.id + "_planks"));
        } catch (Exception e) {
            Confluence.LOGGER.error(e.getMessage());
        }
    }
}
