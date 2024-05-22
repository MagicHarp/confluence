package org.confluence.mod.entity.demoneye;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import org.confluence.mod.util.ModUtils;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public enum DemonEyeVariant implements StringRepresentable {
    NORMAL(0, "normal", false, 30.0, 9.0, 2),
    NORMAL_BIG(1, "normal_big", true, 34.5, 10.0, 4),
    CATARACT(2, "cataract", false, 32.5, 9.0, 4),
    CATARACT_BIG(3, "cataract_big", true, 37.0, 10.0, 4),
    SLEEPY(4, "sleepy", false, 30.0, 8.0, 2),
    SLEEPY_BIG(5, "sleepy_big", true, 33.0, 8.5, 2),
    DILATED(6, "dilated", true, 25.0, 9.0, 2),
    DILATED_SMALL(7, "dilated_small", false, 22.5, 8.0, 1),
    GREEN(8, "green", true, 30.0, 10.0, 0),
    GREEN_SMALL(9, "green_small", false, 25.5, 8.5, 0),
    PURPLE(10, "purple", false, 30.0, 7.0, 4),
    PURPLE_BIG(11, "purple_big", true, 33.0, 7.5, 4),

    OWL(12, "owl", false, 37.5, 8.0, 6),
    SPACESHIP(13, "spaceship", false, 30.0, 10.0, 4);

    public static final Codec<DemonEyeVariant> CODEC = StringRepresentable.fromEnum(DemonEyeVariant::values);
    private static final IntFunction<DemonEyeVariant> BY_ID = ByIdMap.continuous(DemonEyeVariant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
    final int id;
    private final String name;

    DemonEyeVariant(int pId, String pName, boolean big, double health, double damage, int armor) {
        this.id = pId;
        this.name = pName;
    }

    public int getId() {
        return this.id;
    }

    public static DemonEyeVariant byId(int pId) {
        return BY_ID.apply(pId);
    }

    public @NotNull String getSerializedName() {
        return this.name;
    }

    public static DemonEyeVariant random(RandomSource randomSource) {
        if (ModUtils.isHalloween()) {
            return byId(randomSource.nextBoolean() ? 12 : 13);
        }
        return byId(randomSource.nextInt(12)); // 0 ~ 11
    }
}
