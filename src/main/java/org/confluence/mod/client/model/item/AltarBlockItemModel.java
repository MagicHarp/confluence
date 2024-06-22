package org.confluence.mod.client.model.item;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.block.common.AltarBlock;
import org.confluence.mod.client.model.block.AltarBlockModel;
import software.bernie.geckolib.model.GeoModel;

public class AltarBlockItemModel extends GeoModel<AltarBlock.Item> {
    @Override
    public ResourceLocation getModelResource(AltarBlock.Item animatable) {
        return AltarBlockModel.MODELS[animatable.getVariant().getId()];
    }

    @Override
    public ResourceLocation getTextureResource(AltarBlock.Item animatable) {
        return AltarBlockModel.TEXTURES[animatable.getVariant().getId()];
    }

    @Override
    public ResourceLocation getAnimationResource(AltarBlock.Item animatable) {
        return null;
    }
}
