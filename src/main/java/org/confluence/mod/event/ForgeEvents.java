package org.confluence.mod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.Confluence;
import org.confluence.mod.mana.ManaProvider;
import org.confluence.mod.mana.ManaStorage;
import org.confluence.mod.util.PlayerUtils;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Confluence.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (player.getCapability(ManaProvider.MANA_CAPABILITY).isPresent()) return;
            event.addCapability(new ResourceLocation(Confluence.MODID, "mana"), new ManaProvider());
        }
    }

    public static Random RANDOM;

    @SubscribeEvent
    public static void beforeServerStarting(ServerAboutToStartEvent event) {
        RANDOM = new Random(event.getServer().getWorldData().worldGenOptions().seed());
    }

    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerUtils.syncMana2Client(serverPlayer);
            PlayerUtils.syncAdvancements(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.CLIENT || event.phase == TickEvent.Phase.START) return;

        ServerPlayer serverPlayer = (ServerPlayer) event.player;
        PlayerUtils.regenerateMana(serverPlayer);
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

    @SubscribeEvent
    public static void livingHurt(LivingHurtEvent event) {
        event.setAmount(event.getAmount() * RANDOM.nextFloat(0.8F, 1.25F));
    }
}
