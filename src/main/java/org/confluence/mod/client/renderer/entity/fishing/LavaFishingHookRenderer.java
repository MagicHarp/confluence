package org.confluence.mod.client.renderer.entity.fishing;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.fishing.LavaFishingHook;

public class LavaFishingHookRenderer extends EntityRenderer<LavaFishingHook> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/entity/fishing/lava.png");

    public LavaFishingHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(LavaFishingHook pEntity) {
        return null;
    }
}
