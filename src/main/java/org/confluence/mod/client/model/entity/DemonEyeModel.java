package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.demoneye.DemonEye;
import software.bernie.geckolib.model.GeoModel;

public class DemonEyeModel extends GeoModel<DemonEye> {
    private static final ResourceLocation MODEL = Confluence.asResource("geo/entity/demon_eye.geo.json");
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            Confluence.asResource("textures/entity/demon_eye/normal.png"),
            Confluence.asResource("textures/entity/demon_eye/cataract.png"),
            Confluence.asResource("textures/entity/demon_eye/sleepy.png"),
            Confluence.asResource("textures/entity/demon_eye/dilated.png"),
            Confluence.asResource("textures/entity/demon_eye/green.png"),
            Confluence.asResource("textures/entity/demon_eye/purple.png"),
            Confluence.asResource("textures/entity/demon_eye/owl.png"),
            Confluence.asResource("textures/entity/demon_eye/spaceship.png")
    };
    private static final ResourceLocation ANIMATION = Confluence.asResource("animations/entity/demon_eye.animation.json");

    @Override
    public ResourceLocation getModelResource(DemonEye animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(DemonEye animatable) {
        return TEXTURES[animatable.getVariant().getTextureIndex()];
    }

    @Override
    public ResourceLocation getAnimationResource(DemonEye animatable) {
        return ANIMATION;
    }
}
