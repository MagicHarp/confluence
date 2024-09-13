package org.confluence.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.hook.BatHookEntity;
import org.jetbrains.annotations.NotNull;

public class BatHookRenderer extends AbstractHookRenderer<BatHookEntity> {
    private static final ResourceLocation TEXTURE = Confluence.asResource("textures/entity/hook/grappling_hook.png");
    private static final BlockState CHAIN = Blocks.CHAIN.defaultBlockState();

    public BatHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public BlockState getChain(BatHookEntity entity) {
        return CHAIN;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BatHookEntity pEntity) {
        return TEXTURE;
    }
}
