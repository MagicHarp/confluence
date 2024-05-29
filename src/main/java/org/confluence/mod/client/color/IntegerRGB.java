package org.confluence.mod.client.color;

public record IntegerRGB(int red, int green, int blue) {
    public static final IntegerRGB HALLOW_A = IntegerRGB.of(0xFF0051);
    public static final IntegerRGB HALLOW_B = IntegerRGB.of(0x12FFE2);
    public static final IntegerRGB HALLOW_C = IntegerRGB.of(0xFFFA00);
    public static final IntegerRGB BLACK = IntegerRGB.of(0x000000);
    public static final IntegerRGB GRAY = IntegerRGB.of(0x828282);
    public static final IntegerRGB WHITE = IntegerRGB.of(0xFFFFFF);
    public static final IntegerRGB BLUE = IntegerRGB.of(0x9696FF);
    public static final IntegerRGB GREEN = IntegerRGB.of(0x96FF96);
    public static final IntegerRGB ORANGE = IntegerRGB.of(0x96FF96);
    public static final IntegerRGB LIGHT_RED = IntegerRGB.of(0xFF9696);
    public static final IntegerRGB PINK = IntegerRGB.of(0xFF96FF);
    public static final IntegerRGB LIGHT_PURPLE = IntegerRGB.of(0xD2A0FF);
    public static final IntegerRGB LIME = IntegerRGB.of(0x96FF0A);
    public static final IntegerRGB YELLOW = IntegerRGB.of(0xFFFF0A);
    public static final IntegerRGB CYAN = IntegerRGB.of(0x05C8FF);
    public static final IntegerRGB RED = IntegerRGB.of(0xFF2864);
    public static final IntegerRGB PURPLE = IntegerRGB.of(0xB428FF);

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
