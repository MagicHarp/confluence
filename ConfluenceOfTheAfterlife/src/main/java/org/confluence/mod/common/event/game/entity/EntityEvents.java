package org.confluence.mod.common.event.game.entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModAttributes;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class EntityEvents {
    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {

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
