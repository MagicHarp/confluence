package org.confluence.mod.mixin.accessor;

import net.minecraft.world.level.storage.loot.LootParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootParams.class)
public interface LootParamsAccessor {
    @Mutable
    @Accessor
    void setLuck(float luck);
}
