package org.confluence.mod.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.confluence.mod.client.model.entity.DemonEyeModel;
import org.confluence.mod.entity.demoneye.DemonEye;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DemonEyeRenderer extends GeoEntityRenderer<DemonEye> {
    public DemonEyeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DemonEyeModel());
    }
}
