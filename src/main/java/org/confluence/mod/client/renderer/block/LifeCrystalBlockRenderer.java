package org.confluence.mod.client.renderer.block;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.confluence.mod.block.common.LifeCrystalBlock;
import org.confluence.mod.client.model.block.LifeCrystalBlockModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LifeCrystalBlockRenderer extends GeoBlockRenderer<LifeCrystalBlock.Entity> {
    public LifeCrystalBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(new LifeCrystalBlockModel());
    }
}
