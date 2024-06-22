package org.confluence.mod.item.curio.construction;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class BrickLayer extends BaseCurioItem implements IRightClickSubtractor {
    public BrickLayer() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.brick_layer.info"),
            Component.translatable("item.confluence.brick_layer.info2")
        };
    }
}
