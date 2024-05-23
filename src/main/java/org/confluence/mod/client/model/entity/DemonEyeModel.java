package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.entity.demoneye.DemonEye;
import software.bernie.geckolib.model.GeoModel;

import static org.confluence.mod.Confluence.MODID;

public class DemonEyeModel extends GeoModel<DemonEye> {
    private static final ResourceLocation MODEL = new ResourceLocation(MODID, "geo/entity/demon_eye.geo.json");
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/demon_eye/normal.png"),
        new ResourceLocation(MODID, "textures/entity/demon_eye/cataract.png"),
        new ResourceLocation(MODID, "textures/entity/demon_eye/sleepy.png"),
        new ResourceLocation(MODID, "textures/entity/demon_eye/dilated.png"),
        new ResourceLocation(MODID, "textures/entity/demon_eye/green.png"),
        new ResourceLocation(MODID, "textures/entity/demon_eye/purple.png"),
        new ResourceLocation(MODID, "textures/entity/demon_eye/owl.png"),
        new ResourceLocation(MODID, "textures/entity/demon_eye/spaceship.png")
    };
    private static final ResourceLocation ANIMATION = new ResourceLocation(MODID, "animations/entity/demon_eye.animation.json");

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
