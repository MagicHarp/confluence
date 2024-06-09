package org.confluence.mod.client.shimmer;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import org.confluence.mod.client.color.AnimateColor;

public class RainbowTorchColor extends ColorPointLight.Template {
    public static final RainbowTorchColor INSTANCE = new RainbowTorchColor();

    public RainbowTorchColor() {
        super(14.0F, 0.0F, 0.0F, 0.0F, 0.4F);
    }

    public void update() {
        this.r = AnimateColor.getDiscoR() / 255.0F;
        this.g = AnimateColor.getDiscoG() / 255.0F;
        this.b = AnimateColor.getDiscoB() / 255.0F;
    }
}
