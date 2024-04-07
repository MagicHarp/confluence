package org.confluence.mod.item.curio.informational;

import org.confluence.mod.item.ModRarity;

public class FishFinder extends AbstractInfoCurio implements IFishermansPocketGuide, IWeatherRadio, ISextant {
    public FishFinder() {
        super(ModRarity.ORANGE);
    }
}
