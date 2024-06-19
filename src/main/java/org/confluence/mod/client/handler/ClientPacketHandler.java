package org.confluence.mod.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import org.confluence.mod.capability.ability.AbilityProvider;
import org.confluence.mod.network.s2c.AutoAttackPacketS2C;
import org.confluence.mod.network.s2c.FlushPlayerAbilityPacketS2C;
import org.confluence.mod.network.s2c.ScopeEnablePacketS2C;
import org.confluence.mod.network.s2c.ShieldOfCthulhuPacketS2C;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class ClientPacketHandler {
    private static boolean autoAttack = false;
    private static boolean hasCthulhu = false;
    private static boolean hasScope = false;

    public static boolean couldAutoAttack() {
        return autoAttack;
    }

    public static boolean isHasCthulhu() {
        return hasCthulhu;
    }

    public static boolean isHasScope() {
        return hasScope;
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

    public static void handleScope(ScopeEnablePacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> hasScope = packet.enable());
        context.setPacketHandled(true);
    }
}
