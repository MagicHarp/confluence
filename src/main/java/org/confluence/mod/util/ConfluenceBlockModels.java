package org.confluence.mod.util;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.block.ConfluenceBlocks;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceBlockModels extends BlockModelProvider {
    public ConfluenceBlockModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ConfluenceBlocks.BLOCKS.getEntries().forEach(block -> {
            Block value = block.get();
            String path = block.getId().getPath();
            if (value instanceof CustomModel) return;
            if (value instanceof ButtonBlock) {
                ResourceLocation texture = new ResourceLocation(MODID, path.replaceFirst("button", "planks"));
                button(path, texture);
                buttonInventory(path + "_inventory", texture);
                buttonPressed(path + "_pressed", texture);
            } else if (value instanceof RotatedPillarBlock) {
                ResourceLocation top = new ResourceLocation(MODID, path + "_top");
                ResourceLocation side = new ResourceLocation(MODID, path);
                cubeColumn(path, side, top); // log
                cubeColumnHorizontal(path + "_horizontal", side, top); // log_horizontal
                cubeColumn(path, side, side); // wood
            } else if (value instanceof FenceBlock) {
                ResourceLocation texture = new ResourceLocation(MODID, path.replaceFirst("fence", "planks"));
                fencePost(path + "_post", texture);
                fenceSide(path + "_side", texture);
            } else if (value instanceof FenceGateBlock) {
                //fenceGate();
                //fenceGateOpen();
                //fenceGateWall();
                //fenceGateWallOpen();
            } else if (value instanceof PressurePlateBlock) {

            }
        });
    }
}
