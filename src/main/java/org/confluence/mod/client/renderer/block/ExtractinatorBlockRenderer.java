package org.confluence.mod.client.renderer.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.confluence.mod.block.common.ExtractinatorBlock;
import org.confluence.mod.client.model.block.ExtractinatorBlockModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ExtractinatorBlockRenderer extends GeoBlockRenderer<ExtractinatorBlock.Entity> {
    public ExtractinatorBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new ExtractinatorBlockModel());
    }
}
