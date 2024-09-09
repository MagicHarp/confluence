package org.confluence.mod.client.connected;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface NonNullFunction<T, R> extends Function<T, R> {

    @Override
    R apply(T t);

    default <V> NonNullFunction<T, V> andThen(NonNullFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(apply(t));
    }
}
