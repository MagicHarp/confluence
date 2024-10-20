package org.confluence.mod.common.event.game.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.*;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.init.ModAttachments;
import org.confluence.mod.common.init.ModTags;
import org.confluence.mod.util.PlayerUtils;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Confluence.MODID)
public final class PlayerEvents {
    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (PlayerUtils.isServerNotFake(player)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            PlayerUtils.syncMana2Client(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {

    }

    @SubscribeEvent
    public static void itemEntityPickup$Pre(ItemEntityPickupEvent.Pre event) {
        ItemEntity itemEntity = event.getItemEntity();
        ItemStack itemStack = itemEntity.getItem();
        Player player = event.getPlayer();
        if (itemStack.is(ModTags.Items.PROVIDE_MANA)) {
            player.getData(ModAttachments.MANA_STORAGE.get()).receiveMana(() -> itemStack.getCount() * 100);
            itemEntity.discard();
            event.setCanPickup(TriState.FALSE);
        } else if (itemStack.is(ModTags.Items.PROVIDE_LIFE)) {
            player.heal(itemStack.getCount() * 4.0F);
            itemEntity.discard();
            event.setCanPickup(TriState.FALSE);
        }
    }

    @SubscribeEvent
    public static void itemFished(ItemFishedEvent event) {

    }

    @SubscribeEvent
    public static void arrowLoose(ArrowLooseEvent event) {

    }
}
