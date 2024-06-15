package org.confluence.mod.command;

public enum ConfluenceMoon {
    MOON_1,
    MOON_2,
    MOON_3,

    UNKNOWN;

    public static ConfluenceMoon getById(int id) {
        for (ConfluenceMoon moon : values()) {
            if (moon.ordinal() == id) {
                return moon;
            }
        }
        return UNKNOWN;
    }
}
