package org.confluence.mod.common.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.CommonConfigs;
import org.confluence.mod.network.s2c.GamePhasePacketS2C;
import org.confluence.mod.network.s2c.ManaPacketS2C;
import org.confluence.mod.network.s2c.WindSpeedPacketS2C;
import org.confluence.mod.terra_curio.common.init.ModAttributes;

import static org.confluence.mod.Confluence.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEvents {
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CommonConfigs.onLoad();
        });
    }

    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            Confluence.registerMinecartAbility();
        });
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {

    }

    @SubscribeEvent
    public static void register(RegisterEvent event) {

    }

    @SubscribeEvent
    public static void spawnPlacementRegister(RegisterSpawnPlacementsEvent event) {

    }

    @SubscribeEvent
    public static void attributeCreate(EntityAttributeCreationEvent event) {

    }

    @SubscribeEvent
    public static void entityAttributeModification(EntityAttributeModificationEvent event) {
        ModAttributes.readJsonConfig();
        ModAttributes.registerAttribute(ModAttributes.CRIT_CHANCE, event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_VELOCITY, event::add);
        ModAttributes.registerAttribute(ModAttributes.RANGED_DAMAGE, event::add);
        ModAttributes.registerAttribute(ModAttributes.DODGE_CHANCE, event::add);
        ModAttributes.registerAttribute(ModAttributes.AGGRO, event::add);
        ModAttributes.registerAttribute(ModAttributes.MAGIC_DAMAGE, event::add);
        ModAttributes.registerAttribute(ModAttributes.ARMOR_PASS, event::add);
        ModAttributes.registerAttribute(ModAttributes.PICKUP_RANGE, event::add);
    }

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                ManaPacketS2C.TYPE,
                ManaPacketS2C.STREAM_CODEC,
                ManaPacketS2C::handle
        );
        registrar.playToClient(
                GamePhasePacketS2C.TYPE,
                GamePhasePacketS2C.STREAM_CODEC,
                GamePhasePacketS2C::handle
        );
        registrar.playToClient(
                WindSpeedPacketS2C.TYPE,
                WindSpeedPacketS2C.STREAM_CODEC,
                WindSpeedPacketS2C::handle
        );
    }
}
