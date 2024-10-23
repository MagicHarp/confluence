package org.confluence.mod.mixinauxi;

@SuppressWarnings("unchecked")
public interface SelfGetter<T> {
    default T self(){
        return (T) this;
    }
}
