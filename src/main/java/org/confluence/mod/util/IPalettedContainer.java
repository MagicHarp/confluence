package org.confluence.mod.util;

import net.minecraft.world.level.chunk.PalettedContainer;

public interface IPalettedContainer<T> {
    PalettedContainer<T> confluence$recreateSingle(T ele);
}
