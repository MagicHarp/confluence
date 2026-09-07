package org.confluence.mod.client.entity.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.confluence.mod.common.entity.monster.BaseWormMonster;

/// 普通蠕虫头部渲染器。头部朝向由服务端的实际三维位移确定。
public class WormHeadRenderer<T extends BaseWormMonster> extends GeoNormalRenderer<T> {
    public WormHeadRenderer(EntityRendererProvider.Context context, ResourceLocation path, float scale) {
        super(context, path, true, scale, 0.0F);
    }

    @Override
    protected boolean usesInterpolatedLight(T worm) {
        return true;
    }
}
