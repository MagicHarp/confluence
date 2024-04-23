package org.confluence.mod.capability.prefix;

import org.confluence.mod.item.ModPrefix;

public enum PrefixType {
    UNIVERSAL(new Enum[][]{ModPrefix.Universal.values()}),
    MELEE(new Enum[][]{ModPrefix.Common.values(), ModPrefix.Melee.values()}),
    RANGED(new Enum[][]{ModPrefix.Common.values(), ModPrefix.Ranged.values()}),
    MAGIC_AND_SUMMING(new Enum[][]{ModPrefix.Common.values(), ModPrefix.MagicAndSummoning.values()}),
    CURIO(new Enum[][]{ModPrefix.Curio.values()});

    public final Enum<? extends ModPrefix>[][] available;

    PrefixType(Enum<? extends ModPrefix>[][] available) {
        this.available = available;
    }
}
