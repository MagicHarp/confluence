package org.confluence.mod.item.curio.informational;

public interface IWatch {
    String wrapTime(long dayTime);

    static String format(long time) {
        return (time < 10 ? "0" : "") + time;
    }
}
