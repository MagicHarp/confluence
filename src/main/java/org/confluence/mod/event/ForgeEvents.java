package org.confluence.mod.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.confluence.mod.Confluence;
import org.confluence.mod.mana.ManaProvider;
import org.confluence.mod.mana.ManaStorage;
import org.confluence.mod.network.ManaPacketS2C;
import org.confluence.mod.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void serverStarting(ServerStartingEvent event) {
    }

    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        /* 根据游戏规则(terraGamePhase)为每位进入游戏的玩家进行成就同步 */
    }

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(Confluence.MODID, "mana_capability"), new ManaProvider());
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerPlayer serverPlayer = (ServerPlayer) event.player;
        serverPlayer.getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(manaStorage -> {
            if (manaStorage.receiveMana(() -> manaStorage.getCurrentMana() * 5 / manaStorage.getMaxMana()) > 0) {
                NetworkHandler.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> serverPlayer),
                    new ManaPacketS2C(manaStorage.getStars(), manaStorage.getCurrentMana())
                );

                serverPlayer.sendSystemMessage(Component.literal("Server: " + manaStorage.getCurrentMana()));
            }
        });
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        LazyOptional<ManaStorage> manaStorageLazyOptional = event.getOriginal().getCapability(ManaProvider.MANA_CAPABILITY);
        manaStorageLazyOptional.ifPresent(old -> {
            event.getEntity().getCapability(ManaProvider.MANA_CAPABILITY).ifPresent(neo -> neo.copyFrom(old));
        });
        manaStorageLazyOptional.invalidate();
    }
}
