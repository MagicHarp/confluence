package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ConfluenceItems.Axes;
import org.confluence.mod.item.ConfluenceItems.Materials;
import org.confluence.mod.item.ConfluenceItems.Pickaxes;
import org.confluence.mod.item.ConfluenceItems.Swords;

import static org.confluence.mod.block.ConfluenceBlocks.*;

public class ConfluenceChineseProvider extends LanguageProvider {
    public ConfluenceChineseProvider(PackOutput output) {
        super(output, Confluence.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(Ores.TIN_ORE.get(), "锡矿石");
        add(Ores.DEEPSLATE_TIN_ORE.get(), "深层锡矿石");
        add(Ores.RAW_TIN_BLOCK.get(), "原锡块");
        add(Ores.TIN_BLOCK.get(), "锡块");
        add(Ores.LEAD_ORE.get(), "铅矿石");
        add(Ores.DEEPSLATE_LEAD_ORE.get(), "深层铅矿石");
        add(Ores.RAW_TIN_BLOCK.get(), "原铅块");
        add(Ores.LEAD_BLOCK.get(), "铅块");
        add(Ores.SILVER_ORE.get(), "银矿石");
        add(Ores.DEEPSLATE_SILVER_ORE.get(), "深层银矿石");
        add(Ores.RAW_SILVER_BLOCK.get(), "原银块");
        add(Ores.SILVER_BLOCK.get(), "银块");
        add(Ores.WOLFRAM_ORE.get(), "钨矿石");
        add(Ores.DEEPSLATE_WOLFRAM_ORE.get(), "深层钨矿石");
        add(Ores.RAW_WOLFRAM_BLOCK.get(), "原钨块");
        add(Ores.WOLFRAM_BLOCK.get(), "钨块");
        add(Ores.PLATINUM_ORE.get(), "铂金矿石");
        add(Ores.DEEPSLATE_PLATINUM_ORE.get(), "深层铂金矿石");
        add(Ores.RAW_PLATINUM_BLOCK.get(), "原铂金块");
        add(Ores.PLATINUM_BLOCK.get(), "铂金块");

        add(EBONY_LOG_BLOCKS.BUTTON.get(), "乌木按钮");

        add(EBONY_STONE.get(), "黑檀石块");
        add(EBONY_SAND.get(), "黑檀沙块");
        add(EBONY_COBBLE_STONE.get(), "黑檀圆石");
        add(CORRUPTION_GRASS_BLOCKS.get(), "腐化草方块");
        add(PEARL_STONE.get(), "珍珠石块");
        add(PEARL_SAND.get(), "珍珠沙块");
        add(HALLOW_GRASS_BLOCKS.get(), "神圣草方块");
        add(ANOTHER_CRIMSON_STONE.get(), "猩红石块");
        add(ANOTHER_CRIMSON_SAND.get(), "猩红沙块");
        add(ANOTHER_CRIMSON_COBBLE_STONE.get(), "猩红圆石");
        add(ANOTHER_CRIMSON_GRASS_BLOCKS.get(), "猩红草方块");
        add(ASH_BLOCK.get(), "灰烬块");
        add(BIG_RUBY_BLOCK.get(), "大红玉块");
        add(BIG_AMBER_BLOCK.get(), "大琥珀块");
        add(BIG_TOPAZ_BLOCK.get(), "大黄玉块");
        add(BIG_ANOTHER_EMERALD_BLOCK.get(), "大翡翠块");
        add(BIG_SAPPHIRE_BLOCK.get(), "大蓝玉块");
        add(BIG_ANOTHER_AMETHYST_BLOCK.get(), "大紫晶块");
        add(ANOTHER_POLISHED_GRANITE.get(), "抛光异域花岗岩块");
        add(POLISHED_MARBLE.get(), "抛光异域大理石块");
        add(ANOTHER_COPPER_BRICKS.get(), "铜砖块");
        add(ANOTHER_COPPER_PLATE.get(), "铜条板块");
        add(ANOTHER_CRIMSON_ORE_BRICKS.get(), "猩红矿砖");
        add(ANOTHER_CRIMSON_ROCK_BRICKS.get(), "猩红岩石砖");
        add(ANOTHER_CRIMSON_STONE_BRICKS.get(), "猩红石砖");
        add(ANOTHER_GOLD_BRICKS.get(), "金砖块");
        add(ANOTHER_IRON_BRICKS.get(), "铁砖块");
        add(ANOTHER_STONE_BRICKS.get(), "异域石砖");
        add(EVIL_ORE_BRICKS.get(), "魔矿砖");
        add(EVIL_ROCK_BRICKS.get(), "魔矿石砖");
        add(GEL_BLOCK.get(), "凝胶块");
        add(GREEN_CANDY_BLOCK.get(), "绿色糖块");
        add(ICE_BRICKS.get(), "冰砖块");
        add(ICED_GEL_BLOCK.get(), "冻凝胶块");
        add(LEAD_BRICKS.get(), "铅砖块");
        add(METEORITE_BRICKS.get(), "陨石砖块");
        add(PEARL_ROCK_BRICKS.get(), "珍珠岩石砖");
        add(PINK_GEL_BLOCK.get(), "粉凝胶块");
        add(PLATINUM_BRICKS.get(), "铂金砖块");
        add(RED_CANDY_BLOCK.get(), "红色糖块");
        add(SILVER_BRICKS.get(), "银砖块");
        add(SNOW_BRICKS.get(), "雪砖块");
        add(SUN_PLATE.get(), "日盘块");
        add(TIN_BRICKS.get(), "锡砖块");
        add(TIN_PLATE.get(), "锡条板块");
        add(WOLFRAM_BRICKS.get(), "钨砖块");
        add(ANOTHER_LAVA_BEAM.get(), "异域熔岩梁");
        add(ANOTHER_LAVA_BRICKS.get(), "异域熔岩砖块");
        add(ANOTHER_OBSIDIAN_BEAM.get(), "异域黑曜石梁");
        add(ANOTHER_OBSIDIAN_BRICKS.get(), "异域黑曜石砖");
        add(ANOTHER_OBSIDIAN_PLATE.get(), "异域黑曜石条板块");
        add(ANOTHER_OBSIDIAN_SMALL_BRICKS.get(), "异域黑曜石细致砖");
        add(ANOTHER_SMOOTH_OBSIDIAN_SLAB.get(), "异域光滑黑曜石台阶");
        add(ANOTHER_SMOOTH_OBSIDIAN.get(), "异域光滑黑曜石块");
        add(CHISELED_ANOTHER_OBSIDIAN_BRICKS.get(), "錾制异域黑曜石块");
        add(SPOOKY_WOOD_PLANKS.get(), "阴森木板");
        add(ANOTHER_NORTHLAND_BEAM.get(), "北地风情木梁");
        add(ANOTHER_OAK_BEAM.get(), "经典风情木梁");


        add(Materials.RAW_TIN.get(), "原锡矿");
        add(Swords.COPPER_SHORT_SWORD.get(), "铜质短剑");
        add(Axes.COPPER_AXE.get(), "铜斧");
        add(Pickaxes.COPPER_PICKAXE.get(), "铜镐");

    }
}
