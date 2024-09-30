package org.confluence.mod.entity.boss.geoModel;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.boss.TerraBossBase;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GeoNormalModel<T extends TerraBossBase> extends DefaultedEntityGeoModel<T> {
    public GeoNormalModel(String path) {
        super(new ResourceLocation(Confluence.MODID, path));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}