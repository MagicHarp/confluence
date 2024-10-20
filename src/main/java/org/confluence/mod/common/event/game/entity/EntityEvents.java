package org.confluence.mod.common.event.game.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.capability.ManaStorage;
import org.confluence.mod.mixin.accessor.EntityAccessor;
import org.confluence.mod.util.CuriosUtils;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class EntityEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        if (!event.isEntityRegistered(ManaStorage.CAP, EntityType.PLAYER)) {
            event.registerEntity(ManaStorage.CAP, EntityType.PLAYER, new ManaStorage.Provider());
        }
    }

    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {

    }

    @SubscribeEvent
    public static void entityMount(EntityMountEvent event) {
        if (event.isMounting() || event.getLevel().isClientSide) return;
        if (event.getEntityMounting() instanceof Player player && event.getEntityBeingMounted() instanceof AbstractMinecart abstractMinecart) {
            Item item = Confluence.MINECART_CURIO.get(abstractMinecart.getType());
            if (item == null) return;
            ItemStack itemStack = new ItemStack(item);
            if (CuriosUtils.getSlot(player, "minecart", 0).isEmpty()) {
                CuriosApi.getCuriosInventory(player).ifPresent(inv -> inv.setEquippedCurio("minecart", 0, itemStack));
            } else {
                player.addItem(itemStack);
            }
            ((EntityAccessor) player).setVehicle(null);
            ((EntityAccessor) abstractMinecart).callRemovePassenger(player);
            abstractMinecart.discard();
            event.setCanceled(true);
        }
    }
}
