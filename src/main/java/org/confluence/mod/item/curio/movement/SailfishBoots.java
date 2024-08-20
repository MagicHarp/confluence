package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;
import org.confluence.mod.client.color.FloatRGB;
import org.joml.Vector3f;

public class SailfishBoots extends BaseSpeedBoots {
    @Override
    public Vector3f getParticleColorStart() {
        return FloatRGB.fromInteger(0x46579f).toVector();
    }

    @Override
    public Vector3f getParticleColorEnd() {
        return FloatRGB.fromInteger(0x46579f).toVector();
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.sailfish_boots.info"),
            Component.translatable("item.confluence.sailfish_boots.info2"),
            Component.translatable("item.confluence.sailfish_boots.info3"),
            Component.translatable("item.confluence.sailfish_boots.info4"),
            Component.translatable("item.confluence.sailfish_boots.info5"),
            Component.translatable("item.confluence.sailfish_boots.info6"),
        };
    }
}
