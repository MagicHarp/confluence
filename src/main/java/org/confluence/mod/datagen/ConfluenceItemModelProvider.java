package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.item.ConfluenceItems;
import org.confluence.mod.item.common.Icons;
import org.confluence.mod.item.magic.StaffItem;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.List;

import static org.confluence.mod.Confluence.MODID;

public class ConfluenceItemModelProvider extends ItemModelProvider {
    private static final List<Item> SKIP_ITEMS = List.of(ConfluenceBlocks.PEARL_LOG_BLOCKS.LEAVES.get().asItem());
    private static final ResourceLocation MISSING_ITEM = new ResourceLocation(MODID, "item/item_icon");
    private static final ResourceLocation MISSING_BLOCK = new ResourceLocation(MODID, "item/blocks_icon");

    public ConfluenceItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Icons icons : Icons.values()) {
            String path = icons.name().toLowerCase();
            withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
        }

        ConfluenceItems.ITEMS.getEntries().forEach(item -> {
            Item value = item.get();
            if (shouldSkip(value)) return;

            String path = item.getId().getPath().toLowerCase();
            boolean isBlockItem = false;
            try {
                if (value instanceof BlockItem blockItem) {
                    isBlockItem = true;
                    Block block = blockItem.getBlock();
                    if (block instanceof CustomItemModel) return;
                    withExistingParent(path, new ResourceLocation(MODID, "block/" + path + (hasInventory(block) ? "_inventory" : "")));
                } else if (isHandheld(value)) {
                    ItemModelBuilder builder = withExistingParent(path, "item/handheld").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                    if (value instanceof Image32x i32) {
                        i32.preset(builder);
                    } else if (value instanceof Image64x i64) {
                        i64.preset(builder);
                    }
                } else if (value instanceof SpawnEggItem) {
                    withExistingParent(path, "item/template_spawn_egg");
                } else {
                    withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
                }
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
                withExistingParent(path, isBlockItem ? MISSING_BLOCK : MISSING_ITEM);
            }
        });
    }

    private static boolean hasInventory(Block block) {
        return block instanceof ButtonBlock || block instanceof FenceBlock;
    }

    private static boolean isHandheld(Item item) {
        return item instanceof TieredItem || item instanceof StaffItem;
    }

    private static boolean shouldSkip(Item item) {
        return item instanceof CustomModel || item instanceof GeoItem || SKIP_ITEMS.contains(item);
    }
}
