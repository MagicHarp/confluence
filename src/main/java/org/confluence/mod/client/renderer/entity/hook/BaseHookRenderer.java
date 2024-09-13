package org.confluence.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.Confluence;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.entity.hook.BaseHookEntity;
import org.jetbrains.annotations.NotNull;

public class BaseHookRenderer extends AbstractHookRenderer<BaseHookEntity> {
    private static final ResourceLocation[] TEXTURE = new ResourceLocation[]{
        Confluence.asResource("textures/entity/hook/grappling_hook.png"),
        Confluence.asResource("textures/entity/hook/amethyst_hook.png"),
        Confluence.asResource("textures/entity/hook/topaz_hook.png"),
        Confluence.asResource("textures/entity/hook/sapphire_hook.png"),
        Confluence.asResource("textures/entity/hook/emerald_hook.png"),
        Confluence.asResource("textures/entity/hook/ruby_hook.png"),
        Confluence.asResource("textures/entity/hook/amber_hook.png"),
        Confluence.asResource("textures/entity/hook/diamond_hook.png")
    };
    private final BlockState[] CHAINS;

    public BaseHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.CHAINS = new BlockState[]{
            Blocks.CHAIN.defaultBlockState(),
            ModBlocks.AMETHYST_CHAIN.get().defaultBlockState(),
            ModBlocks.TOPAZ_CHAIN.get().defaultBlockState(),
            ModBlocks.SAPPHIRE_CHAIN.get().defaultBlockState(),
            ModBlocks.EMERALD_CHAIN.get().defaultBlockState(),
            ModBlocks.RUBY_CHAIN.get().defaultBlockState(),
            ModBlocks.AMBER_CHAIN.get().defaultBlockState(),
            ModBlocks.DIAMOND_CHAIN.get().defaultBlockState()
        };
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BaseHookEntity pEntity) {
        return TEXTURE[pEntity.getVariant().getId()];
    }

    @Override
    public BlockState getChain(BaseHookEntity entity) {
        return CHAINS[entity.getVariant().getId()];
    }
}
