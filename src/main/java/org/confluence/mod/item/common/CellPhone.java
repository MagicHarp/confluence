package org.confluence.mod.item.common;

import org.confluence.mod.item.ModRarity;
import org.confluence.mod.item.curio.informational.*;

public class CellPhone extends MagicMirror implements ICompass, IDepthMeter, IDPSMeter, IFishermansPocketGuide,
    ILifeFormAnalyzer, IMetalDetector, IRadar, ISextant, IStopwatch, ITallyCounter, IWatch, IWeatherRadio {
    public CellPhone() {
        super(ModRarity.LIME);
    }
}
