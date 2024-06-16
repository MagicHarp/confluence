package org.confluence.mod.util;

public interface IEntity {
    int c$getCthulhuSprintingTime();

    void c$setCthulhuSprintingTime(int amount);

    default boolean c$isOnCthulhuSprinting() {
        return c$getCthulhuSprintingTime() > 20;
    }

    void c$setShouldRot(boolean bool);

    boolean c$isShouldRot();

    void c$e_setCoolDown(int ticks);

    void c$setOriginalNoGravity(boolean bool);
}
