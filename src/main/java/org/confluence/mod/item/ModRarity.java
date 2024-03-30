package org.confluence.mod.item;

import net.minecraft.world.item.Rarity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModRarity {
    public static final Rarity GRAY = Rarity.create("gray", style -> style.withColor(0x828282));
    public static final Rarity WHITE = Rarity.create("white", style -> style.withColor(0xFFFFFF));
    public static final Rarity BLUE = Rarity.create("blue", style -> style.withColor(0x9696FF));
    public static final Rarity GREEN = Rarity.create("green", style -> style.withColor(0x96FF96));
    public static final Rarity ORANGE = Rarity.create("orange", style -> style.withColor(0x96FF96));
    public static final Rarity LIGHT_RED = Rarity.create("light_red", style -> style.withColor(0xFF9696));
    public static final Rarity PINK = Rarity.create("pink", style -> style.withColor(0xFF96FF));
    public static final Rarity LIGHT_PURPLE = Rarity.create("light_purple", style -> style.withColor(0xD2A0FF));
    public static final Rarity LIME = Rarity.create("lime", style -> style.withColor(0x96FF0A));
    public static final Rarity YELLOW = Rarity.create("yellow", style -> style.withColor(0xFFFF0A));
    public static final Rarity CYAN = Rarity.create("cyan", style -> style.withColor(0x05C8FF));
    public static final Rarity RED = Rarity.create("red", style -> style.withColor(0xFF2864));
    public static final Rarity PURPLE = Rarity.create("purple", style -> style.withColor(0xB428FF));

    public static final Rarity QUEST = Rarity.create("quest", style -> style.withColor(0xFFAF00));

    @OnlyIn(Dist.CLIENT)
    public static class Animate {
        public static int DiscoStyle = 0;
        public static int DiscoR = Byte.MAX_VALUE;
        public static int DiscoB = 0;
        public static int DiscoG = 0;

        private static final int update = 7;

        public static void DoUpdate_AnimateDiscoRGB() {
            if (DiscoStyle == 0) {
                DiscoG += update;
                if (DiscoG >= Byte.MAX_VALUE) {
                    DiscoG = Byte.MAX_VALUE;
                    ++DiscoStyle;
                }
            }
            if (DiscoStyle == 1) {
                DiscoR -= update;
                if (DiscoR <= 0) {
                    DiscoR = 0;
                    ++DiscoStyle;
                }
            }
            if (DiscoStyle == 2) {
                DiscoB += update;
                if (DiscoB >= Byte.MAX_VALUE) {
                    DiscoB = Byte.MAX_VALUE;
                    ++DiscoStyle;
                }
            }
            if (DiscoStyle == 3) {
                DiscoG -= update;
                if (DiscoG <= 0) {
                    DiscoG = 0;
                    ++DiscoStyle;
                }
            }
            if (DiscoStyle == 4) {
                DiscoR += update;
                if (DiscoR >= Byte.MAX_VALUE) {
                    DiscoR = Byte.MAX_VALUE;
                    ++DiscoStyle;
                }
            }
            if (DiscoStyle != 5) return;
            DiscoB -= update;
            if (DiscoB > 0) return;
            DiscoB = 0;
            DiscoStyle = 0;
        }

        public static void DoUpdate_AnimateCursorColors() {
        }

        public static int getDiscoColor() {
            return ((DiscoR + 128) << 16) + ((DiscoG + 128) << 8) + (DiscoB + 128);
        }
    }
}
