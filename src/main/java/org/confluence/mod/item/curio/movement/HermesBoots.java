package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import org.confluence.mod.client.color.FloatRGB;
import org.joml.Vector3f;

public class HermesBoots extends BaseSpeedBoots {
    public static final Vector3f START_COLOR = FloatRGB.fromInteger(0x009409).toVector();
    public static final Vector3f END_COLOR = FloatRGB.fromInteger(0x009409).toVector();

    @Override
    public Vector3f getParticleColorStart() {
        return START_COLOR;
    }

    @Override
    public Vector3f getParticleColorEnd() {
        return END_COLOR;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.hermes_boots.info"),
            Component.translatable("item.confluence.hermes_boots.info2"),
            Component.translatable("item.confluence.hermes_boots.info3"),
            Component.translatable("item.confluence.hermes_boots.info4"),
            Component.translatable("item.confluence.hermes_boots.info5"),
            Component.translatable("item.confluence.hermes_boots.info6"),
            Component.translatable("item.confluence.hermes_boots.info7"),
            Component.translatable("item.confluence.hermes_boots.info8"),
            Component.translatable("item.confluence.hermes_boots.info9"),
            Component.translatable("item.confluence.hermes_boots.info10")
        };
    }
}
