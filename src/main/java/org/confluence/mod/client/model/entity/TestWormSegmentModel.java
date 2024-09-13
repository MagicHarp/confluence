package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.worm.TestWormPart;
import software.bernie.geckolib.model.GeoModel;

// TODO: 暂时使用恶魔眼模型
public class TestWormSegmentModel extends GeoModel<TestWormPart> {
    private static final ResourceLocation MODEL = Confluence.asResource("geo/entity/demon_eye.geo.json");
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/demon_eye/normal.png");
    private static final ResourceLocation ANIMATION = Confluence.asResource("animations/entity/demon_eye.animation.json");

    @Override
    public ResourceLocation getModelResource(TestWormPart animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(TestWormPart animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(TestWormPart animatable) {
        return ANIMATION;
    }
}
