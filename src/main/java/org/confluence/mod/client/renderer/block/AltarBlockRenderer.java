package org.confluence.mod.client.renderer.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.confluence.mod.block.crafting.AltarBlock;
import org.confluence.mod.client.model.block.AltarBlockModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AltarBlockRenderer extends GeoBlockRenderer<AltarBlock.Entity> {
    public AltarBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new AltarBlockModel());
    }
}
