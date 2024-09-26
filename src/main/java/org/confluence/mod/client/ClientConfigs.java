package org.confluence.mod.client;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public final class ClientConfigs {
    public static boolean terraStyleHealth = true;
    public static boolean leftEffectIcon = true;
    public static boolean hurtRedOverlay = true;
    public static boolean bloodyEffect = true;
    public static boolean damageIndicator = true;
    private static ForgeConfigSpec.BooleanValue TERRA_STYLE_HEALTH;
    private static ForgeConfigSpec.BooleanValue LEFT_EFFECT_ICON;
    private static ForgeConfigSpec.BooleanValue HURT_RED_OVERLAY;

    public static void onClientLoad() {
        terraStyleHealth = TERRA_STYLE_HEALTH.get();
        leftEffectIcon = LEFT_EFFECT_ICON.get();
        hurtRedOverlay = HURT_RED_OVERLAY.get();
    }

    public static void registerClient() {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        TERRA_STYLE_HEALTH = BUILDER.push("HUD").define("drawVanillaHealth", true);
        LEFT_EFFECT_ICON = BUILDER.define("leftEffectIcon", true);
        HURT_RED_OVERLAY = BUILDER.pop().push("Entity").define("hurtRedOverlay", true);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BUILDER.build());
    }
}
