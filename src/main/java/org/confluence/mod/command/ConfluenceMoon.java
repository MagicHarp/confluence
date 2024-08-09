package org.confluence.mod.command;

public enum ConfluenceMoon {
    // MC风格
    MC_FULL_MOON,// 满月
    MC_WANING_GIBBOUS,// 亏凸月
    MC_THIRD_QUARTER,// 下弦月
    MC_WANING_CRESCENT,// 残月
    MC_NEW_MOON,// 新月
    MC_WAXING_CRESCENT,// 峨眉月
    MC_FIRST_QUARTER,// 上弦月
    MC_WAXING_GIBBOUS,// 盈凸月

    // 特殊多彩月亮
    MC_YELLOW,
    MC_RINGED,
    MC_MYTHRIL,
    MC_BRIGHT_BLUE,
    MC_GREEN,
    MC_PINK,
    MC_ORANGE,
    MC_PURPLE,
    MC_SMILEY,

    // 事件月亮
    MC_FROST, // 霜月
    MC_PUMPKIN, // 南瓜月
    // 血月
    MC_BLOOD_FULL_MOON,// 满月
    MC_BLOOD_WANING_GIBBOUS,// 亏凸月
    MC_BLOOD_THIRD_QUARTER,// 下弦月
    MC_BLOOD_WANING_CRESCENT,// 残月
    MC_BLOOD_NEW_MOON,// 新月
    MC_BLOOD_WAXING_CRESCENT,// 峨眉月
    MC_BLOOD_FIRST_QUARTER,// 上弦月
    MC_BLOOD_WAXING_GIBBOUS,// 盈凸月


    // TR风格
    TR_FULL_MOON,// 满月
    TR_WANING_GIBBOUS,// 亏凸月
    TR_THIRD_QUARTER,// 下弦月
    TR_WANING_CRESCENT,// 残月
    TR_NEW_MOON,// 新月
    TR_WAXING_CRESCENT,// 峨眉月
    TR_FIRST_QUARTER,// 上弦月
    TR_WAXING_GIBBOUS,// 盈凸月

    // 特殊多彩月亮
    TR_YELLOW,
    TR_RINGED,
    TR_MYTHRIL,
    TR_BRIGHT_BLUE,
    TR_GREEN,
    TR_PINK,
    TR_ORANGE,
    TR_PURPLE,
    TR_SMILEY,

    // 事件月亮
    TR_FROST, // 霜月
    TR_PUMPKIN, // 南瓜月
    // 血月
    TR_BLOOD_FULL_MOON,// 满月
    TR_BLOOD_WANING_GIBBOUS,// 亏凸月
    TR_BLOOD_THIRD_QUARTER,// 下弦月
    TR_BLOOD_WANING_CRESCENT,// 残月
    TR_BLOOD_NEW_MOON,// 新月
    TR_BLOOD_WAXING_CRESCENT,// 峨眉月
    TR_BLOOD_FIRST_QUARTER,// 上弦月
    TR_BLOOD_WAXING_GIBBOUS,// 盈凸月

    UNKNOWN;

    public static ConfluenceMoon getById(int id) {
        for (ConfluenceMoon moon : values()) {
            if (moon.ordinal() == id) {
                return moon;
            }
        }
        return UNKNOWN;
    }
}
