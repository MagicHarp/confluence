package org.confluence.mod.entity.boss.geoModel;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.boss.geoEntity.KingSkull;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class KingSkullModel extends DefaultedEntityGeoModel<KingSkull> {
    public KingSkullModel() {
        super(new ResourceLocation(Confluence.MODID, "king_skull"));
    }

    // We want our model to render using the translucent render type
    @Override
    public RenderType getRenderType(KingSkull animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}