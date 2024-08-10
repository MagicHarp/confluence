package org.confluence.mod.mixin.accessor;

import net.minecraft.server.level.DistanceManager;
import net.minecraft.server.level.TickingTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DistanceManager.class)
public interface DistanceManagerAccessor {
    @Accessor
    TickingTracker getTickingTicketsTracker();
}
