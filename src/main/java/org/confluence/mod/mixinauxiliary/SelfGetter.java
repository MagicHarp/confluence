package org.confluence.mod.mixinauxiliary;

public interface SelfGetter<T> {
    default T self(){
        return (T) this;
    }
}
