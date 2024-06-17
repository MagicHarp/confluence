package org.confluence.mod.item.curio;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.construction.AncientChisel;
import org.confluence.mod.item.curio.construction.ExtendoGrip;
import org.confluence.mod.item.curio.expert.*;
import org.confluence.mod.item.curio.fishing.AnglerEarring;
import org.confluence.mod.item.curio.health.BandOfRegeneration;
import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.item.curio.missellaneous.FlowerBoots;
import org.confluence.mod.item.curio.missellaneous.TreasureMagnet;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum CurioItems implements EnumRegister<BaseCurioItem> {
    //ADHESIVE_BANDAGE("adhesive_bandage", AdhesiveBandage::new), // 粘性绷带
    BEZOAR("bezoar", Bezoar::new), // 牛黄
    //MEDICATED_BANDAGE("medicated_bandage", MedicatedBandage::new), // 药用绷带
    BLINDFOLD("blindfold", Blindfold::new), // 蒙眼布
    //POCKET_MIRROR("pocket_mirror", PocketMirror::new), // 袖珍镜
    //REFLECTIVE_SHADES("reflective_shades", ReflectiveShades::new), // 反光墨镜
    VITAMINS("vitamins", Vitamins::new), // 维生素
    //ARMOR_POLISH("armor_polish", ArmorPolish::new), // 盔甲抛光剂
    //ARMOR_BRACING("armor_bracing", ArmorBracing::new), // 盔甲背带
    FAST_CLOCK("fast_clock", FastClock::new), // 快走时钟
    //TRIFOLD_MAP("trifold_map", TrifoldMap::new), // 三折地图
    //THE_PLAN("the_plan", ThePlan::new), // 计划书
    //MEGAPHONE("megaphone", Megaphone::new), // 扩音器
    //NAZAR("nazar", Nazar::new), // 邪眼
    //COUNTERCURSE_MANTRA("contercurse_mantra", CountercurseMantra::new), // 反诅咒咒语
    //ANKH_CHARM("ankh_charm", AnkhCharm::new), // 十字章护身符
    //ANKH_SHIELD("ankh_shield", AnkhShield::new), // 十字章护盾
    STAR_CLOAK("star_cloak", StarCloak::new), // 星星斗篷
    STAR_VEIL("star_veil", StarVeil::new), // 星星面纱
    BEE_CLOAK("bee_cloak", BeeCloak::new), // 蜜蜂斗篷
    BLACK_BELT("black_belt", BlackBelt::new), // 黑腰带
    /* 天界徽章 */
    /* 月光护身符 */
    SUN_STONE("sun_stone", SunStone::new), // 太阳石 (WIP)
    MOON_STONE("moon_stone", MoonStone::new), // 月亮石 (WIP)
    CELESTIAL_STONE("celestial_stone", CelestialStone::new), // 天界石
    /* 月亮贝壳 */
    /* 天界贝壳 */
    COBALT_SHIELD("cobalt_shield", CobaltShield::new), // 钴护盾
    CROSS_NECKLACE("cross_necklace", CrossNecklace::new), // 十字项链
    RANGER_EMBLEM("ranger_emblem", RangerEmblem::new), // 游侠徽章
    /* 召唤师徽章 */
    WARRIOR_EMBLEM("warrior_emblem", WarriorEmblem::new), // 战士徽章
    SORCERER_EMBLEM("sorcerer_emblem", SorcererEmblem::new), // 巫士徽章
    AVENGER_EMBLEM("avenger_emblem", AvengerEmblem::new), // 复仇者勋章
    EYE_OF_THE_GOLEM("eye_of_the_golem", EyeOfTheGolem::new), // 石巨人之眼
    DESTROYER_EMBLEM("destroyer_emblem", DestroyerEmblem::new), // 毁灭者勋章
    FERAL_CLAWS("feral_claws", FeralClaws::new), // 狂爪手套
    TITAN_GLOVE("titan_glove", TitanGlove::new), // 泰坦手套
    POWER_GLOVE("power_glove", PowerGlove::new), // 强力手套
    MECHANICAL_GLOVE("mechanical_glove", MechanicalGlove::new), // 机械手套
    FIRE_GAUNTLET("fire_gauntlet", FireGauntlet::new), // 烈火手套
    FLESH_KNUCKLES("flesh_knuckles", FleshKnuckles::new), // 血肉指虎
    BERSERKERS_GLOVE("berserkers_glove", BerserkersGlove::new), // 狂战士手套
    PALADINS_SHIELD("paladins_shield", PaladinsShield::new), // 圣骑士护盾
    HERO_SHIELD("hero_shield", HeroShield::new), // 英雄护盾
    FROZEN_TURTLE_SHELL("frozen_turtle_shell", FrozenTurtleShell::new), // 冰冻海龟壳
    FROZEN_SHIELD("frozen_shield", FrozenShield::new), // 冰冻护盾
    HONEY_COMB("honey_comb", HoneyComb::new), // 蜂窝
    SHARK_TOOTH_NECKLACE("shark_tooth_necklace", SharkToothNecklace::new), // 鲨牙项链
    STINGER_NECKLACE("stinger_necklace", StingerNecklace::new), // 毒刺项链
    PANIC_NECKLACE("panic_necklace", PanicNecklace::new), // 恐慌项链
    SWEETHEART_NECKLACE("sweetheart_necklace", SweetheartNecklace::new), // 甜心项链
    MAGIC_QUIVER("magic_quiver", MagicQuiver::new), // 魔法箭袋
    MOLTEN_QUIVER("molten_quiver", MoltenQuiver::new), // 熔火箭袋
    STALKERS_QUIVER("stalkers_quiver", StalkersQuiver::new), // 潜行者箭袋
    /* 侦察镜 */
    /* 步枪瞄准镜 */
    /* 狙击镜 */
    MAGMA_STONE("magma_stone", MagmaStone::new), // 岩浆石
    OBSIDIAN_ROSE("obsidian_rose", ObsidianRose::new), // 黑曜石玫瑰
    OBSIDIAN_SHIELD("obsidian_shield", ObsidianShield::new), // 黑曜石护盾
    OBSIDIAN_SKULL("obsidian_skull", ObsidianSkull::new), // 黑曜石骷髅头
    MOLTEN_SKULL_ROSE("molten_skull_rose", MoltenSkullRose::new), // 熔火骷髅头玫瑰
    OBSIDIAN_SKULL_ROSE("obsidian_skull_rose", ObsidianSkullRose::new), // 黑曜石骷髅头玫瑰
    HAND_WARMER("hand_warmer", HandWarmer::new), // 暖手宝
    PUTRID_SCENT("putrid_scent", PutridScent::new), // 腐香囊
    SHACKLE("shackle", Shackle::new), // 脚镣
    /* 学徒围巾 */
    /* 侍卫护盾 */
    /* 女猎人圆盾 */
    /* 武僧腰带 */
    /* 大力士甲虫 */
    /* 死灵卷轴 */
    /* 甲虫莎草纸 */
    /* 矮人项链 */


    /* 工具腰带 */
    /* 工具箱 */
    /* 喷漆器 */
    EXTENDO_GRIP("extendo_grip", ExtendoGrip::new), // 加长握爪
    /* 便携式水泥搅拌机 */
    /* 砌砖刀 */
    /* 建筑师发明背包 */
    /* 自动安放器 */
    ANCIENT_CHISEL("ancient_chisel", AncientChisel::new), // 远古凿子
    /* 创造之手 */

    BAND_OF_REGENERATION("band_of_regeneration", BandOfRegeneration::new), // 再生手环

    COPPER_WATCH("copper_watch", HourWatch::new), // 铜表
    TIN_WATCH("tin_watch", HourWatch::new), // 锡表
    SILVER_WATCH("silver_watch", HalfHourWatch::new), // 银表
    TUNGSTEN_WATCH("tungsten_watch", HalfHourWatch::new), // 钨表
    GOLDEN_WATCH("golden_watch", MinuteWatch::new), // 金表
    PLATINUM_WATCH("platinum_watch", MinuteWatch::new), // 铂金表
    DEPTH_METER("depth_meter", DepthMeter::new), // 深度计
    COMPASS("compass", Compass::new), // 罗盘
    RADAR("radar", Radar::new), // 雷达
    LIFE_FORM_ANALYZER("life_form_analyzer", LifeFormAnalyzer::new), // 生命体分析机
    TALLY_COUNTER("tally_counter", TallyCounter::new), // 杀怪计数器
    METAL_DETECTOR("metal_detector", MetalDetector::new), // 金属探测器
    STOPWATCH("stopwatch", Stopwatch::new), // 秒表
    DPS_METER("dps_meter", DPSMeter::new), // 每秒伤害计数器
    FISHERMANS_POCKET_GUIDE("fishermans_pocket_guide", FishermansPocketGuide::new), // 渔民袖珍宝典
    WEATHER_RADIO("weather_radio", WeatherRadio::new), // 天气收音机
    SEXTANT("sextant", Sextant::new), // 六分仪
    GPS("gps", GPS::new), // 全球定位系统
    REK_3000("rek_3000", REK3000::new), // R.E.K.3000
    GOBLIN_TECH("goblin_tech", GoblinTech::new), // 哥布林数据仪
    FISH_FINDER("fish_finder", FishFinder::new), // 探鱼器
    PDA("pda", PDA::new), // 个人数字助手
    //MECHANICAL_LENS("mechanical_lens", MechanicalLens::new), // 机械晶状体
    /* 标尺 */
    /* 机械标尺 */


    /* 梯凳 */
    /* 飞毯 */
    AGLET("aglet", Aglet::new), // 金属带扣
    ANKLET_OF_THE_WIND("anklet_of_the_wind", AnkletOfTheWind::new), // 疾风脚镯
    MAGILUMINESCENCE("magiluminescence", Magiluminescence::new), // 魔光护符
    LAVA_CHARM("lava_charm", LavaCharm::new), // 熔岩护身符
    MAGMA_SKULL("magma_skull", MagmaSkull::new), // 岩浆骷髅头
    MOLTEN_CHARM("molten_charm", MoltenCharm::new), // 熔火护身符
    /* 攀爬爪 */
    /* 鞋钉 */
    /* 猛虎攀爬装备 */
    /* 分趾厚底袜 */
    /* 忍者大师装备 */
    ICE_SKATES("ice_skates", IceSkates::new), // 溜冰鞋
    HERMES_BOOTS("hermes_boots", BaseSpeedBoots::new), // 赫尔墨斯靴
    FLURRY_BOOTS("flurry_boots", BaseSpeedBoots::new), // 疾风雪靴
    SAILFISH_BOOTS("sailfish_boots", BaseSpeedBoots::new), // 旗鱼靴
    DUNERIDER_BOOTS("dunerider_boots", DuneriderBoots::new), // 沙丘行者靴
    ROCKET_BOOTS("rocket_boots", RocketBoots::new), // 火箭靴
    SPECTRE_BOOTS("spectre_boots", SpectreBoots::new), // 幽灵靴
    FAIRY_BOOTS("fairy_boots", FairyBoots::new), // 仙灵靴
    LIGHTNING_BOOTS("lightning_boots", LightningBoots::new), // 闪电靴
    FROSTSPARK_BOOTS("frostspark_boots", FrostsparkBoots::new), // 霜花靴
    WATER_WALKING_BOOTS("water_walking_boots", WaterWalkingBoots::new), // 水上漂靴
    OBSIDIAN_WATER_WALKING_BOOTS("obsidian_water_walking_boots", ObsidianWaterWalkingBoots::new), // 黑曜石水上漂靴
    LAVA_WADERS("lava_waders", LavaWaders::new), // 熔岩靴
    TERRASPARK_BOOTS("terraspark_boots", TerrasparkBoots::new), // 泰拉闪耀靴
    CLOUD_IN_A_BOTTLE("cloud_in_a_bottle", CloudInABottle::new), // 云朵瓶
    TSUNAMI_IN_A_BOTTLE("tsunami_in_a_bottle", TsunamiInABottle::new), // 海啸瓶
    BLIZZARD_IN_A_BOTTLE("blizzard_in_a_bottle", BlizzardInABottle::new), // 暴雪瓶
    SANDSTORM_IN_A_BOTTLE("sandstorm_in_a_bottle", SandstormInABottle::new), // 沙暴瓶
    FART_IN_A_BOTTLE("fart_in_a_bottle", FartInABottle::new), // 罐中臭屁
    SHINY_RED_BALLOON("shiny_red_balloon", Balloon::new), // 闪亮红气球
    BALLOON_PUFFERFISH("balloon_pufferfish", Balloon::new), // 气球河豚鱼
    CLOUD_IN_A_BALLOON("cloud_in_a_balloon", CloudInABalloon::new), // 云朵气球
    BLIZZARD_IN_A_BALLOON("blizzard_in_a_balloon", BlizzardInABalloon::new), // 暴雪气球
    SANDSTORM_IN_A_BALLOON("sandstorm_in_a_balloon", SandstormInABalloon::new), // 沙暴气球
    FART_IN_A_BALLOON("fart_in_a_balloon", FartInABalloon::new), // 臭屁气球
    SHARKRON_BALLOON("sharkron_balloon", SharkronBalloon::new), // 鲨鱼龙气球
    HONEY_BALLOON("honey_balloon", HoneyBalloon::new), // 蜂蜜气球
    BUNDLE_OF_BALLOONS("bundle_of_balloons", BundleOfBalloons::new), // 气球束
    LUCKY_HORSESHOE("lucky_horseshoe", LuckyHorseshoe::new), // 幸运马掌
    OBSIDIAN_HORSESHOE("obsidian_horseshoe", ObsidianHorseshoe::new), // 黑曜石马掌
    BLUE_HORSESHOE_BALLOON("blue_horseshoe_balloon", BlueHorseshoeBalloon::new), // 蓝马掌气球
    WHITE_HORSESHOE_BALLOON("white_horseshoe_balloon", WhiteHorseshoeBalloon::new), // 白马掌气球
    YELLOW_HORSESHOE_BALLOON("yellow_horseshoe_balloon", YellowHorseshoeBalloon::new), // 黄马掌气球
    GREEN_HORSESHOE_BALLOON("green_horseshoe_balloon", GreenHorseshoeBalloon::new), // 绿马掌气球
    PINK_HORSESHOE_BALLOON("pink_horseshoe_balloon", PinkHorseshoeBalloon::new), // 粉马掌气球
    AMBER_HORSESHOE_BALLOON("amber_horseshoe_balloon", AmberHorseshoeBalloon::new), // 琥珀马掌气球
    BUNDLE_OF_HORSESHOE_BALLOONS("bundle_of_horseshoe_balloons", BundleOfHorseshoeBalloons::new), // 马掌气球束
    /* 浮游圈 */
    FLIPPER("flipper", Flipper::new), // 脚蹼
    /* 潜水装备 */
    /* 水母潜水装备 */
    /* 北极潜水装备 */
    FROG_LEG("frog_leg", FrogLeg::new), // 蛙腿
    FROG_FLIPPER("frog_flipper", FrogFlipper::new), // 青蛙脚蹼
    /* 青蛙蹼 */
    /* 青蛙装备 */
    AMBHIPIAN_BOOTS("ambhipian_boots", AmbhipianBoots::new), // 水陆两用靴


    /* 向导巫毒娃娃 */
    /* 服装商巫毒娃娃 */
    //LUCKY_COIN("lucky_coin", LuckyCoin::new), // 幸运币
    //GOLD_RING("gold_ring", GoldRing::new), // 金戒指
    //COIN_RING("coin_ring", CoinRing::new), // 钱币戒指
    /* 优惠卡 */
    /* 贪婪戒指 */
    TREASURE_MAGNET("treasure_magnet", TreasureMagnet::new), // 宝藏磁石
    FLOWER_BOOTS("flower_boots", FlowerBoots::new), // 花靴
    /* 植物纤维绳索宝典 */
    /* 水母项链 */
    /* 收音机 */
    //SPECTRE_GOGGLES("spectre_goggles", SpectreGoggles::new), // 幽灵护目镜
    /* 炫彩斗篷 */

    ANGLER_EARRING("angler_earring", AnglerEarring::new), // 渔夫耳环

    ROYAL_GEL("royal_gel", RoyalGel::new), // 皇家凝胶
    SHIELD_OF_CTHULHU("shield_of_cthulhu", ShieldOfCthulhu::new), // 克苏鲁护盾
    WORM_SCARF("worm_scarf", WormScarf::new), // 蠕虫围巾
    BRAIN_OF_CONFUSION("brain_of_confusion", BrainOfConfusion::new), // 混乱之脑
    HIVE_PACK("hive_pack", HivePack::new), // 蜂巢背包
    /* 骨头手套 */
    /* 骸骨头盔 */
    /* 挥发明胶 */
    /* 孢子囊 */
    /* 闪亮石 */
    /* 翱翔徽章 */
    GRAVITY_GLOBE("gravity_globe", GravityGlobe::new); // 重力球

    private final RegistryObject<BaseCurioItem> value;

    CurioItems(String id, Supplier<BaseCurioItem> curio) {
        this.value = ModItems.ITEMS.register(id, curio);
    }

    @Override
    public RegistryObject<BaseCurioItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering curio items");
    }
}
