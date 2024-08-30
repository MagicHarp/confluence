package org.confluence.mod.client.model.block;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.crafting.ExtractinatorBlock;
import software.bernie.geckolib.model.GeoModel;

public class ExtractinatorBlockModel extends GeoModel<ExtractinatorBlock.Entity> {
    public static final ResourceLocation MODEL = new ResourceLocation(Confluence.MODID, "geo/block/extractinator.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/block/extractinator.png");
    public static final ResourceLocation ANIMATIONS = new ResourceLocation(Confluence.MODID, "animations/block/extractinator.animation.json");

    @Override
    public ResourceLocation getModelResource(ExtractinatorBlock.Entity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ExtractinatorBlock.Entity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ExtractinatorBlock.Entity animatable) {
        return ANIMATIONS;
    }

}
