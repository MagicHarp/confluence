package org.confluence.mod.common.event.game;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.api.event.ShimmerItemTransmutationEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class GameEvents {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {

    }

//    @SubscribeEvent
//    public static void onRegisterTeamData(RegisterTeamDataEvent event) {
//        ModEntities.ENTITIES.getEntries().forEach(entry -> {
//            EntityType<?> entityType = entry.get();
//            event.registerTeamData(entityType, data -> data.setCanTeam(false));
//        });
//    }

    @SubscribeEvent
    public static void itemAttributeModifier(ItemAttributeModifierEvent event) {

    }

    @SubscribeEvent
    public static void itemStackedOnOther(ItemStackedOnOtherEvent event) {

    }

    @SubscribeEvent
    public static void shimmerTransmutation$Post(ShimmerItemTransmutationEvent.Post event) {
//        if (ConfluenceData.get((ServerLevel) event.getSource().level()).isGraduated()) {
//            ItemStack itemStack = event.getSource().getItem();
//            Item item = itemStack.getItem();
//            if (item == ModItems.BOTTOMLESS_WATER_BUCKET.get()) {
//                event.setShrink(1);
//                event.setTargets(Collections.singletonList(new ItemStack(ModItems.BOTTOMLESS_SHIMMER_BUCKET.get())));
//            } else if (item == ModItems.BOTTOMLESS_SHIMMER_BUCKET.get()) {
//                event.setShrink(1);
//                event.setTargets(Collections.singletonList(new ItemStack(ModItems.BOTTOMLESS_WATER_BUCKET.get())));
//            }
//        }
    }
}
