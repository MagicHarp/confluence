package org.confluence.mod.util;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;

public class ConfluenceItemModels extends ItemModelProvider {
    public ConfluenceItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Confluence.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ConfluenceItems.ITEMS.getEntries().forEach(item -> {
            Item value = item.get();
            String path = item.getId().getPath();
            if (value instanceof CustomModel) return;
            if (value instanceof BlockItem blockItem) {
                blockItem(blockItem, path);
            } else if (isHandheld(value)) {
                itemHandheld(item, path);
            } else {
                itemGenerated(path);
            }
        });
    }

    public void blockItem(BlockItem blockItem, String path) {
        path = path.toLowerCase();
        Block block = blockItem.getBlock();
        if (block instanceof ButtonBlock) path += "_inventory";
        withExistingParent(path, "block/" + path);
    }

    public void itemGenerated(String path) {
        path = path.toLowerCase();
        withExistingParent(path, "item/generated")
            .texture("layer0", new ResourceLocation(Confluence.MODID, "item/" + path));
    }

    public <I extends Item> void itemHandheld(RegistryObject<I> item, String path) {
        path = path.toLowerCase();
        ItemModelBuilder builder = withExistingParent(path, "item/handheld")
            .texture("layer0", new ResourceLocation(Confluence.MODID, "item/" + path));
        if (item.get() instanceof Image32x i32) {
            i32.preset(builder);
        } else if (item.get() instanceof Image64x i64) {
            i64.preset(builder);
        }
    }

    private static boolean isHandheld(Item item) {
        return item instanceof TieredItem;
    }
}
