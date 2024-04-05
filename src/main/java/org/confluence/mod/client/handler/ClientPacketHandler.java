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
public class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;

    private static boolean echoBlockVisible = false;
    private static boolean showHolyWaterColor = false;
    private static boolean continueSwing = false;

    private static ResourceLocation specificMoon = null;

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
                specificMoon = null;
            } else {
                specificMoon = new ResourceLocation(MODID, "textures/environment/specific_moon_" + packet.id() + ".png");
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

    public static boolean shouldContinueSwing() {
        return continueSwing;
    }

    public static @Nullable ResourceLocation getSpecificMoon() {
        return specificMoon;
    }

    public static void handleSwing(ContinuingSwingHandPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> continueSwing = packet.swing());
        context.setPacketHandled(true);
    }
}
