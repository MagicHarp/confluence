package org.confluence.mod.common.event.game.entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.confluence.mod.Confluence;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class EntityEvents {
    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {

    }
//
//    @SubscribeEvent
//    public static void entityMount(EntityMountEvent event) {
//        if (event.isMounting() || event.getLevel().isClientSide) return;
//        if (event.getEntityMounting() instanceof Player player && event.getEntityBeingMounted() instanceof AbstractMinecart abstractMinecart) {
//            Item item = Confluence.MINECART_CURIO.get(abstractMinecart.getType());
//            if (item == null) return;
//            ItemStack itemStack = new ItemStack(item);
//            if (CuriosUtils.getSlot(player, "minecart", 0).isEmpty()) {
//                CuriosApi.getCuriosInventory(player).ifPresent(inv -> inv.setEquippedCurio("minecart", 0, itemStack));
//            } else {
//                player.addItem(itemStack);
//            }
//            ((EntityAccessor) player).setVehicle(null);
//            ((EntityAccessor) abstractMinecart).callRemovePassenger(player);
//            abstractMinecart.discard();
//            event.setCanceled(true);
//        }
//    }
}
