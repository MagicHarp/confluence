package org.confluence.mod.util;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ConfluenceBlocks;
import org.confluence.mod.item.ConfluenceItems;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ConfluenceEnglish extends LanguageProvider {
    public ConfluenceEnglish(PackOutput output) {
        super(output, Confluence.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ConfluenceBlocks.BLOCKS.getEntries().forEach(block -> {
            Block block1 = block.get();
            if (!block1.getDescriptionId().equals("block.minecraft.air")) {
                add(block1, toTitleCase(block.getId().getPath()));
            }
        });
        ConfluenceItems.ITEMS.getEntries().forEach(item -> add(item.get(), toTitleCase(item.getId().getPath())));
    }

    private static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
            .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
    }
}
