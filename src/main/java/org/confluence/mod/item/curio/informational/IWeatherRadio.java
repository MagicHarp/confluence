package org.confluence.mod.item.curio.informational;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public interface IWeatherRadio {
    static Component getInfo(LocalPlayer localPlayer, float x, float y) {
        Level level = localPlayer.level();
        String weather = level.dimensionType().hasFixedTime() ? "clear" : "cloudy";
        if (level.isRaining()) {
            if (level.getBiome(localPlayer.getOnPos()).is(Tags.Biomes.IS_COLD)) {
                weather = "snow";
            } else {
                weather = "rain";
            }
        } else if (level.isThundering()) {
            weather = "thunder";
        }
        return Component.translatable(
            "info.confluence.weather_radio." + weather,
            "%.2f".formatted(Mth.length(x, y))
        );
    }
}
