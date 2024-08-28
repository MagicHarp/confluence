package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.entity.worm.AbstractWormEntity;
import software.bernie.geckolib.model.GeoModel;

import static org.confluence.mod.Confluence.MODID;

// 对于所有蠕虫的总实体通用（因为它们不应该向客户端显示）
// TODO: 暂时使用恶魔眼模型
public class WormOverallModel extends GeoModel<AbstractWormEntity> {
    private static final ResourceLocation MODEL = new ResourceLocation(MODID, "geo/entity/demon_eye.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/demon_eye/normal.png");
    private static final ResourceLocation ANIMATION = new ResourceLocation(MODID, "animations/entity/demon_eye.animation.json");

    @Override
    public ResourceLocation getModelResource(AbstractWormEntity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(AbstractWormEntity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(AbstractWormEntity animatable) {
        return ANIMATION;
    }
}
