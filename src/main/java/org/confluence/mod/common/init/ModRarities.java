package org.confluence.mod.common.init;

import org.confluence.mod.common.component.RarityComponent;
import org.confluence.mod.util.color.AnimateColor;

public final class ModRarities {
    public static final RarityComponent GRAY = new RarityComponent("gray", 0x828282);
    public static final RarityComponent WHITE = new RarityComponent("white", 0xFFFFFF);
    public static final RarityComponent BLUE = new RarityComponent("blue", 0x9696FF);
    public static final RarityComponent GREEN = new RarityComponent("green", 0x96FF96);
    public static final RarityComponent ORANGE = new RarityComponent("orange", 0xFFC896);
    public static final RarityComponent LIGHT_RED = new RarityComponent("light_red", 0xFF9696);
    public static final RarityComponent PINK = new RarityComponent("pink", 0xFF96FF);
    public static final RarityComponent LIGHT_PURPLE = new RarityComponent("light_purple", 0xD2A0FF);
    public static final RarityComponent LIME = new RarityComponent("lime", 0x96FF0A);
    public static final RarityComponent YELLOW = new RarityComponent("yellow", 0xFFFF0A);
    public static final RarityComponent CYAN = new RarityComponent("cyan", 0x05C8FF);
    public static final RarityComponent RED = new RarityComponent("red", 0xFF2864);
    public static final RarityComponent PURPLE = new RarityComponent("purple", 0xB428FF);

    public static final RarityComponent EXPERT = new RarityComponent("expert", 0x000000) {
        @Override
        public int color() {
            return AnimateColor.getExpertColor();
        }
    };
    public static final RarityComponent MASTER = new RarityComponent("master", 0x000000) {
        @Override
        public int color() {
            return AnimateColor.getMasterColor();
        }
    };
    public static final RarityComponent QUEST = new RarityComponent("quest", 0xFFAF00);
}
