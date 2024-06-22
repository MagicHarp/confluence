package org.confluence.mod.client.model.block;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.block.common.AltarBlock;
import software.bernie.geckolib.model.GeoModel;

import static org.confluence.mod.Confluence.MODID;

public class AltarBlockModel extends GeoModel<AltarBlock.Entity> {
    public static final ResourceLocation[] MODELS = new ResourceLocation[]{
        new ResourceLocation(MODID, "geo/block/demon_altar.geo.json"),
        new ResourceLocation(MODID, "geo/block/crimson_altar.geo.json")
    };
    public static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/block/demon_altar.png"),
        new ResourceLocation(MODID, "textures/block/crimson_altar.png")
    };
    public static final ResourceLocation[] ANIMATIONS = new ResourceLocation[]{
        new ResourceLocation(MODID, "animations/block/demon_altar.animation.json"),
        new ResourceLocation(MODID, "animations/block/crimson_altar.animation.json")
    };

    @Override
    public ResourceLocation getModelResource(AltarBlock.Entity animatable) {
        return MODELS[animatable.getVariant().getId()];
    }

    @Override
    public ResourceLocation getTextureResource(AltarBlock.Entity animatable) {
        return TEXTURES[animatable.getVariant().getId()];
    }

    @Override
    public ResourceLocation getAnimationResource(AltarBlock.Entity animatable) {
        return ANIMATIONS[animatable.getVariant().getId()];
    }
}
