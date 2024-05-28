package org.confluence.mod.item.common;

import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.misc.ModRarity;

public class CellPhone extends MagicMirror implements ICompass, IDepthMeter, IDPSMeter, IFishermansPocketGuide,
    ILifeFormAnalyzer, IMetalDetector, IRadar, ISextant, IStopwatch, ITallyCounter, IWatch, IWeatherRadio {
    public CellPhone() {
        super(ModRarity.LIME);
    }
}
