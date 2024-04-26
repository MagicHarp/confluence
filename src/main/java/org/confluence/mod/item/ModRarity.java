package org.confluence.mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Rarity;

public final class ModRarity {
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

    public static final Rarity EXPERT = Rarity.create("expert", style -> style.withColor(0x000000));
    public static final Rarity MASTER = Rarity.create("master", style -> style.withColor(0x000000));
    public static final Rarity QUEST = Rarity.create("quest", style -> style.withColor(0xFFAF00));

    public static class Animate {
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

    public interface Special {
        MutableComponent withColor(String descriptionId);
    }

    public interface Expert extends Special {
        @Override
        default MutableComponent withColor(String descriptionId) {
            return Component.translatable(descriptionId).withStyle(style -> style.withColor(Animate.getExpertColor()));
        }
    }

    public interface Master extends Special {
        @Override
        default MutableComponent withColor(String descriptionId) {
            return Component.translatable(descriptionId).withStyle(style -> style.withColor(Animate.getMasterColor()));
        }
    }
}
