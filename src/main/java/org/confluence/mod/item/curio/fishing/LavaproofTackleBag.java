package org.confluence.mod.item.curio.fishing;

import net.minecraft.network.chat.Component;
import org.confluence.mod.misc.ModRarity;

public class LavaproofTackleBag extends AnglerTackleBag implements ILavaproofFishingHook {
    public LavaproofTackleBag() {
        super(ModRarity.YELLOW);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.lavaproof_tackle_bag.info"),
            Component.translatable("item.confluence.lavaproof_tackle_bag.info2"),
            Component.translatable("item.confluence.lavaproof_tackle_bag.info3"),
            Component.translatable("item.confluence.lavaproof_tackle_bag.info4")
        };
    }
}
