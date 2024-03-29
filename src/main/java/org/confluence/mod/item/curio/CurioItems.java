package org.confluence.mod.item.curio;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.combat.Bezoar;
import org.confluence.mod.item.curio.combat.HoneyComb;
import org.confluence.mod.item.curio.combat.Shackle;
import org.confluence.mod.item.curio.construction.ExtendoGrip;
import org.confluence.mod.item.curio.healthandmana.BandOfRegeneration;
import org.confluence.mod.item.curio.healthandmana.BandOfStarpower;
import org.confluence.mod.item.curio.informational.MechanicalLens;
import org.confluence.mod.item.curio.miscellaneous.SpectreGoggles;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum CurioItems implements EnumRegister<BaseCurioItem> {
    SHACKLE("shackle", Shackle::new), // 脚镣
    HONEY_COMB("honey_comb", HoneyComb::new), // 蜂窝 (WIP)
    BEZOAR("bezoar", Bezoar::new), // 牛黄
    BLACK_BELT("black_belt"), // 黑腰带

    EXTENDO_GRIP("extendo_grip", ExtendoGrip::new), // 加长握爪

    BAND_OF_REGENERATION("band_of_regeneration", BandOfRegeneration::new), // 再生手环
    BAND_OF_STARPOWER("band_of_starpower", BandOfStarpower::new), // 星力手环

    MECHANICAL_LENS("mechanical_lens", MechanicalLens::new), // 机械晶状体

    SPECTRE_GOGGLES("spectre_goggles", SpectreGoggles::new), // 幽灵护目镜

    AGLET("aglet", Aglet::new), // 金属带扣
    ANKLET_OF_THE_WIND("anklet_of_the_wind", AnkletOfTheWind::new), // 疾风脚镯
    MAGILUMINESCENCE("magiluminescence", Magiluminescence::new), // 魔光护符 (WIP)
    FLIPPER("flipper", Flipper::new), // 脚蹼
    /* 攀爬爪 */
    /* 鞋钉 */
    /* 猛虎攀爬装备 */
    CLOUD_IN_A_BOTTLE("cloud_in_a_bottle", CloudInABottle::new), // 云朵瓶
    BLIZZARD_IN_A_BOTTLE("blizzard_in_a_bottle", BlizzardInABottle::new), // 暴雪瓶
    SANDSTORM_IN_A_BOTTLE("sandstorm_in_a_bottle", SandstormInABottle::new), // 沙暴瓶
    FART_IN_A_BOTTLE("fart_in_a_bottle", FartInABottle::new), // 罐中臭屁
    ICE_SKATES("ice_skates", IceSkates::new), // 溜冰鞋
    HERMES_BOOTS("hermes_boots", BaseSpeedBoots::new), // 赫尔墨斯靴
    FLURRY_BOOTS("flurry_boots", BaseSpeedBoots::new), // 疾风雪靴
    SAILFISH_BOOTS("sailfish_boots", BaseSpeedBoots::new), // 旗鱼靴
    DUNERIDER_BOOTS("dunerider_boots", DuneriderBoots::new), // 沙丘行者靴
    ROCKET_BOOTS("rocket_boots", RocketBoots::new), // 火箭靴
    SPECTRE_BOOTS("spectre_boots", SpectreBoots::new), // 幽灵靴
    LIGHTNING_BOOTS("lightning_boots", LightningBoots::new), // 闪电靴
    FROSTSPARK_BOOTS("frostspark_boots", FrostSparkBoots::new), // 霜花靴
    SHINY_RED_BALLOON("shiny_red_balloon", ShinyRedBalloon::new), // 闪亮红气球
    CLOUD_IN_A_BALLOON("cloud_in_a_balloon", CloudInABalloon::new), // 云朵气球
    BLIZZARD_IN_A_BALLOON("blizzard_in_a_balloon", BlizzardInABalloon::new), // 暴雪气球
    SANDSTORM_IN_A_BALLOON("sandstorm_in_a_balloon", SandstormInABalloon::new), // 沙暴气球
    FART_IN_A_BALLOON("fart_in_a_balloon", FartInABalloon::new), // 臭屁气球
    FROG_LEG("frog_leg", FrogLeg::new), // 蛙腿
    FROG_FLIPPER("frog_flipper", FrogFlipper::new), // 青蛙脚蹼
    /* 青蛙蹼 */
    /* 青蛙装备 */
    AMBHIPIAN_BOOTS("ambhipian_boots", AmbhipianBoots::new), // 水陆两用靴
    LUCKY_HORSESHOE("lucky_horseshoe", LuckyHorseshoe::new), // 幸运马掌
    BLUE_HORSESHOE_BALLOON("blue_horseshoe_balloon", BlueHorseshoeBalloon::new), // 蓝马掌气球
    WHITE_HORSESHOE_BALLOON("white_horseshoe_balloon", WhiteHorseshoeBalloon::new), // 白马掌气球
    YELLOW_HORSESHOE_BALLOON("yellow_horseshoe_balloon", YellowHorseshoeBalloon::new), // 黄马掌气球
    GREEN_HORSESHOE_BALLOON("green_horseshoe_balloon", GreenHorseshoeBalloon::new), // 绿马掌气球
    ;

    private final RegistryObject<BaseCurioItem> value;

    CurioItems(String id) {
        this(id, BaseCurioItem::new);
    }

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
