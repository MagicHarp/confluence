package org.confluence.mod.client;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ClientConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue PLAY_SHOES_SOUND = BUILDER.push("Speed Shoes").define("playSound", true);
    private static final ForgeConfigSpec.BooleanValue SHOW_SHOES_PARTICLE = BUILDER.define("showParticle", true);
    private static final ForgeConfigSpec.DoubleValue INFORMATION_HUD_TOP = BUILDER.pop().push("Information HUD").comment("finalTop = screenHeight * top").defineInRange("top", 0.5, 0.0, 1.0);
    private static final ForgeConfigSpec.BooleanValue INFORMATION_HUD_LEFT = BUILDER.comment("left or right").define("isLeft", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean playShoesSound = true;
    public static boolean showShoesParticle = true;
    public static double informationHudTop = 0.5;
    public static boolean informationIsLeft = false;

    public static void onLoad() {
        playShoesSound = PLAY_SHOES_SOUND.get();
        showShoesParticle = SHOW_SHOES_PARTICLE.get();
        informationHudTop = INFORMATION_HUD_TOP.get();
        informationIsLeft = INFORMATION_HUD_LEFT.get();
    }
}
