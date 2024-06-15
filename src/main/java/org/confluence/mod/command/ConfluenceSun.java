package org.confluence.mod.command;

public enum ConfluenceSun {
    SUN_1,
    SUN_2,
    SUN_3,

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

