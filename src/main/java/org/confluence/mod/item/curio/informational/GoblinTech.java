package org.confluence.mod.item.curio.informational;

import org.confluence.mod.item.ModRarity;

public class GoblinTech extends AbstractInfoCurio implements IMetalDetector, IStopwatch, IDPSMeter {
    public GoblinTech() {
        super(ModRarity.ORANGE);
    }
}
