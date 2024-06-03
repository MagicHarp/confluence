package org.confluence.mod.client.color;

public final class AnimateColor {
    private static int DiscoStyle = 0;
    private static int DiscoR = 255;
    private static int DiscoB = 0;
    private static int DiscoG = 0;
    private static final int update = 7;

    public static void doUpdateExpertColor() {
        if (DiscoStyle == 0) {
            DiscoG += update;
            if (DiscoG >= 255) {
                DiscoG = 255;
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
            if (DiscoB >= 255) {
                DiscoB = 255;
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
            if (DiscoR >= 255) {
                DiscoR = 255;
                ++DiscoStyle;
            }
        }
        if (DiscoStyle != 5) return;
        DiscoB -= update;
        if (DiscoB > 0) return;
        DiscoB = 0;
        DiscoStyle = 0;
    }

    public static int getExpertColor() {
        return (DiscoR << 16) + (DiscoG << 8) + DiscoB;
    }
}
