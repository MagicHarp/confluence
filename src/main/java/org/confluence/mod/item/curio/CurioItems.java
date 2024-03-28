package org.confluence.mod.item.curio;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
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

    EXTENDO_GRIP("extendo_grip", ExtendoGrip::new), // 加长握爪

    BAND_OF_REGENERATION("band_of_regeneration", BandOfRegeneration::new), // 再生手环
    BAND_OF_STARPOWER("band_of_starpower", BandOfStarpower::new), // 星力手环

    MECHANICAL_LENS("mechanical_lens", MechanicalLens::new), // 机械晶状体

    SPECTRE_GOGGLES("spectre_goggles", SpectreGoggles::new), // 幽灵护目镜

    AGLET("aglet", Aglet::new), // 金属带扣
    ANKLET_OF_THE_WIND("anklet_of_the_wind", AnkletOfTheWind::new), // 疾风脚镯
    CLOUD_IN_A_BOTTLE("cloud_in_a_bottle", CloudInABottle::new), // 云朵瓶
    HERMES_BOOTS("hermes_boots", BaseSpeedBoots::new), // 赫尔墨斯靴
    FLURRY_BOOTS("flurry_boots", BaseSpeedBoots::new), // 疾风雪靴
    SAILFISH_BOOTS("sailfish_boots", BaseSpeedBoots::new), // 旗鱼靴
    DUNERIDER_BOOTS("dunerider_boots", DuneriderBoots::new), // 沙丘行者靴
    ROCKET_BOOTS("rocket_boots", RocketBoots::new), // 火箭靴
    SPECTRE_BOOTS("spectre_boots", SpectreBoots::new), // 幽灵靴
    SHINY_RED_BALLOON("shiny_red_balloon", ShinyRedBalloon::new), // 闪亮红气球
    CLOUD_IN_A_BALLOON("cloud_in_a_balloon", CloudInABalloon::new), // 云朵气球
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
