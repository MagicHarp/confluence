package org.confluence.mod.client.model.block;

import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.LifeCrystalBlock;
import software.bernie.geckolib.model.GeoModel;

public class LifeCrystalBlockModel extends GeoModel<LifeCrystalBlock.Entity> {
    public static final ResourceLocation MODEL = new ResourceLocation(Confluence.MODID, "geo/block/life_crystal_block.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/block/life_crystal_block.png");

    @Override
    public ResourceLocation getModelResource(LifeCrystalBlock.Entity animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(LifeCrystalBlock.Entity animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(LifeCrystalBlock.Entity animatable) {
        return null;
    }
}
