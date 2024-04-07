package org.confluence.mod.item.curio.informational;

import org.confluence.mod.datagen.limit.CustomName;
import org.confluence.mod.item.ModRarity;

public class PDA extends AbstractInfoCurio implements ICompass, IDepthMeter, IDPSMeter, IFishermansPocketGuide,
    ILifeFormAnalyzer, IMetalDetector, IRadar, ISextant, IStopwatch, ITallyCounter, IWatch, IWeatherRadio, CustomName {
    public PDA() {
        super(ModRarity.PINK);
    }

    @Override
    public String getName() {
        return "PDA";
    }
}
