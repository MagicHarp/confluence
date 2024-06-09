package org.confluence.mod.client.handler;

import com.lowdragmc.shimmer.client.light.ColorPointLight;
import com.lowdragmc.shimmer.client.light.LightManager;
import de.dafuqs.revelationary.api.revelations.WorldRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.client.shimmer.PlayerPointLight;
import org.confluence.mod.command.GamePhase;
import org.confluence.mod.network.s2c.*;
import org.confluence.mod.util.IEntity;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static org.confluence.mod.Confluence.MODID;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;

    private static boolean echoBlockVisible = false;
    private static boolean showHolyWaterColor = false;
    private static boolean autoAttack = false;
    private static boolean hasCthulhu = false;

    private static ResourceLocation moonTexture = null;
    private static int moonSpecific = -1;
    private static GamePhase gamePhase;

    public static void handleMana(ManaPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            maxMana = packet.maxMana();
            currentMana = packet.currentMana();
        });
        context.setPacketHandled(true);
    }

    public static void handleEchoBlock(EchoBlockVisibilityPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            echoBlockVisible = packet.visible();
            ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).rebuildAllChunks();
        });
        context.setPacketHandled(true);
    }

    public static void handleHolyWater(HolyWaterColorUpdatePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            showHolyWaterColor = packet.show();
            ((WorldRendererAccessor) Minecraft.getInstance().levelRenderer).rebuildAllChunks();
        });
        context.setPacketHandled(true);
    }

    public static void handleSpecificMoon(SpecificMoonPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (packet.id() < 0) {
                moonSpecific = -1;
                moonTexture = null;
            } else {
                moonSpecific = packet.id();
                moonTexture = new ResourceLocation(MODID, "textures/environment/specific_moon_" + moonSpecific + ".png");
            }
        });
        context.setPacketHandled(true);
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

    public static boolean showHolyWaterColor() {
        return showHolyWaterColor;
    }

    public static boolean couldAutoAttack() {
        return autoAttack;
    }

    public static boolean isHasCthulhu() {
        return hasCthulhu;
    }

    public static @Nullable ResourceLocation getMoonTexture() {
        return moonTexture;
    }

    public static int getMoonSpecific() {
        return moonSpecific;
    }

    public static GamePhase getGamePhase() {
        return gamePhase;
    }

    public static boolean isHardcore() {
        return gamePhase.ordinal() > 1;
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

    public static void handleFlush(FlushPlayerAbilityPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            if (packet.flush()) {
                LocalPlayer localPlayer = Minecraft.getInstance().player;
                if (localPlayer != null) localPlayer.getCapability(AbilityProvider.CAPABILITY)
                    .ifPresent(playerAbility -> playerAbility.flushAbility(localPlayer));
            }
        });
        context.setPacketHandled(true);
    }

    public static void handleRemoteRot(BroadcastGravitationRotPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) return;
            Entity entity = localPlayer.level().getEntity(packet.entityId());
            if (entity != null) {
                ((IEntity) entity).c$setShouldRot(packet.enabled());
            }
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
                if (player == null) return;
                if (packet.enable()) {
                    PlayerPointLight light = new PlayerPointLight(LightManager.INSTANCE, player.position().toVector3f(), packet.color());
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
}
