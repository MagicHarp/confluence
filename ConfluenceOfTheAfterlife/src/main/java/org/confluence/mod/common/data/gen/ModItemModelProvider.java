package org.confluence.mod.common.data.gen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.block.ModBlocks;
import org.confluence.mod.common.init.block.ModOreBlocks;
import org.confluence.mod.common.init.item.*;
import org.confluence.mod.terra_curio.common.item.curio.BaseCurioItem;
import software.bernie.geckolib.animatable.GeoItem;

import java.util.List;
import java.util.Set;

import static org.confluence.mod.Confluence.MODID;

public class ModItemModelProvider extends ItemModelProvider {
    private static final Set<Item> SKIP_ITEMS = Set.of(ModBlocks.BEACH_BOX.asItem());
    private static final ResourceLocation MISSING_ITEM = Confluence.asResource("item/item_icon");
    private static final ResourceLocation MISSING_BLOCK = Confluence.asResource("item/blocks_icon");

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        // 一般物品
        List<DeferredRegister.Items> customModels = List.of(IconItems.ICONS,TerraPotions.POTIONS,MaterialItems.MATERIALS, ArrowItems.ARROWS);
        customModels.forEach(reg -> reg.getEntries().forEach(item -> {
            String path = item.getId().getPath().toLowerCase();
            try {withExistingParent(path, "item/generated").texture("layer0", Confluence.asResource("item/" + path));}
            catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
                withExistingParent(path,MISSING_ITEM);
            }
        }));

        // 手持物品
        List<DeferredRegister.Items> Handled = List.of(SwordItems.SWORDS, BowItems.BOWS);
        Handled.forEach(reg -> reg.getEntries().forEach(item -> {
            String path = item.getId().getPath().toLowerCase();
            try { withExistingParent(path, "item/handheld").texture("layer0", Confluence.asResource("item/" + path));}
            catch (Exception e) {
            Confluence.LOGGER.error(e.getMessage());
            withExistingParent(path,MISSING_ITEM);
        }
        }));



        ModItems.ITEMS.getEntries().forEach(item -> {
            Item value = item.get();

            if (shouldSkip(value)) return;

            String path = item.getId().getPath().toLowerCase();
            boolean isBlockItem = false;
            try {
//                if (value instanceof HerbSeedItem) {
//                    withExistingParent(path, "item/generated").texture("layer0", Confluence.asResource("item/" + path));
//                } else
                if (value instanceof BlockItem blockItem) {

                    isBlockItem = true;
                    Block block = blockItem.getBlock();
//                    if (block instanceof CustomItemModel) return;
                    if (block instanceof DoorBlock) {
                        withExistingParent(path, "item/generated").texture("layer0", Confluence.asResource("item/" + path));
                    } else if (block instanceof TrapDoorBlock) {
                        withExistingParent(path, Confluence.asResource("block/" + path + "_bottom"));
                    } else {
                        withExistingParent(path, Confluence.asResource("block/" + path + (hasInventory(block) ? "_inventory" : "")));
                    }

                } else if (isHandheld(value)) {
                    ItemModelBuilder builder = withExistingParent(path, "item/handheld").texture("layer0", Confluence.asResource("item/" + path));
//                    if (value instanceof Image24x i32) {
//                        i32.preset(builder);
//                    } else if (value instanceof ReversalImage24x i32) {
//                        i32.preset(builder);
//                    } else if (value instanceof Image64x i64) {
//                        i64.preset(builder);
//                    }
                } else if (value instanceof SpawnEggItem) {
                    withExistingParent(path, "item/template_spawn_egg");
                } else if (value instanceof BaseCurioItem) {
                    withExistingParent(path, "item/generated").texture("layer0", Confluence.asResource("item/curio/" + path));
                }
//                else if (value instanceof BaseFoodItem || value instanceof BottleFoodItem) {
//                    withExistingParent(path, "item/generated").texture("layer0", Confluence.asResource("item/food/" + path));
//                } else if (value instanceof ReversalImage16x) {
//                    withExistingParent(path, "confluence:item/handheld_mirror").texture("layer0", Confluence.asResource("item/" + path));
//                }
                else {
                    withExistingParent(path, "item/generated").texture("layer0", Confluence.asResource("item/" + path));
                }
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
                withExistingParent(path, isBlockItem ? MISSING_BLOCK : MISSING_ITEM);
            }
        });




/*
        Swords.SWORDS.getEntries().forEach(item -> {
            String path = item.getId().getPath().toLowerCase();
            withExistingParent(path, "item/handheld").texture("layer0", Confluence.asResource("item/" + path));
        });
*/
    }

    private static boolean hasInventory(Block block) {
        return block instanceof ButtonBlock || block instanceof FenceBlock;
    }

    private static boolean isHandheld(Item item) {
        return item instanceof TieredItem
//                || item instanceof StaffItem
                ;
    }

    private static boolean shouldSkip(Item item) {
        return (
                //item instanceof IconItem ||
                item instanceof GeoItem
//                        && !(item instanceof NormalGeoItem))
                        || SKIP_ITEMS.contains(item))
//                || item instanceof VanillaPotionItem
                ;
    }
}
