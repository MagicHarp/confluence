package org.confluence.mod.util;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems;

import java.util.Arrays;

public class ConfluenceItemModels extends ItemModelProvider {
    public ConfluenceItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Confluence.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Arrays.stream(ConfluenceItems.Materials.values()).forEach(item -> itemGenerated(item.getValue(), item.name()));
        Arrays.stream(ConfluenceItems.Swords.values()).forEach(item -> itemHandheld(item.getValue(), item.name()));
        Arrays.stream(ConfluenceItems.Axes.values()).forEach(item -> itemHandheld(item.getValue(), item.name()));
        Arrays.stream(ConfluenceItems.Pickaxes.values()).forEach(item -> itemHandheld(item.getValue(), item.name()));
        //Arrays.stream(ConfluenceItems.HammerAxes.values()).forEach(item -> itemHandheld(item.getValue(), item.name()));
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(Confluence.MODID, "block/" + path);
    }

    public void itemGenerated(RegistryObject<Item> item, String path) {
        String name = item.getId().getPath();
        withExistingParent(name, "item/generated")
            .texture("layer0", new ResourceLocation(Confluence.MODID, "item/" + path.toLowerCase()));
    }

    public <I extends Item> void itemHandheld(RegistryObject<I> item, String path) {
        String name = item.getId().getPath();
        withExistingParent(name, "item/handheld")
            .texture("layer0", new ResourceLocation(Confluence.MODID, "item/" + path.toLowerCase()));
    }
}
