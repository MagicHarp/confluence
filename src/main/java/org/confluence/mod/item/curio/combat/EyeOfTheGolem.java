package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class EyeOfTheGolem extends BaseCurioItem implements ICriticalHit {
    public EyeOfTheGolem() {
        super(ModRarity.LIME);
    }

    @Override
    public double getChance() {
        return 0.1;
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.eye_of_the_golem.info"),
            Component.translatable("item.confluence.eye_of_the_golem.info2")
        };
    }
}
