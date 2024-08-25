package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import org.confluence.mod.client.color.FloatRGB;
import org.joml.Vector3f;

public class FlurryBoots extends BaseSpeedBoots {
    private static final Vector3f START_COLOR = FloatRGB.fromInteger(0x1cb9fc).toVector();
    private static final Vector3f END_COLOR = FloatRGB.fromInteger(0x1cb9fc).toVector();

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.flurry_boots.info"),
            Component.translatable("item.confluence.flurry_boots.info2"),
            Component.translatable("item.confluence.flurry_boots.info3"),
            Component.translatable("item.confluence.flurry_boots.info4")
        };
    }

    @Override
    public Vector3f getParticleColorStart() {
        return START_COLOR;
    }

    @Override
    public Vector3f getParticleColorEnd() {
        return END_COLOR;
    }
}
