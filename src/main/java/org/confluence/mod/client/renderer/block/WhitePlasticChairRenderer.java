package org.confluence.mod.client.renderer.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.confluence.mod.block.furniture.chair.WhitePlasticChairBlock;
import org.confluence.mod.client.model.block.WhitePlasticChairBlockModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class WhitePlasticChairRenderer extends GeoBlockRenderer<WhitePlasticChairBlock.Entity> {
    public WhitePlasticChairRenderer(BlockEntityRendererProvider.Context context) {
        super(new WhitePlasticChairBlockModel());
    }
}
