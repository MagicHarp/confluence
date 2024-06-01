package org.confluence.mod.client.renderer.item.gun;

import org.confluence.mod.client.model.item.DefaultGunItemModel;
import org.confluence.mod.item.gun.AbstractGunItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DefaultGunItemRenderer<I extends AbstractGunItem> extends GeoItemRenderer<I> {
    public DefaultGunItemRenderer(String id) {
        super(new DefaultGunItemModel<>(id, false));
    }
}
