package org.confluence.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.client.model.entity.hook.SkeletronHandModel;
import org.confluence.mod.entity.hook.SkeletronHandEntity;
import org.jetbrains.annotations.NotNull;

public class SkeletronHandRenderer extends AbstractHookRenderer<SkeletronHandEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Confluence.MODID, "textures/entity/hook/skeletron_hand.png");
    private final BlockState CHAIN;

    public SkeletronHandRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SkeletronHandModel(pContext.bakeLayer(SkeletronHandModel.LAYER_LOCATION)));
        this.CHAIN = ModBlocks.BONE_CHAIN.get().defaultBlockState();
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
