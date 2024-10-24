package org.confluence.mod.common;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class CommonConfigs {
    public static ModConfigSpec.BooleanValue DROP_MONEY;

    public static void onLoad() {

    }

    public static void register(ModContainer container) {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        DROP_MONEY = BUILDER
                .comment("Determines if entity drops money after death")
                .define("dropsMoney", true);
        container.registerConfig(ModConfig.Type.COMMON, BUILDER.build());
    }
}
