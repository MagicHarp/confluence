package org.confluence.mod.client.handler;

import de.dafuqs.revelationary.api.revelations.WorldRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.s2c.*;
import org.jetbrains.annotations.Nullable;

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
    private static boolean hasGlobe = false;

    private static ResourceLocation moonTexture = null;
    private static int moonSpecific = -1;

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

    public static boolean isHasGlobe() {
        return hasGlobe;
    }

    public static @Nullable ResourceLocation getMoonTexture() {
        return moonTexture;
    }

    public static int getMoonSpecific() {
        return moonSpecific;
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

    public static void handleGlobe(GravityGlobePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> hasGlobe = packet.has());
        context.setPacketHandled(true);
    }
}
