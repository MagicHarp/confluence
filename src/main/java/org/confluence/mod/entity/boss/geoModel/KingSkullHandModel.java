package org.confluence.mod.entity.boss.geoModel;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.boss.geoEntity.KingSkullHand;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class KingSkullHandModel extends DefaultedEntityGeoModel<KingSkullHand> {
    public KingSkullHandModel() {
        super(new ResourceLocation(Confluence.MODID, "king_skull_hand"));
    }

    // We want our model to render using the translucent render type
    @Override
    public RenderType getRenderType(KingSkullHand animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}