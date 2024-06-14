package org.confluence.mod.item.curio.informational;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public interface IWeatherRadio {
    static Component getInfo(Player localPlayer, String speed) {
        Level level = localPlayer.level();
        String weather = level.dimension() == Level.OVERWORLD ? "clear" : "cloudy";
        if (level.isRaining()) {
            if (level.getBiome(localPlayer.getOnPos()).is(Tags.Biomes.IS_COLD)) {
                weather = "snow";
            } else {
                weather = "rain";
            }
        } else if (level.isThundering()) {
            weather = "thunder";
        }
        return Component.translatable("info.confluence.weather_radio." + weather, speed);
    }

    Component TOOLTIP = Component.translatable("curios.tooltip.weather_radio");
    byte INDEX = 1;
}
