package org.confluence.mod.mixin.chunk;

import net.minecraft.core.IdMap;
import net.minecraft.world.level.chunk.PalettedContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PalettedContainer.class)
public interface PalettedContainerAccessor<T> {
    @Accessor
    PalettedContainer.Data<T> getData();

    @Accessor
    IdMap<T> getRegistry();
}
