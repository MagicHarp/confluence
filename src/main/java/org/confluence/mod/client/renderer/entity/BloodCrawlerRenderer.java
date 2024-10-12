package org.confluence.mod.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.confluence.mod.client.model.entity.BloodCrawlerModel;
import org.confluence.mod.entity.monster.BloodCrawler;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BloodCrawlerRenderer extends GeoEntityRenderer<BloodCrawler> {
    public BloodCrawlerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BloodCrawlerModel());
    }

    @Override
    protected float getDeathMaxRotation(BloodCrawler animatable){
        return 0;
    }
}
