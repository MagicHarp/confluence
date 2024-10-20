package org.confluence.mod.common.event.game;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.confluence.mod.Confluence;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class GameEvents {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {

    }

//    @SubscribeEvent
//    public static void curios(CurioEquipEvent event) {
//
//    }

//    @SubscribeEvent
//    public static void onRegisterTeamData(RegisterTeamDataEvent event) {
//
//    }

    @SubscribeEvent
    public static void itemAttributeModifier(ItemAttributeModifierEvent event) {

    }

    @SubscribeEvent
    public static void itemStackedOnOther(ItemStackedOnOtherEvent event) {

    }

//    @SubscribeEvent
//    public static void shimmerTransmutation$post(ShimmerItemTransmutationEvent.Post event) {
//
//    }
}
