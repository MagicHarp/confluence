package org.confluence.mod.mixinauxiliary;

public interface IEntity {
    int confluence$getCthulhuSprintingTime();

    void confluence$setCthulhuSprintingTime(int amount);

    default boolean confluence$isOnCthulhuSprinting() {
        return confluence$getCthulhuSprintingTime() > 20;
    }

    void confluence$setShouldRot(boolean bool);

    boolean confluence$isShouldRot();

    void confluence$entity_setCoolDown(int ticks);

    void confluence$setOriginalNoGravity(boolean bool);

    boolean confluence$isInShimmer();

    byte HAD_SETUP = -1;
    byte HAS_GRAVITY = 0;
    byte NO_GRAVITY = 1;
}
