package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;

public class ShinyRedBalloon extends Balloon {
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.shiny_red_balloon.info"),
        };
    }
}
