package org.confluence.mod.mixinauxiliary;

@SuppressWarnings("unchecked")
public interface SelfGetter<T> {
    default T self(){
        return (T) this;
    }
}
