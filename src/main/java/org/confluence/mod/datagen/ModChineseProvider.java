package org.confluence.mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.Ores;
import org.confluence.mod.item.armor.Armors;
import org.confluence.mod.item.axe.Axes;
import org.confluence.mod.item.common.Gels;
import org.confluence.mod.item.common.Materials;
import org.confluence.mod.item.common.SpawnEggs;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.item.hammer.Hammers;
import org.confluence.mod.item.magic.ManaWeapons;
import org.confluence.mod.item.pickaxe.Pickaxes;
import org.confluence.mod.item.sword.Swords;

import static org.confluence.mod.block.DecorativeBlocks.*;
import static org.confluence.mod.block.ModBlocks.*;
import static org.confluence.mod.item.ModItems.*;
import static org.confluence.mod.item.potion.TerraPotions.*;

public class ModChineseProvider extends LanguageProvider {
    public ModChineseProvider(PackOutput output) {
        super(output, Confluence.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("creativetab.confluence.building_blocks", "汇流来世 | 建筑方块");
        add("creativetab.confluence.natural_blocks", "汇流来世 | 自然方块");
        add("creativetab.confluence.materials", "汇流来世 | 材料");
        add("creativetab.confluence.creatures", "汇流来世 | 生物");
        add("creativetab.confluence.tools", "汇流来世 | 工具");
        add("creativetab.confluence.warriors", "汇流来世 | 战士武器");
        add("creativetab.confluence.shooters", "汇流来世 | 射手武器");
        add("creativetab.confluence.mages", "汇流来世 | 法师武器");
        add("creativetab.confluence.summoners", "汇流来世 | 召唤师武器");
        add("creativetab.confluence.creatives", "汇流来世 | 创造者物品");
        add("creativetab.confluence.food_and_potions", "汇流来世 | 食物与药水");
        add("creativetab.confluence.armors", "汇流来世 | 盔甲");
        add("creativetab.confluence.functional", "汇流来世 | 功能方块");
        add("creativetab.confluence.curios", "汇流来世 | 饰品");

        add("item.confluence.meteorite_ingot.tooltip", "摸起来是温的");

        add("curios.tooltip.speed_boots", "穿戴者可飞速奔跑");
        add("curios.tooltip.may_fly", "可飞行");
        add("curios.tooltip.jump_boost", "增加跳跃高度");
        add("curios.tooltip.multi_jump", "可让持有者二连跳");
        add("curios.tooltip.negates_fall_damage", "消除掉落伤害");
        add("item.confluence.honey_comb.tooltip", "受到伤害后释放蜜蜂并将使用者浸入蜂蜜中(仍在开发中)");
        add("item.confluence.bezoar.tooltip", "对中毒免疫");
        add("item.confluence.blindfold.tooltip", "对失明免疫");
        add("item.confluence.cobalt_shield.tooltip", "对击退免疫");
        add("item.confluence.band_of_regeneration.tooltip", "缓慢再生生命");
        add("item.confluence.band_of_starpower.tooltip", "最大魔力增加20");
        add("item.confluence.mechanical_lens.tooltip", "给予改良的布线视野");
        add("item.confluence.spectre_goggles.tooltip", "开启回声视觉，显示隐藏物块");
        add("item.confluence.magiluminescence.tooltip", "提高移动速度和加速度(仍在开发中)");
        add("item.confluence.magiluminescence.tooltip2", "穿戴时可提供照明(仍在开发中)");
        add("item.confluence.magiluminescence.tooltip3", "“我黑暗生命中的一道短暂曙光。”");
        add("item.confluence.sandstorm_on_a_bottle.tooltip", "可让持有者更好地二连跳");
        add("item.confluence.ice_skates.tooltip", "提供额外冰面行动力");
        add("item.confluence.ice_skates.tooltip2", "落到冰上时冰不会碎");
        add("item.confluence.dunerider_boots.tooltip", "穿戴者可飞速奔跑，在沙地上还能跑得更快");
        add("item.confluence.dunerider_boots.tooltip2", "“无节律行走就不会引来蠕虫”");
        add("item.confluence.lucky_horseshoe.tooltip", "“据说能带来好运、驱除邪灵”");
        add("item.confluence.lightning_boots.attribute", "移动速度提高8%");
        add("item.confluence.lightning_boots.tooltip", "可飞行、可飞速奔跑");
        add("item.confluence.horseshoe_balloon.tooltip", "增加跳跃高度、消除掉落伤害");

        add("death.attack.falling_star", "%1$s 被坠星压扁了");

        add("biome.confluence.the_corruption", "腐化之地");
        //region blocks
        add(Ores.TIN_ORE.get(), "锡矿石");
        add(Ores.DEEPSLATE_TIN_ORE.get(), "深层锡矿石");
        add(Ores.RAW_TIN_BLOCK.get(), "粗锡块");
        add(Ores.TIN_BLOCK.get(), "锡块");
        add(Ores.LEAD_ORE.get(), "铅矿石");
        add(Ores.DEEPSLATE_LEAD_ORE.get(), "深层铅矿石");
        add(Ores.RAW_LEAD_BLOCK.get(), "粗铅块");
        add(Ores.LEAD_BLOCK.get(), "铅块");
        add(Ores.SILVER_ORE.get(), "银矿石");
        add(Ores.DEEPSLATE_SILVER_ORE.get(), "深层银矿石");
        add(Ores.RAW_SILVER_BLOCK.get(), "粗银块");
        add(Ores.SILVER_BLOCK.get(), "银块");
        add(Ores.TUNGSTEN_ORE.get(), "钨矿石");
        add(Ores.DEEPSLATE_TUNGSTEN_ORE.get(), "深层钨矿石");
        add(Ores.RAW_TUNGSTEN_BLOCK.get(), "粗钨块");
        add(Ores.TUNGSTEN_BLOCK.get(), "钨块");
        add(Ores.PLATINUM_ORE.get(), "铂金矿石");
        add(Ores.DEEPSLATE_PLATINUM_ORE.get(), "深层铂金矿石");
        add(Ores.RAW_PLATINUM_BLOCK.get(), "粗铂金块");
        add(Ores.PLATINUM_BLOCK.get(), "铂金块");
        add(Ores.RUBY_ORE.get(), "红玉矿石");
        add(Ores.DEEPSLATE_RUBY_ORE.get(), "深层红玉矿石");
        add(Ores.TOPAZ_ORE.get(), "黄玉矿石");
        add(Ores.DEEPSLATE_TOPAZ_ORE.get(), "深层黄玉矿石");
        add(Ores.AMBER_ORE.get(), "琥珀矿石");
        add(Ores.DEEPSLATE_AMBER_ORE.get(), "深层琥珀矿石");
        add(Ores.ANOTHER_AMETHYST_ORE.get(), "异域紫晶矿石");
        add(Ores.DEEPSLATE_ANOTHER_AMETHYST_ORE.get(), "深层异域紫晶矿石");
        add(Ores.SAPPHIRE_ORE.get(), "蓝玉矿石");
        add(Ores.DEEPSLATE_SAPPHIRE_ORE.get(), "深层蓝玉矿石");
        add(Ores.METEORITE_ORE.get(), "陨石矿石");
        add(Ores.EBONY_ORE.get(), "魔矿石");
        add(Ores.DEEPSLATE_EBONY_ORE.get(), "深层魔矿石");
        add(Ores.EBONY_BLOCK.get(), "魔矿块");
        add(Ores.RAW_EBONY_BLOCK.get(), "魔原矿块");
        add(Ores.ANOTHER_CRIMSON_ORE.get(), "猩红矿石");
        add(Ores.DEEPSLATE_ANOTHER_CRIMSON_ORE.get(), "深层猩红矿石");
        add(Ores.ANOTHER_CRIMSON_BLOCK.get(), "猩红矿块");
        add(Ores.RAW_ANOTHER_CRIMSON_BLOCK.get(), "猩红原矿块");
        add(Ores.HELLSTONE.get(), "狱石");


        add(EBONY_LOG_BLOCKS.BUTTON.get(), "乌木按钮");
        add(EBONY_LOG_BLOCKS.PLANKS.get(), "乌木板");
        add(EBONY_LOG_BLOCKS.LOG.get(), "乌木原木");
        add(EBONY_LOG_BLOCKS.STRIPPED_LOG.get(), "乌木去皮原木");
        add(EBONY_LOG_BLOCKS.STRIPPED_WOOD.get(), "乌木去皮木");
        add(EBONY_LOG_BLOCKS.DOOR.get(), "乌木门");
        add(EBONY_LOG_BLOCKS.SIGN.get(), "乌木告示牌");
        add(EBONY_LOG_BLOCKS.STAIRS.get(), "乌木楼梯");
        add(EBONY_LOG_BLOCKS.SLAB.get(), "乌木台阶");
        add(EBONY_LOG_BLOCKS.TRAPDOOR.get(), "乌木活板门");
        add(EBONY_LOG_BLOCKS.PRESSURE_PLATE.get(), "乌木压力板");
        add(EBONY_LOG_BLOCKS.WOOD.get(), "乌木");
        add(EBONY_LOG_BLOCKS.LEAVES.get(), "乌木树叶");
        add(EBONY_LOG_BLOCKS.FENCE.get(), "乌木栅栏");
        add(EBONY_LOG_BLOCKS.FENCE_GATE.get(), "乌木栅栏门");


        add(PEARL_LOG_BLOCKS.BUTTON.get(), "珍珠木按钮");
        add(PEARL_LOG_BLOCKS.PLANKS.get(), "珍珠木板");
        add(PEARL_LOG_BLOCKS.LOG.get(), "珍珠木原木");
        add(PEARL_LOG_BLOCKS.STRIPPED_LOG.get(), "珍珠木去皮原木");
        add(PEARL_LOG_BLOCKS.STRIPPED_WOOD.get(), "珍珠木去皮木");
        add(PEARL_LOG_BLOCKS.DOOR.get(), "珍珠木门");
        add(PEARL_LOG_BLOCKS.SIGN.get(), "珍珠木告示牌");
        add(PEARL_LOG_BLOCKS.STAIRS.get(), "珍珠木楼梯");
        add(PEARL_LOG_BLOCKS.SLAB.get(), "珍珠木台阶");
        add(PEARL_LOG_BLOCKS.TRAPDOOR.get(), "珍珠木活板门");
        add(PEARL_LOG_BLOCKS.PRESSURE_PLATE.get(), "珍珠木压力板");
        add(PEARL_LOG_BLOCKS.WOOD.get(), "珍珠木");
        add(PEARL_LOG_BLOCKS.LEAVES.get(), "珍珠木树叶");
        add(PEARL_LOG_BLOCKS.FENCE.get(), "珍珠木栅栏");
        add(PEARL_LOG_BLOCKS.FENCE_GATE.get(), "珍珠木栅栏门");


        add(SHADOW_LOG_BLOCKS.BUTTON.get(), "暗影木按钮");
        add(SHADOW_LOG_BLOCKS.PLANKS.get(), "暗影木板");
        add(SHADOW_LOG_BLOCKS.LOG.get(), "暗影木原木");
        add(SHADOW_LOG_BLOCKS.STRIPPED_LOG.get(), "暗影木去皮原木");
        add(SHADOW_LOG_BLOCKS.STRIPPED_WOOD.get(), "暗影木去皮木");
        add(SHADOW_LOG_BLOCKS.DOOR.get(), "暗影木门");
        add(SHADOW_LOG_BLOCKS.TRAPDOOR.get(), "暗影木活板门");
        add(SHADOW_LOG_BLOCKS.SIGN.get(), "暗影木告示牌");
        add(SHADOW_LOG_BLOCKS.STAIRS.get(), "暗影木楼梯");
        add(SHADOW_LOG_BLOCKS.SLAB.get(), "暗影木台阶");
        add(SHADOW_LOG_BLOCKS.WOOD.get(), "暗影木");
        add(SHADOW_LOG_BLOCKS.LEAVES.get(), "暗影木树叶");
        add(SHADOW_LOG_BLOCKS.FENCE.get(), "暗影木栅栏");
        add(SHADOW_LOG_BLOCKS.FENCE_GATE.get(), "暗影木栅栏门");
        add(SHADOW_LOG_BLOCKS.PRESSURE_PLATE.get(), "暗影木压力板");


        add(PALM_LOG_BLOCKS.BUTTON.get(), "沙漠风情按钮");
        add(PALM_LOG_BLOCKS.PLANKS.get(), "沙漠风情木板");
        add(PALM_LOG_BLOCKS.LOG.get(), "棕榈原木");
        add(PALM_LOG_BLOCKS.DOOR.get(), "沙漠风情木门");
        add(PALM_LOG_BLOCKS.TRAPDOOR.get(), "沙漠风情活板门");
        add(PALM_LOG_BLOCKS.SIGN.get(), "沙漠风情告示牌");
        add(PALM_LOG_BLOCKS.STAIRS.get(), "沙漠风情楼梯");
        add(PALM_LOG_BLOCKS.SLAB.get(), "沙漠风情台阶");
        add(PALM_LOG_BLOCKS.WOOD.get(), "棕榈木");
        add(PALM_LOG_BLOCKS.STRIPPED_LOG.get(), "棕榈去皮原木");
        add(PALM_LOG_BLOCKS.STRIPPED_WOOD.get(), "棕榈去皮木");
        add(PALM_LOG_BLOCKS.LEAVES.get(), "棕榈树叶");
        add(PALM_LOG_BLOCKS.FENCE.get(), "沙漠风情栅栏");
        add(PALM_LOG_BLOCKS.FENCE_GATE.get(), "沙漠风情栅栏门");
        add(PALM_LOG_BLOCKS.PRESSURE_PLATE.get(), "沙漠风情压力板");


        add(ASH_LOG_BLOCKS.BUTTON.get(), "白蜡木按钮");
        add(ASH_LOG_BLOCKS.PLANKS.get(), "白蜡木板");
        add(ASH_LOG_BLOCKS.LOG.get(), "白蜡木原木");
        add(ASH_LOG_BLOCKS.STRIPPED_LOG.get(), "白蜡木去皮原木");
        add(ASH_LOG_BLOCKS.STRIPPED_WOOD.get(), "白蜡木去皮木");
        add(ASH_LOG_BLOCKS.DOOR.get(), "白蜡木门");
        add(ASH_LOG_BLOCKS.TRAPDOOR.get(), "白蜡木活板门");
        add(ASH_LOG_BLOCKS.SIGN.get(), "白蜡木告示牌");
        add(ASH_LOG_BLOCKS.STAIRS.get(), "白蜡木楼梯");
        add(ASH_LOG_BLOCKS.SLAB.get(), "白蜡木台阶");
        add(ASH_LOG_BLOCKS.WOOD.get(), "白蜡木");
        add(ASH_LOG_BLOCKS.LEAVES.get(), "白蜡木树叶");
        add(ASH_LOG_BLOCKS.FENCE.get(), "白蜡木栅栏");
        add(ASH_LOG_BLOCKS.FENCE_GATE.get(), "白蜡木栅栏门");
        add(ASH_LOG_BLOCKS.PRESSURE_PLATE.get(), "白蜡木压力板");



        add(EBONY_STONE.get(), "黑檀石块");
        add(EBONY_SAND.get(), "黑檀沙块");
        add(CORRUPT_GRASS_BLOCK.get(), "腐化草方块");
        add(PEARL_STONE.get(), "珍珠石块");
        add(PEARL_SAND.get(), "珍珠沙块");
        add(HALLOW_GRASS_BLOCK.get(), "神圣草方块");
        add(ANOTHER_CRIMSON_STONE.get(), "猩红石块");
        add(ANOTHER_CRIMSON_SAND.get(), "猩红沙块");
        add(ANOTHER_CRIMSON_GRASS_BLOCK.get(), "猩红草方块");
        add(ASH_BLOCK.get(), "灰烬块");
        add(BIG_RUBY_BLOCK.get(), "大红玉块");
        add(BIG_AMBER_BLOCK.get(), "大琥珀块");
        add(BIG_TOPAZ_BLOCK.get(), "大黄玉块");
        add(BIG_SAPPHIRE_BLOCK.get(), "大蓝玉块");
        add(BIG_ANOTHER_AMETHYST_BLOCK.get(), "大紫晶块");
        add(ANOTHER_POLISHED_GRANITE.get(), "异域花岗岩块");
        add(POLISHED_MARBLE.get(), "异域大理石块");
        add(ANOTHER_COPPER_BRICKS.get(), "铜砖块");
        add(ANOTHER_COPPER_PLATE.get(), "铜条板块");
        add(ANOTHER_CRIMSON_ORE_BRICKS.get(), "猩红矿砖");
        add(ANOTHER_CRIMSON_ROCK_BRICKS.get(), "猩红石砖");
        add(ANOTHER_GOLD_BRICKS.get(), "金砖块");
        add(ANOTHER_IRON_BRICKS.get(), "铁砖块");
        add(ANOTHER_STONE_BRICKS.get(), "异域石砖");
        add(EBONY_ORE_BRICKS.get(), "魔矿砖");
        add(EBONY_ROCK_BRICKS.get(), "黑檀石砖");
        add(BLUE_GEL_BLOCK.get(), "凝胶块");
        add(GREEN_CANDY_BLOCK.get(), "绿色糖块");
        add(ICE_BRICKS.get(), "冰砖块");
        add(FROZEN_GEL_BLOCK.get(), "冻凝胶块");
        add(LEAD_BRICKS.get(), "铅砖块");
        add(METEORITE_BRICKS.get(), "陨石砖块");
        add(PEARL_ROCK_BRICKS.get(), "珍珠石砖");
        add(PINK_GEL_BLOCK.get(), "粉凝胶块");
        add(PLATINUM_BRICKS.get(), "铂金砖块");
        add(RED_CANDY_BLOCK.get(), "红色糖块");
        add(SILVER_BRICKS.get(), "银砖块");
        add(SNOW_BRICKS.get(), "雪砖块");
        add(SUN_PLATE.get(), "日盘块");
        add(TIN_BRICKS.get(), "锡砖块");
        add(TIN_PLATE.get(), "锡条板块");
        add(TUNGSTEN_BRICKS.get(), "钨砖块");
        add(ANOTHER_LAVA_BEAM.get(), "异域熔岩梁");
        add(ANOTHER_LAVA_BRICKS.get(), "异域熔岩砖块");
        add(ANOTHER_OBSIDIAN_BEAM.get(), "异域黑曜石梁");
        add(ANOTHER_OBSIDIAN_BRICKS.get(), "异域黑曜石砖");
        add(ANOTHER_OBSIDIAN_PLATE.get(), "异域黑曜石条板块");
        add(ANOTHER_OBSIDIAN_SMALL_BRICKS.get(), "异域切制黑曜石砖");
        add(ANOTHER_SMOOTH_OBSIDIAN.get(), "异域光滑黑曜石块");
        add(CHISELED_ANOTHER_OBSIDIAN_BRICKS.get(), "錾制异域黑曜石块");
        add(CRYSTAL_BLOCK.get(), "水晶块");
        add(ANOTHER_OAK_BEAM.get(), "经典风情木梁");
        add(ANOTHER_OAK_PLANKS.get(), "经典风情木板");
        add(ANOTHER_NORTHLAND_BEAM.get(), "北地风情木梁");
        add(ANOTHER_NORTHLAND_PLANKS.get(), "北地风情木板");
        add(ANOTHER_GRANITE_COLUMN.get(), "异域花岗岩梁");
        add(MARBLE_COLUMN.get(), "异域大理岩梁");
        add(THIN_ICE_BLOCK.get(), "碎冰块");


        add(SPOOKY_LOG_BLOCKS.PLANKS.get(), "阴森木板");
        add(SPOOKY_LOG_BLOCKS.PRESSURE_PLATE.get(), "阴森木压力板");
        add(SPOOKY_LOG_BLOCKS.FENCE.get(), "阴森木栅栏");
        add(SPOOKY_LOG_BLOCKS.FENCE_GATE.get(), "阴森木栅栏门");
        add(SPOOKY_LOG_BLOCKS.BUTTON.get(), "阴森按钮");
        add(SPOOKY_LOG_BLOCKS.DOOR.get(), "阴森木门");
        add(SPOOKY_LOG_BLOCKS.TRAPDOOR.get(), "阴森木活板门");
        add(SPOOKY_LOG_BLOCKS.SIGN.get(), "阴森告示牌");
        add(SPOOKY_LOG_BLOCKS.STAIRS.get(), "阴森楼梯");
        add(SPOOKY_LOG_BLOCKS.SLAB.get(), "阴森台阶");

        //endregion blocks

        //region items
        add(Materials.RAW_TIN.get(), "粗锡");
        add(Materials.TIN_INGOT.get(), "锡锭");
        add(Materials.RAW_LEAD.get(), "粗铅");
        add(Materials.LEAD_INGOT.get(), "铅锭");
        add(Materials.RAW_SILVER.get(), "粗银");
        add(Materials.SILVER_INGOT.get(), "银锭");
        add(Materials.RAW_TUNGSTEN.get(), "粗钨");
        add(Materials.TUNGSTEN_INGOT.get(), "钨锭");
        add(Materials.RAW_PLATINUM.get(), "粗铂金");
        add(Materials.PLATINUM_INGOT.get(), "铂金锭");
        add(Materials.RAW_METEORITE.get(), "陨铁原矿");
        add(Materials.METEORITE_INGOT.get(), "陨铁锭");
        add(Materials.RAW_EBONY.get(), "粗魔矿");
        add(Materials.EBONY_INGOT.get(), "魔矿锭");
        add(Materials.RAW_ANOTHER_CRIMSON.get(), "粗猩红矿");
        add(Materials.ANOTHER_CRIMSON_INGOT.get(), "猩红矿锭");
        add(Materials.RAW_HELLSTONE.get(), "狱石矿");
        add(Materials.PRIMORDIAL_HELLSTONE_INGOT.get(), "原始狱石矿锭");
        add(Materials.HELLSTONE_INGOT.get(), "狱石矿锭");
        add(Materials.RUBY.get(), "红玉");
        add(Materials.AMBER.get(), "琥珀");
        add(Materials.TOPAZ.get(), "黄玉");
        //add(Materials.ANOTHER_EMERALD.get(), "翡翠");ebony_planks_from_ebony_log_crafting.json
        add(Materials.SAPPHIRE.get(), "蓝玉");
        add(Materials.ANOTHER_AMETHYST.get(), "异域紫晶石");
        add(Materials.FALLING_STAR.get(), "坠落之星");
        add(Materials.CARRION.get(), "腐肉");
        add(Materials.CRYSTALLINE_LENS.get(), "晶状体");
        add(Materials.BLACK_LENS.get(), "黑晶状体");
        add(Materials.CRYSTAL_SHARDS_ITEM.get(), "水晶碎块");
        add(Materials.ANTLION_MANDIBLE.get(), "蚁狮下颚");
        add(Materials.BLINKROOT_GAINS.get(), "闪耀假果");
        add(Materials.DAYBLOOM_POLLEN.get(), "太阳花粉");
        add(Materials.DEATHWEED_FLESH.get(), "死亡舌叶");
        add(Materials.FIREBLOSSOM_BUD.get(), "火焰花蕾");
        add(Materials.MOONGLOW_PETAL.get(), "月光之叶");
        add(Materials.SHIVERTHRON_SHARD.get(), "寒颤棘刺");
        add(Materials.WATERLEAF_POT.get(), "幌菊精华");
        add(Materials.BLACK_INK.get(), "黑墨水");
        add(Materials.SHARK_FIN.get(), "鲨鱼鳍");
        add(Materials.SHADOW_SCALE.get(), "暗影鳞片");
        add(Materials.TISSUE_SAMPLE.get(), "组织样本");
        add(Materials.PURPLE_MUCUS.get(), "紫色黏液");
        add(Materials.MANA_STAR.get(), "魔力水晶");
        add(Materials.LIFE_CRYSTAL.get(), "生命水晶");
        add(Materials.CURSED_FLAME.get(), "诅咒火");
        add(Materials.ICHOR.get(), "灵液");



        add(Swords.COPPER_SHORT_SWORD.get(), "铜短剑");
        add(Swords.COPPER_BOARD_SWORD.get(), "铜阔剑");
        add(Swords.TIN_SHORT_SWORD.get(), "锡短剑");
        add(Swords.TIN_BOARD_SWORD.get(), "锡阔剑");
        add(Swords.LEAD_SHORT_SWORD.get(), "铅短剑");
        add(Swords.LEAD_BOARD_SWORD.get(), "铅阔剑");
        add(Swords.SILVER_SHORT_SWORD.get(), "银短剑");
        add(Swords.SILVER_BOARD_SWORD.get(), "银阔剑");
        add(Swords.TUNGSTEN_SHORT_SWORD.get(), "钨短剑");
        add(Swords.TUNGSTEN_BOARD_SWORD.get(), "钨阔剑");
        add(Swords.PLATINUM_SHORT_SWORD.get(), "铂金短剑");
        add(Swords.PLATINUM_BOARD_SWORD.get(), "铂金阔剑");
        add(Swords.GOLDEN_SHORT_SWORD.get(), "金短剑");
        add(Swords.GOLDEN_BOARD_SWORD.get(), "金阔剑");
        add(Swords.CACTUS_SWORD.get(), "仙人掌剑");

        add(Axes.COPPER_AXE.get(), "铜斧");
        add(Axes.TIN_AXE.get(), "锡斧");
        add(Axes.LEAD_AXE.get(), "铅斧");
        add(Axes.SILVER_AXE.get(), "银斧");
        add(Axes.TUNGSTEN_AXE.get(), "钨斧");
        add(Axes.GOLDEN_AXE.get(), "金斧");
        add(Axes.PLATINUM_AXE.get(), "铂金斧");

        add(Pickaxes.COPPER_PICKAXE.get(), "铜镐");
        add(Pickaxes.TIN_PICKAXE.get(), "锡镐");
        add(Pickaxes.LEAD_PICKAXE.get(), "铅镐");
        add(Pickaxes.SILVER_PICKAXE.get(), "银镐");
        add(Pickaxes.TUNGSTEN_PICKAXE.get(), "钨镐");
        add(Pickaxes.GOLDEN_PICKAXE.get(), "金镐");
        add(Pickaxes.PLATINUM_PICKAXE.get(), "铂金镐");
        /* Hammers */
        add(Hammers. COPPER_HAMMER.get(), "铜锤");
        add(Hammers. TIN_HAMMER.get(), "锡锤");
        add(Hammers. LEAD_HAMMER.get(), "铅锤");
        add(Hammers. SILVER_HAMMER.get(), "银锤");
        add(Hammers. TUNGSTEN_HAMMER.get(), "钨锤");
        add(Hammers. GOLDEN_HAMMER.get(), "金锤");
        add(Hammers. PLATINUM_HAMMER.get(), "铂金锤");

        /* HammerAxes */
        add(Gels.BLUE_GEL.get(), "蓝色粘液球");
        add(Gels.PINK_GEL.get(), "粉色粘液球");
        add(Gels.FROZEN_GEL.get(), "冰冻粘液球");
        add(Gels.HONEY_GEL.get(), "蜂蜜粘液球");

        add(APPLE_JUICE.get(), "苹果汁");
        add(BLACKCURRANT.get(), "黑醋栗");
        add(BLOOD_ORANGE.get(), "血橙");
        add(BLOODY_MOSCATO.get(), "猩红麝香葡萄");
        add(ELDERBERRY.get(), "接骨木果");
        add(ICE_MIRROR.get(), "冰雪镜");
        add(LESSER_HEALING_POTION.get(), "弱效治疗药水");
        add(HEALING_POTION.get(), "治疗药水");
        add(GREATER_HEALING_POTION.get(), "强效治疗药水");
        add(SUPER_HEALING_POTION.get(), "超级治疗药水");
        add(LESSER_MANA_POTION.get(), "弱效魔力药水");
        add(MANA_POTION.get(), "魔力药水");
        add(GREATER_MANA_POTION.get(), "强效魔力药水");
        add(SUPER_MANA_POTION.get(), "超级魔力药水");
        add(MAGIC_MIRROR.get(), "魔镜");
        add(ROPE.get(), "绳子");
        add(ROPE_COIL.get(), "绳圈");
        add(SHURIKEN.get(), "手里剑");
        //法杖
        add(ManaWeapons.RUBY_STAFF.get(), "红玉法杖");
        add(ManaWeapons.AMBER_STAFF.get(), "琥珀法杖");
        add(ManaWeapons.TOPAZ_STAFF.get(), "黄玉法杖");
        add(ManaWeapons.EMERALD_STAFF.get(), "翡翠法杖");
        add(ManaWeapons.SAPPHIRE_STAFF.get(), "蓝玉法杖");
        add(ManaWeapons.DIAMOND_STAFF.get(), "钻石法杖");
        add(ManaWeapons.AMETHYST_STAFF.get(), "紫晶法杖");
        add(ManaWeapons.AQUA_SCEPTER.get(), "海蓝权杖");
        add(ManaWeapons.WOND_OF_SPARKING.get(), "火花魔棒");
        add(ManaWeapons.WOND_OF_FROSTING.get(), "霜冻魔棒");

        //刷怪蛋
        add(SpawnEggs.BLUE_SLIME_SPAWN_EGG.get(),"蓝色史莱姆刷怪蛋");
        add(SpawnEggs.RED_SLIME_SPAWN_EGG.get(),"红色史莱姆刷怪蛋");
        add(SpawnEggs.YELLOW_SLIME_SPAWN_EGG.get(),"黄色史莱姆刷怪蛋");
        add(SpawnEggs.PURPLE_SLIME_SPAWN_EGG.get(),"紫色史莱姆刷怪蛋");
        add(SpawnEggs.DESERT_SLIME_SPAWN_EGG.get(),"沙漠史莱姆刷怪蛋");
        add(SpawnEggs.PINK_SLIME_SPAWN_EGG.get(),"粉色史莱姆刷怪蛋");
        add(SpawnEggs.ICE_SLIME_SPAWN_EGG.get(),"冰冻史莱姆刷怪蛋");
        add(SpawnEggs.GREEN_SLIME_SPAWN_EGG.get(),"绿色史莱姆刷怪蛋");
        add(SpawnEggs.BLACK_SLIME_SPAWN_EGG.get(),"史莱姆之母刷怪蛋");
        add(SpawnEggs.CRIMSON_SLIME_SPAWN_EGG.get(),"猩红史莱姆刷怪蛋");
        add(SpawnEggs.TROPIC_SLIME_SPAWN_EGG.get(),"热带史莱姆刷怪蛋");
        add(SpawnEggs.LUMINOUS_SLIME_SPAWN_EGG.get(),"夜明史莱姆刷怪蛋");
        add(SpawnEggs.LAVA_SLIME_SPAWN_EGG.get(),"岩浆史莱姆刷怪蛋");
        //光剑
        add(Swords.RED_LIGHT_SABER.get(),"陨石红光剑");
        add(Swords.ORANGE_LIGHT_SABER.get(),"陨石橙光剑");
        add(Swords.YELLOW_LIGHT_SABER.get(),"陨石黄光剑");
        add(Swords.GREEN_LIGHT_SABER.get(),"陨石绿光剑");
        add(Swords.BLUE_LIGHT_SABER.get(),"陨石蓝光剑");
        add(Swords.PURPLE_LIGHT_SABER.get(),"陨石紫光剑");
        add(Swords.WHITE_LIGHT_SABER.get(),"陨石白光剑");

        //功能方块
        add(ECHO_BLOCK.get(),"回声块");
        add(ACTUATORS.get(),"促动器");
        //饰品
        add(CurioItems.MECHANICAL_LENS.get(),"机械晶状体");
        add(CurioItems.SPECTRE_GOGGLES.get(),"幽灵护目镜");
        add(CurioItems.AGLET.get(),"金属带扣");
        add(CurioItems.ANKLET_OF_THE_WIND.get(),"疾风脚镯");
        add(CurioItems.CLOUD_IN_A_BOTTLE.get(),"云朵瓶");
        add(CurioItems.SHACKLE.get(),"脚镣");
        add(CurioItems.BAND_OF_REGENERATION.get(),"再生手环");
        add(CurioItems.BAND_OF_STARPOWER.get(),"星力手环");
        add(CurioItems.EXTENDO_GRIP.get(),"加长握爪");
        add(CurioItems.BLIZZARD_IN_A_BOTTLE.get(),"暴雪瓶");
        add(CurioItems.ICE_SKATES.get(),"溜冰鞋");
        add(CurioItems.HERMES_BOOTS.get(),"赫尔墨斯靴");
        add(CurioItems.FLURRY_BOOTS.get(),"疾风雪靴");
        add(CurioItems.SAILFISH_BOOTS.get(),"旗鱼靴");
        add(CurioItems.DUNERIDER_BOOTS.get(),"沙丘行者靴");
        add(CurioItems.ROCKET_BOOTS.get(),"火箭靴");
        add(CurioItems.SPECTRE_BOOTS.get(),"幽灵靴");
        add(CurioItems.LIGHTNING_BOOTS.get(),"闪电靴");
        add(CurioItems.FROSTSPARK_BOOTS.get(),"霜花靴");
        add(CurioItems.SHINY_RED_BALLOON.get(),"闪亮红气球");
        add(CurioItems.CLOUD_IN_A_BALLOON.get(),"云朵气球");
        add(CurioItems.BLIZZARD_IN_A_BALLOON.get(),"暴雪气球");
        add(CurioItems.FROG_LEG.get(),"蛙腿");
        add(CurioItems.AMBHIPIAN_BOOTS.get(),"水陆两用靴");
        add(CurioItems.LUCKY_HORSESHOE.get(),"幸运马掌");
        add(CurioItems.HONEY_COMB.get(), "蜂窝");
        add(CurioItems.BEZOAR.get(), " 牛黄");
        add(CurioItems.BLACK_BELT.get(), "黑腰带");
        add(CurioItems.MAGILUMINESCENCE.get(), "魔光护符");
        add(CurioItems.FLIPPER.get(), "脚蹼");
        add(CurioItems.SANDSTORM_IN_A_BOTTLE.get(), "沙暴瓶");
        add(CurioItems.FART_IN_A_BOTTLE.get(), "罐中臭屁");
        add(CurioItems.SANDSTORM_IN_A_BALLOON.get(), "沙暴气球");
        add(CurioItems.FART_IN_A_BALLOON.get(), "臭屁气球");
        add(CurioItems.FROG_FLIPPER.get(), "青蛙脚蹼");
        add(CurioItems.BLUE_HORSESHOE_BALLOON.get(), "蓝马掌气球");
        add(CurioItems.WHITE_HORSESHOE_BALLOON.get(), "白马掌气球");
        add(CurioItems.YELLOW_HORSESHOE_BALLOON.get(), "黄马掌气球");
        add(CurioItems.GREEN_HORSESHOE_BALLOON.get(), "绿马掌气球");
        add(CurioItems.BLINDFOLD.get(), "蒙眼布");
        add(CurioItems.AVENGER_EMBLEM.get(), "复仇者勋章");
        add(CurioItems.COBALT_SHIELD.get(), "钴护盾");
        add(CurioItems.CROSS_NECKLACE.get(), "十字项链");
        add(CurioItems.DESTROYER_EMBLEM.get(), "毁灭者勋章");
        add(CurioItems.EYE_OF_THE_GOLEM.get(), "石巨人之眼");
        add(CurioItems.PINK_HORSESHOE_BALLOON.get(), "粉马掌气球");
        add(CurioItems.SHARKRON_BALLOON.get(), "鲨鱼龙气球");
        add(CurioItems.BALLOON_PUFFERFISH.get(), "气球河豚鱼");
        add(CurioItems.TSUNAMI_IN_A_BOTTLE.get(), "海啸瓶");
        add(CurioItems.MAGMA_SKULL.get(), "岩浆骷髅头");
        add(CurioItems.LAVA_CHARM.get(), "熔岩护身符");
        add(CurioItems.OBSIDIAN_ROSE.get(), "黑曜石玫瑰");
        add(CurioItems.OBSIDIAN_SHIELD.get(), "黑曜石护盾");
        add(CurioItems.OBSIDIAN_SKULL.get(), "黑曜石骷髅头");
        add(CurioItems.MOLTEN_SKULL_ROSE.get(), "熔火骷髅头玫瑰");
        add(CurioItems.OBSIDIAN_SKULL_ROSE.get(), "黑曜石骷髅头玫瑰");

        //盔甲
        add(Armors.CACTUS_CHESTPLATE.get(),"仙人掌胸甲");
        add(Armors.CACTUS_HELMET.get(),"仙人掌头盔");
        add(Armors.CACTUS_LEGGINGS.get(),"仙人掌护腿");
        add(Armors.CACTUS_BOOTS.get(),"仙人掌靴子");
        add(Armors.PLANK_CHESTPLATE.get(),"木制胸甲");
        add(Armors.PLANK_HELMET.get(),"木制头盔");
        add(Armors.PLANK_LEGGINGS.get(),"木制护腿");
        add(Armors.PLANK_BOOTS.get(),"木制靴子");
        add(Armors.RAIN_CAP.get(),"雨帽");
        add(Armors.RAINCOAT.get(),"雨衣");
        add(Armors.SNOW_CAPS.get(),"防雪帽");
        add(Armors.SNOW_SUITS.get(),"防雪衣");
        add(Armors.INSULATED_PANTS.get(),"保温裤");
        add(Armors.INSULATED_SHOES.get(),"保温鞋");
        add(Armors.SNOW_PINK_CAPS.get(),"粉色防雪帽");
        add(Armors.SNOW_PINK_SUITS.get(),"粉色防雪衣");
        add(Armors.COPPER_HELMET.get(),"铜头盔");
        add(Armors.COPPER_CHESTPLATE.get(),"铜胸甲");
        add(Armors.COPPER_LEGGINGS.get(),"铜护腿");
        add(Armors.COPPER_BOOTS.get(),"铜靴子");
        add(Armors.TIN_HELMET.get(),"锡头盔");
        add(Armors.TIN_CHESTPLATE.get(),"锡胸甲");
        add(Armors.TIN_LEGGINGS.get(),"锡护腿");
        add(Armors.TIN_BOOTS.get(),"锡靴子");

        //endregion items
    }
}
