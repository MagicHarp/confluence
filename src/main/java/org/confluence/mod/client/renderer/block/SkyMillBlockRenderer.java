package org.confluence.mod.client.renderer.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.confluence.mod.block.crafting.SkyMillBlock;
import org.confluence.mod.client.model.block.SkyMillBlockModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SkyMillBlockRenderer extends GeoBlockRenderer<SkyMillBlock.Entity> {
    public SkyMillBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new SkyMillBlockModel());
    }
}
