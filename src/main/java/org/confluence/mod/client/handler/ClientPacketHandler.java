package org.confluence.mod.client.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.network.s2c.AutoAttackPacketS2C;
import org.confluence.mod.network.s2c.GravityGlobePacketS2C;
import org.confluence.mod.network.s2c.ShieldOfCthulhuPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandler {
    private static boolean autoAttack = false;
    private static boolean hasCthulhu = false;
    private static boolean hasGlobe = false;

    public static boolean couldAutoAttack() {
        return autoAttack;
    }

    public static boolean isHasCthulhu() {
        return hasCthulhu;
    }

    public static boolean isHasGlobe() {
        return hasGlobe;
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
