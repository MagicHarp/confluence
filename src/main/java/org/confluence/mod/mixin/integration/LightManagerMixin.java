package org.confluence.mod.mixin.integration;

import com.lowdragmc.shimmer.client.light.LightManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LightManager.class, remap = false)
public abstract class LightManagerMixin {
}
