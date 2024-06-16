package org.confluence.mod.item.curio.combat;

import net.minecraft.network.chat.Component;
import org.confluence.mod.item.curio.BaseCurioItem;
import org.confluence.mod.misc.ModRarity;

public class PocketMirror extends BaseCurioItem implements EffectInvul.Stoned {
    public PocketMirror() {
        super(ModRarity.ORANGE);
    }

    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.pocket_mirror.info"),
                Component.translatable("item.confluence.pocket_mirror.info2"),
                Component.translatable("item.confluence.pocket_mirror.info3")
        };
    }
}
