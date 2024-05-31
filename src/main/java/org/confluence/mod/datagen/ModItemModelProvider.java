package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.confluence.mod.Confluence;
import org.confluence.mod.datagen.limit.CustomModel;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.common.IconItem;
import org.confluence.mod.item.curio.BaseCurioItem;

import static org.confluence.mod.Confluence.MODID;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (IconItem.Icons icons : IconItem.Icons.values()) {
            String path = icons.name().toLowerCase();
            withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/" + path));
        }

        ModItems.ITEMS.getEntries().forEach(item -> {
            try {
                Item value = item.get();
                if (value instanceof CustomModel) return;

                String path = item.getId().getPath().toLowerCase();
                if (value instanceof BaseCurioItem) {
                    withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(MODID, "item/curio/" + path));
                }
            } catch (Exception e) {
                Confluence.LOGGER.error(e.getMessage());
            }
        });
    }
}
