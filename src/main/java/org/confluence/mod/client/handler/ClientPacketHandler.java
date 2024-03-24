package org.confluence.mod.client.handler;

import de.dafuqs.revelationary.api.revelations.WorldRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.EchoBlockVisibilityPacketS2C;
import org.confluence.mod.network.HolyWaterColorUpdatePacketS2C;
import org.confluence.mod.network.ManaPacketS2C;
import org.confluence.mod.network.MechanicalBlockVisibilityPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ClientPacketHandler {
    private static int maxMana = 20;
    private static int currentMana = 20;
    private static boolean echoBlockVisible = false;
    private static boolean mechanicalBlockVisible = false;
    private static boolean showHolyWaterColor = false;

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

    public static void handleMechanicalBlock(MechanicalBlockVisibilityPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            mechanicalBlockVisible = packet.visible();
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

    public static int getCurrentMana() {
        return currentMana;
    }

    public static int getMaxMana() {
        return maxMana;
    }

    public static boolean isEchoBlockVisible() {
        return echoBlockVisible;
    }

    public static boolean isMechanicalBlockVisible() {
        return mechanicalBlockVisible;
    }

    public static boolean showHolyWaterColor() {
        return showHolyWaterColor;
    }
}
