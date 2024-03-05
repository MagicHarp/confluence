package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.block.WoodSetType;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceBlockStateProvider extends BlockStateProvider {
    public ConfluenceBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ConfluenceBlocks.BLOCKS.getEntries().forEach(block -> {
            Block value = block.get();
            String path = block.getId().getPath();
            if (value instanceof CustomModel) return;

            if (value instanceof ButtonBlock buttonBlock) {
                buttonBlock(buttonBlock, texture(path, "_button"));
            } else if (value instanceof RotatedPillarBlock rotatedPillarBlock) {
                ResourceLocation side = new ResourceLocation(MODID, path);
                if (path.contains("wood")) {
                    axisBlock(rotatedPillarBlock, side, side);
                } else if (path.contains("log")) {
                    logBlock(rotatedPillarBlock);
                } else {
                    axisBlock(rotatedPillarBlock, side);
                }
            } else if (value instanceof FenceBlock fenceBlock) {
                fenceBlock(fenceBlock, path, texture(path, "_fence"));
            } else if (value instanceof FenceGateBlock fenceGateBlock) {
                fenceGateBlock(fenceGateBlock, path, texture(path, "_fence_gate"));
            } else if (value instanceof PressurePlateBlock pressurePlateBlock) {
                pressurePlateBlock(pressurePlateBlock, texture(path, "_pressure_plate"));
            } else if (value instanceof SlabBlock slabBlock) {
                ResourceLocation texture = texture(path, "_slab");
                slabBlock(slabBlock, texture, texture);
            } else if (value instanceof StairBlock stairBlock) {
                stairsBlock(stairBlock, path, texture(path, "_stairs"));
            } else if (value instanceof TrapDoorBlock trapDoorBlock) {
                trapdoorBlock(trapDoorBlock, path, new ResourceLocation(MODID, path), true);
            } else if (value instanceof DoorBlock doorBlock) {
                doorBlock(doorBlock, path, new ResourceLocation(MODID, path + "_bottom"), new ResourceLocation(MODID, path + "_top"));
            } else {
                simpleBlock(value);
            }
        });
    }

    private static ResourceLocation texture(String path, String regex) {
        for (WoodSetType woodSetType : WoodSetType.values()) {
            if (path.contains(woodSetType.name().toLowerCase())) {
                return new ResourceLocation(MODID, path.replaceFirst(regex, "_planks"));
            }
        }
        return new ResourceLocation(MODID, path.replaceFirst(regex, ""));
    }
}
