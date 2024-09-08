package org.confluence.mod.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public final class ModTriggers {
    public static CuriosEquippedTrigger CURIOS_EQUIPPED;

    public static void initialize() {
        CURIOS_EQUIPPED = CriteriaTriggers.register(new CuriosEquippedTrigger());
    }
}
