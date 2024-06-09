package org.confluence.mod.client.shimmer;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import net.minecraft.world.entity.LivingEntity;
import org.confluence.mod.effect.ModEffects;
import org.confluence.mod.item.curio.CurioItems;
import org.confluence.mod.util.CuriosUtils;
import org.joml.Vector3f;

public class PlayerPointLight extends ColorPointLight {
    public PlayerPointLight(LightManager lightManager, Vector3f pos, int color) {
        super(lightManager, pos, color, 7.0F, -1, false);
    }

    public static boolean checkLight(LivingEntity living) {
        boolean curio = CuriosUtils.hasCurio(living, CurioItems.MAGILUMINESCENCE.get());
        boolean effect = living.hasEffect(ModEffects.SHINE.get());
        return curio || effect;
    }
}
