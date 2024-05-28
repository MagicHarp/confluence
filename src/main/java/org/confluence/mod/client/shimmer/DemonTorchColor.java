package org.confluence.mod.client.shimmer;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import org.confluence.mod.client.color.FloatRGB;

public class DemonTorchColor extends ColorPointLight.Template {
    private static float demonTorch = 0.0F;
    private static float demonTorchDir = 1.0F;
    public static final DemonTorchColor INSTANCE = new DemonTorchColor();

    public DemonTorchColor() {
        super(14.0F, 0.0F, 0.0F, 0.0F, 1.0F);
    }

    public void update() {
        demonTorch += demonTorchDir * 0.01F;
        if (demonTorch > 1.0F) {
            demonTorch = 1.0F;
            demonTorchDir = -1.0F;
        }
        if (demonTorch < 0.0F) {
            demonTorch = 0.0F;
            demonTorchDir = 1.0F;
        }
        FloatRGB color = FloatRGB.DEMON_A.mixture(FloatRGB.DEMON_B, demonTorch);
        this.r = color.red();
        this.g = color.green();
        this.b = color.blue();
    }
}
