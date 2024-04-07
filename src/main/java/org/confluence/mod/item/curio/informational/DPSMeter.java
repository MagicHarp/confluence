package org.confluence.mod.item.curio.informational;

import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.ModRarity;

public class DPSMeter extends AbstractInfoCurio implements IDPSMeter, CustomName {
    public DPSMeter() {
        super(ModRarity.BLUE);
    }

    @Override
    public String getName() {
        return "DPS Meter";
    }
}
