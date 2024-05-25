package org.confluence.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.hook.SkeletronHandEntity;
import org.jetbrains.annotations.NotNull;

public class SkeletronHandRenderer extends AbstractHookRenderer<SkeletronHandEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/entity/hook/grappling_hook.png");
    private static final BlockState CHAIN = Blocks.CHAIN.defaultBlockState();

    public SkeletronHandRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SkeletronHandEntity pEntity) {
        return TEXTURE;
    }

    @Override
    public BlockState getChain(SkeletronHandEntity entity) {
        return CHAIN;
    }
}
