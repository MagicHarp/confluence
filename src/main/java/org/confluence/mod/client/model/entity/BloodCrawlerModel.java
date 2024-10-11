package org.confluence.mod.client.model.entity;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.BloodCrawler.BloodCrawler;
import software.bernie.geckolib.model.GeoModel;

public class BloodCrawlerModel extends GeoModel<BloodCrawler> {
    private static final ResourceLocation MODEL = Confluence.asResource("geo/entity/blood_crawler.geo.json");
    private static final ResourceLocation TEXTURES = Confluence.asResource("textures/entity/blood_crawler.png");
    private static final ResourceLocation ANIMATION = Confluence.asResource("animations/entity/blood_crawler.animation.json");

    @Override
    public ResourceLocation getModelResource(BloodCrawler animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(BloodCrawler animatable) {
        return TEXTURES;
    }

    @Override
    public ResourceLocation getAnimationResource(BloodCrawler animatable) {
        return ANIMATION;
    }
}
