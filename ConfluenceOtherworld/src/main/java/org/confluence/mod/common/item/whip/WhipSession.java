package org.confluence.mod.common.item.whip;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.common.entity.projectile.whip.WhipAttackEntity;

import java.util.HashMap;
import java.util.Map;

/// 每个玩家共用一个挥动周期，松开只停止续挥，不打断当前攻击。
@Mod.EventBusSubscriber(modid = Confluence.MODID)
public final class WhipSession {
    private static final Map<ServerPlayer, WhipSession> SESSIONS = new HashMap<>();
    private WhipAttackEntity attack;
    private ItemStack heldStack = ItemStack.EMPTY;
    private int selectedSlot;
    private boolean held;

    private WhipSession() {}

    public static void setHeld(ServerPlayer player, boolean pressed) {
        if (!pressed) {
            WhipSession session = SESSIONS.get(player);
            if (session != null) session.held = false;
            return;
        }
        if (!canSwing(player) || !(player.getMainHandItem().getItem() instanceof BaseWhipItem))
            return;
        WhipSession session = SESSIONS.computeIfAbsent(player, ignored -> new WhipSession());
        session.held = true;
        session.heldStack = player.getMainHandItem();
        session.selectedSlot = player.getInventory().selected;
        session.startSwing(player, InteractionHand.MAIN_HAND);
    }

    public static void requestSwing(ServerPlayer player, InteractionHand hand) {
        if (!canSwing(player) || !(player.getItemInHand(hand).getItem() instanceof BaseWhipItem))
            return;
        SESSIONS.computeIfAbsent(player, ignored -> new WhipSession()).startSwing(player, hand);
    }

    private void startSwing(ServerPlayer player, InteractionHand hand) {
        if (attack != null) return;
        if (player.getItemInHand(hand).getItem() instanceof BaseWhipItem whip && BaseWhipItem.swingStep(player) > 0.0F) {
            attack = whip.createAttack(player, hand);
        }
    }

    private static boolean canSwing(ServerPlayer player) {
        return player.isAlive() && !player.isRemoved() && !player.isSpectator() && player.getServer() != null
                && player.getServer().getPlayerList().getPlayer(player.getUUID()) == player;
    }

    @SubscribeEvent
    public static void tick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        var iterator = SESSIONS.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            ServerPlayer player = entry.getKey();
            WhipSession session = entry.getValue();
            if (!canSwing(player) || session.attack != null && session.attack.level() != player.level()) {
                if (session.attack != null) session.attack.discard();
                iterator.remove();
                continue;
            }
            if (session.selectedSlot != player.getInventory().selected || session.heldStack != player.getMainHandItem() || session.heldStack.isEmpty())
                session.held = false;
            if (session.attack != null && session.attack.isRemoved()) session.attack = null;
            if (session.held) session.startSwing(player, InteractionHand.MAIN_HAND);
            else if (session.attack == null) iterator.remove();
        }
    }

    @SubscribeEvent
    public static void serverStopped(ServerStoppedEvent event) {
        SESSIONS.clear();
    }
}
