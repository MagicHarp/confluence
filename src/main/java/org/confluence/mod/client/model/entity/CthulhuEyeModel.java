package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.boss.CthulhuEye;
import software.bernie.geckolib.model.GeoModel;

public class CthulhuEyeModel extends GeoModel<CthulhuEye> {
    private static final ResourceLocation MODEL = Confluence.asResource("geo/entity/eye_of_cthulhu.geo.json");
    private static final ResourceLocation TEXTURES = Confluence.asResource("textures/entity/eye_of_cthulhu.png");
    private static final ResourceLocation ANIMATION = Confluence.asResource("animations/entity/eye_of_cthulhu.animation.json");

    @Override
    public ResourceLocation getModelResource(CthulhuEye animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(CthulhuEye animatable) {
        return TEXTURES;
    }

    @Override
    public ResourceLocation getAnimationResource(CthulhuEye animatable) {
        return ANIMATION;
    }
}
