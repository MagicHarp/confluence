package org.confluence.mod.capability.prefix;

import net.minecraft.util.RandomSource;

public enum PrefixType {
    UNIVERSAL(new Enum[][]{ModPrefix.Universal.values()}),
    MELEE(new Enum[][]{ModPrefix.Common.values(), ModPrefix.Melee.values()}),
    RANGED(new Enum[][]{ModPrefix.Common.values(), ModPrefix.Ranged.values()}),
    MAGIC_AND_SUMMING(new Enum[][]{ModPrefix.Common.values(), ModPrefix.MagicAndSumming.values()}),
    CURIO(new Enum[][]{ModPrefix.Curio.values()}),
    UNKNOWN(new Enum[][]{});

    private final Enum<? extends ModPrefix>[][] available;

    PrefixType(Enum<? extends ModPrefix>[][] available) {
        this.available = available;
    }

    public Enum<? extends ModPrefix>[][] getAvailable() {
        return available;
    }

    public ModPrefix randomPrefix(RandomSource random) {
        Enum<? extends ModPrefix>[] values = available[random.nextInt(available.length)];
        return (ModPrefix) values[random.nextInt(values.length)];
    }

    public static void updatePrefix() {
        UNIVERSAL.available[0] = ModPrefix.Universal.values();
        ModPrefix.Common[] commons = ModPrefix.Common.values();
        MELEE.available[0] = commons;
        MELEE.available[1] = ModPrefix.Melee.values();
        RANGED.available[0] = commons;
        RANGED.available[1] = ModPrefix.Ranged.values();
        MAGIC_AND_SUMMING.available[0] = commons;
        MAGIC_AND_SUMMING.available[1] = ModPrefix.MagicAndSumming.values();
        CURIO.available[0] = ModPrefix.Curio.values();
    }
}
