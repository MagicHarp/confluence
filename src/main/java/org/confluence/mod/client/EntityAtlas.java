package org.confluence.mod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.Confluence;
import org.jetbrains.annotations.NotNull;

public class EntityAtlas extends TextureAtlasHolder {
    public EntityAtlas(){
        super(Minecraft.getInstance().getTextureManager(), Confluence.asResource("textures/atlas/entity.png"), Confluence.asResource("entity"));
    }

    @Override
    @NotNull
    public TextureAtlasSprite getSprite(@NotNull ResourceLocation pLocation){
        return super.getSprite(pLocation);
    }
}
