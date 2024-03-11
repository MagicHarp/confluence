package org.confluence.mod.client.renderer;

public record Color(int red, int green, int blue) {
    public static final Color HALLOW_A = Color.of(0xFF0051);
    public static final Color HALLOW_B = Color.of(0x12FFE2);
    public static final Color HALLOW_C = Color.of(0xFFFA00);

    public static Color of(int rgb) {
        return new Color((rgb & 0xFF0000) >> 16, (rgb & 0x00FF00) >> 8, rgb & 0x0000FF);
    }

    public Color mixture(Color another, float anotherPercent) {
        int r = Math.round(this.red - (this.red - another.red) * anotherPercent);
        int g = Math.round(this.green - (this.green - another.green) * anotherPercent);
        int b = Math.round(this.blue - (this.blue - another.blue) * anotherPercent);
        return new Color(r, g, b);
    }

    public int get() {
        return (red << 16) + (green << 8) + blue;
    }
}
