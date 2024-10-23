package org.confluence.mod.common.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public final class ModTriggers {
    public static CuriosEquippedTrigger CURIOS_EQUIPPED;
    public static ShimmerTransmutationTrigger SHIMMER_TRANSMUTATION;

    public static void initialize() {
        CURIOS_EQUIPPED = CriteriaTriggers.register(CuriosEquippedTrigger.ID.toString(), new CuriosEquippedTrigger());
        SHIMMER_TRANSMUTATION = CriteriaTriggers.register(ShimmerTransmutationTrigger.ID.toString(), new ShimmerTransmutationTrigger());
    }
}
