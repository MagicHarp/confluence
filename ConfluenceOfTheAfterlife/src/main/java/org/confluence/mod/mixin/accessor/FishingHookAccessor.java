package org.confluence.mod.mixin.accessor;

import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FishingHook.class)
public interface FishingHookAccessor {
    @Accessor
    int getLuck();

    @Accessor
    int getLureSpeed();
}
