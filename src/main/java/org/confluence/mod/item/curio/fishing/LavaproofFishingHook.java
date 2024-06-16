package org.confluence.mod.item.curio.fishing;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class LavaproofFishingHook extends BaseCurioItem implements ILavaproofFishingHook {
    public LavaproofFishingHook() {
        super(ModRarity.LIME);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.lavaproof_fishing_hook.info"),
                Component.translatable("item.confluence.lavaproof_fishing_hook.info2")
        };
    }
}
