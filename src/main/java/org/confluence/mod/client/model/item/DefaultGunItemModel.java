package org.confluence.mod.client.model.item;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.gun.AbstractGunItem;
import software.bernie.geckolib.model.GeoModel;

public class DefaultGunItemModel<I extends AbstractGunItem> extends GeoModel<I> {
    private final ResourceLocation model;
    private final ResourceLocation texture;
    private final ResourceLocation animation;

    public DefaultGunItemModel(String id, boolean hasAnimation) {
        this.model = Confluence.asResource("geo/item/gun/" + id + ".geo.json");
        this.texture = Confluence.asResource("textures/item/gun/" + id + ".png");
        this.animation = (hasAnimation ? Confluence.asResource("animations/item/gun/" + id + ".animation.json") : null);
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
