package org.confluence.mod.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import org.confluence.mod.Confluence;
import org.jetbrains.annotations.NotNull;

public class CustomSlimeRenderer extends SlimeRenderer {
    private final ResourceLocation texture;

    public CustomSlimeRenderer(EntityRendererProvider.Context context, String path) {
        super(context);
        this.texture = new ResourceLocation(Confluence.MODID, path);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Slime slime) {
        return texture;
    }
}
