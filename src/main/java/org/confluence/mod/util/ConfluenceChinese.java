package org.confluence.mod.util;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ConfluenceBlocks.Ores;
import org.confluence.mod.item.ConfluenceItems.Axes;
import org.confluence.mod.item.ConfluenceItems.Materials;
import org.confluence.mod.item.ConfluenceItems.Pickaxes;
import org.confluence.mod.item.ConfluenceItems.Swords;

public class ConfluenceChinese extends LanguageProvider {
    public ConfluenceChinese(PackOutput output) {
        super(output, Confluence.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(Ores.TIN_ORE.get(), "锡矿石");
        add(Ores.DEEPSLATE_TIN_ORE.get(),"深层锡矿石");
        add(Ores.RAW_TIN_BLOCK.get(), "原锡块");
        add(Ores.TIN_BLOCK.get(), "锡块");

        add(Materials.RAW_TIN.get(), "原锡矿");
        add(Swords.COPPER_SHORT_SWORD.get(), "铜质短剑");
        add(Axes.COPPER_AXE.get(), "铜斧");
        add(Pickaxes.COPPER_PICKAXE.get(), "铜镐");
    }
}
