package org.confluence.mod.item.curio.fishing;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;

public class HighTestFishingLine extends BaseCurioItem implements IHighTestFishingLine {

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.high_test_fishing_line.info"),
                Component.translatable("item.confluence.high_test_fishing_line.info2")
        };
    }
}
