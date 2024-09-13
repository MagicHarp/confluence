package org.confluence.mod.client.model.block;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.furniture.chair.WhitePlasticChairBlock;
import software.bernie.geckolib.model.GeoModel;

public class WhitePlasticChairBlockModel extends GeoModel<WhitePlasticChairBlock.Entity> {
    public static final ResourceLocation MODEL = Confluence.asResource("geo/block/white_plastic_chair.geo.json");
    public static final ResourceLocation TEXTURE = Confluence.asResource("textures/block/white_plastic_chair.png");

    @Override
    public ResourceLocation getModelResource(WhitePlasticChairBlock.Entity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(WhitePlasticChairBlock.Entity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(WhitePlasticChairBlock.Entity animatable) {
        return null;
    }
}
