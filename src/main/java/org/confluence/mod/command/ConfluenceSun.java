package org.confluence.mod.command;

public enum ConfluenceSun {
    MC_SUN,
    TR_SUN,
// 日食
    MC_ECLIPSE,
    TR_ECLIPSE,

    UNKNOWN;

    public static ConfluenceSun getById(int id) {
        for (ConfluenceSun sun : values()) {
            if (sun.ordinal() == id) {
                return sun;
            }
        }
        return UNKNOWN;
    }
}

