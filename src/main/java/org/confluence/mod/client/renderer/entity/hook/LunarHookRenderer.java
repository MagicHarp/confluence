package org.confluence.mod.client.renderer.entity.hook;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.confluence.mod.block.ModBlocks;
import org.confluence.mod.entity.hook.LunarHookEntity;
import org.jetbrains.annotations.NotNull;

import static org.confluence.mod.Confluence.MODID;

public class LunarHookRenderer extends AbstractHookRenderer<LunarHookEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
        new ResourceLocation(MODID, "textures/entity/hook/amethyst_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/amber_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/sapphire_hook.png"),
        new ResourceLocation(MODID, "textures/entity/hook/emerald_hook.png")
    };
    private final BlockState[] CHAINS;

    public LunarHookRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.CHAINS = new BlockState[]{
            ModBlocks.AMETHYST_CHAIN.get().defaultBlockState(),
            ModBlocks.AMBER_CHAIN.get().defaultBlockState(),
            ModBlocks.SAPPHIRE_CHAIN.get().defaultBlockState(),
            ModBlocks.EMERALD_CHAIN.get().defaultBlockState()
        };
    }

    @Override
    public BlockState getChain(LunarHookEntity entity) {
        return CHAINS[entity.getVariant().getId()];
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(LunarHookEntity pEntity) {
        return TEXTURES[pEntity.getVariant().getId()];
    }
}
