package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.item.ConfluenceItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConfluenceEnglishProvider extends LanguageProvider {
    public ConfluenceEnglishProvider(PackOutput output) {
        super(output, Confluence.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluence.building_blocks", "Confluence | Buildings");
        add("creativetab.confluence.natural_blocks", "Confluence | Naturals");
        add("creativetab.confluence.materials", "Confluence | Materials");

        ConfluenceBlocks.BLOCKS.getEntries().forEach(block -> {
            Block block1 = block.get();
            if (!(block1 instanceof WallSignBlock)) {
                add(block1, toTitleCase(block.getId().getPath()));
            }
        });
        ConfluenceItems.ITEMS.getEntries().forEach(item -> {
            Item item1 = item.get();
            if (!(item1 instanceof BlockItem)) {
                add(item.get(), toTitleCase(item.getId().getPath()));
            }
        });
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
