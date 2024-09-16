package org.confluence.mod.client.renderer.entity.bomb;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.bomb.BaseBombEntityModel;
import org.confluence.mod.entity.projectile.bombs.BaseBombEntity;
import org.jetbrains.annotations.NotNull;

public class BaseBombEntityRenderer extends BombEntityRenderer<BaseBombEntity> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/bomb/bomb_entity.png");
    private final BaseBombEntityModel model;

    public BaseBombEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BaseBombEntityModel(pContext.bakeLayer(BaseBombEntityModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseBombEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public EntityModel<BaseBombEntity> getModel(BaseBombEntity pEntity) {
        return model;
    }
}
