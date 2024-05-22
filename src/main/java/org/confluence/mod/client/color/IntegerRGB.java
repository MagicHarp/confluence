package org.confluence.mod.client.color;

public record IntegerRGB(int red, int green, int blue) {
    public static final IntegerRGB HALLOW_A = IntegerRGB.of(0xFF0051);
    public static final IntegerRGB HALLOW_B = IntegerRGB.of(0x12FFE2);
    public static final IntegerRGB HALLOW_C = IntegerRGB.of(0xFFFA00);

    public static IntegerRGB of(int rgb) {
        return new IntegerRGB((rgb & 0xFF0000) >> 16, (rgb & 0x00FF00) >> 8, rgb & 0x0000FF);
    }

    public IntegerRGB mixture(IntegerRGB another, float anotherRatio) {
        int r = Math.round(red - (red - another.red) * anotherRatio);
        int g = Math.round(green - (green - another.green) * anotherRatio);
        int b = Math.round(blue - (blue - another.blue) * anotherRatio);
        return new IntegerRGB(r, g, b);
    }

    public int get() {
        return (red << 16) + (green << 8) + blue;
    }
}
