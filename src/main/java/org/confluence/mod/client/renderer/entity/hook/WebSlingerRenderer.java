package org.confluence.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.model.entity.hook.WebSlingerModel;
import org.confluence.mod.entity.hook.WebSlingerEntity;
import org.jetbrains.annotations.NotNull;

public class WebSlingerRenderer extends AbstractHookRenderer<WebSlingerEntity> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/hook/web_slinger.png");
    private final BlockState CHAIN;

    public WebSlingerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new WebSlingerModel(pContext.bakeLayer(WebSlingerModel.LAYER_LOCATION)));
        this.CHAIN = ModBlocks.SILK_CHAIN.get().defaultBlockState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WebSlingerEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public BlockState getChain(WebSlingerEntity entity) {
        return CHAIN;
    }
}
