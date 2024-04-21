package org.confluence.mod.util;

import net.minecraftforge.registries.RegistryObject;

public interface EnumRegister<E> {
    RegistryObject<E> getValue();

    default E get() {
        return getValue().get();
    }

    default <T> T get(Class<T> clazz) {
        E item = get();
        if (clazz.isInstance(item)) {
            return clazz.cast(item);
        }
        throw new ClassCastException("Can not cast %s to %s".formatted(item, clazz));
    }
}
