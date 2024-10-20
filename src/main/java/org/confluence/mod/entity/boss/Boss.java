package org.confluence.mod.entity.boss;

public interface Boss {
    default boolean shouldShowMessage(){
        return true;
    }
}
