package org.confluence.mod.item.curio.fishing;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;

public class TackleBox extends BaseCurioItem implements ITackleBox {

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.tackle_box.info")
        };
    }
}
