package org.confluence.mod.command;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.IntFunction;

public enum SpecificMoon implements StringRepresentable {
    VANILLA(0),

    // 特殊多彩月亮
    MC_YELLOW(1),
    MC_RINGED(2),
    MC_MYTHRIL(3),
    MC_BRIGHT_BLUE(4),
    MC_GREEN(5),
    MC_PINK(6),
    MC_ORANGE(7),
    MC_PURPLE(8),
    MC_SMILEY(9),

    // 事件月亮
    MC_FROST(10), // 霜月
    MC_PUMPKIN(11), // 南瓜月
    // 血月
    MC_BLOOD_FULL_MOON(12), // 满月
    MC_BLOOD_WANING_GIBBOUS(13), // 亏凸月
    MC_BLOOD_THIRD_QUARTER(14), // 下弦月
    MC_BLOOD_WANING_CRESCENT(15), // 残月
    MC_BLOOD_NEW_MOON(16), // 新月
    MC_BLOOD_WAXING_CRESCENT(17), // 峨眉月
    MC_BLOOD_FIRST_QUARTER(18), // 上弦月
    MC_BLOOD_WAXING_GIBBOUS(19), // 盈凸月


    // TR风格
    TR_FULL_MOON(20), // 满月
    TR_WANING_GIBBOUS(21), // 亏凸月
    TR_THIRD_QUARTER(22), // 下弦月
    TR_WANING_CRESCENT(23), // 残月
    TR_NEW_MOON(24), // 新月
    TR_WAXING_CRESCENT(25), // 峨眉月
    TR_FIRST_QUARTER(26), // 上弦月
    TR_WAXING_GIBBOUS(27), // 盈凸月

    // 特殊多彩月亮
    TR_YELLOW(28),
    TR_RINGED(29),
    TR_MYTHRIL(30),
    TR_BRIGHT_BLUE(31),
    TR_GREEN(32),
    TR_PINK(33),
    TR_ORANGE(34),
    TR_PURPLE(35),
    TR_SMILEY(36),

    // 事件月亮
    TR_FROST(37), // 霜月
    TR_PUMPKIN(38), // 南瓜月
    // 血月
    TR_BLOOD_FULL_MOON(39), // 满月
    TR_BLOOD_WANING_GIBBOUS(40), // 亏凸月
    TR_BLOOD_THIRD_QUARTER(41), // 下弦月
    TR_BLOOD_WANING_CRESCENT(42), // 残月
    TR_BLOOD_NEW_MOON(43), // 新月
    TR_BLOOD_WAXING_CRESCENT(44), // 峨眉月
    TR_BLOOD_FIRST_QUARTER(45), // 上弦月
    TR_BLOOD_WAXING_GIBBOUS(46); // 盈凸月

    private static final IntFunction<SpecificMoon> BY_ID = ByIdMap.continuous(SpecificMoon::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
    private final int id;

    SpecificMoon(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SpecificMoon byId(int pId) {
        return BY_ID.apply(pId);
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public boolean isBloodyMoon() {
        return (id >= MC_BLOOD_FULL_MOON.id && id <= MC_BLOOD_WAXING_GIBBOUS.id) || (id >= TR_BLOOD_FULL_MOON.id && id <= TR_BLOOD_WAXING_GIBBOUS.id);
    }

    public static SpecificMoon getCommonMoon(RandomSource randomSource, int currentMoon) {
        boolean terraStyle = randomSource.nextBoolean();
        if (randomSource.nextFloat() < 0.04F) {
            if (terraStyle) {
                return byId(randomSource.nextInt(TR_YELLOW.id, TR_SMILEY.id + 1));
            }
            return byId(randomSource.nextInt(MC_YELLOW.id, MC_SMILEY.id + 1));
        }
        if (terraStyle) {
            return getCorrespondingMoon(VANILLA, currentMoon);
        }
        return VANILLA;
    }

    public static SpecificMoon getBloodyMoon(boolean terraStyle, int currentMoon) {
        return switch (currentMoon) {
            case 1 -> terraStyle ? TR_BLOOD_WANING_GIBBOUS : MC_BLOOD_WANING_GIBBOUS;
            case 2 -> terraStyle ? TR_BLOOD_THIRD_QUARTER : MC_BLOOD_THIRD_QUARTER;
            case 3 -> terraStyle ? TR_BLOOD_WANING_CRESCENT : MC_BLOOD_WANING_CRESCENT;
            case 4 -> terraStyle ? TR_BLOOD_NEW_MOON : MC_BLOOD_NEW_MOON;
            case 5 -> terraStyle ? TR_BLOOD_WAXING_CRESCENT : MC_BLOOD_WAXING_CRESCENT;
            case 6 -> terraStyle ? TR_BLOOD_FIRST_QUARTER : MC_BLOOD_FIRST_QUARTER;
            case 7 -> terraStyle ? TR_BLOOD_WAXING_GIBBOUS : MC_BLOOD_WAXING_GIBBOUS;
            default -> terraStyle ? TR_BLOOD_FULL_MOON : MC_BLOOD_FULL_MOON;
        };
    }

    public static SpecificMoon getCorrespondingMoon(SpecificMoon specificMoon, int currentMoon) {
        if (specificMoon == VANILLA) {
            return switch (currentMoon) {
                case 1 -> TR_WANING_GIBBOUS;
                case 2 -> TR_THIRD_QUARTER;
                case 3 -> TR_WANING_CRESCENT;
                case 4 -> TR_NEW_MOON;
                case 5 -> TR_WAXING_CRESCENT;
                case 6 -> TR_FIRST_QUARTER;
                case 7 -> TR_WAXING_GIBBOUS;
                default -> TR_FULL_MOON;
            };
        }
        if (specificMoon == MC_FROST) return TR_FROST;
        if (specificMoon == TR_FROST) return MC_FROST;
        if (specificMoon == MC_PUMPKIN) return TR_PUMPKIN;
        if (specificMoon == TR_PUMPKIN) return MC_PUMPKIN;
        int id = specificMoon.id;
        if (id >= MC_YELLOW.id && id <= MC_SMILEY.id) {
            return byId(id + (TR_YELLOW.id - MC_YELLOW.id));
        }
        if (id >= TR_YELLOW.id && id <= TR_SMILEY.id) {
            return byId(id - (TR_YELLOW.id - MC_YELLOW.id));
        }
        if (id >= MC_BLOOD_FULL_MOON.id && id <= MC_BLOOD_WAXING_GIBBOUS.id) {
            return byId(id + (TR_BLOOD_FULL_MOON.id - MC_BLOOD_FULL_MOON.id));
        }
        if (id >= TR_BLOOD_FULL_MOON.id && id <= TR_BLOOD_WAXING_GIBBOUS.id) {
            return byId(id - (TR_BLOOD_FULL_MOON.id - MC_BLOOD_FULL_MOON.id));
        }
        return specificMoon;
    }
}
