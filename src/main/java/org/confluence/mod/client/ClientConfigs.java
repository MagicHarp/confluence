package org.confluence.mod.client;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public final class ClientConfigs {
    public static boolean terraStyleHealth = true;
    private static ForgeConfigSpec.BooleanValue TERRA_STYLE_HEALTH;

    public static void onClientLoad() {
        terraStyleHealth = TERRA_STYLE_HEALTH.get();
    }

    public static void registerClient() {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        TERRA_STYLE_HEALTH = BUILDER.push("HUD").define("drawVanillaHealth", true);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BUILDER.build());
    }
}
