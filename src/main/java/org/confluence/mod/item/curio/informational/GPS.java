package org.confluence.mod.item.curio.informational;

import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.ModRarity;

public class GPS extends AbstractInfoCurio implements IWatch,IDepthMeter,ICompass, CustomName {
    public GPS() {
        super(ModRarity.ORANGE);
    }

    @Override
    public String getName() {
        return "GPS";
    }
}
