package org.confluence.mod.client;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ClientConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue PLAY_SHOES_SOUND = BUILDER.push("Speed Shoes").define("playSound", true);
    private static final ForgeConfigSpec.BooleanValue SHOW_SHOES_PARTICLE = BUILDER.define("showParticle", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean playShoesSound = true;
    public static boolean showShoesParticle = true;

    public static void onLoad() {
        playShoesSound = PLAY_SHOES_SOUND.get();
        showShoesParticle = SHOW_SHOES_PARTICLE.get();
    }
}
