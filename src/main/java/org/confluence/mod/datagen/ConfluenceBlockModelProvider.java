package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.block.ConfluenceBlocks;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceBlockModelProvider extends BlockModelProvider {
    public ConfluenceBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ConfluenceBlocks.BLOCKS.getEntries().forEach(block -> {
            Block value = block.get();
            String path = block.getId().getPath();
            if (value instanceof CustomModel) return;
            if (value instanceof EntityBlock) {
                if (value instanceof SignBlock) {
                    sign(path, planks(path, "sign"));
                }
                return;
            }

            if (value instanceof ButtonBlock) {
                ResourceLocation texture = planks(path, "button");
                button(path, texture);
                buttonInventory(path + "_inventory", texture);
                buttonPressed(path + "_pressed", texture);
            } else if (value instanceof RotatedPillarBlock) {
                ResourceLocation side = new ResourceLocation(MODID, path);
                if (path.contains("wood")) {
                    cubeColumn(path, side, side); // only wood
                } else {
                    ResourceLocation top = top(path);
                    cubeColumn(path, side, top); // log or other
                    cubeColumnHorizontal(path + "_horizontal", side, top); // log_horizontal or other
                }
            } else if (value instanceof FenceBlock) {
                ResourceLocation texture = planks(path, "fence");
                fencePost(path + "_post", texture);
                fenceSide(path + "_side", texture);
            } else if (value instanceof FenceGateBlock) {
                ResourceLocation texture = planks(path, "fence_gate");
                fenceGate(path, texture);
                fenceGateOpen(path + "_open", texture);
                fenceGateWall(path + "_wall", texture);
                fenceGateWallOpen(path + "_wall_open", texture);
            } else if (value instanceof PressurePlateBlock) {
                ResourceLocation texture = planks(path, "pressure_plate");
                pressurePlate(path, texture);
                pressurePlateDown(path + "_down", texture);
            } else if (value instanceof SlabBlock) {
                ResourceLocation texture = planks(path, "slab");
                slab(path, texture, texture, texture);
                slabTop(path + "_top", texture, texture, texture);
            } else if (value instanceof StairBlock) {
                ResourceLocation texture = planks(path, "stairs");
                stairs(path, texture, texture, texture);
                stairsInner(path + "_inner", texture, texture, texture);
                stairsOuter(path + "_outer", texture, texture, texture);
            } else if (value instanceof TrapDoorBlock) {
                ResourceLocation texture = new ResourceLocation(MODID, path);
                trapdoorOrientableBottom(path + "_bottom", texture);
                trapdoorOrientableOpen(path + "_open", texture);
                trapdoorOrientableTop(path + "_top", texture);
            } else if (value instanceof DoorBlock) {
                ResourceLocation bottom = bottom(path);
                ResourceLocation top = top(path);
                doorBottomLeft(path + "_bottom_left", bottom, top);
                doorBottomLeftOpen(path + "_bottom_left_open", bottom, top);
                doorBottomRight(path + "_bottom_right", bottom, top);
                doorBottomRightOpen(path + "_bottom_right_open", bottom, top);
                doorTopLeft(path + "_top_left", bottom, top);
                doorTopLeftOpen(path + "_top_left_open", bottom, top);
                doorTopRight(path + "_top_right", bottom, top);
                doorTopRightOpen(path + "_top_right_open", bottom, top);
            } else if (value instanceof ICubeTop) {
                cubeTop(path, side(path), top(path));
            } else if (value instanceof ICubeBottomTop) {
                cubeBottomTop(path, side(path), bottom(path), top(path));
            } else {
                cubeAll(path, new ResourceLocation(MODID, path));
                //cube();
            }
        });
    }

    private static ResourceLocation planks(String path, String regex) {
        return new ResourceLocation(MODID, path.replaceFirst(regex, "planks"));
    }

    private static ResourceLocation bottom(String path) {
        return new ResourceLocation(MODID, path + "_bottom");
    }

    private static ResourceLocation top(String path) {
        return new ResourceLocation(MODID, path + "_top");
    }

    private static ResourceLocation side(String path) {
        return new ResourceLocation(MODID, path + "_side");
    }
}
