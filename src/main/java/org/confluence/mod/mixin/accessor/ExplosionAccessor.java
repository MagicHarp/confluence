package org.confluence.mod.mixin.accessor;

import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Explosion.class)
public interface ExplosionAccessor {
    @Mutable
    @Accessor
    void setBlockInteraction(Explosion.BlockInteraction interaction);
}
