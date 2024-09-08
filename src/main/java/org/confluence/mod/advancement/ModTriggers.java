package org.confluence.mod.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public final class ModTriggers {
    public static CuriosEquippedTrigger CURIOS_EQUIPPED;
    public static ShimmerTransmutationTrigger SHIMMER_TRANSMUTATION;

    public static void initialize() {
        CURIOS_EQUIPPED = CriteriaTriggers.register(new CuriosEquippedTrigger());
        SHIMMER_TRANSMUTATION = CriteriaTriggers.register(new ShimmerTransmutationTrigger());
    }
}
