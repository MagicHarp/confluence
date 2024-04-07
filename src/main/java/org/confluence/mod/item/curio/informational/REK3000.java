package org.confluence.mod.item.curio.informational;

import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.ModRarity;

public class REK3000 extends AbstractInfoCurio implements ILifeFormAnalyzer, IRadar, ITallyCounter, CustomName {
    public REK3000() {
        super(ModRarity.ORANGE);
    }

    @Override
    public String getName() {
        return "R.E.K.3000";
    }
}
