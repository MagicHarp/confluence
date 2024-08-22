package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.entity.worm.test.TestWormPart;
import software.bernie.geckolib.model.GeoModel;

import static org.confluence.mod.Confluence.MODID;

// TODO: 暂时使用恶魔眼模型
public class TestWormSegmentModel extends GeoModel<TestWormPart> {
    private static final ResourceLocation MODEL = new ResourceLocation(MODID, "geo/entity/demon_eye.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/demon_eye/normal.png");
    private static final ResourceLocation ANIMATION = new ResourceLocation(MODID, "animations/entity/demon_eye.animation.json");

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
