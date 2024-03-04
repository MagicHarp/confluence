package org.confluence.mod.util;

import net.minecraftforge.registries.RegistryObject;

public interface EnumRegister<E> {
    RegistryObject<E> getValue();

    default E get() {
        return getValue().get();
    }
}
