package org.confluence.mod.entity.demoneye;

import com.mojang.serialization.Codec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public enum DemonEyeVariant implements StringRepresentable {
    NORMAL(0, "normal");

    public static final Codec<DemonEyeVariant> CODEC = StringRepresentable.fromEnum(DemonEyeVariant::values);
    private static final IntFunction<DemonEyeVariant> BY_ID = ByIdMap.continuous(DemonEyeVariant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
    final int id;
    private final String name;

    DemonEyeVariant(int pId, String pName) {
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
}
