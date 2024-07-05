package org.confluence.mod.util;

import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public interface EnumRegister<E> extends Supplier<E> {
    RegistryObject<E> getValue();

    default E get() {
        return getValue().get();
    }

    default <T extends E> T get(Class<T> clazz) {
        E item = getValue().get();
        if (clazz.isInstance(item)) return clazz.cast(item);
        throw new ClassCastException("Can not cast %s to %s".formatted(getValue().getId(), clazz));
    }
}
