package org.confluence.mod.client.model.item;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.item.gun.AbstractGunItem;
import software.bernie.geckolib.model.GeoModel;

import static org.confluence.mod.Confluence.MODID;

public class DefaultGunItemModel<I extends AbstractGunItem> extends GeoModel<I> {
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private final ResourceLocation animation;

    public DefaultGunItemModel(String id, boolean hasAnimation) {
        this.model = new ResourceLocation(MODID, "geo/item/gun/" + id + ".geo.json");
        this.texture = new ResourceLocation(MODID, "textures/item/gun/" + id + ".png");
        this.animation = (hasAnimation ? new ResourceLocation(MODID, "animations/item/gun/" + id + ".animation.json") : null);
    }

    @Override
    public ResourceLocation getModelResource(I animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(I animatable) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(I animatable) {
        return animation;
    }
}
