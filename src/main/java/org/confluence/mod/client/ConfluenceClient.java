package org.confluence.mod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.confluence.mod.Conflunce;

@Mod.EventBusSubscriber(modid = Conflunce.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ConfluenceClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
    }
}
