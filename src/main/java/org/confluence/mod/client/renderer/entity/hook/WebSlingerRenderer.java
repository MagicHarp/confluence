package org.confluence.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.entity.hook.WebSlingerEntity;
import org.jetbrains.annotations.NotNull;

public class WebSlingerRenderer extends AbstractHookRenderer<WebSlingerEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/entity/hook/grappling_hook.png");
    private final BlockState CHAIN;

    public WebSlingerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
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
