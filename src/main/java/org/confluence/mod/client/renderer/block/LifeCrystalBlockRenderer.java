package org.confluence.mod.client.renderer.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.common.LifeCrystalBlock;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LifeCrystalBlockRenderer extends GeoBlockRenderer<LifeCrystalBlock.Entity> {
    public LifeCrystalBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new GeoModel<>() {
            private static final ResourceLocation MODEL = new ResourceLocation(Confluence.MODID, "geo/block/life_crystal_block.geo.json");
            private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/block/life_crystal_block.png");

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
        });
    }
}
