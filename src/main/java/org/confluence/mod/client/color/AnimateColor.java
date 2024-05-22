package org.confluence.mod.client.color;

public final class AnimateColor {
    private static int DiscoStyle = 0;
    private static int DiscoR = 255;
    private static int DiscoG = 0;
    private static int DiscoB = 0;
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

    public static int getDiscoR() {
        return DiscoR;
    }

    public static int getDiscoG() {
        return DiscoG;
    }

    public static int getDiscoB() {
        return DiscoB;
    }

    private static int mouseTextColor = 0;
    private static int mouseTextColorChange = 1;
    private static float masterColor = 1.0F;
    private static int masterColorDir = 1;

    public static void doUpdateMasterColor() {
        mouseTextColor += mouseTextColorChange;
        if (mouseTextColor == 255) {
            mouseTextColorChange = -1;
        }
        if (mouseTextColor <= 190) {
            mouseTextColorChange = 1;
        }
        masterColor += masterColorDir * 0.05F;
        if (masterColor > 1.0F) {
            masterColor = 1.0F;
            masterColorDir = -1;
        }
        if (masterColor >= 0.0F) return;
        masterColor = 0.0F;
        masterColorDir = 1;
    }

    public static int getMasterColor() {
        return (mouseTextColor << 16) + ((int) (masterColor * 190) << 8);
    }
}
