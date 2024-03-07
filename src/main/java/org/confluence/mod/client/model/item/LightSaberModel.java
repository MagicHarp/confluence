package org.confluence.mod.client.model.item;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.sword.LightSaber;
import software.bernie.geckolib.model.GeoModel;

public class LightSaberModel extends GeoModel<LightSaber> {
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private final ResourceLocation animation;

    public LightSaberModel(String color) {
        this.model = new ResourceLocation(Confluence.MODID, "geo/item/light_saber.geo.json");
        this.texture = new ResourceLocation(Confluence.MODID, "textures/item/light_saber/" + color + "_light_saber.png");
        this.animation = new ResourceLocation(Confluence.MODID, "animations/item/light_saber.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(LightSaber animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(LightSaber animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(LightSaber animatable) {
        return animation;
    }
}
