package org.confluence.mod.item.curio.movement;

import net.minecraft.network.chat.Component;

public class ShinyRedBalloon extends Balloon{
    @Override
    public Component[] getInformation() {
        return new Component[]{
            Component.translatable("item.confluence.shiny_red_balloon.info"),
            Component.translatable("item.confluence.shiny_red_balloon.info2"),
            Component.translatable("item.confluence.shiny_red_balloon.info3"),
            Component.translatable("item.confluence.shiny_red_balloon.info4"),
            Component.translatable("item.confluence.shiny_red_balloon.info5"),
            Component.translatable("item.confluence.shiny_red_balloon.info6"),
            Component.translatable("item.confluence.shiny_red_balloon.info7"),
            Component.translatable("item.confluence.shiny_red_balloon.info8"),
            Component.translatable("item.confluence.shiny_red_balloon.info9"),
            Component.translatable("item.confluence.shiny_red_balloon.info10"),
            Component.translatable("item.confluence.shiny_red_balloon.info11"),
            Component.translatable("item.confluence.shiny_red_balloon.info12")
        };
    }
}
