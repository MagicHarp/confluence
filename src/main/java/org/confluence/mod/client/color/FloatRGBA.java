package org.confluence.mod.client.color;

import net.minecraft.util.Mth;

public record FloatRGBA(float red, float green, float blue, float alpha) {
    public static final FloatRGBA DEMON_A = new FloatRGBA(0.5F, 0.3F, 1.0F, 1.0F);
    public static final FloatRGBA DEMON_B = new FloatRGBA(1.0F, 0.3F, 0.0F, 1.0F);

    public FloatRGBA mixture(FloatRGBA another, float anotherRatio) {
        float r = Mth.clamp(red - (red - another.red) * anotherRatio, 0.0F, 1.0F);
        float g = Mth.clamp(green - (green - another.green) * anotherRatio, 0.0F, 1.0F);
        float b = Mth.clamp(blue - (blue - another.blue) * anotherRatio, 0.0F, 1.0F);
        float a = Mth.clamp(alpha - (alpha - another.alpha) * anotherRatio, 0.0F, 1.0F);
        return new FloatRGBA(r, g, b, a);
    }

    public int get() {
        return ((int) (red * 255) << 16) + ((int) (green * 255) << 8) + (int) (blue * 255);
    }
}
