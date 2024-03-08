package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;
import software.bernie.geckolib.animatable.GeoItem;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceItemModelProvider extends ItemModelProvider {
    private static final ResourceLocation MISSING_ITEM = new ResourceLocation(MODID, "item/item_icon");
    private static final ResourceLocation MISSING_BLOCK = new ResourceLocation(MODID, "item/blocks_icon");

    public ConfluenceItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (ConfluenceItems.Icons icons : ConfluenceItems.Icons.values()) {
            String path = icons.name().toLowerCase();
            withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
        }

        ConfluenceItems.ITEMS.getEntries().forEach(item -> {
            Item value = item.get();
            String path = item.getId().getPath().toLowerCase();
            if (value instanceof CustomModel || value instanceof GeoItem) return;

            boolean isBlockItem = false;
            try {
                if (value instanceof BlockItem blockItem) {
                    isBlockItem = true;
                    withExistingParent(path, new ResourceLocation(MODID, "block/" + path + (blockItem.getBlock() instanceof ButtonBlock ? "_inventory" : "")));
                } else if (isHandheld(value)) {
                    ItemModelBuilder builder = withExistingParent(path, "item/handheld").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                    if (value instanceof Image32x i32) {
                        i32.preset(builder);
                    } else if (value instanceof Image64x i64) {
                        i64.preset(builder);
                    }
                } else {
                    withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                }
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
                withExistingParent(path, isBlockItem ? MISSING_BLOCK : MISSING_ITEM);
            }
        });
    }

    private static boolean isHandheld(Item item) {
        return item instanceof TieredItem;
    }
}
