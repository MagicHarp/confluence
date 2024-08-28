package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.entity.boss.eow.EaterOfWorldPart;
import software.bernie.geckolib.model.GeoModel;

import static org.confluence.mod.Confluence.MODID;

// TODO: 暂时使用恶魔眼模型
public class EaterOfWorldSegmentModel extends GeoModel<EaterOfWorldPart> {
    private static final ResourceLocation MODEL = new ResourceLocation(MODID, "geo/entity/demon_eye.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/demon_eye/normal.png");
    private static final ResourceLocation ANIMATION = new ResourceLocation(MODID, "animations/entity/demon_eye.animation.json");

    @Override
    public ResourceLocation getModelResource(EaterOfWorldPart animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(EaterOfWorldPart animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(EaterOfWorldPart animatable) {return ANIMATION;}
}
