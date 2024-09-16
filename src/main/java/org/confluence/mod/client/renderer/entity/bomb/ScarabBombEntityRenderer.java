package org.confluence.mod.client.renderer.entity.bomb;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.confluence.mod.client.model.entity.bomb.ScarabBombEntityModel;
import org.confluence.mod.entity.projectile.bombs.ScarabBombEntity;
import org.jetbrains.annotations.NotNull;

public class ScarabBombEntityRenderer extends BombEntityRenderer<ScarabBombEntity> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/bomb/scarab_bomb_entity.png");
    private final ScarabBombEntityModel model;

    public ScarabBombEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new ScarabBombEntityModel(pContext.bakeLayer(ScarabBombEntityModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ScarabBombEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public EntityModel<ScarabBombEntity> getModel(ScarabBombEntity pEntity) {
        return model;
    }
}
