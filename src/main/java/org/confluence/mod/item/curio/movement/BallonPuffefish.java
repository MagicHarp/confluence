package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;

public class BallonPuffefish extends Balloon {
    @Override
    public Component[] getInformation() {
        return new Component[]{
                Component.translatable("item.confluence.balloon_pufferfish.info"),
                Component.translatable("item.confluence.balloon_pufferfish.info2"),
                Component.translatable("item.confluence.balloon_pufferfish.info3")
        };
    }
}
