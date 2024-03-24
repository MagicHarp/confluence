package org.confluence.mod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigs {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue DROP_MONEY = BUILDER.comment("Determines if player drops money after dead").define("dropsMoney", true);
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Boolean dropsMoney;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        dropsMoney = DROP_MONEY.get();
    }
}
