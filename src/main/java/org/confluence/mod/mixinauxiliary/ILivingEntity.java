package org.confluence.mod.mixinauxiliary;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface ILivingEntity {
    void confluence$setBreakEasyCrashBlock(boolean breaking);

    boolean confluence$isBreakEasyCrashBlock();
    Object2IntMap<Immunity> confluence$getImmunityTicks();

    int c$getInvulnerableTime(int constant);
}
