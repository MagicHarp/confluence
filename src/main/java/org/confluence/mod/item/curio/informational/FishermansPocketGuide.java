package org.confluence.mod.item.curio.informational;

import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.ModRarity;

public class FishermansPocketGuide extends AbstractInfoCurio implements IFishermansPocketGuide, CustomName {
    public FishermansPocketGuide() {
        super(ModRarity.BLUE);
    }

    @Override
    public String getName() {
        return "Fisherman's Pocket Guide";
    }
}
