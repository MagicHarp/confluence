package org.confluence.mod.client.shimmer;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import org.joml.Vector3f;

public class PlayerPointLight extends ColorPointLight {
    public PlayerPointLight(LightManager lightManager, Vector3f pos) {
        super(lightManager, pos, 0xFFFFFD55, 7.0F, -1, false);
    }
}
