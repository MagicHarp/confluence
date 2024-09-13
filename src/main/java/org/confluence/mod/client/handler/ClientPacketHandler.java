package org.confluence.mod.client.handler;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.Confluence;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.client.shimmer.PlayerPointLight;
import org.confluence.mod.command.GamePhase;
import org.confluence.mod.misc.ModSoundEvents;
import org.confluence.mod.mixinauxiliary.ILevelRenderer;
import org.confluence.mod.network.s2c.*;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;

    private static boolean echoBlockVisible = false;
    private static boolean autoAttack = false;
    private static boolean hasCthulhu = false;
    private static boolean hasTabi = false;
    private static boolean hasScope = false;
    private static int rightClickSubtractor = 0;

    private static ResourceLocation moonTexture = null;
    private static int moonSpecific = -1;
    private static GamePhase gamePhase;

    public static void handleMana(ManaPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxMana = packet.maxMana();
            currentMana = packet.currentMana();
            if (currentMana == maxMana && Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.playSound(ModSoundEvents.COOLDOWN_RECOVERY.get());
            }
        });
        context.setPacketHandled(true);
    }

    public static void handleEchoBlock(EchoBlockVisibilityPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            echoBlockVisible = packet.visible();
            rebuildAllChunks();
        });
        context.setPacketHandled(true);
    }

    public static void handleSpecificMoon(SpecificMoonPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (moonSpecific == 11 || packet.id() == 11) {
                rebuildAllChunks();
            }
            if (packet.id() < 0) {
                moonSpecific = -1;
                moonTexture = null;
            } else {
                moonSpecific = packet.id();
                moonTexture = Confluence.asResource("textures/environment/specific_moon_" + moonSpecific + ".png");
            }
        });
        context.setPacketHandled(true);
    }

    private static void rebuildAllChunks() {
        ((ILevelRenderer) Minecraft.getInstance().levelRenderer).confluence$rebuildAllChunks();
    }

    public static int getCurrentMana() {
        return currentMana;
    }

    public static int getMaxMana() {
        return maxMana;
    }

    public static boolean isEchoBlockVisible() {
        return echoBlockVisible;
    }

    public static boolean couldAutoAttack() {
        return autoAttack;
    }

    public static boolean isHasCthulhu() {
        return hasCthulhu;
    }

    public static boolean isHasTabi() {
        return hasTabi;
    }

    public static boolean isHasScope() {
        return hasScope;
    }

    public static int getRightClickSubtractor() {
        return rightClickSubtractor;
    }

    public static @Nullable ResourceLocation getMoonTexture() {
        return moonTexture;
    }

    public static int getMoonSpecific() {
        return moonSpecific;
    }

    public static boolean isBloodyMoon() {
        ClientLevel level = Minecraft.getInstance().level;
        return moonSpecific == 11 && level != null && level.dimension() == Level.OVERWORLD;
    }

    public static GamePhase getGamePhase() {
        return gamePhase;
    }

    public static boolean isHardcore() {
        return gamePhase.ordinal() > 1;
    }

    public static void handleSubstractor(RightClickSubtractorPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> rightClickSubtractor = packet.amount());
        context.setPacketHandled(true);
    }

    public static void handlePickupDelay(SetItemEntityPickupDelayPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level == null) return;
            if (level.getEntity(packet.id()) instanceof ItemEntity itemEntity) {
                itemEntity.setPickUpDelay(packet.delay());
            }
        });
        context.setPacketHandled(true);
    }

    public boolean isGraduated() {
        return gamePhase.ordinal() == 6;
    }

    public static void handleSwing(AutoAttackPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> autoAttack = packet.autoAttack());
        context.setPacketHandled(true);
    }

    public static void handleCthulhu(ShieldOfCthulhuPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> hasCthulhu = packet.has());
        context.setPacketHandled(true);
    }

    public static void handleTabi(TabiPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> hasTabi = packet.has());
        context.setPacketHandled(true);
    }

    public static void handleFlush(FlushPlayerAbilityPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer != null) localPlayer.getCapability(AbilityProvider.CAPABILITY)
                .ifPresent(playerAbility -> playerAbility.flushAbility(localPlayer));
        });
        context.setPacketHandled(true);
    }

    @SuppressWarnings("unchecked")
    public static void handleLight(PlayerLightPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            try {
                Field field = LightManager.class.getDeclaredField("NO_UV_LIGHT_PLAYER");
                field.setAccessible(true);
                Map<UUID, ColorPointLight> NO_UV_LIGHT_PLAYER = (Map<UUID, ColorPointLight>) field.get(LightManager.INSTANCE);
                ClientLevel level = Minecraft.getInstance().level;
                if (level == null) return;
                Player player = level.getPlayerByUUID(packet.playerUUID());
                if (player == null) {
                    NO_UV_LIGHT_PLAYER.remove(packet.playerUUID());
                } else if (packet.enable()) {
                    PlayerPointLight light = new PlayerPointLight(LightManager.INSTANCE, player.position().toVector3f(), 0xFFFFFD55);
                    NO_UV_LIGHT_PLAYER.put(packet.playerUUID(), light);
                } else {
                    NO_UV_LIGHT_PLAYER.remove(packet.playerUUID());
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
        context.setPacketHandled(true);
    }

    public static void handleGamePhase(GamePhasePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> gamePhase = packet.gamePhase());
        context.setPacketHandled(true);
    }

    public static void handleScope(ScopeEnablePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> hasScope = packet.enable());
        context.setPacketHandled(true);
    }
}
