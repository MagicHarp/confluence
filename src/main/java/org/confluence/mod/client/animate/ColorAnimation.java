package org.confluence.mod.client.animate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ColorAnimation {
    public static final Codec<ColorAnimation> CODEC = Codec.STRING.dispatch("type", ColorAnimation::getType, type -> switch (type) {
        case "master" -> MapCodec.unit(MasterColorAnimation.INSTANCE);
        case "expert" -> MapCodec.unit(ExpertColorAnimation.INSTANCE);
        default -> RecordCodecBuilder.mapCodec(instance -> instance.group(
                ColorState.CODEC.fieldOf("color").forGetter(ColorAnimation::getColorState)
        ).apply(instance, ColorAnimation::new));
    });

    protected ColorState color;

    public ColorAnimation(int red, int green, int blue) {
        this.color = new ColorState(red, green, blue);
    }

    public ColorAnimation(int color) {
        this.color = new ColorState(color);
    }

    public ColorAnimation(ColorState colorState) {
        this.color = colorState;
    }

    public int getColor() {
        return color.color();
    }

    public ColorState getColorState() {
        return color;
    }


    public void updateColor() {
    }

    public String getType() {
        return "default";
    }

    public record ColorState(int red, int green, int blue) {
        public static final Codec<ColorState> CODEC = Codec.INT.xmap(ColorState::new, color -> (color.red() << 16) + (color.green() << 8) + color.blue());

        public ColorState(int color) {
            this((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF);
        }

        // 颜色的最大值
        private static final int MAX_COLOR_VALUE = 255;
        private static final int MIN_COLOR_VALUE = 0;

        // 更新颜色的方法
        public ColorState updateColor(int deltaR, int deltaG, int deltaB) {
            return new ColorState(
                    clamp(red + deltaR, MIN_COLOR_VALUE, MAX_COLOR_VALUE),
                    clamp(green + deltaG, MIN_COLOR_VALUE, MAX_COLOR_VALUE),
                    clamp(blue + deltaB, MIN_COLOR_VALUE, MAX_COLOR_VALUE)
            );
        }

        // 限制颜色值在指定范围内
        private static int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }

        public int color() {
            return (red << 16) + (green << 8) + blue;
        }
    }
}
