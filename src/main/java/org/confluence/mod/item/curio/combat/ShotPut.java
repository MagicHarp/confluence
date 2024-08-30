package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class ShotPut extends BaseCurioItem implements EffectInvul.Levitation {
    public ShotPut() {
        super(ModRarity.LIGHT_RED);
    }

    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.shot_put.info")
        };
    }
}
