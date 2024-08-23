package org.confluence.mod.event;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.integration.apothic.ApothicHelper;
import org.confluence.mod.misc.ModAttributes;
import org.confluence.mod.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::register);
    }

    @SubscribeEvent
    public static void modify(EntityAttributeModificationEvent event) {
        if (!ApothicHelper.isAttributesLoaded()) {
            event.add(EntityType.PLAYER, ModAttributes.CRIT_CHANCE.get());
        }
    }
}
